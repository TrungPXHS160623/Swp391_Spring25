/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dao;

import Context.DBContext;
import Dto.CartContactDto;
import Dto.CartDetailDto;
import Dto.CartDetailDto2;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * DAO for handling checkout contact information and order processing
 */
public class CartContactDao {
    private static final Logger LOGGER = Logger.getLogger(CartContactDao.class.getName());
    
    /**
     * Fetches product details for the specified product IDs
     * Used to display selected products on the contact page
     */
    public List<CartDetailDto> getSelectedProducts(List<Integer> productIds, int cartId) {
        List<CartDetailDto> selectedProducts = new ArrayList<>();
        
        if (productIds == null || productIds.isEmpty()) {
            return selectedProducts;
        }
        
        // Create placeholder for SQL IN clause
        StringBuilder placeholders = new StringBuilder();
        for (int i = 0; i < productIds.size(); i++) {
            placeholders.append("?");
            if (i < productIds.size() - 1) {
                placeholders.append(",");
            }
        }
        
        String sql = "SELECT ci.cart_item_id, ci.cart_id, ci.product_id, ci.quantity, " +
                     "p.product_name, p.price, p.discount_price, " +
                     "pi.image_url FROM Cart_Items ci " +
                     "JOIN Products p ON ci.product_id = p.product_id " +
                     "LEFT JOIN ProductImages pi ON p.product_id = pi.product_id AND pi.is_primary = 1 " +
                     "WHERE ci.cart_id = ? AND ci.product_id IN (" + placeholders.toString() + ")";
        
        try (Connection conn = new DBContext().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, cartId);
            // Set product IDs in the prepared statement
            for (int i = 0; i < productIds.size(); i++) {
                stmt.setInt(i + 2, productIds.get(i));
            }
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    CartDetailDto item = new CartDetailDto();
                    item.setCartItemId(rs.getInt("cart_item_id"));
                    item.setCartId(rs.getInt("cart_id"));
                    item.setProductId(rs.getInt("product_id"));
                    item.setProductName(rs.getString("product_name"));
                    item.setQuantity(rs.getInt("quantity"));
                    item.setPrice(rs.getDouble("price"));
                    item.setDiscountPrice(rs.getDouble("discount_price"));
                    item.setImageUrl(rs.getString("image_url"));
                    
                    item.setTotalPrice(); // Calculate total price
                    selectedProducts.add(item);
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error querying selected products", e);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Database connection error", e);
        }
        
        return selectedProducts;
    }
    
    /**
     * Creates a new order based on customer information and selected products
     * Returns the new order ID if successful, or -1 if failed
     */
    public int createOrder(int userId, List<Integer> productIds, double totalAmount, CartContactDto contactInfo) {
        Connection conn = null;
        PreparedStatement orderStmt = null;
        PreparedStatement itemStmt = null;
        PreparedStatement shippingStmt = null;
        ResultSet generatedKeys = null;
        int orderId = -1;

        try {
            conn = new DBContext().getConnection();
            conn.setAutoCommit(false);
            
            // 1. Insert the order with customer information
            String orderSql = "INSERT INTO Orders (user_id, order_status, total_amount, created_at, updated_at, " +
                             "fullname, gender, email, phone, address, notes) " +
                             "VALUES (?, 'Pending', ?, GETDATE(), GETDATE(), ?, ?, ?, ?, ?, ?)";
            
            orderStmt = conn.prepareStatement(orderSql, Statement.RETURN_GENERATED_KEYS);
            orderStmt.setInt(1, userId);
            orderStmt.setDouble(2, totalAmount);
            orderStmt.setString(3, contactInfo.getFullName());
            orderStmt.setString(4, contactInfo.getGender());
            orderStmt.setString(5, contactInfo.getEmail());
            orderStmt.setString(6, contactInfo.getPhone());
            orderStmt.setString(7, contactInfo.getAddress());
            orderStmt.setString(8, contactInfo.getNotes());
            orderStmt.executeUpdate();
            
            generatedKeys = orderStmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                orderId = generatedKeys.getInt(1);
                
                // Get cart ID for this user
                CartDetailDao cartDetailDao = new CartDetailDao();
                List<CartDetailDto> cartItems = cartDetailDao.getCartDetails(userId);
                
                if (!cartItems.isEmpty()) {
                    int cartId = cartItems.get(0).getCartId();
                    
                    // 2. Insert order items
                    String itemSql = "INSERT INTO Order_Items (order_id, product_id, quantity, price, subtotal) " +
                                    "SELECT ?, ci.product_id, ci.quantity, " +
                                    "CASE WHEN p.discount_price > 0 THEN p.discount_price ELSE p.price END, " +
                                    "CASE WHEN p.discount_price > 0 THEN p.discount_price * ci.quantity ELSE p.price * ci.quantity END " +
                                    "FROM Cart_Items ci " +
                                    "JOIN Products p ON ci.product_id = p.product_id " +
                                    "WHERE ci.cart_id = ? AND ci.product_id = ?";
                    
                    itemStmt = conn.prepareStatement(itemSql);
                    
                    for (Integer productId : productIds) {
                        itemStmt.setInt(1, orderId);
                        itemStmt.setInt(2, cartId);
                        itemStmt.setInt(3, productId);
                        itemStmt.addBatch();
                    }
                    itemStmt.executeBatch();
                    
                    // 3. Create shipping entry
                    String shippingSql = "INSERT INTO Shipping (order_id, shipping_address, shipping_status, estimated_delivery) " +
                                        "VALUES (?, ?, 'Pending', DATEADD(day, 5, GETDATE()))";
                    
                    shippingStmt = conn.prepareStatement(shippingSql);
                    shippingStmt.setInt(1, orderId);
                    shippingStmt.setString(2, contactInfo.getAddress());
                    shippingStmt.executeUpdate();
                    
                    // 4. Remove items from cart
                    this.removeFromCart(conn, cartId, productIds);
                    
                    // Commit transaction
                    conn.commit();
                    
                    return orderId;
                }
            }
            
            conn.rollback();
            return -1;
            
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error creating order: " + e.getMessage(), e);
            try {
                if (conn != null) {
                    conn.rollback();
                }
            } catch (SQLException se) {
                LOGGER.log(Level.SEVERE, "Error rolling back transaction", se);
            }
            return -1;
        } finally {
            try {
                if (generatedKeys != null) generatedKeys.close();
                if (itemStmt != null) itemStmt.close();
                if (orderStmt != null) orderStmt.close();
                if (shippingStmt != null) shippingStmt.close();
                if (conn != null) {
                    conn.setAutoCommit(true);
                    conn.close();
                }
            } catch (SQLException e) {
                LOGGER.log(Level.SEVERE, "Error closing resources", e);
            }
        }
    }
    
    /**
     * Removes items from cart after successful order
     */
    private void removeFromCart(Connection conn, int cartId, List<Integer> productIds) throws SQLException {
        if (productIds == null || productIds.isEmpty()) {
            return;
        }
        
        // Create placeholder for SQL IN clause
        StringBuilder placeholders = new StringBuilder();
        for (int i = 0; i < productIds.size(); i++) {
            placeholders.append("?");
            if (i < productIds.size() - 1) {
                placeholders.append(",");
            }
        }
        
        String sql = "DELETE FROM Cart_Items WHERE cart_id = ? AND product_id IN (" + placeholders.toString() + ")";
        
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, cartId);
            // Set product IDs in the prepared statement
            for (int i = 0; i < productIds.size(); i++) {
                stmt.setInt(i + 2, productIds.get(i));
            }
            
            stmt.executeUpdate();
        }
    }
    
    /**
     * Gets order details by order ID
     */
    public List<CartDetailDto2> getOrderDetails(int orderId) {
        List<CartDetailDto2> orderDetails = new ArrayList<>();
        
        String sql = "SELECT oi.order_id, oi.product_id, oi.quantity, oi.price, oi.subtotal, " +
                     "p.product_name, pi.image_url FROM Order_Items oi " +
                     "JOIN Products p ON oi.product_id = p.product_id " +
                     "LEFT JOIN ProductImages pi ON p.product_id = pi.product_id AND pi.is_primary = 1 " +
                     "WHERE oi.order_id = ?";
        
        try (Connection conn = new DBContext().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, orderId);
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    CartDetailDto2 item = new CartDetailDto2();
                    item.setOrderId(rs.getInt("order_id"));
                    item.setProductId(rs.getInt("product_id"));
                    item.setProductName(rs.getString("product_name"));
                    item.setQuantity(rs.getInt("quantity"));
                    item.setPrice(rs.getDouble("price"));
                    item.setImageUrl(rs.getString("image_url"));
                    item.setTotalPrice(rs.getDouble("subtotal"));
                    
                    orderDetails.add(item);
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error querying order details", e);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Database connection error", e);
        }
        
        return orderDetails;
    }
}

package Dao;

import Context.DBContext;
import Dto.CartDetailDto;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * DAO cho chi tiết giỏ hàng
 */
public class CartDetailDao {

    private static final Logger LOGGER = Logger.getLogger(CartDetailDao.class.getName());

    // Lấy danh sách sản phẩm trong giỏ hàng của một user_id
    public List<CartDetailDto> getCartDetails(int userId) {
        String sql = "SELECT ci.cart_item_id, ci.cart_id, ci.product_id, ci.quantity, "
                + "p.product_name, p.price, p.discount_price, "
                + "pi.image_url FROM Cart_Items ci "
                + "JOIN Cart c ON ci.cart_id = c.cart_id "
                + "JOIN Products p ON ci.product_id = p.product_id "
                + "LEFT JOIN ProductImages pi ON p.product_id = pi.product_id AND pi.is_primary = 1 "
                + "WHERE c.user_id = ?";

        List<CartDetailDto> cartDetails = new ArrayList<>();

        try (Connection conn = new DBContext().getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    CartDetailDto item = new CartDetailDto(
                            rs.getInt("cart_item_id"),
                            rs.getInt("cart_id"),
                            rs.getInt("product_id"),
                            rs.getString("product_name"),
                            rs.getString("image_url"),
                            rs.getInt("quantity"),
                            rs.getDouble("price"),
                            rs.getDouble("discount_price"),
                            0.0 // Giá trị mặc định, sẽ tính lại sau
                    );

                    item.setTotalPrice(); // Gọi phương thức để tính lại totalPrice
                    cartDetails.add(item);
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Lỗi SQL khi lấy chi tiết giỏ hàng", e);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Lỗi kết nối CSDL", e);
        }
        return cartDetails;
    }
    //update số lượng
    public void updateQuantity(int cartId, int productId, int quantity) {
        String sql = "UPDATE Cart_Items SET quantity = ? WHERE cart_id = ? AND product_id = ?";
        try (Connection conn = new DBContext().getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, quantity);
            stmt.setInt(2, cartId);
            stmt.setInt(3, productId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Lỗi SQL khi lấy chi tiết giỏ hàng", e);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Lỗi kết nối CSDL", e);
        }
    }
    //thêm hoặc update cart
    public boolean addToCart(int userId, int productId) {
    String checkCartSql = "SELECT cart_id FROM Cart WHERE user_id = ?";
    String createCartSql = "INSERT INTO Cart (user_id, created_at, updated_at, status) VALUES (?, GETDATE(), GETDATE(), 'active')";
    String checkItemSql = "SELECT cart_item_id, quantity FROM Cart_Items WHERE cart_id = ? AND product_id = ?";
    String insertItemSql = "INSERT INTO Cart_Items (cart_id, product_id, quantity) VALUES (?, ?, 1)";
    String updateItemSql = "UPDATE Cart_Items SET quantity = quantity + 1 WHERE cart_item_id = ?";

    try (Connection conn = new DBContext().getConnection()) {
        int cartId = -1;

        // Kiểm tra giỏ hàng của user
        try (PreparedStatement checkCartStmt = conn.prepareStatement(checkCartSql)) {
            checkCartStmt.setInt(1, userId);
            try (ResultSet rs = checkCartStmt.executeQuery()) {
                if (rs.next()) {
                    cartId = rs.getInt("cart_id");
                }
            }
        }

        // Nếu user chưa có giỏ hàng, tạo mới
        if (cartId == -1) {
            try (PreparedStatement createCartStmt = conn.prepareStatement(createCartSql, Statement.RETURN_GENERATED_KEYS)) {
                createCartStmt.setInt(1, userId);
                createCartStmt.executeUpdate();

                try (ResultSet generatedKeys = createCartStmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        cartId = generatedKeys.getInt(1);
                    }
                }
            }
        }

        if (cartId == -1) {
            LOGGER.log(Level.SEVERE, "Không thể tạo giỏ hàng cho user " + userId);
            return false;
        }

        // Kiểm tra sản phẩm đã có trong giỏ hàng chưa
        try (PreparedStatement checkItemStmt = conn.prepareStatement(checkItemSql)) {
            checkItemStmt.setInt(1, cartId);
            checkItemStmt.setInt(2, productId);
            try (ResultSet rs = checkItemStmt.executeQuery()) {
                if (rs.next()) {
                    // Nếu sản phẩm đã có, tăng số lượng
                    int cartItemId = rs.getInt("cart_item_id");
                    try (PreparedStatement updateStmt = conn.prepareStatement(updateItemSql)) {
                        updateStmt.setInt(1, cartItemId);
                        updateStmt.executeUpdate();
                    }
                } else {
                    // Nếu chưa có, thêm sản phẩm mới vào giỏ hàng
                    try (PreparedStatement insertStmt = conn.prepareStatement(insertItemSql)) {
                        insertStmt.setInt(1, cartId);
                        insertStmt.setInt(2, productId);
                        insertStmt.executeUpdate();
                    }
                }
            }
        }
        return true;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Lỗi SQL khi lấy chi tiết giỏ hàng", e);
            return false;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Lỗi kết nối CSDL", e);
            return false;
        }
    }
   // lấy số lượng cart
    public int getCartItemCount(int userId) {
        String sql = "SELECT SUM(quantity) AS total FROM Cart_Items WHERE cart_id = (SELECT cart_id FROM Cart WHERE user_id = ?)";

        try (Connection conn = new DBContext().getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("total");
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Lỗi SQL khi lấy chi tiết giỏ hàng", e);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Lỗi kết nối CSDL", e);
        }
        return 0;
    }
//xoá cart
    public boolean removeFromCart(int cartItemId) {
        String sql = "DELETE FROM Cart_Items WHERE cart_item_id = ?";

        try (Connection conn = new DBContext().getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, cartItemId);
            int rowsAffected = stmt.executeUpdate();

            return rowsAffected > 0;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Lỗi SQL khi lấy chi tiết giỏ hàng", e);
            return false;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Lỗi kết nối CSDL", e);
            return false;
        }
    }
// lấy sản phẩm trong cart
    public CartDetailDto getCartItem(int cartId, int productId) {
        String query = "SELECT ci.cart_item_id, ci.cart_id, ci.product_id, ci.quantity, "
                + "p.product_name, p.price, p.discount_price, "
                + "pi.image_url FROM Cart_Items ci "
                + "JOIN Products p ON ci.product_id = p.product_id "
                + "LEFT JOIN ProductImages pi ON p.product_id = pi.product_id AND pi.is_primary = 1 "
                + "WHERE ci.cart_id = ? AND ci.product_id = ?";

        try (Connection conn = new DBContext().getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, cartId);
            ps.setInt(2, productId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    CartDetailDto item = new CartDetailDto();
                    item.setCartItemId(rs.getInt("cart_item_id"));
                    item.setCartId(rs.getInt("cart_id"));
                    item.setProductId(rs.getInt("product_id"));
                    item.setProductName(rs.getString("product_name"));
                    item.setPrice(rs.getDouble("price"));
                    item.setDiscountPrice(rs.getDouble("discount_price"));
                    item.setQuantity(rs.getInt("quantity"));
                    item.setImageUrl(rs.getString("image_url"));

                    // Gọi setTotalPrice() để tính toán
                    item.setTotalPrice();
                    return item;
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Lỗi SQL khi lấy chi tiết giỏ hàng", e);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Lỗi kết nối CSDL", e);
        }

        return null;
    }

    public double getProductTotalPrice(int productId, int cartId) {
        CartDetailDto cartItem = getCartItem(cartId, productId);
        return (cartItem != null) ? cartItem.getTotalPrice() : 0.0;
    }

    public static void main(String[] args) {
        // Khởi tạo DAO
        CartDetailDao cartDetailDao = new CartDetailDao();

//        // Chọn userId cần test
//        int testUserId = 3;
//
//        // Gọi phương thức getCartDetails
//        List<CartDetailDto> cartDetails = cartDetailDao.getCartDetails(testUserId);
//
//        // Kiểm tra kết quả
//        if (cartDetails.isEmpty()) {
//            System.out.println("Giỏ hàng của userId " + testUserId + " đang trống hoặc không tồn tại.");
//        } else {
//            System.out.println("Chi tiết giỏ hàng của userId " + testUserId + ":");
//            for (CartDetailDto item : cartDetails) {
//                System.out.println("Cart Item ID: " + item.getCartItemId());
//                System.out.println("Cart ID: " + item.getCartId());
//                System.out.println("Product ID: " + item.getProductId());
//                System.out.println("Product Name: " + item.getProductName());
//                System.out.println("Image URL: " + item.getImageUrl());
//                System.out.println("Quantity: " + item.getQuantity());
//                System.out.println("Price: " + item.getPrice() + " VND");
//                System.out.println("Discount Price: " + item.getDiscountPrice() + " VND");
//                System.out.println("Total Price: " + item.getTotalPrice() + " VND");
//                System.out.println("-----------------------------");
//            }
//        }
// Kiểm tra thêm sản phẩm vào giỏ hàng
        int userId = 3; // Giả sử user có ID = 1
        int productId = 2; // Giả sử sản phẩm có ID = 2

        boolean result = cartDetailDao.addToCart(userId, productId);

        // In kết quả kiểm tra
        if (result) {
            System.out.println("✅ Đã thêm sản phẩm vào giỏ hàng thành công!");
        } else {
            System.out.println("❌ Lỗi khi thêm sản phẩm vào giỏ hàng!");
        }
    }
}

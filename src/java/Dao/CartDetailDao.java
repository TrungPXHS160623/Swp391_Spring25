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
                + "JOIN Carts c ON ci.cart_id = c.cart_id "
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
}

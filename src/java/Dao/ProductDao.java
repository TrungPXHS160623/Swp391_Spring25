package Dao;

import Context.DBContext;
import Dto.ProductDto;
import Entity.Product;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * DAO Product
 */
public class ProductDao {

    private static final Logger LOGGER = Logger.getLogger(ProductDao.class.getName());

    // Lấy tất cả các sản phẩm trong database kèm ảnh
    public List<ProductDto> getAllProducts() {
        String sql = "SELECT p.*, pi.image_url FROM Products p "
                + "LEFT JOIN ProductImages pi ON p.product_id = pi.product_id AND pi.is_primary = 1 "
                + "ORDER BY p.created_at DESC";
        return getProductList(sql);
    }

    // Lấy sản phẩm mới nhất
    public ProductDto getNewestProduct() {
        String sql = "SELECT TOP 1 p.*, pi.image_url FROM Products p "
                + "LEFT JOIN ProductImages pi ON p.product_id = pi.product_id AND pi.is_primary = 1 "
                + "ORDER BY p.created_at DESC";
        return getSingleProduct(sql);
    }

// Lấy sản phẩm có giảm giá sâu nhất
    public ProductDto getBestDiscountedProduct() {
        String sql = "SELECT TOP 1 p.*, pi.image_url FROM Products p "
                + "LEFT JOIN ProductImages pi ON p.product_id = pi.product_id AND pi.is_primary = 1 "
                + "ORDER BY p.discount_percentage DESC, p.discount_price ASC";
        return getSingleProduct(sql);
    }

// Lấy sản phẩm bán chạy nhất
    public ProductDto getBestSellingProduct() {
        String sql = "SELECT TOP 1 p.*, pi.image_url FROM Products p "
                + "LEFT JOIN ProductImages pi ON p.product_id = pi.product_id AND pi.is_primary = 1 "
                + "ORDER BY p.sold_quantity DESC";
        return getSingleProduct(sql);
    }

// Lấy sản phẩm được đánh giá cao nhất
    public ProductDto getTopRatedProduct() {
        String sql = "SELECT TOP 1 p.*, pi.image_url FROM Products p "
                + "LEFT JOIN ProductImages pi ON p.product_id = pi.product_id AND pi.is_primary = 1 "
                + "ORDER BY p.average_rating DESC, p.sold_quantity DESC";
        return getSingleProduct(sql);
    }

    private ProductDto getSingleProduct(String sql) {
        try (Connection conn = new DBContext().getConnection(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return extractProduct(rs);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Lỗi SQL khi lấy sản phẩm", e);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Lỗi kết nối CSDL", e);
        }
        return null; // Trả về null nếu không có sản phẩm nào
    }

    // Chuyển ResultSet thành Product Object
    private ProductDto extractProduct(ResultSet rs) throws SQLException {
        return new ProductDto(
                rs.getInt("product_id"),
                rs.getString("product_name"),
                rs.getString("description"),
                rs.getDouble("price"),
                rs.getInt("stock_quantity"),
                rs.getInt("subcategory_id"),
                rs.getTimestamp("created_at"),
                rs.getTimestamp("updated_at"),
                rs.getDouble("discount_price"),
                rs.getDouble("discount_percentage"),
                rs.getInt("sold_quantity"),
                rs.getDouble("average_rating"),
                rs.getString("image_url") // Thêm ảnh sản phẩm
        );
    }

    public List<ProductDto> getProductList(String sql) {
        List<ProductDto> products = new ArrayList<>();
        try (Connection conn = new DBContext().getConnection(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                products.add(extractProduct(rs));
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Lỗi SQL khi lấy sản phẩm", e);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Lỗi kết nối CSDL", e);
        }
        return products;
    }

    // Test DAO với hàm main
    public static void main(String[] args) {
        ProductDao dao = new ProductDao();

//        System.out.println("🆕 Sản phẩm mới nhất:");
//        printProduct(dao.getNewestProduct());
//
//        System.out.println("\n💰 Sản phẩm giảm giá sâu nhất:");
//        printProduct(dao.getBestDiscountedProduct());
//
//        System.out.println("\n🔥 Sản phẩm bán chạy nhất:");
//        printProduct(dao.getBestSellingProduct());
//
//        System.out.println("\n⭐ Sản phẩm được đánh giá cao nhất:");
//        printProduct(dao.getTopRatedProduct());
        
        List<ProductDto> products = dao.getAllProducts(); // Lấy danh sách sản phẩm

        if (products.isEmpty()) {
            System.out.println("Không có sản phẩm nào trong database.");
        } else {
            System.out.println("Danh sách sản phẩm:");
            for (ProductDto product : products) {
                System.out.println(product);
            }
        }
    }

// Hàm hỗ trợ in thông tin sản phẩm
    private static void printProduct(ProductDto product) {
        if (product != null) {
            System.out.println(product);
        } else {
            System.out.println("⚠ Không tìm thấy sản phẩm nào.");
        }
    }
}

package Dao;

import Context.DBContext;
import Dto.ProductDto;
import Entity.Product;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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

    private List<ProductDto> executeProductQuery(String sql, List<Object> params) {
        List<ProductDto> products = new ArrayList<>();
        try (Connection conn = new DBContext().getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            for (int i = 0; i < params.size(); i++) {
                stmt.setObject(i + 1, params.get(i));
            }

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    products.add(extractProduct(rs));
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Lỗi SQL khi lấy danh sách sản phẩm", e);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Lỗi kết nối CSDL", e);
        }
        return products;
    }

    public List<ProductDto> filterProducts(String keyword, List<Integer> categoryIds, Double minPrice, Double maxPrice, Integer rating, boolean inStock, boolean outOfStock, boolean isDiscounted, boolean isBestseller) {
        StringBuilder sql = new StringBuilder("SELECT p.*, pi.image_url FROM Products p");
        sql.append(" LEFT JOIN ProductImages pi ON p.product_id = pi.product_id AND pi.is_primary = 1 WHERE 1=1");
        List<Object> params = new ArrayList<>();

        if (keyword != null && !keyword.isEmpty()) {
            sql.append(" AND p.product_name LIKE ?");
            params.add("%" + keyword + "%");
        }
        if (!categoryIds.isEmpty()) {
            sql.append(" AND p.subcategory_id IN (").append("?,".repeat(categoryIds.size() - 1)).append("?)");
            params.addAll(categoryIds);
        }
        if (minPrice != null) {
            sql.append(" AND p.price >= ?");
            params.add(minPrice);
        }
        if (maxPrice != null) {
            sql.append(" AND p.price <= ?");
            params.add(maxPrice);
        }
        if (rating != null) {
            sql.append(" AND p.average_rating >= ?");
            params.add(rating);
        }
        if (inStock) {
            sql.append(" AND p.stock_quantity > 0");
        }
        if (outOfStock) {
            sql.append(" AND p.stock_quantity = 0");
        }
        if (isDiscounted) {
            sql.append(" AND p.discount_price IS NOT NULL");
        }
        if (isBestseller) {
            sql.append(" ORDER BY p.sold_quantity DESC");
        }

        return executeProductQuery(sql.toString(), params);
    }

    // Test DAO với hàm main
    public static void main(String[] args) {
        ProductDao productDao = new ProductDao();

        // Thiết lập các tiêu chí lọc
        String keyword = "Laptop Dell XPS 15";
//    List<Integer> categoryIds = List.of(1, 2, 3);  // Ví dụ: Lọc theo 3 danh mục
//    Double minPrice = 10000.0;
//    Double maxPrice = 50000.0;
//    Integer rating = 4;
//    boolean inStock = true;
//    boolean outOfStock = false;
//    boolean isDiscounted = true;
//    boolean isBestseller = false;

        List<Integer> categoryIds = new ArrayList<>(); // Không lọc theo danh mục
        Double minPrice = null; // Không giới hạn giá tối thiểu
        Double maxPrice = null; // Không giới hạn giá tối đa
        Integer rating = null; // Không lọc theo đánh giá
        boolean inStock = false; // Không quan tâm hàng còn hay hết
        boolean outOfStock = false;
        boolean isDiscounted = false; // Không quan tâm có giảm giá hay không
        boolean isBestseller = false; // Không quan tâm sản phẩm bán chạy hay không

        // Gọi phương thức lọc sản phẩm
        List<ProductDto> filteredProducts = productDao.filterProducts(
                keyword, categoryIds, minPrice, maxPrice, rating, inStock, outOfStock, isDiscounted, isBestseller
        );

        // In kết quả ra console để kiểm tra
        for (ProductDto product : filteredProducts) {
            System.out.println(product);
        }
    }
}

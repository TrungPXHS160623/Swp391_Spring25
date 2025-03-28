/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dao;

import Context.DBContext;
import Dto.AdminProductDto;
import Entity.ProductImage;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author LENOVO
 */
public class AdminProductDao {

    private static final Logger LOGGER = Logger.getLogger(AdminProductDao.class.getName());

    /**
     * Phương thức helper chung xây dựng câu truy vấn động theo điều kiện.
     *
     * @param keyword Tìm kiếm theo product_name.
     * @param category Lọc theo subcategory name.
     * @param featured Lọc theo featured ("Yes" hoặc "No") theo logic: Yes nếu
     * soldQuantity >= 50 và averageRating > 4.
     * @param status Lọc theo status ("Sale" nếu stockQuantity > 0, "Soldout"
     * nếu không).
     * @param sortField Sắp xếp theo các trường: Title, Category, ListPrice,
     * SalePrice, featured, status.
     * @param sortDirection ASC hoặc DESC.
     * @param page Trang hiện tại.
     * @param pageSize Số lượng bản ghi mỗi trang.
     * @return Danh sách AdminProductDto.
     */
    public List<AdminProductDto> getProductListDynamic(String keyword, String category, String featured, String status,
            String sortField, String sortDirection, int page, int pageSize) {
        List<AdminProductDto> list = new ArrayList<>();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT p.product_id, p.product_name, p.description, p.price, p.stock_quantity, p.subcategory_id, ");
        sql.append("p.created_at, p.updated_at, p.discount_price, p.discount_percentage, p.sold_quantity, p.average_rating, ");
        sql.append("pi.image_url, sc.subcategory_name ");
        sql.append("FROM Products p ");
        sql.append("LEFT JOIN ProductImages pi ON p.product_id = pi.product_id AND pi.is_primary = 1 ");
        sql.append("LEFT JOIN SubCategories sc ON p.subcategory_id = sc.subcategory_id ");
        sql.append("WHERE 1=1 ");

        // Tìm kiếm theo product_name
        if (keyword != null && !keyword.trim().isEmpty()) {
            sql.append("AND p.product_name LIKE ? ");
        }
        // Lọc theo category (sub-category name)
        if (category != null && !category.trim().isEmpty()) {
            sql.append("AND sc.subcategory_name = ? ");
        }
        // Lọc theo featured: nếu "Yes" thì soldQuantity>=50 AND averageRating>4, nếu "No" thì (soldQuantity<50 OR averageRating<=4)
        if (featured != null && !featured.trim().isEmpty()) {
            if ("Yes".equalsIgnoreCase(featured)) {
                sql.append("AND p.sold_quantity >= 50 AND p.average_rating > 4 ");
            } else if ("No".equalsIgnoreCase(featured)) {
                sql.append("AND (p.sold_quantity < 50 OR p.average_rating <= 4) ");
            }
        }
        // Lọc theo status: nếu "Sale" thì stockQuantity>0, nếu "Soldout" thì stockQuantity<=0
        if (status != null && !status.trim().isEmpty()) {
            if ("Sale".equalsIgnoreCase(status)) {
                sql.append("AND p.stock_quantity > 0 ");
            } else if ("Soldout".equalsIgnoreCase(status)) {
                sql.append("AND p.stock_quantity <= 0 ");
            }
        }

        // Sắp xếp
        if (sortField != null && !sortField.trim().isEmpty()) {
            // Cho phép sort theo: Title, Category, ListPrice, SalePrice, featured, status.
            switch (sortField) {
                case "Title":
                    sortField = "p.product_name";
                    break;
                case "Category":
                    sortField = "sc.subcategory_name";
                    break;
                case "ListPrice":
                    sortField = "p.price";
                    break;
                case "SalePrice":
                    sortField = "p.discount_price";
                    break;
                case "featured":
                    // Sắp xếp theo soldQuantity (giả sử: nhiều bán hơn -> được đánh dấu featured)
                    sortField = "p.sold_quantity";
                    break;
                case "status":
                    sortField = "p.stockQuantity";
                    break;
                default:
                    sortField = "p.product_id";
            }
        } else {
            sortField = "p.product_id";
        }
        if (sortDirection == null || sortDirection.trim().isEmpty() || !sortDirection.equalsIgnoreCase("DESC")) {
            sortDirection = "ASC";
        } else {
            sortDirection = "DESC";
        }
        sql.append("ORDER BY " + sortField + " " + sortDirection + " ");

        // Phân trang
        sql.append("OFFSET ? ROWS FETCH NEXT ? ROWS ONLY");

        try (Connection conn = new DBContext().getConnection(); PreparedStatement stmt = conn.prepareStatement(sql.toString())) {

            int index = 1;
            if (keyword != null && !keyword.trim().isEmpty()) {
                String pattern = "%" + keyword.trim() + "%";
                stmt.setString(index++, pattern);
            }
            if (category != null && !category.trim().isEmpty()) {
                stmt.setString(index++, category.trim());
            }
            // featured và status đã được áp dụng trực tiếp trong SQL

            int offset = (page - 1) * pageSize;
            stmt.setInt(index++, offset);
            stmt.setInt(index++, pageSize);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    AdminProductDto dto = new AdminProductDto();
                    dto.setProductId(rs.getInt("product_id"));
                    dto.setProductName(rs.getString("product_name"));
                    dto.setDescription(rs.getString("description"));
                    dto.setPrice(rs.getDouble("price"));
                    dto.setStockQuantity(rs.getInt("stock_quantity"));
                    dto.setSubcategoryId(rs.getInt("subcategory_id"));
                    dto.setCreatedAt(rs.getTimestamp("created_at"));
                    dto.setUpdatedAt(rs.getTimestamp("updated_at"));
                    dto.setDiscountPrice(rs.getDouble("discount_price"));
                    dto.setDiscountPercentage(rs.getDouble("discount_percentage"));
                    dto.setSoldQuantity(rs.getInt("sold_quantity"));
                    dto.setAverageRating(rs.getDouble("average_rating"));
                    dto.setPrimaryImageUrl(rs.getString("image_url"));
                    dto.setCategory(rs.getString("subcategory_name"));
                    // Tính toán các trường derived
                    String feat = (dto.getSoldQuantity() >= 50 && dto.getAverageRating() > 4) ? "Yes" : "No";
                    String stat = (dto.getStockQuantity() > 0) ? "Sale" : "Soldout";
                    dto.setFeatured(feat);
                    dto.setStatus(stat);

                    list.add(dto);
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Lỗi SQL khi lấy danh sách sản phẩm động", e);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Lỗi SQL khi lấy danh sách sản phẩm động", e);
        }
        return list;
    }

    // Các phương thức riêng biệt
    // Phương thức tìm kiếm Sản Phẩm
    public List<AdminProductDto> searchProductList(String keyword, int page, int pageSize) {
        return getProductListDynamic(keyword, "", "", "", "Title", "ASC", page, pageSize);
    }

    // Phương thức Lọc Sản Phẩm
    public List<AdminProductDto> filterProductList(String category, String featured, String status, int page, int pageSize) {
        return getProductListDynamic("", category, featured, status, "Title", "ASC", page, pageSize);
    }

    // Phương thức sắp xếp Sản Phẩm
    public List<AdminProductDto> sortProductList(String sortField, String sortDirection, int page, int pageSize) {
        return getProductListDynamic("", "", "", "", sortField, sortDirection, page, pageSize);
    }

    // Phương thức goi tất cả sản phẩm
    public List<AdminProductDto> getAllProductList(int page, int pageSize) {
        return getProductListDynamic("", "", "", "", "product_id", "ASC", page, pageSize);
    }

    // Phương thức lấy chi tiết sản phẩm, bao gồm danh sách tất cả media (ảnh và video)
    public AdminProductDto getProductDetail(int productId) {
        AdminProductDto dto = new AdminProductDto();
        // Truy vấn sản phẩm
        String sqlProduct = "SELECT p.product_id, p.product_name, p.description, p.price, p.stock_quantity, "
                + "p.subcategory_id, p.created_at, p.updated_at, p.discount_price, p.discount_percentage, "
                + "p.sold_quantity, p.average_rating, "
                + "pi.image_url, sc.subcategory_name "
                + "FROM Products p "
                + "LEFT JOIN ProductImages pi ON p.product_id = pi.product_id AND pi.is_primary = 1 "
                + "LEFT JOIN SubCategories sc ON p.subcategory_id = sc.subcategory_id "
                + "WHERE p.product_id = ?";
        // Truy vấn danh sách media cho sản phẩm (bao gồm ảnh và video)
        String sqlMedia = "SELECT image_id, product_id, image_url, created_at, updated_at, is_primary "
                + "FROM ProductImages "
                + "WHERE product_id = ? ORDER BY is_primary DESC, image_id ASC";
        try (Connection conn = new DBContext().getConnection(); PreparedStatement stmtProduct = conn.prepareStatement(sqlProduct); PreparedStatement stmtMedia = conn.prepareStatement(sqlMedia)) {

            stmtProduct.setInt(1, productId);
            try (ResultSet rs = stmtProduct.executeQuery()) {
                if (rs.next()) {
                    dto.setProductId(rs.getInt("product_id"));
                    dto.setProductName(rs.getString("product_name"));
                    dto.setDescription(rs.getString("description"));
                    dto.setPrice(rs.getDouble("price"));
                    dto.setStockQuantity(rs.getInt("stock_quantity"));
                    dto.setSubcategoryId(rs.getInt("subcategory_id"));
                    dto.setCreatedAt(rs.getTimestamp("created_at"));
                    dto.setUpdatedAt(rs.getTimestamp("updated_at"));
                    dto.setDiscountPrice(rs.getDouble("discount_price"));
                    dto.setDiscountPercentage(rs.getDouble("discount_percentage"));
                    dto.setSoldQuantity(rs.getInt("sold_quantity"));
                    dto.setAverageRating(rs.getDouble("average_rating"));
                    dto.setPrimaryImageUrl(rs.getString("image_url"));
                    dto.setCategory(rs.getString("subcategory_name"));

                    // Tính toán các trường derived
                    String feat = (dto.getSoldQuantity() >= 50 && dto.getAverageRating() > 4) ? "Yes" : "No";
                    String stat = (dto.getStockQuantity() > 0) ? "Sale" : "Soldout";
                    dto.setFeatured(feat);
                    dto.setStatus(stat);
                }
            }

            // Lấy danh sách media
            List<Entity.ProductImage> mediaList = new ArrayList<>();
            stmtMedia.setInt(1, productId);
            try (ResultSet rsMedia = stmtMedia.executeQuery()) {
                while (rsMedia.next()) {
                    Entity.ProductImage media = new Entity.ProductImage();
                    media.setImage_id(rsMedia.getInt("image_id"));
                    media.setProduct_id(rsMedia.getInt("product_id"));
                    media.setImage_url(rsMedia.getString("image_url"));
                    media.setCreatedAt(rsMedia.getTimestamp("created_at"));
                    media.setUpdatedAt(rsMedia.getTimestamp("updated_at"));
                    media.setIs_primary(rsMedia.getBoolean("is_primary"));
                    mediaList.add(media);
                }
            }
            dto.setMediaList(mediaList);
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Lỗi SQL khi lấy chi tiết sản phẩm", e);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Lỗi SQL khi lấy chi tiết sản phẩm", e);
        }
        return dto;
    }

    public boolean addProduct(AdminProductDto product) {
        String sql = "INSERT INTO Products (product_name, description, price, stock_quantity, subcategory_id, created_at, discount_price, discount_percentage, sold_quantity, average_rating) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = new DBContext().getConnection(); PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, product.getProductName());
            stmt.setString(2, product.getDescription());
            stmt.setDouble(3, product.getPrice());
            stmt.setInt(4, product.getStockQuantity());
            stmt.setInt(5, product.getSubcategoryId());
            stmt.setTimestamp(6, new Timestamp(System.currentTimeMillis()));
            stmt.setDouble(7, product.getDiscountPrice());
            stmt.setDouble(8, product.getDiscountPercentage());
            stmt.setInt(9, product.getSoldQuantity());
            stmt.setDouble(10, product.getAverageRating());

            int affected = stmt.executeUpdate();
            if (affected == 0) {
                return false;
            }
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    product.setProductId(rs.getInt(1));
                }
            }

            // Thêm nhiều ảnh/video vào bảng ProductImages
            if (product.getMediaList() != null && !product.getMediaList().isEmpty()) {
                insertMediaList(conn, product.getProductId(), product.getMediaList());
            }

            return true;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Lỗi SQL khi thêm sản phẩm", e);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Lỗi SQL khi thêm sản phẩm", e);
        }
        return false;
    }

    public boolean updateProduct(AdminProductDto product) {
        String sql = "UPDATE Products SET product_name=?, description=?, price=?, stock_quantity=?, subcategory_id=?, updated_at=?, discount_price=?, discount_percentage=?, sold_quantity=?, average_rating=?"
                + "WHERE product_id=?";
        try (Connection conn = new DBContext().getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, product.getProductName());
            stmt.setString(2, product.getDescription());
            stmt.setDouble(3, product.getPrice());
            stmt.setInt(4, product.getStockQuantity());
            stmt.setInt(5, product.getSubcategoryId());
            stmt.setTimestamp(6, new Timestamp(System.currentTimeMillis()));
            stmt.setDouble(7, product.getDiscountPrice());
            stmt.setDouble(8, product.getDiscountPercentage());
            stmt.setInt(9, product.getSoldQuantity());
            stmt.setDouble(10, product.getAverageRating());
            stmt.setInt(12, product.getProductId());

            int affected = stmt.executeUpdate();
            if (affected == 0) {
                return false;
            }

            // Xử lý cập nhật media (xóa cũ, thêm mới) tùy logic
            if (product.getMediaList() != null && !product.getMediaList().isEmpty()) {
                // Ví dụ xóa hết media cũ, sau đó insert lại
                try (PreparedStatement stmtDel = conn.prepareStatement("DELETE FROM ProductImages WHERE product_id=?")) {
                    stmtDel.setInt(1, product.getProductId());
                    stmtDel.executeUpdate();
                }
                insertMediaList(conn, product.getProductId(), product.getMediaList());
            }

            return true;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Lỗi SQL khi cập nhật sản phẩm", e);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Lỗi SQL khi cập nhật sản phẩm", e);
        }
        return false;
    }

// Hàm hỗ trợ chèn nhiều media
    private void insertMediaList(Connection conn, int productId, List<ProductImage> mediaList) throws SQLException {
        String sqlInsertMedia = "INSERT INTO ProductImages (product_id, image_url, created_at, is_primary) VALUES (?,?,?,?,?)";
        try (PreparedStatement stmtMedia = conn.prepareStatement(sqlInsertMedia)) {
            for (ProductImage pm : mediaList) {
                stmtMedia.setInt(1, productId);
                stmtMedia.setString(2, pm.getImage_url());
                stmtMedia.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
                stmtMedia.setBoolean(5, pm.isIs_primary());
                stmtMedia.addBatch();
            }
            stmtMedia.executeBatch();
        }
    }

    public static void main(String[] args) {
        AdminProductDao dao = new AdminProductDao();
        int page = 1;
        int pageSize = 10; // Số lượng sản phẩm mỗi trang, thay đổi nếu cần

        // Gọi phương thức getAllProductList để lấy danh sách sản phẩm
        List<AdminProductDto> productList = dao.getAllProductList(page, pageSize);

        // In ra thông tin các sản phẩm để kiểm tra
        System.out.println("----- Testing getAllProductList -----");
        if (productList.isEmpty()) {
            System.out.println("Không tìm thấy sản phẩm nào.");
        } else {
            for (AdminProductDto product : productList) {
                // Có thể gọi computeDerivedFields() nếu DAO chưa tự tính toán
                product.computeDerivedFields();
                System.out.println(product);
            }
        }
    }
}

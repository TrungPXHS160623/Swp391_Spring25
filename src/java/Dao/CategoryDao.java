/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dao;

import Context.DBContext;
import Entity.Category;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Acer
 */
public class CategoryDao {

    private static final Logger LOGGER = Logger.getLogger(CategoryDao.class.getName());

    // Thêm danh mục
    public boolean insert(Category category) {
        String sql = "INSERT INTO Categories (category_name, description, created_at, status) VALUES (?, ?, ?, ?)";
        try (Connection conn = new DBContext().getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, category.getCategoryName());
            stmt.setString(2, category.getDescription());
            stmt.setTimestamp(3, Timestamp.valueOf(category.getCreatedAt()));
            stmt.setInt(4, category.getStatus());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Lỗi SQL khi thêm danh mục", e);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Lỗi không mong muốn khi thêm danh mục", e);
        }
        return false;
    }

    // Cập nhật danh mục
    public boolean update(Category category) {
        String sql = "UPDATE Categories SET category_name = ?, description = ?, updated_at = ?, updatedBy = ?, status = ? WHERE category_id = ?";
        try (Connection conn = new DBContext().getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, category.getCategoryName());
            stmt.setString(2, category.getDescription());
            stmt.setTimestamp(3, Timestamp.valueOf(category.getUpdatedAt()));
            stmt.setObject(4, category.getUpdatedBy(), Types.INTEGER);
            stmt.setInt(5, category.getStatus());
            stmt.setInt(6, category.getCategoryId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Lỗi SQL khi cập nhật danh mục", e);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Lỗi không mong muốn khi cập nhật danh mục", e);
        }
        return false;
    }

    // Xóa mềm danh mục
    public boolean delete(int categoryId) {
        String sql = "UPDATE Categories SET deletedAt = ? WHERE category_id = ?";
        try (Connection conn = new DBContext().getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setTimestamp(1, Timestamp.valueOf(java.time.LocalDateTime.now()));
            stmt.setInt(2, categoryId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Lỗi SQL khi xóa mềm danh mục", e);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Lỗi không mong muốn khi xóa mềm danh mục", e);
        }
        return false;
    }

    // Lấy danh mục theo ID
    public Category getById(int categoryId) {
        String sql = "SELECT * FROM Categories WHERE category_id = ? AND deletedAt IS NULL";
        try (Connection conn = new DBContext().getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, categoryId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToCategory(rs);
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Lỗi SQL khi lấy danh mục theo ID", e);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Lỗi không mong muốn khi lấy danh mục theo ID", e);
        }
        return null;
    }

    // Lấy tất cả danh mục
    public List<Category> getAll() {
        List<Category> categories = new ArrayList<>();
        String sql = "SELECT * FROM Categories WHERE deletedAt IS NULL ORDER BY category_id DESC";
        try (Connection conn = new DBContext().getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                categories.add(mapResultSetToCategory(rs));
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Lỗi SQL khi lấy danh sách danh mục", e);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Lỗi không mong muốn khi lấy danh sách danh mục", e);
        }
        return categories;
    }

    // Tìm kiếm danh mục theo tên
    public List<Category> search(String keyword) {
        List<Category> categories = new ArrayList<>();
        String sql = "SELECT * FROM Categories WHERE category_name LIKE ? AND deletedAt IS NULL";
        try (Connection conn = new DBContext().getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + keyword + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    categories.add(mapResultSetToCategory(rs));
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Lỗi SQL khi tìm kiếm danh mục", e);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Lỗi không mong muốn khi tìm kiếm danh mục", e);
        }
        return categories;
    }

    // chức năng phục hồi danh mục
    public boolean restore(int categoryId) {
        String sql = "UPDATE Categories SET deletedAt = NULL WHERE category_id = ?";
        try (Connection conn = new DBContext().getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, categoryId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Lỗi SQL khi phục hồi danh mục", e);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Lỗi kết nối CSDL khi phục hồi danh mục", e);
        }
        return false;
    }

    // chức năng xoá vĩnh viễn danh mục
    public boolean hardDelete(int categoryId) {
        String sql = "DELETE FROM Categories WHERE category_id = ?";
        try (Connection conn = new DBContext().getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, categoryId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Lỗi khi xóa vĩnh viễn danh mục", e);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Lỗi kết nối CSDL khi xoá vĩnh viễn danh mục danh mục", e);
        }
        return false;
    }

    // chức năng Lọc danh mục theo trạng thái
    public List<Category> getByStatus(int status) {
        List<Category> categories = new ArrayList<>();
        String sql = "SELECT * FROM Categories WHERE status = ? AND deletedAt IS NULL ORDER BY category_id DESC";
        try (Connection conn = new DBContext().getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, status);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    categories.add(mapResultSetToCategory(rs));
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Lỗi khi lọc danh mục theo trạng thái", e);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Lỗi kết nối CSDL khi Lọc danh mục theo trạng thái", e);
        }
        return categories;
    }

    // chức năng Phân trang danh mục
    public List<Category> getAll(int page, int limit) {
        List<Category> categories = new ArrayList<>();
        String sql = "SELECT * FROM Categories WHERE deletedAt IS NULL ORDER BY category_id DESC OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
        try (Connection conn = new DBContext().getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, (page - 1) * limit);
            stmt.setInt(2, limit);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    categories.add(mapResultSetToCategory(rs));
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Lỗi khi phân trang danh mục", e);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Lỗi kết nối CSDL khi Phân trang danh mục", e);
        }
        return categories;
    }

    // Chuyển từ ResultSet thành Category Object
    private Category mapResultSetToCategory(ResultSet rs) throws SQLException {
        Category category = new Category();
        category.setCategoryId(rs.getInt("category_id"));
        category.setCategoryName(rs.getString("category_name"));
        category.setDescription(rs.getString("description"));
        category.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());

        Timestamp updatedAtTimestamp = rs.getTimestamp("updated_at");
        category.setUpdatedAt(updatedAtTimestamp != null ? updatedAtTimestamp.toLocalDateTime() : null);

        int updatedBy = rs.getInt("updatedBy");
        category.setUpdatedBy(rs.wasNull() ? null : updatedBy);  // Kiểm tra NULL đúng cách

        Timestamp deletedAtTimestamp = rs.getTimestamp("deletedAt");
        category.setDeletedAt(deletedAtTimestamp != null ? deletedAtTimestamp.toLocalDateTime() : null);

        category.setStatus(rs.getInt("status"));
        return category;
    }

    public static void main(String[] args) {
        CategoryDao categoryDao = new CategoryDao();

    // Test thêm danh mục - Done
    Category newCategory = new Category();
    newCategory.setCategoryName("Test Category");
    newCategory.setDescription("Mô tả danh mục test");
    newCategory.setCreatedAt(LocalDateTime.now());
    newCategory.setStatus(1);
    boolean insertResult = categoryDao.insert(newCategory);
    System.out.println("Thêm danh mục: " + insertResult);
    //Test lấy danh mục theo ID - Done
        Category category = categoryDao.getById(1);
        System.out.println("Lấy danh mục ID 1: " + (category != null ? category.getCategoryName() : "Không tìm thấy"));

    // Test cập nhật danh mục - Done
    if (category != null) {
        category.setCategoryName("Updated Category");
        category.setUpdatedAt(LocalDateTime.now());
        category.setUpdatedBy(1);
        boolean updateResult = categoryDao.update(category);
        System.out.println("Cập nhật danh mục: " + updateResult);
    }

    // Test lấy tất cả danh mục - Done
    List<Category> categories = categoryDao.getAll();
    System.out.println("Tổng số danh mục: " + categories.size());

    // Test tìm kiếm danh mục theo từ khóa - Done
    List<Category> searchResults = categoryDao.search("Test");
    System.out.println("Tìm kiếm 'Test': " + searchResults.size() + " kết quả");

    // Test xóa mềm danh mục - Done
    boolean deleteResult = categoryDao.delete(1);
    System.out.println("Xóa mềm danh mục ID 1: " + deleteResult);

    // Test phục hồi danh mục - Done
    boolean restoreResult = categoryDao.restore(1);
    System.out.println("Phục hồi danh mục ID 1: " + restoreResult);

    // Test xóa vĩnh viễn danh mục - Done
    boolean hardDeleteResult = categoryDao.hardDelete(2);
    System.out.println("Xóa vĩnh viễn danh mục ID 2: " + hardDeleteResult);

    // Test lấy danh mục theo trạng thái - Done
    List<Category> activeCategories = categoryDao.getByStatus(1);
    System.out.println("Danh mục hoạt động: " + activeCategories.size());

    //Test phân trang danh mục
    List<Category> paginatedCategories = categoryDao.getAll(1, 5);
    System.out.println("Danh mục trang 1 (tối đa 5): " + paginatedCategories.size());
    }

}

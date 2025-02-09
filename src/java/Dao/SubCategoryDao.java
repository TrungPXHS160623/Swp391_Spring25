/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dao;

import Context.DBContext;
import Entity.SubCategory;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SubCategoryDao {
    private static final Logger LOGGER = Logger.getLogger(SubCategoryDao.class.getName());

    // Thêm danh mục con
    public boolean insert(SubCategory subCategory) {
        String sql = "INSERT INTO SubCategories (subcategory_name, category_id, description, created_at, status) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = new DBContext().getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, subCategory.getSubcategoryName());
            stmt.setInt(2, subCategory.getCategoryId());
            stmt.setString(3, subCategory.getDescription());
            stmt.setTimestamp(4, Timestamp.valueOf(subCategory.getCreatedAt()));
            stmt.setInt(5, subCategory.getStatus());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Lỗi SQL khi thêm danh mục con", e);
        }catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Lỗi kết nối CSDL", e);
        }
        return false;
    }

    // Cập nhật danh mục con
    public boolean update(SubCategory subCategory) {
        String sql = "UPDATE SubCategories SET subcategory_name = ?, category_id = ?, description = ?, updated_at = ?, updatedBy = ?, status = ? WHERE subcategory_id = ?";
        try (Connection conn = new DBContext().getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, subCategory.getSubcategoryName());
            stmt.setInt(2, subCategory.getCategoryId());
            stmt.setString(3, subCategory.getDescription());
            stmt.setTimestamp(4, Timestamp.valueOf(subCategory.getUpdatedAt()));
            stmt.setObject(5, subCategory.getUpdatedBy(), Types.INTEGER);
            stmt.setInt(6, subCategory.getStatus());
            stmt.setInt(7, subCategory.getSubcategoryId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Lỗi SQL khi cập nhật danh mục con", e);
        }catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Lỗi kết nối CSDL", e);
        }
        return false;
    }

    // Xóa mềm danh mục con
    public boolean delete(int subCategoryId) {
        String sql = "UPDATE SubCategories SET deletedAt = ? WHERE subcategory_id = ?";
        try (Connection conn = new DBContext().getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now()));
            stmt.setInt(2, subCategoryId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Lỗi SQL khi xóa mềm danh mục con", e);
        }catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Lỗi kết nối CSDL", e);
        }
        return false;
    }
    // Xóa cứng danh mục con
    public boolean hardDelete(int subCategoryId) {
        String sql = "DELETE FROM SubCategories WHERE subcategory_id = ?";
        try (Connection conn = new DBContext().getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, subCategoryId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Lỗi SQL khi xóa vĩnh viễn danh mục con", e);
        }
        catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Lỗi kết nối CSDL", e);
        }
        return false;
    }

    // Phục hồi danh mục con đã xóa
    public boolean restore(int subCategoryId) {
        String sql = "UPDATE SubCategories SET deletedAt = NULL WHERE subcategory_id = ?";
        try (Connection conn = new DBContext().getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, subCategoryId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Lỗi SQL khi phục hồi danh mục con", e);
        }catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Lỗi kết nối CSDL", e);
        }
        return false;
    }
    

    // Lấy danh mục con theo ID
    public SubCategory getById(int subCategoryId) {
        String sql = "SELECT * FROM SubCategories WHERE subcategory_id = ? AND deletedAt IS NULL";
        try (Connection conn = new DBContext().getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, subCategoryId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToSubCategory(rs);
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Lỗi SQL khi lấy danh mục con theo ID", e);
        }catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Lỗi kết nối CSDL", e);
        }
        return null;
    }

    // Lấy tất cả danh mục con
    public List<SubCategory> getAll() {
        List<SubCategory> subCategories = new ArrayList<>();
        String sql = "SELECT * FROM SubCategories WHERE deletedAt IS NULL ORDER BY subcategory_id DESC";
        try (Connection conn = new DBContext().getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                subCategories.add(mapResultSetToSubCategory(rs));
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Lỗi SQL khi lấy danh sách danh mục con", e);
        }catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Lỗi kết nối CSDL", e);
        }
        return subCategories;
    }
    // Phân trang danh mục con
    public List<SubCategory> getAll(int page, int limit) {
        List<SubCategory> subCategories = new ArrayList<>();
        String sql = "SELECT * FROM SubCategories WHERE deletedAt IS NULL ORDER BY subcategory_id DESC OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
        try (Connection conn = new DBContext().getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, (page - 1) * limit);
            stmt.setInt(2, limit);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    subCategories.add(mapResultSetToSubCategory(rs));
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Lỗi khi phân trang danh mục con", e);
        }catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Lỗi kết nối CSDL", e);
        }
        return subCategories;
    }
    // Tìm kiếm danh mục con theo tên
    public List<SubCategory> search(String keyword) {
        List<SubCategory> subCategories = new ArrayList<>();
        String sql = "SELECT * FROM SubCategories WHERE subcategory_name LIKE ? AND deletedAt IS NULL";
        try (Connection conn = new DBContext().getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + keyword + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    subCategories.add(mapResultSetToSubCategory(rs));
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Lỗi SQL khi tìm kiếm danh mục con", e);
        }
        catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Lỗi kết nối CSDL", e);
        }
        return subCategories;
    }


    // Chuyển từ ResultSet thành SubCategory Object
    private SubCategory mapResultSetToSubCategory(ResultSet rs) throws SQLException {
        SubCategory subCategory = new SubCategory();
        subCategory.setSubcategoryId(rs.getInt("subcategory_id"));
        subCategory.setSubcategoryName(rs.getString("subcategory_name"));
        subCategory.setCategoryId(rs.getInt("category_id"));
        subCategory.setDescription(rs.getString("description"));
        subCategory.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        
        Timestamp updatedAtTimestamp = rs.getTimestamp("updated_at");
        subCategory.setUpdatedAt(updatedAtTimestamp != null ? updatedAtTimestamp.toLocalDateTime() : null);
        
        int updatedBy = rs.getInt("updatedBy");
        subCategory.setUpdatedBy(rs.wasNull() ? null : updatedBy);
        
        Timestamp deletedAtTimestamp = rs.getTimestamp("deletedAt");
        subCategory.setDeletedAt(deletedAtTimestamp != null ? deletedAtTimestamp.toLocalDateTime() : null);
        
        subCategory.setStatus(rs.getInt("status"));
        return subCategory;
    }

    // Hàm main để test
    public static void main(String[] args) {
        SubCategoryDao subCategoryDao = new SubCategoryDao();
        
        // Thêm danh mục con
        SubCategory newSubCategory = new SubCategory();
        newSubCategory.setSubcategoryName("Test SubCategory");
        newSubCategory.setCategoryId(1);
        newSubCategory.setDescription("Mô tả danh mục con test");
        newSubCategory.setCreatedAt(LocalDateTime.now());
        newSubCategory.setStatus(1);
        boolean insertResult = subCategoryDao.insert(newSubCategory);
        System.out.println("Thêm danh mục con: " + insertResult);
        
        // Lấy tất cả danh mục con
        List<SubCategory> subCategories = subCategoryDao.getAll();
        System.out.println("Tổng số danh mục con: " + subCategories.size());
    }
}

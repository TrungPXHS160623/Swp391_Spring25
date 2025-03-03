package Dao;

import Context.DBContext;
import Entity.Slider;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * DAO Slider
 */
public class SliderDao {
    private static final Logger LOGGER = Logger.getLogger(SliderDao.class.getName());

    // Lấy danh sách tất cả slider
    public List<Slider> getAllSliders() {
        List<Slider> sliders = new ArrayList<>();
        String sql = "SELECT * FROM Sliders";
        try (Connection conn = new DBContext().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                sliders.add(extractSlider(rs));
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Lỗi SQL khi lấy danh sách slider", e);
        }
        catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Lỗi kết nối CSDL", e);
        }
        
        return sliders;
    }

    // Tìm slider theo ID
    public Slider getSliderById(int id) {
        String sql = "SELECT * FROM Sliders WHERE SliderId = ?";
        try (Connection conn = new DBContext().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return extractSlider(rs);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Lỗi SQL khi tìm slider theo ID", e);
        }
        catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Lỗi kết nối CSDL", e);
        }
        return null;
    }
     // Lọc slider theo title
    public List<Slider> getSliderByTitle(String title) {
        List<Slider> sliders = new ArrayList<>();
        String query = "SELECT * FROM Slider WHERE title LIKE ?";
        try (Connection conn = new DBContext().getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            
            ps.setString(1, "%" + title + "%");
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                sliders.add(extractSlider(rs));
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Lỗi SQL khi lọc slider theo status", e);
        }
        catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Lỗi kết nối CSDL", e);
        }
        return sliders;
    }

    // Lọc slider theo trạng thái (true = active, false = deactive)
    public List<Slider> getSliderByStatus(boolean status) {
        List<Slider> sliders = new ArrayList<>();
        String query = "SELECT * FROM Slider WHERE status = ?";
        try (Connection conn = new DBContext().getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            
            ps.setBoolean(1, status);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                sliders.add(extractSlider(rs));
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Lỗi SQL khi tìm slider theo ID", e);
        }
        catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Lỗi kết nối CSDL", e);
        }
        return sliders;
    }
    // Tìm slider theo BackLink
    public List<Slider> getSlidersByBackLink(String backLink) {
        List<Slider> sliders = new ArrayList<>();
        String sql = "SELECT * FROM Sliders WHERE BackLink = ?";
        try (Connection conn = new DBContext().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, backLink);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                sliders.add(extractSlider(rs));
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Lỗi SQL khi tìm slider theo BackLink", e);
        }
        catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Lỗi kết nối CSDL", e);
        }
        return sliders;
    }

    // Thêm slider mới
    public boolean addSlider(Slider slider) {
        String sql = "INSERT INTO Sliders (ImageUrl, Title, BackLink, Status, Notes, CreatedDate, UpdatedDate, LastUpdatedBy) " +
                     "VALUES (?, ?, ?, ?, ?, GETDATE(), GETDATE(), ?)";
        try (Connection conn = new DBContext().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, slider.getImageUrl());
            stmt.setString(2, slider.getTitle());
            stmt.setString(3, slider.getBackLink());
            stmt.setInt(4, slider.getStatus());
            stmt.setString(5, slider.getNotes());
            stmt.setInt(6, slider.getLastUpdatedBy());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Lỗi SQL khi thêm slider", e);
        }
        catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Lỗi kết nối CSDL", e);
        }
        return false;
    }

    // Cập nhật slider
    public boolean updateSlider(Slider slider) {
        String sql = "UPDATE Sliders SET ImageUrl = ?, Title = ?, BackLink = ?, Status = ?, Notes = ?, " +
                     "UpdatedDate = GETDATE(), LastUpdatedBy = ? WHERE SliderId = ?";
        try (Connection conn = new DBContext().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, slider.getImageUrl());
            stmt.setString(2, slider.getTitle());
            stmt.setString(3, slider.getBackLink());
            stmt.setInt(4, slider.getStatus());
            stmt.setString(5, slider.getNotes());
            stmt.setInt(6, slider.getLastUpdatedBy());
            stmt.setInt(7, slider.getId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Lỗi SQL khi cập nhật slider", e);
        }
        catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Lỗi kết nối CSDL", e);
        }
        return false;
    }

    // Xóa slider
    public boolean deleteSlider(int id) {
        String sql = "DELETE FROM Sliders WHERE SliderId = ?";
        try (Connection conn = new DBContext().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Lỗi SQL khi xóa slider", e);
        }
        catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Lỗi kết nối CSDL", e);
        }
        return false;
    }

    // Kích hoạt slider (Status = 1)
    public boolean activateSlider(int id) {
        return updateSliderStatus(id, 1);
    }

    // Vô hiệu hóa slider (Status = 0)
    public boolean deactivateSlider(int id) {
        return updateSliderStatus(id, 0);
    }

    // Cập nhật trạng thái slider
    private boolean updateSliderStatus(int id, int status) {
        String sql = "UPDATE Sliders SET Status = ?, UpdatedDate = GETDATE() WHERE SliderId = ?";
        try (Connection conn = new DBContext().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, status);
            stmt.setInt(2, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Lỗi SQL khi cập nhật trạng thái slider", e);
        }
        catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Lỗi kết nối CSDL", e);
        }
        return false;
    }

    // Chuyển ResultSet thành Slider Object
    private Slider extractSlider(ResultSet rs) throws SQLException {
        return new Slider(
                rs.getInt("SliderId"),
                rs.getString("ImageUrl"),
                rs.getString("Title"),
                rs.getString("BackLink"),
                rs.getInt("Status"),
                rs.getString("Notes"),
                rs.getTimestamp("CreatedDate"),
                rs.getTimestamp("UpdatedDate"),
                rs.getInt("LastUpdatedBy")
        );
    }
     public static void main(String[] args) {
        SliderDao sliderDao = new SliderDao();

//        // Test 1: Lấy tất cả sliders
//        System.out.println("=== Danh sách tất cả sliders ===");
//        List<Slider> sliders = sliderDao.getAllSliders();
//        for (Slider s : sliders) {
//            System.out.println(s);
//        }

        // Test 2: Thêm một slider mới
        System.out.println("\n=== Thêm slider mới ===");
        Slider newSlider = new Slider(0, "image.jpg", "Test Slider", "backlink", 1, "Ghi chú test", null, null, 3);
        boolean addSuccess = sliderDao.addSlider(newSlider);
        System.out.println("Thêm thành công: " + addSuccess);
//
//        // Test 3: Lấy slider theo ID
//        System.out.println("\n=== Lấy slider theo ID ===");
//        Slider foundSlider = sliderDao.getSliderById(1);
//        System.out.println(foundSlider != null ? foundSlider : "Không tìm thấy slider");
//
//        // Test 4: Cập nhật slider
//        System.out.println("\n=== Cập nhật slider ===");
//        if (foundSlider != null) {
//            foundSlider.setTitle("Updated Title");
//            boolean updateSuccess = sliderDao.updateSlider(foundSlider);
//            System.out.println("Cập nhật thành công: " + updateSuccess);
//        }
//
//        // Test 5: Xóa slider theo ID
//        System.out.println("\n=== Xóa slider ===");
//        boolean deleteSuccess = sliderDao.deleteSlider(2);
//        System.out.println("Xóa thành công: " + deleteSuccess);
//
//        // Test 6: Kích hoạt slider
//        System.out.println("\n=== Kích hoạt slider ===");
//        boolean activateSuccess = sliderDao.activateSlider(3);
//        System.out.println("Kích hoạt thành công: " + activateSuccess);
//
//        // Test 7: Vô hiệu hóa slider
//        System.out.println("\n=== Vô hiệu hóa slider ===");
//        boolean deactivateSuccess = sliderDao.deactivateSlider(3);
//        System.out.println("Vô hiệu hóa thành công: " + deactivateSuccess);
    }
}

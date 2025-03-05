/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dao;

import Context.DBContext;
import Entity.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author LENOVO
 */
public class UserDAO {

    private static final Logger LOGGER = Logger.getLogger(UserDAO.class.getName());

    // Chuyển đổi từ ResultSet thành User Object
    private User mapResultSetToUser(ResultSet rs) throws SQLException {
        User user = new User();
        user.setUser_id(rs.getInt("user_id"));
        user.setFull_name(rs.getString("full_name"));
        user.setGender(rs.getString("gender"));
        user.setAddress(rs.getString("address"));
        user.setEmail(rs.getString("email"));
        user.setPhone_number(rs.getString("phone_number"));
        user.setRole_name(rs.getString("role_name"));
        user.setIs_active(rs.getInt("is_active"));
        return user;
    }

    // Liệt kê tất cả các người dùng
    public List<User> getAllUser() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT u.[user_id],u.[full_name],u.[gender],"
                + "  u.[address],u.[email],u.[phone_number],r.[role_name],u.[is_active]"
                + "  FROM [Users] u Join [Roles] r ON u.[role_id] = r.[role_id]";
        try (Connection conn = new DBContext().getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                users.add(mapResultSetToUser(rs));
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Lỗi SQL khi lấy danh sách người dùng", e);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Lỗi không mong muốn khi lấy danh sách người dùng", e);
        }
        return users;
    }

    // Tìm kiếm theo tên người dùng
    public List<User> searchUser(String keyword) {
        List<User> users = new ArrayList<>();
        String sql = "SELECT u.[user_id],u.[full_name],u.[gender],"
                + "  u.[address],u.[email],u.[phone_number],r.[role_name],u.[is_active]"
                + "  FROM [Users] u Join [Roles] r ON u.[role_id] = r.[role_id] Where full_name LIKE ?";
        try (Connection conn = new DBContext().getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + keyword + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    users.add(mapResultSetToUser(rs));
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Lỗi SQL khi tìm kiếm người dùng", e);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Lỗi không mong muốn khi tìm kiếm người dùng", e);
        }
        return users;
    }

    // Chức năng sắp xếp theo mã người dùng
    public List<User> getAllUserSortedById(boolean id_ascending) {
        List<User> users = new ArrayList<>();
        String sql = "SELECT u.[user_id],u.[full_name],u.[gender],"
                + "  u.[address],u.[email],u.[phone_number],r.[role_name],u.[is_active]"
                + "  FROM [Users] u Join [Roles] r ON u.[role_id] = r.[role_id]"
                + "ORDER BY u.user_id " + (id_ascending ? "ASC" : "DESC");
        try (Connection conn = new DBContext().getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                users.add(mapResultSetToUser(rs));
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Lỗi SQL khi lấy danh sách người dùng sắp xếp", e);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Lỗi không mong muốn khi lấy danh sách người dùng sắp xếp", e);
        }
        return users;
    }

    // Chức năng sắp xếp theo tên người dùng
    public List<User> getAllUserSortedByName(boolean name_ascending) {
        List<User> users = new ArrayList<>();
        String sql = "SELECT u.[user_id],u.[full_name],u.[gender],"
                + "  u.[address],u.[email],u.[phone_number],r.[role_name],u.[is_active]"
                + "  FROM [Users] u Join [Roles] r ON u.[role_id] = r.[role_id]"
                + "ORDER BY u.full_name " + (name_ascending ? "ASC" : "DESC");
        try (Connection conn = new DBContext().getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                users.add(mapResultSetToUser(rs));
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Lỗi SQL khi lấy danh sách người dùng sắp xếp", e);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Lỗi không mong muốn khi lấy danh sách người dùng sắp xếp", e);
        }
        return users;
    }

    // Chức năng sắp xếp theo giới tính
    public List<User> getAllUserSortedByGender(boolean gender_ascending) {
        List<User> users = new ArrayList<>();
        String sql = "SELECT u.[user_id],u.[full_name],u.[gender],"
                + "  u.[address],u.[email],u.[phone_number],r.[role_name],u.[is_active]"
                + "  FROM [Users] u Join [Roles] r ON u.[role_id] = r.[role_id]"
                + "ORDER BY u.gender " + (gender_ascending ? "ASC" : "DESC");
        try (Connection conn = new DBContext().getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                users.add(mapResultSetToUser(rs));
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Lỗi SQL khi lấy danh sách người dùng sắp xếp", e);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Lỗi không mong muốn khi lấy danh sách người dùng sắp xếp", e);
        }
        return users;
    }

    // Chức năng sắp xếp theo tên địa chỉ
    public List<User> getAllUserSortedByAddress(boolean address_ascending) {
        List<User> users = new ArrayList<>();
        String sql = "SELECT u.[user_id],u.[full_name],u.[gender],"
                + "  u.[address],u.[email],u.[phone_number],r.[role_name],u.[is_active]"
                + "  FROM [Users] u Join [Roles] r ON u.[role_id] = r.[role_id]"
                + "ORDER BY u.address " + (address_ascending ? "ASC" : "DESC");
        try (Connection conn = new DBContext().getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                users.add(mapResultSetToUser(rs));
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Lỗi SQL khi lấy danh sách người dùng sắp xếp", e);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Lỗi không mong muốn khi lấy danh sách người dùng sắp xếp", e);
        }
        return users;
    }

    // Chức năng sắp xếp theo tên email
    public List<User> getAllUserSortedByEmail(boolean email_ascending) {
        List<User> users = new ArrayList<>();
        String sql = "SELECT u.[user_id],u.[full_name],u.[gender],"
                + "  u.[address],u.[email],u.[phone_number],r.[role_name],u.[is_active]"
                + "  FROM [Users] u Join [Roles] r ON u.[role_id] = r.[role_id]"
                + "ORDER BY u.email " + (email_ascending ? "ASC" : "DESC");
        try (Connection conn = new DBContext().getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                users.add(mapResultSetToUser(rs));
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Lỗi SQL khi lấy danh sách người dùng sắp xếp", e);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Lỗi không mong muốn khi lấy danh sách người dùng sắp xếp", e);
        }
        return users;
    }

    // Chức năng sắp xếp theo tên số điện thoại
    public List<User> getAllUserSortedByPhone_Number(boolean phone_ascending) {
        List<User> users = new ArrayList<>();
        String sql = "SELECT u.[user_id],u.[full_name],u.[gender],"
                + "  u.[address],u.[email],u.[phone_number],r.[role_name],u.[is_active]"
                + "  FROM [Users] u Join [Roles] r ON u.[role_id] = r.[role_id]"
                + "ORDER BY u.phone_number " + (phone_ascending ? "ASC" : "DESC");
        try (Connection conn = new DBContext().getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                users.add(mapResultSetToUser(rs));
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Lỗi SQL khi lấy danh sách người dùng sắp xếp", e);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Lỗi không mong muốn khi lấy danh sách người dùng sắp xếp", e);
        }
        return users;
    }

    // Chức năng sắp xếp theo tên số vai trò
    public List<User> getAllUserSortedByRole(boolean role_ascending) {
        List<User> users = new ArrayList<>();
        String sql = "SELECT u.[user_id],u.[full_name],u.[gender],"
                + "  u.[address],u.[email],u.[phone_number],r.[role_name],u.[is_active]"
                + "  FROM [Users] u Join [Roles] r ON u.[role_id] = r.[role_id]"
                + "ORDER BY r.role_name " + (role_ascending ? "ASC" : "DESC");
        try (Connection conn = new DBContext().getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                users.add(mapResultSetToUser(rs));
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Lỗi SQL khi lấy danh sách người dùng sắp xếp", e);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Lỗi không mong muốn khi lấy danh sách người dùng sắp xếp", e);
        }
        return users;
    }

    // Chức năng sắp xếp theo tên số trạng thái
    public List<User> getAllUserSortedByStatus(boolean status_ascending) {
        List<User> users = new ArrayList<>();
        String sql = "SELECT u.[user_id],u.[full_name],u.[gender],"
                + "  u.[address],u.[email],u.[phone_number],r.[role_name],u.[is_active]"
                + "  FROM [Users] u Join [Roles] r ON u.[role_id] = r.[role_id]"
                + "ORDER BY u.is_active " + (status_ascending ? "ASC" : "DESC");
        try (Connection conn = new DBContext().getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                users.add(mapResultSetToUser(rs));
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Lỗi SQL khi lấy danh sách người dùng sắp xếp", e);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Lỗi không mong muốn khi lấy danh sách người dùng sắp xếp", e);
        }
        return users;
    }

    // Phương thức gọi tất cả giới tính
    public List<String> getAllGenders() {
        List<String> products = new ArrayList<>();
        String sql = "SELECT DISTINCT gender FROM Users"; // Truy vấn lấy các category từ bảng Categories
        try (Connection conn = new DBContext().getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                products.add(rs.getString("gender"));
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Lỗi SQL khi lấy danh sách gender", e);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Lỗi SQL khi lấy danh sách gender", e);
        }
        return products;
    }

    // Phương thức gọi tất cả giới tính
    public List<String> getAllRoles() {
        List<String> products = new ArrayList<>();
        String sql = "SELECT DISTINCT role_name FROM Roles"; // Truy vấn lấy các category từ bảng Categories
        try (Connection conn = new DBContext().getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                products.add(rs.getString("role_name"));
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Lỗi SQL khi lấy danh sách role", e);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Lỗi SQL khi lấy danh sách role", e);
        }
        return products;
    }

    // Phương thức gọi tất cả giới tính
    public List<String> getAllStatuses() {
        List<String> users = new ArrayList<>();
        String sql = "SELECT DISTINCT is_active FROM Users"; // Truy vấn lấy các category từ bảng Categories
        try (Connection conn = new DBContext().getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                users.add(rs.getString("is_active"));
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Lỗi SQL khi lấy danh sách status", e);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Lỗi SQL khi lấy danh sách status", e);
        }
        return users;
    }

    // Phương thức trong UserDAO để lọc sản phẩm theo giới tính
    public List<User> getFilteredUserGender(String gender) {
        List<User> users = new ArrayList<>();
        String sql = "SELECT u.[user_id],u.[full_name],u.[gender],"
                + "  u.[address],u.[email],u.[phone_number],r.[role_name],u.[is_active]"
                + "  FROM [Users] u Join [Roles] r ON u.[role_id] = r.[role_id]"
                + "  WHERE u.gender = ?";

        try (Connection conn = new DBContext().getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            // Set parameters: category and search keyword
            stmt.setString(1,gender); // Lọc theo tên danh mục

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    users.add(mapResultSetToUser(rs));
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Lỗi khi lọc sản phẩm theo gender và từ khóa", e);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Lỗi không mong muốn khi lọc người dùng", e);
        }
        return users;
    }

    // Phương thức trong UserDAO để lọc sản phẩm theo vài trò
    public List<User> getFilteredUserRole(String role_name) {
        List<User> users = new ArrayList<>();
        String sql = "SELECT u.[user_id],u.[full_name],u.[gender],"
                + "  u.[address],u.[email],u.[phone_number],r.[role_name],u.[is_active]"
                + "  FROM [Users] u Join [Roles] r ON u.[role_id] = r.[role_id]"
                + "  WHERE r.role_name LIKE ?";

        try (Connection conn = new DBContext().getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            // Set parameters: category and search keyword
            stmt.setString(1, "%" + role_name + "%"); // Lọc theo tên danh mục

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    users.add(mapResultSetToUser(rs));
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Lỗi khi lọc sản phẩm theo role và từ khóa", e);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Lỗi không mong muốn khi lọc người dùng", e);
        }
        return users;
    }

    // Phương thức trong UserDAO để lọc sản phẩm theo trạng thái
    public List<User> getFilteredUserStatus(String is_active) {
        List<User> users = new ArrayList<>();
        String sql = "SELECT u.[user_id],u.[full_name],u.[gender],"
                + "  u.[address],u.[email],u.[phone_number],r.[role_name],u.[is_active]"
                + "  FROM [Users] u Join [Roles] r ON u.[role_id] = r.[role_id]"
                + "  WHERE u.is_active LIKE ?";

        try (Connection conn = new DBContext().getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            // Set parameters: category and search keyword
            stmt.setString(1, "%" + is_active + "%"); // Lọc theo tên danh mục

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    users.add(mapResultSetToUser(rs));
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Lỗi khi lọc sản phẩm theo status và từ khóa", e);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Lỗi không mong muốn khi lọc người dùng", e);
        }
        return users;
    }

    public static void main(String[] args) {
        UserDAO uDao = new UserDAO();
        List<User> users = uDao.getAllUser();
        // Test xem tất cả người dùng
        for (User user : users) {
            System.out.println("---------------------------------------------------");
            System.out.println("Id: " + user.getUser_id());
            System.out.println("Full name: " + user.getFull_name());
            System.out.println("Gender: " + user.getGender());
            System.out.println("Address: " + user.getAddress());
            System.out.println("Email: " + user.getEmail());
            System.out.println("Mobile: " + user.getPhone_number());
            System.out.println("Role: " + user.getRole_name());
            System.out.println("Status: " + user.getIs_active());
        }

        // Test tìm kiếm người dùng 
        List<User> searchResults = uDao.searchUser("Nguyen");
        System.out.println("------------------------------------");
        System.out.println("Tìm kiếm 'Nguyen': " + searchResults.size() + " kết quả");
        
        // Test sắp xếp tên người dùng
        List<User> sortUserFullName = uDao.getAllUserSortedByName(true);
        System.out.println("------------------------------------");
        System.out.println("Sắp xếp tăng dần theo tên người dùng:");
        for (User user : sortUserFullName) {
            System.out.println(user.getFull_name());
        }
        
        //Test lọc theo giới tính
        String gender = "Male";
        List<User> filterGender = uDao.getFilteredUserGender(gender);
        System.out.println("------------------------------------");
        System.out.println("User with gender '" + gender + "': " + filterGender.size() + " kết quả");
    }
}

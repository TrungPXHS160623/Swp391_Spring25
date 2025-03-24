/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dao;

import Context.DBContext;
import Dto.UserDto;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author LENOVO
 */
public class UserDtoDao {

    private static final Logger LOGGER = Logger.getLogger(UserDao.class.getName());

    /**
     * Lấy danh sách tất cả người dùng với các trường: id, fullname, gender,
     * address, email, phone number, role và status.
     */
    public List<UserDto> getAllUserList(int page, int pageSize) {
        List<UserDto> userList = new ArrayList<>();
        String sql = "SELECT u.user_id, u.full_name, u.gender, u.address, u.email, u.phone_number, "
                + "r.role_name, "
                + "CASE WHEN u.is_active = 1 THEN 'Active' ELSE 'Inactive' END as status "
                + "FROM Users u "
                + "LEFT JOIN Roles r ON u.role_id = r.role_id "
                + "ORDER BY u.user_id "
                + "OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";

        try (Connection conn = new DBContext().getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            int offset = (page - 1) * pageSize;
            stmt.setInt(1, offset);
            stmt.setInt(2, pageSize);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int userId = rs.getInt("user_id");
                String fullName = rs.getString("full_name");
                String gender = rs.getString("gender");
                String address = rs.getString("address");
                String email = rs.getString("email");
                String phoneNumber = rs.getString("phone_number");
                String roleName = rs.getString("role_name");
                String status = rs.getString("status");

                UserDto dto = new UserDto(userId, fullName, gender, address, email, phoneNumber, roleName, status);
                userList.add(dto);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Lỗi SQL khi lấy danh sách User", e);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Lỗi kết nối CSDL khi lấy danh sách User", e);
        }
        return userList;
    }

    public List<UserDto> searchUserList(String keyword, int page, int pageSize) {
        List<UserDto> userList = new ArrayList<>();
        String sql = "SELECT u.user_id, u.full_name, u.gender, u.address, u.email, u.phone_number, "
                + "r.role_name, "
                + "CASE WHEN u.is_active = 1 THEN 'Active' ELSE 'Inactive' END as status "
                + "FROM Users u "
                + "LEFT JOIN Roles r ON u.role_id = r.role_id "
                + "WHERE u.full_name LIKE ? OR u.email LIKE ? OR u.phone_number LIKE ? "
                + "ORDER BY u.user_id "
                + "OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
        try (Connection conn = new DBContext().getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            String searchPattern = "%" + keyword + "%";
            stmt.setString(1, searchPattern);
            stmt.setString(2, searchPattern);
            stmt.setString(3, searchPattern);
            int offset = (page - 1) * pageSize;
            stmt.setInt(4, offset);
            stmt.setInt(5, pageSize);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int userId = rs.getInt("user_id");
                String fullName = rs.getString("full_name");
                String gender = rs.getString("gender");
                String address = rs.getString("address");
                String email = rs.getString("email");
                String phoneNumber = rs.getString("phone_number");
                String roleName = rs.getString("role_name");
                String status = rs.getString("status");

                UserDto dto = new UserDto(userId, fullName, gender, address, email, phoneNumber, roleName, status);
                userList.add(dto);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Lỗi SQL khi tìm kiếm User", e);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Lỗi kết nối CSDL khi tìm kiếm User", e);
        }
        return userList;
    }

    public List<UserDto> filterUserList(String gender, String role, String status, int page, int pageSize) {
        List<UserDto> userList = new ArrayList<>();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT u.user_id, u.full_name, u.gender, u.address, u.email, u.phone_number, ");
        sql.append("r.role_name, ");
        sql.append("CASE WHEN u.is_active = 1 THEN 'Active' ELSE 'Inactive' END as status ");
        sql.append("FROM Users u ");
        sql.append("LEFT JOIN Roles r ON u.role_id = r.role_id ");
        sql.append("WHERE 1=1 ");

        if (gender != null && !gender.trim().isEmpty()) {
            sql.append(" AND u.gender = ? ");
        }
        if (role != null && !role.trim().isEmpty()) {
            sql.append(" AND r.role_name = ? ");
        }
        if (status != null && !status.trim().isEmpty()) {
            sql.append(" AND (CASE WHEN u.is_active = 1 THEN 'Active' ELSE 'Inactive' END) = ? ");
        }

        sql.append("ORDER BY u.user_id ");
        sql.append("OFFSET ? ROWS FETCH NEXT ? ROWS ONLY");

        try (Connection conn = new DBContext().getConnection(); PreparedStatement stmt = conn.prepareStatement(sql.toString())) {

            int index = 1;
            if (gender != null && !gender.trim().isEmpty()) {
                stmt.setString(index++, gender);
            }
            if (role != null && !role.trim().isEmpty()) {
                stmt.setString(index++, role);
            }
            if (status != null && !status.trim().isEmpty()) {
                stmt.setString(index++, status);
            }
            int offset = (page - 1) * pageSize;
            stmt.setInt(index++, offset);
            stmt.setInt(index++, pageSize);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int userId = rs.getInt("user_id");
                String fullName = rs.getString("full_name");
                String genderVal = rs.getString("gender");
                String addressVal = rs.getString("address");
                String emailVal = rs.getString("email");
                String phoneNumber = rs.getString("phone_number");
                String roleName = rs.getString("role_name");
                String statusVal = rs.getString("status");

                UserDto dto = new UserDto(userId, fullName, genderVal, addressVal, emailVal, phoneNumber, roleName, statusVal);
                userList.add(dto);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Lỗi SQL khi lọc User", e);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Lỗi kết nối CSDL khi lọc User", e);
        }
        return userList;
    }

    public List<UserDto> sortUserList(String sortField, String sortDirection, int page, int pageSize) {
        List<UserDto> userList = new ArrayList<>();

        // Danh sách các trường được phép sắp xếp
        List<String> allowedSortFields = List.of("user_id", "full_name", "gender", "address", "email", "phone_number", "role_name", "status");
        // Nếu sortField không hợp lệ, mặc định sắp xếp theo user_id
        if (!allowedSortFields.contains(sortField)) {
            sortField = "user_id";
        }
        // Nếu sortDirection không phải ASC hoặc DESC, mặc định ASC
        if (!"DESC".equalsIgnoreCase(sortDirection)) {
            sortDirection = "ASC";
        }

        String sql = "SELECT u.user_id, u.full_name, u.gender, u.address, u.email, u.phone_number, "
                + "r.role_name, "
                + "CASE WHEN u.is_active = 1 THEN 'Active' ELSE 'Inactive' END as status "
                + "FROM Users u "
                + "LEFT JOIN Roles r ON u.role_id = r.role_id "
                + "ORDER BY " + sortField + " " + sortDirection + " "
                + "OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";

        try (Connection conn = new DBContext().getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            int offset = (page - 1) * pageSize;
            stmt.setInt(1, offset);
            stmt.setInt(2, pageSize);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int userId = rs.getInt("user_id");
                String fullName = rs.getString("full_name");
                String genderVal = rs.getString("gender");
                String addressVal = rs.getString("address");
                String emailVal = rs.getString("email");
                String phoneNumber = rs.getString("phone_number");
                String roleName = rs.getString("role_name");
                String statusVal = rs.getString("status");

                UserDto dto = new UserDto(userId, fullName, genderVal, addressVal, emailVal, phoneNumber, roleName, statusVal);
                userList.add(dto);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Lỗi SQL khi sắp xếp User", e);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Lỗi kết nối CSDL khi sắp xếp User", e);
        }
        return userList;
    }

    public int getUserCount(String keyword, String gender, String role, String status) {
        int count = 0;
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT COUNT(*) as total FROM Users u ");
        sql.append("LEFT JOIN Roles r ON u.role_id = r.role_id ");
        sql.append("WHERE 1=1 ");

        if (keyword != null && !keyword.trim().isEmpty()) {
            sql.append("AND (u.full_name LIKE ? OR u.email LIKE ? OR u.phone_number LIKE ?) ");
        }
        if (gender != null && !gender.trim().isEmpty()) {
            sql.append("AND u.gender = ? ");
        }
        if (role != null && !role.trim().isEmpty()) {
            sql.append("AND r.role_name = ? ");
        }
        if (status != null && !status.trim().isEmpty()) {
            sql.append("AND (CASE WHEN u.is_active = 1 THEN 'Active' ELSE 'Inactive' END) = ? ");
        }

        try (Connection conn = new DBContext().getConnection(); PreparedStatement stmt = conn.prepareStatement(sql.toString())) {

            int index = 1;
            if (keyword != null && !keyword.trim().isEmpty()) {
                String searchPattern = "%" + keyword.trim() + "%";
                stmt.setString(index++, searchPattern);
                stmt.setString(index++, searchPattern);
                stmt.setString(index++, searchPattern);
            }
            if (gender != null && !gender.trim().isEmpty()) {
                stmt.setString(index++, gender.trim());
            }
            if (role != null && !role.trim().isEmpty()) {
                stmt.setString(index++, role.trim());
            }
            if (status != null && !status.trim().isEmpty()) {
                stmt.setString(index++, status.trim());
            }

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                count = rs.getInt("total");
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Lỗi SQL khi đếm số user", e);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Lỗi kết nối CSDL khi đếm số user", e);
        }
        return count;
    }

    // --- Các phương thức mới hỗ trợ cho chức năng Thêm, Sửa, Xem một user ---
    // Mapping role từ String thành role_id
    private int mapRoleToId(String role) {
        if (role == null) {
            return 2; // mặc định User
        }
        switch (role.trim()) {
            case "Admin":
                return 1;
            case "Seller":
                return 3;
            case "Marketing":
                return 4;
            default:
                return 2; // User
        }
    }

    // Mapping status từ String thành boolean (Active = true, NotActive = false)
    private boolean mapStatusToBoolean(String status) {
        return status != null && status.trim().equalsIgnoreCase("Active");
    }

    // Thêm 1 user mới
    public boolean addUser(UserDto user) {
        String sql = "INSERT INTO Users (avatar_url, full_name, gender, email, password_hash, phone_number, role_id, address, is_active, created_at) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = new DBContext().getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, user.getAvatarUrl());
            stmt.setString(2, user.getFullName());
            stmt.setString(3, user.getGender());
            stmt.setString(4, user.getEmail());
            stmt.setString(5, user.getPassword()); // Giả sử đã mã hóa nếu cần
            stmt.setString(6, user.getPhoneNumber());
            stmt.setInt(7, mapRoleToId(user.getRole()));
            stmt.setString(8, user.getAddress());
            stmt.setBoolean(9, mapStatusToBoolean(user.getStatus()));
            stmt.setTimestamp(10, new Timestamp(System.currentTimeMillis()));

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Lỗi SQL khi thêm user", e);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Lỗi kết nối CSDL khi thêm user", e);
        }
        return false;
    }

    // Sửa 1 user được chọn (dựa vào user_id)
    public boolean updateUser(UserDto user) {
        String sql = "UPDATE Users SET avatar_url = ?, full_name = ?, gender = ?, email = ?, password_hash = ?, "
                + "phone_number = ?, role_id = ?, address = ?, is_active = ?, updated_at = ? "
                + "WHERE user_id = ?";
        try (Connection conn = new DBContext().getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, user.getAvatarUrl());
            stmt.setString(2, user.getFullName());
            stmt.setString(3, user.getGender());
            stmt.setString(4, user.getEmail());
            stmt.setString(5, user.getPassword()); // Giả sử đã mã hóa nếu cần
            stmt.setString(6, user.getPhoneNumber());
            stmt.setInt(7, mapRoleToId(user.getRole()));
            stmt.setString(8, user.getAddress());
            stmt.setBoolean(9, mapStatusToBoolean(user.getStatus()));
            stmt.setTimestamp(10, new Timestamp(System.currentTimeMillis()));
            stmt.setInt(11, user.getUserId());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Lỗi SQL khi sửa user", e);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Lỗi kết nối CSDL khi sửa user", e);
        }
        return false;
    }

    // Xem 1 user được chọn theo userId
    public UserDto getUserById(int userId) {
        String sql = "SELECT u.user_id, u.avatar_url, u.full_name, u.gender, u.email, u.password_hash, "
                + "u.phone_number, r.role_name, u.address, "
                + "CASE WHEN u.is_active = 1 THEN 'Active' ELSE 'NotActive' END as status "
                + "FROM Users u "
                + "LEFT JOIN Roles r ON u.role_id = r.role_id "
                + "WHERE u.user_id = ?";
        try (Connection conn = new DBContext().getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String avatarUrl = rs.getString("avatar_url");
                String fullName = rs.getString("full_name");
                String gender = rs.getString("gender");
                String email = rs.getString("email");
                String password = rs.getString("password_hash");
                String phone = rs.getString("phone_number");
                String roleName = rs.getString("role_name");
                String address = rs.getString("address");
                String status = rs.getString("status");

                return new UserDto(userId, avatarUrl, fullName, gender, email, password, phone, roleName, address, status);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Lỗi SQL khi lấy thông tin user", e);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Lỗi kết nối CSDL khi lấy thông tin user", e);
        }
        return null;
    }

    public static void main(String[] args) {
        UserDtoDao userDao = new UserDtoDao();
        int page = 1;      // Số trang cần lấy
        int pageSize = 7;  // Số bản ghi mỗi trang

        List<UserDto> userList = userDao.getAllUserList(page, pageSize);

        if (userList.isEmpty()) {
            System.out.println("Không có người dùng nào được tìm thấy.");
        } else {
            System.out.println("Danh sách User (Trang " + page + "):");
            for (UserDto user : userList) {
                System.out.println("----------------------------------");
                System.out.println("User ID: " + user.getUserId());
                System.out.println("Full Name: " + user.getFullName());
                System.out.println("Gender: " + user.getGender());
                System.out.println("Address: " + user.getAddress());
                System.out.println("Email: " + user.getEmail());
                System.out.println("Phone Number: " + user.getPhoneNumber());
                System.out.println("Role: " + user.getRole());
                System.out.println("Status: " + user.getStatus());
            }
        }

        // --- Test thêm 1 user ---
        UserDto newUser = new UserDto(
                "https://example.com/avatar.jpg",
                "Nguyễn Văn A",
                "Male",
                "nguyenvana@example.com",
                "password123", // Giả sử đã mã hóa nếu cần
                "0123456789",
                "User",
                "Hà Nội",
                "Active"
        );
        boolean addResult = userDao.addUser(newUser);
        System.out.println("Kết quả thêm user: " + (addResult ? "Thành công" : "Thất bại"));

        // Giả sử sau khi thêm, bạn có thể lấy lại user mới thêm bằng cách tìm theo email hoặc lấy user cuối cùng (tùy vào logic của bạn).
        // Ở đây, chúng ta sẽ test getUserById với một user_id cố định (cần thay đổi theo dữ liệu của bạn)
        int testUserId = 1; // thay đổi theo dữ liệu trong DB của bạn

        // --- Test xem 1 user được chọn ---
        UserDto existingUser = userDao.getUserById(testUserId);
        if (existingUser != null) {
            System.out.println("Thông tin user có id " + testUserId + ":");
            System.out.println(existingUser);
        } else {
            System.out.println("Không tìm thấy user có id " + testUserId);
        }

        // --- Test sửa 1 user ---
        if (existingUser != null) {
            // Giả sử cập nhật thông tin: đổi tên, email, trạng thái,...
            existingUser.setFullName("Nguyễn Văn B");
            existingUser.setEmail("nguyenvanb@example.com");
            existingUser.setStatus("NotActive");
            boolean updateResult = userDao.updateUser(existingUser);
            System.out.println("Kết quả cập nhật user: " + (updateResult ? "Thành công" : "Thất bại"));

            // Kiểm tra lại thông tin sau khi sửa
            UserDto updatedUser = userDao.getUserById(testUserId);
            System.out.println("Thông tin user sau khi cập nhật:");
            System.out.println(updatedUser);
        }
    }
}

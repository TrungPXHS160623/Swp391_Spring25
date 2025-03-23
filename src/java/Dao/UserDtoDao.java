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
    }
}

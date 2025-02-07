package dao;

import model.Customer;
import utils.DatabaseUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAO {

    // Lấy danh sách khách hàng với phân trang, lọc, tìm kiếm
    public List<Customer> getAllCustomers(String statusFilter, String searchQuery, String sortBy, int page, int recordsPerPage) {
        List<Customer> customers = new ArrayList<>();
        String query = "SELECT user_id, email, full_name, gender, phone_number, address, status, created_at, updated_at " +
                       "FROM Users WHERE role_id = 2";

        if (statusFilter != null && !statusFilter.isEmpty()) {
            query += " AND status = ?";
        }

        if (searchQuery != null && !searchQuery.isEmpty()) {
            query += " AND (full_name LIKE ? OR email LIKE ? OR phone_number LIKE ?)";
        }

        if (sortBy != null && !sortBy.isEmpty()) {
            query += " ORDER BY " + sortBy;
        } else {
            query += " ORDER BY full_name";
        }

        // Thêm phân trang vào query
        query += " LIMIT ?, ?";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            int paramIndex = 1;
            if (statusFilter != null && !statusFilter.isEmpty()) {
                stmt.setString(paramIndex++, statusFilter);
            }
            if (searchQuery != null && !searchQuery.isEmpty()) {
                String searchPattern = "%" + searchQuery + "%";
                stmt.setString(paramIndex++, searchPattern);
                stmt.setString(paramIndex++, searchPattern);
                stmt.setString(paramIndex++, searchPattern);
            }

            // Phân trang: đặt giá trị cho các tham số LIMIT
            stmt.setInt(paramIndex++, (page - 1) * recordsPerPage);
            stmt.setInt(paramIndex++, recordsPerPage);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                customers.add(new Customer(
                    rs.getInt("user_id"),
                    rs.getString("email"),
                    rs.getString("full_name"),
                    rs.getString("gender"),
                    rs.getString("phone_number"),
                    rs.getString("address"),
                    rs.getString("status"),
                    rs.getTimestamp("created_at"),
                    rs.getTimestamp("updated_at")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return customers;
    }

    // Lấy tổng số khách hàng (để tính số trang)
    public int getCustomersCount(String statusFilter, String searchQuery) {
        int count = 0;
        String query = "SELECT COUNT(*) FROM Users WHERE role_id = 2";

        if (statusFilter != null && !statusFilter.isEmpty()) {
            query += " AND status = ?";
        }

        if (searchQuery != null && !searchQuery.isEmpty()) {
            query += " AND (full_name LIKE ? OR email LIKE ? OR phone_number LIKE ?)";
        }

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            int paramIndex = 1;
            if (statusFilter != null && !statusFilter.isEmpty()) {
                stmt.setString(paramIndex++, statusFilter);
            }
            if (searchQuery != null && !searchQuery.isEmpty()) {
                String searchPattern = "%" + searchQuery + "%";
                stmt.setString(paramIndex++, searchPattern);
                stmt.setString(paramIndex++, searchPattern);
                stmt.setString(paramIndex++, searchPattern);
            }

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }
}

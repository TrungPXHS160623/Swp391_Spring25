//package dao;
//
//
//import java.util.logging.Level;
//import java.util.logging.Logger;
//import utils.DatabaseUtil;
//import utils.DatabaseUtil;
//import model.Customer;
//import model.CustomerHistory;
//import java.sql.*;
//import java.util.ArrayList;
//import java.util.List;
//
//public class CustomerDAO {
//
////    Connection conn = DatabaseUtil.getConnection();
////
////    public CustomerDAO(Connection conn) {
////        this.conn = conn;
////    }
////    //getallusers
////    public List<Customer> getAllCustomers(String status, String search, String sort, int page, int recordsPerPage) throws SQLException {
////        List<Customer> customers = new ArrayList<>();
////        String sql = "select * from users where role_id = ? ";
////
////        try (Connection conn = new DatabaseUtil().getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
////            ps.setInt(1, Integer.parseInt(status));
////            ps.setString(2, "%" + search + "%");
////            ps.setString(3, "%" + search + "%");
////            ps.setString(4, "%" + search + "%");
////            ps.setInt(5, (page - 1) * recordsPerPage);
////            ps.setInt(6, recordsPerPage);
////
////            try (ResultSet rs = ps.executeQuery()) {
////                while (rs.next()) {
////                    customers.add(mapResultSetToCustomer(rs));
////                }
////            }
////        }
////        return customers;
////    }
//    //getallusers
//    private static final Logger LOGGER = Logger.getLogger(CustomerDAO.class.getName());
//
//    // Lấy danh sách tất cả khách hàng (role_id = 2)
//    public List<Customer> getAllCustomers() {
//        List<Customer> customers = new ArrayList<>();
//        String sql = "SELECT * FROM Users WHERE role_id = 2";
//        try (Connection conn = new DatabaseUtil().getConnection();
//             PreparedStatement stmt = conn.prepareStatement(sql);
//             ResultSet rs = stmt.executeQuery()) {
//            while (rs.next()) {
//                customers.add(extractCustomer(rs));
//            }
//        } catch (SQLException e) {
//            LOGGER.log(Level.SEVERE, "Lỗi SQL khi lấy danh sách khách hàng", e);
//        } catch (Exception e) {
//            LOGGER.log(Level.SEVERE, "Lỗi kết nối CSDL", e);
//        }
//        return customers;
//    }
//
//    // Chuyển dữ liệu từ ResultSet thành đối tượng Customer
//    private Customer extractCustomer(ResultSet rs) throws SQLException {
//        return new Customer(
//            rs.getInt("user_id"),
//            rs.getString("email"),
//            rs.getString("full_name"),
//            rs.getString("phone_number"),
//            rs.getString("address"),
//            rs.getInt("is_active")
//        );
//    }
//
////    public int getCustomersCount(String status, String search) throws SQLException {
////        String sql = "SELECT COUNT(*) FROM Customers WHERE isActive = ? AND (full_name LIKE ? OR email LIKE ? OR phone_number LIKE ?)";
////        try (Connection conn = new DatabaseUtil().getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
////            ps.setInt(1, Integer.parseInt(status));
////            ps.setString(2, "%" + search + "%");
////            ps.setString(3, "%" + search + "%");
////            ps.setString(4, "%" + search + "%");
////
////            try (ResultSet rs = ps.executeQuery()) {
////                if (rs.next()) {
////                    return rs.getInt(1);
////                }
////            }
////        }
////        return 0;
////    }
////
////    public List<CustomerHistory> getCustomerHistory(int customerId) throws SQLException {
////        List<CustomerHistory> historyList = new ArrayList<>();
////        String sql = "SELECT * FROM CustomerHistory WHERE customer_id = ? ORDER BY updated_date DESC";
////
////        try (Connection conn = new DatabaseUtil().getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
////            ps.setInt(1, customerId);
////            try (ResultSet rs = ps.executeQuery()) {
////                while (rs.next()) {
////                    historyList.add(new CustomerHistory(
////                            rs.getInt("id"),
////                            rs.getInt("customer_id"),
////                            rs.getString("email"),
////                            rs.getString("full_name"),
////                            rs.getInt("gender"),
////                            rs.getString("phone_number"),
////                            rs.getString("address"),
////                            rs.getString("updated_by"),
////                            rs.getTimestamp("updated_date")
////                    ));
////                }
////            }
////        }
////        return historyList;
////    }
////
////    public Customer getCustomerById(int customerId) throws SQLException {
////        String sql = "SELECT * FROM Customers WHERE user_id = ?";
////        try (Connection conn = new DatabaseUtil().getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
////            ps.setInt(1, customerId);
////            try (ResultSet rs = ps.executeQuery()) {
////                if (rs.next()) {
////                    return mapResultSetToCustomer(rs);
////                }
////            }
////        }
////        return null; // Trả về null nếu không tìm thấy
////    }
////
////    private Customer mapResultSetToCustomer(ResultSet rs) throws SQLException {
////        return new Customer(
////                rs.getInt("user_id"),
////                rs.getInt("google_id"),
////                rs.getString("email"),
////                rs.getString("full_name"),
////                rs.getString("avatar_url"),
////                rs.getString("phone_number"),
////                rs.getString("address"),
////                rs.getInt("role"),
////                rs.getTimestamp("created_at"),
////                rs.getTimestamp("updated_at"),
////                rs.getInt("is_active"),
////                rs.getInt("gender")
////        );
////    }
//    public static void main(String[] args) {
//        CustomerDAO customerDao = new CustomerDAO();
//        List<Customer> customers = customerDao.getAllCustomers();
//
//        if (customers.isEmpty()) {
//            System.out.println("Không có khách hàng nào.");
//        } else {
//            for (Customer customer : customers) {
//                System.out.println("ID: " + customer.getUserId());
//                System.out.println("Email: " + customer.getEmail());
//                System.out.println("Full Name: " + customer.getFullName());
//                System.out.println("Phone: " + customer.getPhoneNumber());
//                System.out.println("Address: " + customer.getAddress());
//                System.out.println("Status: " + customer.getIsActive());
//                System.out.println("----------------------------");
//            }
//        }
//    }
//}

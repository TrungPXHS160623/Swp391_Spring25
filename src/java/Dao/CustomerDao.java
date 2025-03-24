/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dao;

import Context.DBContext;
import Dto.CustomerDto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Admin
 */
public class CustomerDao extends DBContext {
    
    /**
     * Get total number of customers based on filter criteria
     */
    public int getTotalCustomers(String searchText, String status) {
        int total = 0;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            conn = getConnection();
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT COUNT(*) FROM Users WHERE role_id = 2 ");
            
            // Add search condition if not empty
            if (searchText != null && !searchText.isEmpty()) {
                sql.append("AND (full_name LIKE ? OR email LIKE ? OR phone_number LIKE ?) ");
            }
            
            // Add status filter if specified
            if (status != null && !status.isEmpty()) {
                if (status.equals("active")) {
                    sql.append("AND is_active = 1 ");
                } else if (status.equals("inactive")) {
                    sql.append("AND is_active = 0 ");
                } else if (status.equals("verified")) {
                    sql.append("AND is_verified = 1 ");
                } else if (status.equals("unverified")) {
                    sql.append("AND is_verified = 0 ");
                }
            }
            
            ps = conn.prepareStatement(sql.toString());
            
            if (searchText != null && !searchText.isEmpty()) {
                String likePattern = "%" + searchText + "%";
                ps.setString(1, likePattern);
                ps.setString(2, likePattern);
                ps.setString(3, likePattern);
            }
            
            rs = ps.executeQuery();
            if (rs.next()) {
                total = rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println("Error getting total customers: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Database connection error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            closeResources(conn, ps, rs);
        }
        
        return total;
    }
    
    /**
     * Get customers with pagination, filtering and sorting
     */
    public List<CustomerDto> getCustomers(int pageNumber, int pageSize, String searchText, 
                                          String status, String sortBy, String sortOrder) {
        List<CustomerDto> customers = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            conn = getConnection();
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT user_id, full_name, gender, email, phone_number, address, avatar_url, is_active, is_verified, created_at, last_login ")
               .append("FROM Users ")
               .append("WHERE role_id = 2 "); // Assuming role_id 2 is for customers
            
            // Add search condition if not empty
            if (searchText != null && !searchText.isEmpty()) {
                sql.append("AND (full_name LIKE ? OR email LIKE ? OR phone_number LIKE ?) ");
            }
            
            // Add status filter if specified
            if (status != null && !status.isEmpty()) {
                if (status.equals("active")) {
                    sql.append("AND is_active = 1 ");
                } else if (status.equals("inactive")) {
                    sql.append("AND is_active = 0 ");
                } else if (status.equals("verified")) {
                    sql.append("AND is_verified = 1 ");
                } else if (status.equals("unverified")) {
                    sql.append("AND is_verified = 0 ");
                }
            }
            
            // Add sorting
            if (sortBy != null && !sortBy.isEmpty()) {
                switch (sortBy) {
                    case "fullName":
                        sql.append("ORDER BY full_name ");
                        break;
                    case "email":
                        sql.append("ORDER BY email ");
                        break;
                    case "phone":
                        sql.append("ORDER BY phone_number ");
                        break;
                    case "status":
                        sql.append("ORDER BY is_active ");
                        break;
                    default:
                        sql.append("ORDER BY user_id ");
                        break;
                }
                
                if (sortOrder != null && sortOrder.equalsIgnoreCase("desc")) {
                    sql.append("DESC ");
                } else {
                    sql.append("ASC ");
                }
            } else {
                sql.append("ORDER BY user_id ASC ");
            }
            
            // Add pagination
            sql.append("OFFSET ? ROWS FETCH NEXT ? ROWS ONLY");
            
            ps = conn.prepareStatement(sql.toString());
            
            int paramIndex = 1;
            
            if (searchText != null && !searchText.isEmpty()) {
                String likePattern = "%" + searchText + "%";
                ps.setString(paramIndex++, likePattern);
                ps.setString(paramIndex++, likePattern);
                ps.setString(paramIndex++, likePattern);
            }
            
            ps.setInt(paramIndex++, (pageNumber - 1) * pageSize);
            ps.setInt(paramIndex, pageSize);
            
            rs = ps.executeQuery();
            while (rs.next()) {
                CustomerDto customer = new CustomerDto();
                customer.setUserId(rs.getInt("user_id"));
                customer.setFullName(rs.getString("full_name"));
                customer.setGender(rs.getString("gender"));
                customer.setEmail(rs.getString("email"));
                customer.setPhoneNumber(rs.getString("phone_number"));
                customer.setAddress(rs.getString("address"));
                customer.setAvatarUrl(rs.getString("avatar_url"));
                customer.setIsActive(rs.getBoolean("is_active"));
                customer.setIsVerified(rs.getBoolean("is_verified"));
                customer.setCreatedAt(rs.getTimestamp("created_at"));
                customer.setLastLogin(rs.getTimestamp("last_login"));
                
                customers.add(customer);
            }
        } catch (SQLException e) {
            System.out.println("Error getting customers: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Database connection error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            closeResources(conn, ps, rs);
        }
        
        return customers;
    }
    
    /**
     * Get a customer by ID
     */
    public CustomerDto getCustomerById(int userId) {
        CustomerDto customer = null;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            conn = getConnection();
            String sql = "SELECT user_id, full_name, gender, email, phone_number, address, avatar_url, is_active, is_verified, created_at, last_login "
                       + "FROM Users WHERE user_id = ? AND role_id = 2";
            
            ps = conn.prepareStatement(sql);
            ps.setInt(1, userId);
            
            rs = ps.executeQuery();
            if (rs.next()) {
                customer = new CustomerDto();
                customer.setUserId(rs.getInt("user_id"));
                customer.setFullName(rs.getString("full_name"));
                customer.setGender(rs.getString("gender"));
                customer.setEmail(rs.getString("email"));
                customer.setPhoneNumber(rs.getString("phone_number"));
                customer.setAddress(rs.getString("address"));
                customer.setAvatarUrl(rs.getString("avatar_url"));
                customer.setIsActive(rs.getBoolean("is_active"));
                customer.setIsVerified(rs.getBoolean("is_verified"));
                customer.setCreatedAt(rs.getTimestamp("created_at"));
                customer.setLastLogin(rs.getTimestamp("last_login"));
            }
        } catch (SQLException e) {
            System.out.println("Error getting customer by ID: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Database connection error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            closeResources(conn, ps, rs);
        }
        
        return customer;
    }
    
    /**
     * Update customer information
     */
    public boolean updateCustomer(CustomerDto customer) {
        Connection conn = null;
        PreparedStatement ps = null;
        boolean success = false;
        
        try {
            conn = getConnection();
            String sql = "UPDATE Users SET full_name = ?, gender = ?, email = ?, phone_number = ?, "
                    + "address = ?, is_active = ?, is_verified = ?, updated_at = GETDATE() "
                    + "WHERE user_id = ? AND role_id = 2";
            
            ps = conn.prepareStatement(sql);
            ps.setString(1, customer.getFullName());
            ps.setString(2, customer.getGender());
            ps.setString(3, customer.getEmail());
            ps.setString(4, customer.getPhoneNumber());
            ps.setString(5, customer.getAddress());
            ps.setBoolean(6, customer.isIsActive());
            ps.setBoolean(7, customer.isIsVerified());
            ps.setInt(8, customer.getUserId());
            
            int rowsAffected = ps.executeUpdate();
            success = (rowsAffected > 0);
            
        } catch (SQLException e) {
            System.out.println("Error updating customer: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Database connection error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            closeResources(conn, ps, null);
        }
        
        return success;
    }
    
    /**
     * Update customer status (activate/deactivate)
     */
    public boolean updateCustomerStatus(int userId, boolean isActive) {
        Connection conn = null;
        PreparedStatement ps = null;
        boolean success = false;
        
        try {
            conn = getConnection();
            String sql = "UPDATE Users SET is_active = ?, updated_at = GETDATE() "
                    + "WHERE user_id = ? AND role_id = 2";
            
            ps = conn.prepareStatement(sql);
            ps.setBoolean(1, isActive);
            ps.setInt(2, userId);
            
            int rowsAffected = ps.executeUpdate();
            success = (rowsAffected > 0);
            
        } catch (SQLException e) {
            System.out.println("Error updating customer status: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Database connection error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            closeResources(conn, ps, null);
        }
        
        return success;
    }
    
    /**
     * Add a new customer
     */
    public boolean addCustomer(String fullName, String gender, String email, String password, 
                              String phoneNumber, String address, boolean isActive, boolean isVerified) {
        Connection conn = null;
        PreparedStatement ps = null;
        boolean success = false;
        
        try {
            conn = getConnection();
            String sql = "INSERT INTO Users (full_name, gender, email, password_hash, phone_number, address, " +
                        "role_id, is_active, is_verified, created_at, updated_at) " +
                        "VALUES (?, ?, ?, ?, ?, ?, 2, ?, ?, GETDATE(), GETDATE())";
            
            ps = conn.prepareStatement(sql);
            ps.setString(1, fullName);
            ps.setString(2, gender);
            ps.setString(3, email);
            ps.setString(4, password); // In a real app, this should be hashed
            ps.setString(5, phoneNumber);
            ps.setString(6, address);
            ps.setBoolean(7, isActive);
            ps.setBoolean(8, isVerified);
            
            int rowsAffected = ps.executeUpdate();
            success = (rowsAffected > 0);
            
        } catch (SQLException e) {
            System.out.println("Error adding customer: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Database connection error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            closeResources(conn, ps, null);
        }
        
        return success;
    }
    
    /**
     * Close database resources
     */
    private void closeResources(Connection conn, PreparedStatement ps, ResultSet rs) {
        try {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            if (conn != null) conn.close();
        } catch (SQLException e) {
            System.out.println("Error closing resources: " + e.getMessage());
        }
    }
}

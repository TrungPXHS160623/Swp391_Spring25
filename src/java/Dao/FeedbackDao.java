/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dao;

import Context.DBContext;
import Dto.FeedbackDto;
import Entity.FeedbackImage;
import Entity.Review;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Admin
 */
public class FeedbackDao {
    
    public List<FeedbackDto> getFeedbackList(int page, int pageSize, String searchKeyword, 
                                            Integer productId, Integer rating, Boolean status, 
                                            String sortBy, String sortOrder) throws Exception {
        List<FeedbackDto> list = new ArrayList<>();
        int offset = (page - 1) * pageSize;
        
        try (Connection conn = new DBContext().getConnection()) {
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT r.review_id, r.product_id, p.product_name, r.user_id, ");
            sql.append("u.full_name, r.rating, r.comment, r.created_at, r.status ");
            sql.append("FROM Reviews r ");
            sql.append("INNER JOIN Products p ON r.product_id = p.product_id ");
            sql.append("INNER JOIN Users u ON r.user_id = u.user_id ");
            sql.append("WHERE 1=1 ");
            
            List<Object> params = new ArrayList<>();
            
            if (searchKeyword != null && !searchKeyword.trim().isEmpty()) {
                sql.append("AND (u.full_name LIKE ? OR r.comment LIKE ?) ");
                params.add("%" + searchKeyword + "%");
                params.add("%" + searchKeyword + "%");
            }
            
            if (productId != null) {
                sql.append("AND r.product_id = ? ");
                params.add(productId);
            }
            
            if (rating != null) {
                sql.append("AND r.rating = ? ");
                params.add(rating);
            }
            
            if (status != null) {
                sql.append("AND r.status = ? ");
                params.add(status);
            }
            
            // Sorting
            if (sortBy != null && !sortBy.isEmpty()) {
                switch (sortBy) {
                    case "fullName":
                        sql.append("ORDER BY u.full_name ");
                        break;
                    case "productName":
                        sql.append("ORDER BY p.product_name ");
                        break;
                    case "rating":
                        sql.append("ORDER BY r.rating ");
                        break;
                    case "status":
                        sql.append("ORDER BY r.status ");
                        break;
                    default:
                        sql.append("ORDER BY r.created_at ");
                        break;
                }
                sql.append(sortOrder != null && sortOrder.equalsIgnoreCase("desc") ? "DESC " : "ASC ");
            } else {
                sql.append("ORDER BY r.created_at DESC ");
            }
            
            // Pagination
            sql.append("OFFSET ? ROWS FETCH NEXT ? ROWS ONLY");
            params.add(offset);
            params.add(pageSize);
            
            PreparedStatement stmt = conn.prepareStatement(sql.toString());
            
            // Set parameters
            for (int i = 0; i < params.size(); i++) {
                stmt.setObject(i + 1, params.get(i));
            }
            
            ResultSet rs = stmt.executeQuery();
            
            Map<Integer, FeedbackDto> feedbackMap = new HashMap<>();
            
            while (rs.next()) {
                int reviewId = rs.getInt("review_id");
                
                FeedbackDto dto = new FeedbackDto(
                    reviewId,
                    rs.getInt("product_id"),
                    rs.getString("product_name"),
                    rs.getInt("user_id"),
                    rs.getString("full_name"),
                    rs.getInt("rating"),
                    rs.getString("comment"),
                    rs.getTimestamp("created_at"),
                    rs.getBoolean("status")
                );
                
                feedbackMap.put(reviewId, dto);
                list.add(dto);
            }
            
            // Get images for each feedback
            if (!feedbackMap.isEmpty()) {
                String imageSql = "SELECT review_id, image_url FROM FeedbackImages WHERE review_id IN (";
                StringBuilder placeholders = new StringBuilder();
                
                for (int i = 0; i < feedbackMap.keySet().size(); i++) {
                    placeholders.append(i > 0 ? ", ?" : "?");
                }
                imageSql += placeholders.toString() + ")";
                
                PreparedStatement imageStmt = conn.prepareStatement(imageSql);
                int paramIndex = 1;
                for (Integer reviewId : feedbackMap.keySet()) {
                    imageStmt.setInt(paramIndex++, reviewId);
                }
                
                ResultSet imageRs = imageStmt.executeQuery();
                while (imageRs.next()) {
                    int reviewId = imageRs.getInt("review_id");
                    String imageUrl = imageRs.getString("image_url");
                    
                    if (feedbackMap.containsKey(reviewId)) {
                        feedbackMap.get(reviewId).addImageUrl(imageUrl);
                    }
                }
            }
            
        } catch (SQLException e) {
            throw e;
        }
        
        return list;
    }
    
    public int getTotalFeedbacks(String searchKeyword, Integer productId, Integer rating, Boolean status) throws Exception {
        int count = 0;
        
        try (Connection conn = new DBContext().getConnection()) {
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT COUNT(*) as total FROM Reviews r ");
            sql.append("INNER JOIN Products p ON r.product_id = p.product_id ");
            sql.append("INNER JOIN Users u ON r.user_id = u.user_id ");
            sql.append("WHERE 1=1 ");
            
            List<Object> params = new ArrayList<>();
            
            if (searchKeyword != null && !searchKeyword.trim().isEmpty()) {
                sql.append("AND (u.full_name LIKE ? OR r.comment LIKE ?) ");
                params.add("%" + searchKeyword + "%");
                params.add("%" + searchKeyword + "%");
            }
            
            if (productId != null) {
                sql.append("AND r.product_id = ? ");
                params.add(productId);
            }
            
            if (rating != null) {
                sql.append("AND r.rating = ? ");
                params.add(rating);
            }
            
            if (status != null) {
                sql.append("AND r.status = ? ");
                params.add(status);
            }
            
            PreparedStatement stmt = conn.prepareStatement(sql.toString());
            
            // Set parameters
            for (int i = 0; i < params.size(); i++) {
                stmt.setObject(i + 1, params.get(i));
            }
            
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                count = rs.getInt("total");
            }
        } catch (SQLException e) {
            throw e;
        }
        
        return count;
    }
    
    public List<Product> getAllProducts() throws Exception {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT product_id, product_name FROM Products ORDER BY product_name";
        
        try (Connection conn = new DBContext().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                Product product = new Product();
                product.setProductId(rs.getInt("product_id"));
                product.setProductName(rs.getString("product_name"));
                products.add(product);
            }
        } catch (SQLException e) {
            throw e;
        }
        
        return products;
    }
    
    public FeedbackDto getFeedbackById(int reviewId) throws Exception {
        FeedbackDto feedback = null;
        
        try (Connection conn = new DBContext().getConnection()) {
            String sql = "SELECT r.review_id, r.product_id, p.product_name, r.user_id, " +
                         "u.full_name, r.rating, r.comment, r.created_at, r.status " +
                         "FROM Reviews r " +
                         "INNER JOIN Products p ON r.product_id = p.product_id " +
                         "INNER JOIN Users u ON r.user_id = u.user_id " +
                         "WHERE r.review_id = ?";
            
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, reviewId);
            
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                feedback = new FeedbackDto(
                    rs.getInt("review_id"),
                    rs.getInt("product_id"),
                    rs.getString("product_name"),
                    rs.getInt("user_id"),
                    rs.getString("full_name"),
                    rs.getInt("rating"),
                    rs.getString("comment"),
                    rs.getTimestamp("created_at"),
                    rs.getBoolean("status")
                );
                
                // Get images
                String imageSql = "SELECT image_url FROM FeedbackImages WHERE review_id = ?";
                PreparedStatement imageStmt = conn.prepareStatement(imageSql);
                imageStmt.setInt(1, reviewId);
                
                ResultSet imageRs = imageStmt.executeQuery();
                while (imageRs.next()) {
                    feedback.addImageUrl(imageRs.getString("image_url"));
                }
            }
        } catch (SQLException e) {
            throw e;
        }
        
        return feedback;
    }
    
    public boolean updateFeedbackStatus(int reviewId, boolean status) throws Exception {
        boolean updated = false;
        try (Connection conn = new DBContext().getConnection()) {
            String sql = "UPDATE Reviews SET status = ? WHERE review_id = ?";
            
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setBoolean(1, status);
            stmt.setInt(2, reviewId);
            
            int affectedRows = stmt.executeUpdate();
            updated = (affectedRows > 0);
        } catch (SQLException e) {
            throw e;
        }
        
        return updated;
    }
    
    // Helper class to return simplified Product objects for dropdown menus
    public static class Product {
        private int productId;
        private String productName;
        
        public int getProductId() {
            return productId;
        }
        
        public void setProductId(int productId) {
            this.productId = productId;
        }
        
        public String getProductName() {
            return productName;
        }
        
        public void setProductName(String productName) {
            this.productName = productName;
        }
    }
}

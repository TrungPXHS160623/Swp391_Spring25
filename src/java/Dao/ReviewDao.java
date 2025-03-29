package Dao;

import Context.DBContext;
import Entity.Review;
import Entity.FeedbackImage;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ReviewDao extends DBContext {
    
    /**
     * Add a new review
     * @param review The review to add
     * @return The ID of the new review, or -1 if there was an error
     */
    public int addReview(Review review) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int generatedId = -1;
        
        try {
            conn = getConnection();
            String sql = "INSERT INTO [dbo].[Reviews] "
                    + "([product_id], [user_id], [rating], [comment], [created_at], [status]) "
                    + "VALUES (?, ?, ?, ?, ?, ?)";
            
            ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, review.getProductId());
            ps.setInt(2, review.getUserId());
            ps.setInt(3, review.getRating());
            ps.setString(4, review.getComment());
            ps.setTimestamp(5, review.getCreatedAt());
            ps.setBoolean(6, review.isStatus());
            
            int affectedRows = ps.executeUpdate();
            if (affectedRows > 0) {
                rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    generatedId = rs.getInt(1);
                }
            }
        } catch (Exception e) {
            System.out.println("Error adding review: " + e.getMessage());
            e.printStackTrace();
        } finally {
            closeResources(conn, ps, rs);
        }
        
        return generatedId;
    }
    
    /**
     * Add feedback image with better error handling
     * @param image The image to add
     * @return true if successful, false otherwise
     */
    public boolean addFeedbackImage(FeedbackImage image) {
        Connection conn = null;
        PreparedStatement ps = null;
        boolean success = false;
        
        try {
            conn = getConnection();
            String sql = "INSERT INTO [dbo].[FeedbackImages] "
                    + "([review_id], [image_url], [created_at]) "
                    + "VALUES (?, ?, ?)";
            
            ps = conn.prepareStatement(sql);
            ps.setInt(1, image.getReviewId());
            ps.setString(2, image.getImageUrl());
            ps.setTimestamp(3, image.getCreatedAt());
            
            int affectedRows = ps.executeUpdate();
            success = (affectedRows > 0);
            
            if (!success) {
                System.out.println("No rows affected when adding feedback image");
            }
        } catch (Exception e) {
            System.out.println("Error adding feedback image: " + e.getMessage());
            e.printStackTrace();
        } finally {
            closeResources(conn, ps, null);
        }
        
        return success;
    }
    
    /**
     * Get all images for a review
     * @param reviewId The ID of the review
     * @return List of feedback images
     */
    public List<FeedbackImage> getImagesByReviewId(int reviewId) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<FeedbackImage> images = new ArrayList<>();
        
        try {
            conn = getConnection();
            String sql = "SELECT [image_id], [review_id], [image_url], [created_at] "
                       + "FROM [dbo].[FeedbackImages] "
                       + "WHERE [review_id] = ?";
            
            ps = conn.prepareStatement(sql);
            ps.setInt(1, reviewId);
            
            rs = ps.executeQuery();
            while (rs.next()) {
                FeedbackImage image = new FeedbackImage();
                image.setImageId(rs.getInt("image_id"));
                image.setReviewId(rs.getInt("review_id"));
                image.setImageUrl(rs.getString("image_url"));
                image.setCreatedAt(rs.getTimestamp("created_at"));
                
                images.add(image);
            }
        } catch (Exception e) {
            System.out.println("Error getting feedback images: " + e.getMessage());
            e.printStackTrace();
        } finally {
            closeResources(conn, ps, rs);
        }
        
        return images;
    }
    
    /**
     * Check if a user has already reviewed a product
     * @param userId The user ID
     * @param productId The product ID
     * @return true if the user has already reviewed this product
     */
    public boolean hasUserReviewedProduct(int userId, int productId) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        boolean hasReviewed = false;
        
        try {
            conn = getConnection();
            String sql = "SELECT COUNT(*) FROM [dbo].[Reviews] "
                       + "WHERE [user_id] = ? AND [product_id] = ?";
            
            ps = conn.prepareStatement(sql);
            ps.setInt(1, userId);
            ps.setInt(2, productId);
            
            rs = ps.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                hasReviewed = true;
            }
        } catch (Exception e) {
            System.out.println("Error checking if user has reviewed product: " + e.getMessage());
            e.printStackTrace();
        } finally {
            closeResources(conn, ps, rs);
        }
        
        return hasReviewed;
    }
    
    /**
     * Check if a user has already reviewed a product in a specific order
     * @param userId The user ID
     * @param productId The product ID
     * @param orderId The order ID
     * @return The review ID if found, or -1 if not found
     */
    public int getUserReviewForProductInOrder(int userId, int productId, int orderId) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int reviewId = -1;
        
        try {
            conn = getConnection();
            String sql = "SELECT r.review_id FROM [dbo].[Reviews] r "
                       + "JOIN [dbo].[Order_Items] oi ON oi.product_id = r.product_id "
                       + "WHERE r.user_id = ? AND r.product_id = ? AND oi.order_id = ?";
            
            ps = conn.prepareStatement(sql);
            ps.setInt(1, userId);
            ps.setInt(2, productId);
            ps.setInt(3, orderId);
            
            rs = ps.executeQuery();
            if (rs.next()) {
                reviewId = rs.getInt("review_id");
            }
        } catch (Exception e) {
            System.out.println("Error checking user review for product in order: " + e.getMessage());
            e.printStackTrace();
        } finally {
            closeResources(conn, ps, rs);
        }
        
        return reviewId;
    }
    
    /**
     * Get a review by ID
     * @param reviewId The review ID
     * @return The review, or null if not found
     */
    public Review getReviewById(int reviewId) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Review review = null;
        
        try {
            conn = getConnection();
            String sql = "SELECT * FROM [dbo].[Reviews] WHERE [review_id] = ?";
            
            ps = conn.prepareStatement(sql);
            ps.setInt(1, reviewId);
            
            rs = ps.executeQuery();
            if (rs.next()) {
                review = new Review();
                review.setReviewId(rs.getInt("review_id"));
                review.setProductId(rs.getInt("product_id"));
                review.setUserId(rs.getInt("user_id"));
                review.setRating(rs.getInt("rating"));
                review.setComment(rs.getString("comment"));
                review.setCreatedAt(rs.getTimestamp("created_at"));
                review.setStatus(rs.getBoolean("status"));
            }
        } catch (Exception e) {
            System.out.println("Error getting review: " + e.getMessage());
            e.printStackTrace();
        } finally {
            closeResources(conn, ps, rs);
        }
        
        return review;
    }
    
    /**
     * Update an existing review
     * @param review The review to update
     * @return true if successful, false otherwise
     */
    public boolean updateReview(Review review) {
        Connection conn = null;
        PreparedStatement ps = null;
        boolean success = false;
        
        try {
            conn = getConnection();
            String sql = "UPDATE [dbo].[Reviews] SET "
                    + "[rating] = ?, [comment] = ?, [created_at] = ?, [status] = ? "
                    + "WHERE [review_id] = ?";
            
            ps = conn.prepareStatement(sql);
            ps.setInt(1, review.getRating());
            ps.setString(2, review.getComment());
            ps.setTimestamp(3, review.getCreatedAt());
            ps.setBoolean(4, review.isStatus());
            ps.setInt(5, review.getReviewId());
            
            int rowsAffected = ps.executeUpdate();
            success = (rowsAffected > 0);
        } catch (Exception e) {
            System.out.println("Error updating review: " + e.getMessage());
            e.printStackTrace();
        } finally {
            closeResources(conn, ps, null);
        }
        
        return success;
    }
    
    /**
     * Delete all images for a review
     * @param reviewId The review ID
     * @return true if successful, false otherwise
     */
    public boolean deleteReviewImages(int reviewId) {
        Connection conn = null;
        PreparedStatement ps = null;
        boolean success = false;
        
        try {
            conn = getConnection();
            String sql = "DELETE FROM [dbo].[FeedbackImages] WHERE [review_id] = ?";
            
            ps = conn.prepareStatement(sql);
            ps.setInt(1, reviewId);
            
            ps.executeUpdate();
            success = true; // Consider it successful even if no rows affected (might be no images)
        } catch (Exception e) {
            System.out.println("Error deleting review images: " + e.getMessage());
            e.printStackTrace();
        } finally {
            closeResources(conn, ps, null);
        }
        
        return success;
    }
    
    /**
     * Delete a review (used when validation fails)
     * @param reviewId The ID of the review to delete
     * @return true if successful, false otherwise
     */
    public boolean deleteReview(int reviewId) {
        Connection conn = null;
        PreparedStatement ps = null;
        boolean success = false;
        
        try {
            conn = getConnection();
            String sql = "DELETE FROM [dbo].[Reviews] WHERE [review_id] = ?";
            
            ps = conn.prepareStatement(sql);
            ps.setInt(1, reviewId);
            
            int rowsAffected = ps.executeUpdate();
            success = (rowsAffected > 0);
        } catch (Exception e) {
            System.out.println("Error deleting review: " + e.getMessage());
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

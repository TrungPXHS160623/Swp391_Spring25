package dao;

import utils.DatabaseUtil;
import model.Feedback;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FeedbackDAO {

    private static final Logger LOGGER = Logger.getLogger(FeedbackDAO.class.getName());

    // Lấy danh sách tất cả feedbacks (đã đổi ID sang tên user và sản phẩm)
    public List<Feedback> getAllFeedbacks(String search, String statusFilter, String sortColumn, int page, int limit) {
        List<Feedback> feedbacks = new ArrayList<>();
        String sql = "SELECT f.feedback_id, u.full_name, u.email, u.phone_number, p.product_name, "
                + "f.rating, f.feedback_content, f.media_url, f.status, f.created_at, f.updated_at "
                + "FROM Feedbacks f "
                + "JOIN Products p ON f.product_id = p.product_id "
                + "JOIN Users u ON f.user_id = u.user_id "
                + "WHERE (f.status = ? OR ? = '') "
                + "AND (u.full_name LIKE ? OR f.feedback_content LIKE ?) "
                + "ORDER BY " + sortColumn + " "
                + "OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";

        try (Connection conn = DatabaseUtil.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, statusFilter);
            stmt.setString(2, statusFilter);
            stmt.setString(3, "%" + search + "%");
            stmt.setString(4, "%" + search + "%");
            stmt.setInt(5, (page - 1) * limit);
            stmt.setInt(6, limit);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Feedback fb = new Feedback();
                    fb.setFeedback_id(rs.getInt("feedback_id"));
                    fb.setUser_name(rs.getString("full_name"));
                    fb.setEmail(rs.getString("email"));
                    fb.setPhone_number(rs.getString("phone_number"));
                    fb.setProduct_name(rs.getString("product_name"));
                    fb.setRating(rs.getInt("rating"));
                    fb.setFeedback_content(rs.getString("feedback_content"));
                    fb.setMedia_url(rs.getString("media_url"));
                    fb.setStatus(rs.getString("status"));
                    fb.setCreated_at(rs.getTimestamp("created_at"));
                    fb.setUpdated_at(rs.getTimestamp("updated_at"));
                    feedbacks.add(fb);
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Lỗi SQL khi lấy danh sách feedbacks", e);
        }
        return feedbacks;
    }

    public Feedback getFeedbackById(int feedbackId) {
        Feedback fb = null;
        String sql = "SELECT f.feedback_id, u.full_name, u.email, u.phone_number, p.product_name, "
                + "f.rating, f.feedback_content, f.media_url, f.status, f.created_at, f.updated_at "
                + "FROM Feedbacks f "
                + "JOIN Products p ON f.product_id = p.product_id "
                + "JOIN Users u ON f.user_id = u.user_id "
                + "WHERE f.feedback_id = ?";

        try (Connection conn = DatabaseUtil.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, feedbackId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    fb = new Feedback();
                    fb.setFeedback_id(rs.getInt("feedback_id"));
                    fb.setUser_name(rs.getString("full_name"));
                    fb.setEmail(rs.getString("email"));
                    fb.setPhone_number(rs.getString("phone_number"));
                    fb.setProduct_name(rs.getString("product_name"));
                    fb.setRating(rs.getInt("rating"));
                    fb.setFeedback_content(rs.getString("feedback_content"));
                    fb.setMedia_url(rs.getString("media_url"));
                    fb.setStatus(rs.getString("status"));
                    fb.setCreated_at(rs.getTimestamp("created_at"));
                    fb.setUpdated_at(rs.getTimestamp("updated_at"));
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Lỗi SQL khi lấy feedback theo ID", e);
        }
        return fb;
    }

    // Cập nhật trạng thái phản hồi
    public boolean updateFeedbackStatus(int feedbackId, String newStatus) {
        String sql = "UPDATE Feedbacks SET status = ?, updated_at = GETDATE() WHERE feedback_id = ?";
        try (Connection conn = DatabaseUtil.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, newStatus);
            stmt.setInt(2, feedbackId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Lỗi SQL khi cập nhật trạng thái feedback", e);
        }
        return false;
    }

    public List<Feedback> getFeedbacksByUserName(String userName, int page, int limit) {
        List<Feedback> feedbacks = new ArrayList<>();
        String sql = "SELECT f.feedback_id, u.full_name, u.email, u.phone_number, p.product_name, "
                + "f.rating, f.feedback_content, f.media_url, f.status, f.created_at, f.updated_at "
                + "FROM Feedbacks f "
                + "JOIN Products p ON f.product_id = p.product_id "
                + "JOIN Users u ON f.user_id = u.user_id "
                + "WHERE u.full_name LIKE ? "
                + "ORDER BY f.feedback_id "
                + "OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";

        try (Connection conn = DatabaseUtil.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + userName + "%"); // Tìm theo tên người dùng
            stmt.setInt(2, (page - 1) * limit);
            stmt.setInt(3, limit);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Feedback fb = new Feedback();
                    fb.setFeedback_id(rs.getInt("feedback_id"));
                    fb.setUser_name(rs.getString("full_name"));
                    fb.setEmail(rs.getString("email"));
                    fb.setPhone_number(rs.getString("phone_number"));
                    fb.setProduct_name(rs.getString("product_name"));
                    fb.setRating(rs.getInt("rating"));
                    fb.setFeedback_content(rs.getString("feedback_content"));
                    fb.setMedia_url(rs.getString("media_url"));
                    fb.setStatus(rs.getString("status"));
                    fb.setCreated_at(rs.getTimestamp("created_at"));
                    fb.setUpdated_at(rs.getTimestamp("updated_at"));
                    feedbacks.add(fb);
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Lỗi SQL khi tìm kiếm phản hồi theo tên người dùng", e);
        }
        return feedbacks;
    }

}

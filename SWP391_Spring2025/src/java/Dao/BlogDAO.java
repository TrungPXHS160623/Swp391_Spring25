/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dao;

import Context.DBContext;
import Entity.Post;
import Entity.PostMedia;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author LENOVO
 */
public class BlogDAO {

    private static final Logger LOGGER = Logger.getLogger(BlogDAO.class.getName());

    // Chuyển đổi từ ResultSet thành Post Object
    private Post mapResultSetToPost(ResultSet rs) throws SQLException {
        Post post = new Post();
        post.setPostId(rs.getInt("post_id"));
        post.setTitle(rs.getString("title"));
        post.setContent(rs.getString("content"));
        post.setSummary(rs.getString("summary"));
        post.setCreatedAt(rs.getTimestamp("created_at"));
        post.setUpdateAt(rs.getTimestamp("updated_at"));
        post.setUser_name(rs.getString("full_name"));
        post.setCategory_name(rs.getString("category_name"));

        // Ánh xạ thông tin media nếu có
        PostMedia media = new PostMedia();
        media.setMediaId(rs.getInt("media_id"));
        media.setMediaUrl(rs.getString("media_url"));
        media.setMediaType(rs.getString("media_type"));
        media.setDescription(rs.getString("description"));
        return post;
    }

    public List<Post> getAllBlogs(int page, int pageSize) {
        List<Post> result = new ArrayList<>();
        String sql = "SELECT p.post_id, p.title, p.content, p.summary, p.created_at, p.updated_at, "
                + "       u.full_name, c.category_name, "
                + "       pm.media_id, pm.media_url, pm.media_type, pm.description "
                + "FROM Posts p "
                + "JOIN Users u ON p.user_id = u.user_id "
                + "JOIN Categories c ON p.category_id = c.category_id "
                + "LEFT JOIN ( "
                + "     SELECT post_id, MIN(media_id) AS media_id "
                + "     FROM Post_Media "
                + "     GROUP BY post_id "
                + ") t ON p.post_id = t.post_id "
                + "LEFT JOIN Post_Media pm ON t.media_id = pm.media_id "
                + "ORDER BY p.updated_at DESC "
                + "OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
        int offset = (page - 1) * pageSize;
        try (Connection conn = new DBContext().getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, offset);
            stmt.setInt(2, pageSize);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Post post = mapResultSetToPost(rs);
                    result.add(post);
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error getAllBlogs (result set)", e);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error getAllBlogs (result set)", e);
        }
        return result;
    }

    // 2. Tìm kiếm blog (có phân trang) Tìm theo title hoặc content chứa keyword
    public List<Post> searchBlogs(String keyword, int page, int pageSize) {
        List<Post> result = new ArrayList<>();
        String sql = "SELECT p.post_id, p.title, p.content, p.summary, p.created_at, p.updated_at, "
                + "       u.full_name, c.category_name, "
                + "       pm.media_id, pm.media_url, pm.media_type, pm.description "
                + "FROM Posts p "
                + "JOIN Users u ON p.user_id = u.user_id "
                + "JOIN Categories c ON p.category_id = c.category_id "
                + "LEFT JOIN ( "
                + "     SELECT post_id, MIN(media_id) AS media_id "
                + "     FROM Post_Media "
                + "     GROUP BY post_id "
                + ") t ON p.post_id = t.post_id "
                + "LEFT JOIN Post_Media pm ON t.media_id = pm.media_id "
                + "WHERE p.title LIKE ? "
                + "ORDER BY p.updated_at DESC "
                + "OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";

        int offset = (page - 1) * pageSize;
        String likeParam = "%" + keyword + "%";
        try (Connection conn = new DBContext().getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + keyword + "%");
            stmt.setInt(2, offset);
            stmt.setInt(3, pageSize);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Post post = mapResultSetToPost(rs);
                    result.add(post);
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error searchBlogs (result set)", e);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error searchBlogs (result set)", e);
        }
        return result;
    }

    //3. Lấy danh sách blog theo Category (có phân trang)
    public List<Post> getBlogsByCategory(String category, int page, int pageSize) {
        List<Post> result = new ArrayList<>();
        String sql = "SELECT p.post_id, p.title, p.content, p.summary, p.created_at, p.updated_at, "
                + "       u.full_name, c.category_name, "
                + "       pm.media_id, pm.media_url, pm.media_type, pm.description "
                + "FROM Posts p "
                + "JOIN Users u ON p.user_id = u.user_id "
                + "JOIN Categories c ON p.category_id = c.category_id "
                + "LEFT JOIN ( "
                + "     SELECT post_id, MIN(media_id) AS media_id "
                + "     FROM Post_Media "
                + "     GROUP BY post_id "
                + ") t ON p.post_id = t.post_id "
                + "LEFT JOIN Post_Media pm ON t.media_id = pm.media_id "
                + "WHERE c.category_name = ? "
                + "ORDER BY p.updated_at DESC "
                + "OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
        int offset = (page - 1) * pageSize;
        try (Connection conn = new DBContext().getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, category);
            stmt.setInt(2, offset);
            stmt.setInt(3, pageSize);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Post post = mapResultSetToPost(rs);
                    result.add(post);
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error getBlogsByCategory (result set)", e);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error getBlogsByCategory (result set)", e);
        }
        return result;
    }

    //4. Lấy các bài viết mới nhất limit: số bài muốn lấy
    public List<Post> getLatestBlogs(int limit) {
        List<Post> result = new ArrayList<>();
        String sql = "SELECT p.post_id, p.title "
                + "FROM Posts p "
                + "ORDER BY p.updated_at DESC "
                + "OFFSET 0 ROWS FETCH NEXT ? ROWS ONLY";
        try (Connection conn = new DBContext().getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, limit);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Post post = new Post();
                    post.setPostId(rs.getInt("post_id"));
                    post.setTitle(rs.getString("title"));
                    result.add(post);
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error getLatestBlogs", e);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error getLatestBlogs", e);
        }
        return result;
    }

    public static void main(String[] args) {
        try {
            BlogDAO dao = new BlogDAO();

            // 1. Test getAllBlogs (page=1, pageSize=3)
            System.out.println("=== TEST getAllBlogs (page=1, pageSize=3) ===");
            List<Post> allBlogs = dao.getAllBlogs(1, 3);
            for (Post p : allBlogs) {
                System.out.println("postId=" + p.getPostId()
                        + ", title=" + p.getTitle()
                        + ", summary=" + p.getSummary()
                        + ", updatedAt=" + p.getUpdateAt());
            }

            // 2. Test searchBlogs (keyword="Laptop", page=1, pageSize=3)
            System.out.println("\n=== TEST searchBlogs (keyword='Laptop') ===");
            List<Post> searchResult = dao.searchBlogs("Laptop", 1, 3);
            for (Post p : searchResult) {
                System.out.println("postId=" + p.getPostId()
                        + ", title=" + p.getTitle()
                        + ", summary=" + p.getSummary()
                        + ", updatedAt=" + p.getUpdateAt());
            }

            // 3. Test getBlogsByCategory (category="Phone", page=1, pageSize=3)
            System.out.println("\n=== TEST getBlogsByCategory (category='Phone') ===");
            List<Post> categoryBlogs = dao.getBlogsByCategory("Phone", 1, 3);
            for (Post p : categoryBlogs) {
                System.out.println("postId=" + p.getPostId()
                        + ", title=" + p.getTitle()
                        + ", summary=" + p.getSummary()
                        + ", updatedAt=" + p.getUpdateAt());
            }

            // 4. Test getLatestBlogs (limit=2)
            System.out.println("\n=== TEST getLatestBlogs (limit=2) ===");
            List<Post> latestBlogs = dao.getLatestBlogs(2);
            for (Post p : latestBlogs) {
                System.out.println("postId=" + p.getPostId()
                        + ", title=" + p.getTitle());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

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

        post.setCoverMedia(media);

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

    public List<String> getAllCategoryNames() {
        List<String> categories = new ArrayList<>();
        String sql = "SELECT category_name FROM Categories";
        try (Connection conn = new DBContext().getConnection(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                categories.add(rs.getString("category_name"));
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error getAllCategoryNames", e);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error getAllCategoryNames", e);
        }
        return categories;
    }

    public Post getDetailBlog(int postId) {
        Post post = null;
        String sqlPost = "SELECT p.post_id, p.title, p.content, p.summary, p.created_at, p.updated_at, "
                + "       u.full_name, c.category_name, "
                + "       pm.media_id, pm.media_url, pm.media_type, pm.description "
                + "FROM Posts p "
                + "JOIN Users u ON p.user_id = u.user_id "
                + "JOIN Categories c ON p.category_id = c.category_id "
                + "LEFT JOIN ( "
                + "    SELECT post_id, MIN(media_id) AS media_id "
                + "    FROM Post_Media "
                + "    GROUP BY post_id "
                + ") t ON p.post_id = t.post_id "
                + "LEFT JOIN Post_Media pm ON t.media_id = pm.media_id "
                + "WHERE p.post_id = ?";

        try (Connection conn = new DBContext().getConnection(); PreparedStatement stmt = conn.prepareStatement(sqlPost)) {
            stmt.setInt(1, postId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    // Sử dụng hàm mapping có sẵn để ánh xạ các trường chính của bài viết
                    post = mapResultSetToPost(rs);
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error getDetailBlog (prepare statement for post)", e);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error getDetailBlog (connection for post)", e);
        }

        // Nếu bài viết được tìm thấy, lấy toàn bộ media liên quan
        if (post != null) {
            List<PostMedia> mediaList = new ArrayList<>();
            String sqlMedia = "SELECT media_id, post_id, media_url, media_type, description, created_at, updated_at "
                    + "FROM Post_Media "
                    + "WHERE post_id = ?";
            try (Connection conn = new DBContext().getConnection(); PreparedStatement stmt = conn.prepareStatement(sqlMedia)) {
                stmt.setInt(1, postId);
                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        PostMedia media = new PostMedia();
                        media.setMediaId(rs.getInt("media_id"));
                        media.setMediaUrl(rs.getString("media_url"));
                        media.setMediaType(rs.getString("media_type"));
                        media.setDescription(rs.getString("description"));
                        mediaList.add(media);
                    }
                }
            } catch (SQLException e) {
                LOGGER.log(Level.SEVERE, "Error getDetailBlog (prepare statement for media)", e);
            } catch (Exception e) {
                LOGGER.log(Level.SEVERE, "Error getDetailBlog (connection for media)", e);
            }
            post.setMediaList(mediaList);
        }
        return post;
    }

    public static void main(String[] args) {
        try {
            BlogDAO dao = new BlogDAO();
            int postIdToTest = 1; // Thay đổi postId này theo dữ liệu có sẵn trong DB của bạn
            Post post = dao.getDetailBlog(postIdToTest);

            // 1. Test getAllBlogs (page=1, pageSize=3)
            System.out.println("=== TEST getAllBlogs (page=1, pageSize=3) ===");
            List<Post> allBlogs = dao.getAllBlogs(1, 3);
            for (Post p : allBlogs) {
                System.out.println("postId=" + p.getPostId()
                        + ", title=" + p.getTitle()
                        + ", summary=" + p.getSummary()
                        + ", updatedAt=" + p.getUpdateAt());
                // Kiểm tra và in ra image URL của coverMedia
                if (p.getCoverMedia() != null && p.getCoverMedia().getMediaUrl() != null) {
                    System.out.println("Image URL: " + p.getCoverMedia().getMediaUrl());
                } else {
                    System.out.println("Image URL: [No Image]");
                }
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

            // 5. Test getDetailBlog (postid = 1)
            if (post != null) {
                System.out.println("Post ID: " + post.getPostId());
                System.out.println("Title: " + post.getTitle());
                System.out.println("Content: " + post.getContent());
                System.out.println("Summary: " + post.getSummary());
                System.out.println("Created At: " + post.getCreatedAt());
                System.out.println("Updated At: " + post.getUpdateAt());
                System.out.println("Author: " + post.getUser_name());
                System.out.println("Category: " + post.getCategory_name());

                // In danh sách toàn bộ media (ảnh, video)
                if (post.getMediaList() != null && !post.getMediaList().isEmpty()) {
                    System.out.println("Media List:");
                    for (PostMedia media : post.getMediaList()) {
                        System.out.println("  Media ID: " + media.getMediaId());
                        System.out.println("  Media URL: " + media.getMediaUrl());
                        System.out.println("  Media Type: " + media.getMediaType());
                        System.out.println("  Description: " + media.getDescription());
                        System.out.println("  -------------------------");
                    }
                } else {
                    System.out.println("No media found for this post.");
                }
            } else {
                System.out.println("No post found with ID: " + postIdToTest);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

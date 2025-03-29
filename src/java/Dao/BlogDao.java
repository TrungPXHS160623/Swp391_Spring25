/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dao;

import Context.DBContext;
import Dto.PostDto;
import Entity.PostMedia;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author LENOVO
 */
public class BlogDao {

    private static final Logger LOGGER = Logger.getLogger(BlogDao.class.getName());

    /**
     * Lấy danh sách tất cả blog. Trả về: title, summary, dayUpdate, mediaUrl
     * (là của media is_primary = 1) và category.
     */
    public List<PostDto> getAllBlog() {
        List<PostDto> list = new ArrayList<>();
        String sql = "SELECT p.post_id, p.title, p.summary, CONVERT(varchar, p.updated_at, 103) as dayUpdate, "
                + "pm.media_url, c.category_name, u.full_name as author "
                + "FROM Posts p "
                + "LEFT JOIN Post_Media pm ON p.post_id = pm.post_id AND pm.is_primary = 1 "
                + "LEFT JOIN Categories c ON p.category_id = c.category_id "
                + "LEFT JOIN Users u ON p.user_id = u.user_id "
                + "ORDER BY p.updated_at DESC";
        try (Connection conn = new DBContext().getConnection(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                PostDto dto = new PostDto();
                dto.setPostId(rs.getInt("post_id"));  // Set postId ở đây
                dto.setTitle(rs.getString("title"));
                dto.setAuthor(rs.getString("author"));
                dto.setSummary(rs.getString("summary"));
                dto.setDayUpdate(rs.getString("dayUpdate"));
                dto.setMediaUrl(rs.getString("media_url"));
                dto.setCategory(rs.getString("category_name"));
                list.add(dto);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Lỗi SQL khi lấy danh sách blog", e);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Lỗi SQL khi lấy danh sách blog", e);
        }
        return list;
    }

    /**
     * Tìm kiếm blog theo tiêu đề (keyword).
     */
    public List<PostDto> searchBlog(String keyword) {
        List<PostDto> list = new ArrayList<>();
        String sql = "SELECT p.title, p.summary, CONVERT(varchar, p.updated_at, 103) as dayUpdate, "
                + "pm.media_url, c.category_name, u.full_name as author "
                + "FROM Posts p "
                + "LEFT JOIN Post_Media pm ON p.post_id = pm.post_id AND pm.is_primary = 1 "
                + "LEFT JOIN Categories c ON p.category_id = c.category_id "
                + "LEFT JOIN Users u ON p.user_id = u.user_id "
                + "WHERE p.title LIKE ? "
                + "ORDER BY p.updated_at DESC";
        try (Connection conn = new DBContext().getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + keyword + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    PostDto dto = new PostDto();
                    dto.setTitle(rs.getString("title"));
                    dto.setAuthor(rs.getString("author"));
                    dto.setSummary(rs.getString("summary"));
                    dto.setDayUpdate(rs.getString("dayUpdate"));
                    dto.setMediaUrl(rs.getString("media_url"));
                    dto.setCategory(rs.getString("category_name"));
                    list.add(dto);
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Lỗi SQL khi tìm kiếm blog", e);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Lỗi SQL khi tìm kiếm blog", e);
        }
        return list;
    }

    /**
     * Lọc blog theo category.
     */
    public List<PostDto> filterBlogbyCategory(String category) {
        List<PostDto> list = new ArrayList<>();
        String sql = "SELECT p.post_id, p.title, p.summary, CONVERT(varchar, p.updated_at, 103) as dayUpdate, "
                + "pm.media_url, c.category_name, u.full_name as author "
                + "FROM Posts p "
                + "LEFT JOIN Post_Media pm ON p.post_id = pm.post_id AND pm.is_primary = 1 "
                + "LEFT JOIN Categories c ON p.category_id = c.category_id "
                + "LEFT JOIN Users u ON p.user_id = u.user_id "
                + "WHERE c.category_name = ? "
                + "ORDER BY p.updated_at DESC";
        try (Connection conn = new DBContext().getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, category);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    PostDto dto = new PostDto();
                    dto.setPostId(rs.getInt("post_id"));
                    dto.setAuthor(rs.getString("author"));
                    dto.setTitle(rs.getString("title"));
                    dto.setSummary(rs.getString("summary"));
                    dto.setDayUpdate(rs.getString("dayUpdate"));
                    dto.setMediaUrl(rs.getString("media_url"));
                    dto.setCategory(rs.getString("category_name"));
                    list.add(dto);
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Lỗi SQL khi lọc blog theo category", e);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Lỗi SQL khi lọc blog theo category", e);
        }
        return list;
    }

    /**
     * Lấy 3 blog mới nhất.
     */
    public List<PostDto> latest3Bloglink() {
        List<PostDto> list = new ArrayList<>();
        String sql = "SELECT TOP 3 p.post_id, p.title, p.summary, CONVERT(varchar, p.updated_at, 103) as dayUpdate, "
                + "pm.media_url, c.category_name "
                + "FROM Posts p "
                + "LEFT JOIN Post_Media pm ON p.post_id = pm.post_id AND pm.is_primary = 1 "
                + "LEFT JOIN Categories c ON p.category_id = c.category_id "
                + "ORDER BY p.updated_at DESC";
        try (Connection conn = new DBContext().getConnection(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                PostDto dto = new PostDto();
                dto.setPostId(rs.getInt("post_id"));  // Đảm bảo lấy post_id
                dto.setTitle(rs.getString("title"));
                dto.setSummary(rs.getString("summary"));
                dto.setDayUpdate(rs.getString("dayUpdate"));
                dto.setMediaUrl(rs.getString("media_url"));
                dto.setCategory(rs.getString("category_name"));
                list.add(dto);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Lỗi SQL khi lấy 3 blog mới nhất", e);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Lỗi SQL khi lấy 3 blog mới nhất", e);
        }
        return list;
    }

    /**
     * Lấy chi tiết blog theo postId. Trả về: title, content, author, dayUpdate,
     * mediaUrl (với description của media) và category.
     */
    public PostDto getBlogDetail(int postId) {
        PostDto dto = new PostDto();
        String sqlPost = "SELECT p.post_id, p.title, p.content, CONVERT(varchar, p.updated_at, 103) as dayUpdate, "
                + "u.full_name as author, c.category_name "
                + "FROM Posts p "
                + "LEFT JOIN Users u ON p.user_id = u.user_id "
                + "LEFT JOIN Categories c ON p.category_id = c.category_id "
                + "WHERE p.post_id = ?";
        String sqlMedia = "SELECT media_id, post_id, media_url, media_type, created_at, updated_at, description, is_primary "
                + "FROM Post_Media WHERE post_id = ? ORDER BY is_primary DESC, media_id ASC";

        try (Connection conn = new DBContext().getConnection(); PreparedStatement stmtPost = conn.prepareStatement(sqlPost); PreparedStatement stmtMedia = conn.prepareStatement(sqlMedia)) {

            // Lấy thông tin bài viết
            stmtPost.setInt(1, postId);
            try (ResultSet rsPost = stmtPost.executeQuery()) {
                if (rsPost.next()) {
                    dto.setPostId(rsPost.getInt("post_id"));
                    dto.setTitle(rsPost.getString("title"));
                    dto.setContent(rsPost.getString("content"));
                    dto.setDayUpdate(rsPost.getString("dayUpdate"));
                    dto.setAuthor(rsPost.getString("author"));
                    dto.setCategory(rsPost.getString("category_name"));
                    // Ở list view, summary có thể được set theo logic khác nếu cần
                    dto.setSummary(rsPost.getString("title")); // ví dụ, set summary là tiêu đề (bạn có thể thay đổi)
                }
            }

            // Lấy tất cả media của bài viết
            stmtMedia.setInt(1, postId);
            List<Entity.PostMedia> mediaList = new ArrayList<>();
            try (ResultSet rsMedia = stmtMedia.executeQuery()) {
                while (rsMedia.next()) {
                    Entity.PostMedia media = new Entity.PostMedia();
                    media.setMediaId(rsMedia.getInt("media_id"));
                    media.setPostId(rsMedia.getInt("post_id"));
                    media.setMediaUrl(rsMedia.getString("media_url"));
                    media.setMediaType(rsMedia.getString("media_type"));
                    media.setDescription(rsMedia.getString("description"));
                    // Các trường created_at, updated_at có thể được set nếu cần
                    mediaList.add(media);
                }
            }
            dto.setMediaList(mediaList);
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Lỗi SQL khi lấy chi tiết blog", e);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Lỗi SQL khi lấy chi tiết blog", e);
        }
        return dto;
    }

    public List<String> getAllCategoryNames() {
        List<String> categories = new ArrayList<>();
        String sql = "SELECT category_name FROM Categories WHERE status = 1"; // hoặc bỏ WHERE nếu muốn lấy tất cả
        try (Connection conn = new DBContext().getConnection(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                categories.add(rs.getString("category_name"));
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Lỗi SQL khi lấy danh sách category", e);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Lỗi SQL khi lấy danh sách category", e);
        }
        return categories;
    }

    public static void main(String[] args) {
        BlogDao blogDao = new BlogDao();

        // Test getAllBlog
        System.out.println("---- getAllBlog() ----");
        List<PostDto> allBlogs = blogDao.getAllBlog();
        if (allBlogs.isEmpty()) {
            System.out.println("Không có blog nào được tìm thấy.");
        } else {
            for (PostDto blog : allBlogs) {
                System.out.println(blog);
            }
        }

        // Test searchBlog với từ khóa "example"
        System.out.println("\n---- searchBlog(\"example\") ----");
        List<PostDto> searchBlogs = blogDao.searchBlog("example");
        if (searchBlogs.isEmpty()) {
            System.out.println("Không có blog nào khớp với từ khóa.");
        } else {
            for (PostDto blog : searchBlogs) {
                System.out.println(blog);
            }
        }

        // Test filterBlogbyCategory với category "Phone"
        System.out.println("\n---- filterBlogbyCategory(\"Phone\") ----");
        List<PostDto> filteredBlogs = blogDao.filterBlogbyCategory("Phone");
        if (filteredBlogs.isEmpty()) {
            System.out.println("Không có blog nào trong category này.");
        } else {
            for (PostDto blog : filteredBlogs) {
                System.out.println(blog);
            }
        }

        // Test latest3Bloglink
        System.out.println("\n---- latest3Bloglink() ----");
        List<PostDto> latestBlogs = blogDao.latest3Bloglink();
        if (latestBlogs.isEmpty()) {
            System.out.println("Không có blog nào được tìm thấy.");
        } else {
            for (PostDto blog : latestBlogs) {
                System.out.println(blog);
            }
        }

        // Test getBlogDetail cho postId = 1 (có thể thay đổi theo dữ liệu trong DB của bạn)
        int testPostId = 1;
        System.out.println("\n---- getBlogDetail(" + testPostId + ") ----");
        PostDto blogDetail = blogDao.getBlogDetail(testPostId);
        if (blogDetail == null) {
            System.out.println("Không tìm thấy blog có postId = " + testPostId);
        } else {
            System.out.println(blogDetail);
        }
    }
}

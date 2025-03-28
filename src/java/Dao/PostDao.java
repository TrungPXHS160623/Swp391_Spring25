/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dao;

import Context.DBContext;
import Dto.PostDto;
import Dto.PostDto2;
import Entity.CategoryPost;
import Entity.Post_2;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.sql.Timestamp;

/**
 *
 * @author Admin
 */
public class PostDao {

    // Get all posts with pagination, filtering, and sorting
    public List<PostDto2> getPosts(int pageNumber, int pageSize, String categoryId, String authorId,
            String status, String searchTitle, String sortBy, String sortOrder) throws Exception {
        List<PostDto2> posts = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = new DBContext().getConnection();

            // Build the base query with joins to get author name and category name
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT p.post_id, p.title, p.content, p.thumbnail_url, p.user_id, ");
            sql.append("u.full_name as author_name, p.category_id, cp.category_name, p.status, p.created_at ");
            sql.append("FROM [Post] p ");
            sql.append("LEFT JOIN [Users] u ON p.user_id = u.user_id ");
            sql.append("LEFT JOIN [CategoryPost] cp ON p.category_id = cp.category_id ");
            sql.append("WHERE 1=1 ");

            // Add filters
            if (categoryId != null && !categoryId.isEmpty()) {
                sql.append("AND p.category_id = ? ");
            }
            if (authorId != null && !authorId.isEmpty()) {
                sql.append("AND p.user_id = ? ");
            }
            if (status != null && !status.isEmpty()) {
                sql.append("AND p.status = ? ");
            }
            if (searchTitle != null && !searchTitle.isEmpty()) {
                sql.append("AND p.title LIKE ? ");
            }

            // Add sorting
            if (sortBy != null && !sortBy.isEmpty()) {
                sql.append("ORDER BY ");
                switch (sortBy) {
                    case "title":
                        sql.append("p.title ");
                        break;
                    case "category":
                        sql.append("cp.category_name ");
                        break;
                    case "author":
                        sql.append("u.full_name ");
                        break;
                    case "status":
                        sql.append("p.status ");
                        break;
                    default:
                        sql.append("p.created_at ");
                        break;
                }

                if (sortOrder != null && sortOrder.equalsIgnoreCase("desc")) {
                    sql.append("DESC ");
                } else {
                    sql.append("ASC ");
                }
            } else {
                sql.append("ORDER BY p.created_at DESC ");
            }

            // Add pagination
            sql.append("OFFSET ? ROWS FETCH NEXT ? ROWS ONLY");

            stmt = conn.prepareStatement(sql.toString());

            // Set parameters for filters
            int paramIndex = 1;
            if (categoryId != null && !categoryId.isEmpty()) {
                stmt.setInt(paramIndex++, Integer.parseInt(categoryId));
            }
            if (authorId != null && !authorId.isEmpty()) {
                stmt.setInt(paramIndex++, Integer.parseInt(authorId));
            }
            if (status != null && !status.isEmpty()) {
                // Convert string status to boolean
                stmt.setBoolean(paramIndex++, status.equalsIgnoreCase("true") || status.equalsIgnoreCase("active"));
            }
            if (searchTitle != null && !searchTitle.isEmpty()) {
                stmt.setString(paramIndex++, "%" + searchTitle + "%");
            }

            // Set parameters for pagination
            stmt.setInt(paramIndex++, (pageNumber - 1) * pageSize);
            stmt.setInt(paramIndex, pageSize);

            rs = stmt.executeQuery();

            while (rs.next()) {
                PostDto2 post = new PostDto2();
                post.setPostId(rs.getInt("post_id"));
                post.setTitle(rs.getString("title"));
                post.setContent(rs.getString("content"));
                post.setThumbnailUrl(rs.getString("thumbnail_url"));
                post.setUserId(rs.getInt("user_id"));
                post.setAuthorName(rs.getString("author_name"));
                post.setCategoryId(rs.getInt("category_id"));
                post.setCategoryName(rs.getString("category_name"));
                post.setStatus(rs.getBoolean("status"));
                post.setCreatedAt(rs.getTimestamp("created_at"));

                posts.add(post);
            }
        } finally {
            if (rs != null) try {
                rs.close();
            } catch (Exception e) {
            }
            if (stmt != null) try {
                stmt.close();
            } catch (Exception e) {
            }
            if (conn != null) try {
                conn.close();
            } catch (Exception e) {
            }
        }

        return posts;
    }

    // Count total number of posts with filters
    public int countPosts(String categoryId, String authorId, String status, String searchTitle) throws Exception {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        int count = 0;

        try {
            conn = new DBContext().getConnection();

            StringBuilder sql = new StringBuilder();
            sql.append("SELECT COUNT(*) FROM [Post] p WHERE 1=1 ");

            // Add filters
            if (categoryId != null && !categoryId.isEmpty()) {
                sql.append("AND p.category_id = ? ");
            }
            if (authorId != null && !authorId.isEmpty()) {
                sql.append("AND p.user_id = ? ");
            }
            if (status != null && !status.isEmpty()) {
                sql.append("AND p.status = ? ");
            }
            if (searchTitle != null && !searchTitle.isEmpty()) {
                sql.append("AND p.title LIKE ? ");
            }

            stmt = conn.prepareStatement(sql.toString());

            // Set parameters for filters
            int paramIndex = 1;
            if (categoryId != null && !categoryId.isEmpty()) {
                stmt.setInt(paramIndex++, Integer.parseInt(categoryId));
            }
            if (authorId != null && !authorId.isEmpty()) {
                stmt.setInt(paramIndex++, Integer.parseInt(authorId));
            }
            if (status != null && !status.isEmpty()) {
                // Convert string status to boolean
                stmt.setBoolean(paramIndex++, status.equalsIgnoreCase("true") || status.equalsIgnoreCase("active"));
            }
            if (searchTitle != null && !searchTitle.isEmpty()) {
                stmt.setString(paramIndex, "%" + searchTitle + "%");
            }

            rs = stmt.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } finally {
            if (rs != null) try {
                rs.close();
            } catch (Exception e) {
            }
            if (stmt != null) try {
                stmt.close();
            } catch (Exception e) {
            }
            if (conn != null) try {
                conn.close();
            } catch (Exception e) {
            }
        }

        return count;
    }

    // Get post by ID
    public PostDto2 getPostById(int postId) throws Exception {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        PostDto2 post = null;

        try {
            conn = new DBContext().getConnection();

            String sql = "SELECT p.post_id, p.title, p.content, p.thumbnail_url, p.user_id, "
                    + "u.full_name as author_name, p.category_id, cp.category_name, p.status, p.created_at "
                    + "FROM [Post] p "
                    + "LEFT JOIN [Users] u ON p.user_id = u.user_id "
                    + "LEFT JOIN [CategoryPost] cp ON p.category_id = cp.category_id "
                    + "WHERE p.post_id = ?";

            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, postId);

            rs = stmt.executeQuery();

            if (rs.next()) {
                post = new PostDto2();
                post.setPostId(rs.getInt("post_id"));
                post.setTitle(rs.getString("title"));
                post.setContent(rs.getString("content"));
                post.setThumbnailUrl(rs.getString("thumbnail_url"));
                post.setUserId(rs.getInt("user_id"));
                post.setAuthorName(rs.getString("author_name"));
                post.setCategoryId(rs.getInt("category_id"));
                post.setCategoryName(rs.getString("category_name"));
                post.setStatus(rs.getBoolean("status"));
                post.setCreatedAt(rs.getTimestamp("created_at"));
            }
        } finally {
            if (rs != null) try {
                rs.close();
            } catch (Exception e) {
            }
            if (stmt != null) try {
                stmt.close();
            } catch (Exception e) {
            }
            if (conn != null) try {
                conn.close();
            } catch (Exception e) {
            }
        }

        return post;
    }

    // Insert a new post
    public int insertPost(Post_2 post) throws Exception {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        int postId = -1;

        try {
            conn = new DBContext().getConnection();

            String sql = "INSERT INTO [Post] (title, content, thumbnail_url, user_id, category_id, status) "
                    + "VALUES (?, ?, ?, ?, ?, ?); SELECT SCOPE_IDENTITY();";

            stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, post.getTitle());
            stmt.setString(2, post.getContent());
            stmt.setString(3, post.getThumbnailUrl());
            stmt.setInt(4, post.getUserId());
            stmt.setInt(5, post.getCategoryId());
            stmt.setBoolean(6, post.isStatus());

            stmt.executeUpdate();
            rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                postId = rs.getInt(1);
            }
        } finally {
            if (rs != null) try {
                rs.close();
            } catch (Exception e) {
            }
            if (stmt != null) try {
                stmt.close();
            } catch (Exception e) {
            }
            if (conn != null) try {
                conn.close();
            } catch (Exception e) {
            }
        }

        return postId;
    }

    // Update an existing post
    public boolean updatePost(Post_2 post) throws Exception {
        Connection conn = null;
        PreparedStatement stmt = null;
        int rowsUpdated = 0;

        try {
            conn = new DBContext().getConnection();

            String sql = "UPDATE [Post] SET title = ?, content = ?, thumbnail_url = ?, "
                    + "category_id = ?, status = ? WHERE post_id = ?";

            stmt = conn.prepareStatement(sql);
            stmt.setString(1, post.getTitle());
            stmt.setString(2, post.getContent());
            stmt.setString(3, post.getThumbnailUrl());
            stmt.setInt(4, post.getCategoryId());
            stmt.setBoolean(5, post.isStatus());
            stmt.setInt(6, post.getPostId());

            rowsUpdated = stmt.executeUpdate();
        } finally {
            if (stmt != null) try {
                stmt.close();
            } catch (Exception e) {
            }
            if (conn != null) try {
                conn.close();
            } catch (Exception e) {
            }
        }

        return rowsUpdated > 0;
    }

    // Update post status
    public boolean updatePostStatus(int postId, boolean status) throws Exception {
        Connection conn = null;
        PreparedStatement stmt = null;
        int rowsUpdated = 0;

        try {
            conn = new DBContext().getConnection();

            String sql = "UPDATE [Post] SET status = ? WHERE post_id = ?";

            stmt = conn.prepareStatement(sql);
            stmt.setBoolean(1, status);
            stmt.setInt(2, postId);

            rowsUpdated = stmt.executeUpdate();
        } finally {
            if (stmt != null) try {
                stmt.close();
            } catch (Exception e) {
            }
            if (conn != null) try {
                conn.close();
            } catch (Exception e) {
            }
        }

        return rowsUpdated > 0;
    }

    // Get all categories for dropdown selection
    public List<CategoryPost> getAllCategories() throws Exception {
        List<CategoryPost> categories = new ArrayList<>();
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            conn = new DBContext().getConnection();
            stmt = conn.createStatement();

            // Modified query to include all categories (both active and inactive)
            String sql = "SELECT DISTINCT cp.category_id, cp.category_name, cp.description, cp.created_at, cp.updated_at, cp.is_active "
                    + "FROM [CategoryPost] cp "
                    + "LEFT JOIN [Post] p ON p.category_id = cp.category_id "
                    + "ORDER BY cp.category_name";

            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                CategoryPost category = new CategoryPost();
                category.setCategoryId(rs.getInt("category_id"));
                category.setCategoryName(rs.getString("category_name"));
                category.setDescription(rs.getString("description"));
                category.setCreatedAt(rs.getTimestamp("created_at"));
                category.setUpdatedAt(rs.getTimestamp("updated_at"));
                category.setIsActive(rs.getBoolean("is_active"));

                categories.add(category);
            }
        } finally {
            if (rs != null) try {
                rs.close();
            } catch (Exception e) {
            }
            if (stmt != null) try {
                stmt.close();
            } catch (Exception e) {
            }
            if (conn != null) try {
                conn.close();
            } catch (Exception e) {
            }
        }

        return categories;
    }

    // Get all authors for dropdown selection
    public List<Entity.User> getAllAuthors() throws Exception {
        List<Entity.User> authors = new ArrayList<>();
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            conn = new DBContext().getConnection();
            stmt = conn.createStatement();

            String sql = "SELECT DISTINCT u.user_id, u.full_name "
                    + "FROM [Users] u "
                    + "INNER JOIN [Post] p ON u.user_id = p.user_id "
                    + "ORDER BY u.full_name";

            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                Entity.User author = new Entity.User();
                author.setUser_id(rs.getInt("user_id"));
                author.setFull_name(rs.getString("full_name"));

                authors.add(author);
            }
        } finally {
            if (rs != null) try {
                rs.close();
            } catch (Exception e) {
            }
            if (stmt != null) try {
                stmt.close();
            } catch (Exception e) {
            }
            if (conn != null) try {
                conn.close();
            } catch (Exception e) {
            }
        }

        return authors;
    }
}

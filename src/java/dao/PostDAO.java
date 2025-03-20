package dao;

import utils.DatabaseUtil;
import model.Post;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.BlogPost;

public class PostDAO {
    private final Connection connection; // Kết nối cơ sở dữ liệu

   public PostDAO() {
      DatabaseUtil dbContext = new DatabaseUtil();
      this.connection = dbContext.getConnection(); // Lấy kết nối từ DBContext
   }

    public void insertPost(Post post) throws SQLException {
        String sql = "INSERT INTO [dbo].[Posts] "
                + "(user_id, title, thumbnail, category, brief_info, description, is_featured, status, created_at, updated_at) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseUtil.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, post.getUser_id());
            stmt.setString(2, post.getTitle());
            stmt.setString(3, post.getThumbnail());
            stmt.setString(4, post.getCategory());
            stmt.setString(5, post.getBrief_info());
            stmt.setString(6, post.getDescription());
            stmt.setBoolean(7, post.getIs_featured());
            stmt.setString(8, post.getStatus());
            stmt.setTimestamp(9, new Timestamp(post.getCreated_at().getTime()));
            stmt.setTimestamp(10, new Timestamp(post.getUpdated_at().getTime()));

            stmt.executeUpdate();
        }
    }

    public List<Post> getAllPostsPerPage(int page, int postPerPage) {
        List<Post> posts = new ArrayList<>();
        String sql = "SELECT p.post_id, p.user_id, p.title, p.category, p.thumbnail, "
                + "p.brief_info, p.description, p.is_featured, p.status, p.created_at, p.updated_at "
                + "FROM [dbo].[Posts] p "
                + "ORDER BY p.post_id "
                + "OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";

        try (Connection conn = DatabaseUtil.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, (page - 1) * postPerPage);
            stmt.setInt(2, postPerPage);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    posts.add(new Post(
                            rs.getInt("post_id"),
                            rs.getInt("user_id"),
                            rs.getString("title"),
                            rs.getString("category"),
                            rs.getString("thumbnail"),
                            rs.getString("brief_info"),
                            rs.getString("description"),
                            rs.getBoolean("is_featured"),
                            rs.getString("status"),
                            rs.getTimestamp("created_at"),
                            rs.getTimestamp("updated_at")
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return posts;
    }

    public int getTotalPost() {
        int total = 0;
        String sql = "SELECT COUNT(*) FROM [dbo].[Posts]";
        try (Connection conn = DatabaseUtil.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                total = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return total;
    }

    public static void main(String[] args) {
        PostDAO dao = new PostDAO();
        BlogPost list = dao.getPostbyID(3);
 
            System.out.println(list);
        
    }
    public List<BlogPost> listBlog(String search, int page, int size, String cateId, String status) {
        List<BlogPost> list = new ArrayList<>();
        // Câu lệnh SQL với JOIN thêm bảng Categories để lấy category_name
        String query = "SELECT b.*, u.full_name, c.category_name FROM Blogs b " +
                "JOIN Users u ON b.User_id = u.User_id " +  // JOIN với bảng Users qua User_id
                "JOIN Categories c ON b.category_id = c.category_id " +  // JOIN với bảng Categories qua category_id
                "WHERE b.title LIKE ? AND (b.category_id = ? OR ? = '') AND (b.status = ? OR ? = '') " +
                "ORDER BY b.id " +  // Đảm bảo có ORDER BY khi dùng OFFSET-FETCH
                "OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";

        // Kiểm tra giá trị null và gán giá trị mặc định
        if (search == null) search = "";
        if (cateId == null) cateId = "";
        if (status == null) status = "";

        try {
            PreparedStatement ps = connection.prepareStatement(query);
            // Cài đặt các tham số vào PreparedStatement
            ps.setString(1, "%" + search + "%");
            ps.setString(2, cateId);
            ps.setString(3, cateId); // Cùng cateId cho câu điều kiện thứ 2
            ps.setString(4, status);
            ps.setString(5, status); // Cùng status cho câu điều kiện thứ 2
            ps.setInt(6, (page - 1) * size);  // OFFSET
            ps.setInt(7, size);  // FETCH NEXT (LIMIT)

            ResultSet rs = ps.executeQuery();

            // Lặp qua kết quả và tạo đối tượng BlogPost
            while (rs.next()) {
                list.add(new BlogPost(
                        rs.getInt(1), // id
                        rs.getString(2), // title
                        rs.getString(3), // brief_info
                        rs.getString(4), // thumbnail
                        rs.getString(5), // details
                        rs.getDate(6), // updatedDate
                        rs.getInt(7), // PostCategories_id
                        rs.getInt(8), // User_id
                        rs.getBoolean(9), // flag_feature
                        rs.getInt(10), // status
                        rs.getString(11), // full_name lấy từ bảng Users
                        rs.getString(12) // category_name lấy từ bảng Categories
                ));
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return list;
    }


    public void addPost(BlogPost post) {
      String sql = "INSERT INTO Blogs (title, brief_info, thumbnail, details, category_id, user_id, flag_feature, status, updatedDate) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

      try (PreparedStatement stmt = connection.prepareStatement(sql)) { // Use 'connection' field

         stmt.setString(1, post.getTitle());
         stmt.setString(2, post.getBrief_info());
         stmt.setString(3, post.getThumbnail());
         stmt.setString(4, post.getDetails());
         stmt.setInt(5, post.getPostCategories_id());
         stmt.setInt(6, post.getUser_id());
         stmt.setBoolean(7, post.isFlag_feature());
         stmt.setInt(8, post.getStatus());
         
         stmt.setDate(9, post.getUpdatedDate());
         int rowsAffected = stmt.executeUpdate();
         System.out.println("Rows inserted: " + rowsAffected);
      } catch (SQLException e) {
         e.printStackTrace();
      }
   }
    public BlogPost getPostbyID(int id) {
      try {
         String query = "SELECT p.*, pc.category_name, u.full_name\n"
                 + "FROM Blogs p\n"
                 + "INNER JOIN Categories pc ON p.category_id = pc.category_id\n"
                 + "INNER JOIN users u ON p.user_id = u.user_id\n"
                 + "WHERE p.id = ?\n"
                 + "ORDER BY p.id ASC;";
         PreparedStatement statement = connection.prepareStatement(query);
         statement.setInt(1, id);
         ResultSet rs = statement.executeQuery();
         if (rs.next()) {
            BlogPost post = new BlogPost(rs.getInt("id"), // id
                    rs.getString(2), // title
                    rs.getString(3), // brief_info
                    rs.getString(4), // thumbnail
                    rs.getString(5), // details
                    rs.getDate(6), // updatedDate
                    rs.getInt(7), // PostCategories_id
                    rs.getInt(8), // User_id
                    rs.getBoolean(9), // flag_feature
                    rs.getInt(10),
                    rs.getString(11));

            return post;
         }
      } catch (SQLException ex) {
         Logger.getLogger(PostDAO.class.getName()).log(Level.SEVERE, null, ex);
      }
      return null;
   }
    public int updatePost(BlogPost blogPost) {
        String query = "UPDATE Blogs SET title = ?, brief_info = ?, thumbnail = ?, details = ?, category_id = ?, flag_feature = ?, status = ? WHERE id = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, blogPost.getTitle());
            ps.setString(2, blogPost.getBrief_info());
            ps.setString(3, blogPost.getThumbnail());
            ps.setString(4, blogPost.getDetails());
            ps.setInt(5, blogPost.getPostCategories_id());
            ps.setBoolean(6, blogPost.isFlag_feature());
            ps.setInt(7, blogPost.getStatus());
            ps.setInt(8, blogPost.getId());
            return ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
        return 0;
   }

    public boolean deleteBlogById(String id) {
        boolean isDeleted = false;
        String query = "DELETE FROM Blogs WHERE id = ?"; // Câu lệnh DELETE

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            // Cài đặt tham số id vào PreparedStatement
            ps.setString(1, id);

            // Thực thi câu lệnh và kiểm tra số lượng bản ghi bị xóa
            int rowsAffected = ps.executeUpdate();

            // Nếu ít nhất một bản ghi bị xóa, trả về true
            if (rowsAffected > 0) {
                isDeleted = true;
            }
        } catch (SQLException e) {
            System.out.println("Error deleting blog: " + e.getMessage());
        }

        return isDeleted; // Trả về true nếu xóa thành công, false nếu không có bản ghi nào bị xóa
    }
    
}

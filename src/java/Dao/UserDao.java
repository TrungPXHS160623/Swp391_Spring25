/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dao;

import Context.DBContext;
import Entity.User;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Acer
 */
public class UserDao {

    private static final Logger LOGGER = Logger.getLogger(UserDao.class.getName());

    //Kiểm tra đăng nhập
    public User login(String email, String password) {
    String sql = "SELECT * FROM Users WHERE email = ? AND password_hash = ?";
    try (Connection conn = new DBContext().getConnection(); 
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setString(1, email);
        stmt.setString(2, password);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            User user = extractUser(rs);
            updateLastLogin(user.getUser_id()); // Cập nhật last_login
            return user;
        }
    } catch (SQLException e) {
        LOGGER.log(Level.SEVERE, "Lỗi SQL khi đăng nhập", e);
    } catch (Exception e) {
        LOGGER.log(Level.SEVERE, "Lỗi kết nối CSDL", e);
    }
    return null;
}

    //kiểm tra email đã tồn tại chưa
    public boolean isEmailRegistered(String email) {
        String sql = "SELECT 1 FROM Users WHERE email = ?";
        try (Connection conn = new DBContext().getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Lỗi SQL khi kiểm tra email", e);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Lỗi kết nối CSDL", e);
        }
        return false;
    }

    //lưu token remember me
    /*
    Khi đăng nhập, nếu Remember Me được chọn, thì:
    Tạo một token ngẫu nhiên (UUID).
    Lưu token đó vào reset_token của User.
    Hạn sử dụng là 7 ngày (reset_token_expiry).
    Nếu người dùng quay lại sau 7 ngày → Token hết hạn → Phải đăng nhập lại.
     */
    public boolean updateRememberToken(int userId, String token) {
        String sql = "UPDATE Users SET reset_token = ?, reset_token_expiry = DATEADD(DAY, 7, GETDATE()) WHERE user_id = ?";
        try (Connection conn = new DBContext().getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, token);
            stmt.setInt(2, userId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Lỗi SQL khi lưu Remember Me token", e);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Lỗi kết nối CSDL", e);
        }
        return false;
    }

    //lấy user từ token remember me
    public User getUserByRememberToken(String token) {
        String sql = "SELECT * FROM Users WHERE reset_token = ? AND reset_token_expiry > GETDATE()";
        try (Connection conn = new DBContext().getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, token);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return extractUser(rs);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Lỗi SQL khi lấy User từ Remember Me token", e);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Lỗi kết nối CSDL", e);
        }
        return null;
    }

    /*
    Khi người dùng nhấn "Quên mật khẩu":
    Tạo một token đặt lại mật khẩu.
    Lưu vào reset_token và reset_token_expiry.
    Gửi email chứa link:
    https://xxx.com/reset-password?token=xxxxxx 
    Khi người dùng nhập mật khẩu mới → Kiểm tra token hợp lệ rồi đổi mật khẩu.
     */
    //tạo token để đặt lại mật khẩu
    public boolean generateResetToken(String email, String token, Timestamp expiry) {
        String sql = "UPDATE Users SET reset_token = ?, reset_token_expiry = ? WHERE email = ?";
        try (Connection conn = new DBContext().getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, token);
            stmt.setTimestamp(2, expiry);
            stmt.setString(3, email);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Lỗi SQL khi tạo Reset Token", e);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Lỗi kết nối CSDL", e);
        }
        return false;
    }

    //lấy user từ reset token
    public User getUserByResetToken(String token) {
        String sql = "SELECT * FROM Users WHERE reset_token = ? AND reset_token_expiry > GETDATE()";
        try (Connection conn = new DBContext().getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, token);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return extractUser(rs);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Lỗi SQL khi lấy User từ Reset Token", e);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Lỗi kết nối CSDL", e);
        }
        return null;
    }

    //Cập nhật mật khẩu mới - Sau khi người dùng đặt lại mật khẩu, cần cập nhật trong DB.
    public boolean updatePassword(int userId, String newPassword) {
        String sql = "UPDATE Users SET password_hash = ?, reset_token = NULL, reset_token_expiry = NULL WHERE user_id = ?";
        try (Connection conn = new DBContext().getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, newPassword);  // Nên mã hóa trước khi lưu!
            stmt.setInt(2, userId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Lỗi SQL khi cập nhật mật khẩu", e);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Lỗi kết nối CSDL", e);
        }
        return false;
    }

    //Vô hiệu hóa Token sau khi đặt lại mật khẩu 
    public boolean invalidateResetToken(String email) {
        String sql = "UPDATE Users SET reset_token = NULL, reset_token_expiry = NULL WHERE email = ?";
        try (Connection conn = new DBContext().getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, email);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Lỗi SQL khi vô hiệu hóa Reset Token", e);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Lỗi kết nối CSDL", e);
        }
        return false;

    }

    //Lưu thời điểm đăng nhập cuối
    public void updateLastLogin(int userId) {
        String sql = "UPDATE Users SET last_login = GETDATE() WHERE user_id = ?";
        try (Connection conn = new DBContext().getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Lỗi SQL khi cập nhật Last Login", e);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Lỗi kết nối CSDL", e);
        }
    }
    //đăng kí tài khoản
    public boolean register(User user) {
    if (isEmailRegistered(user.getEmail())) {
        LOGGER.log(Level.WARNING, "Email đã tồn tại: {0}", user.getEmail());
        return false; // Email đã tồn tại, không cho phép đăng ký
    }

    String sql = "INSERT INTO Users (full_name, gender, email, password_hash, phone_number, address, avatar_url, role_id, is_active, is_verified, created_at) "
               + "VALUES (?, ?, ?, ?, ?, ?, NULL, ?, ?, ?, ?)";

    try (Connection conn = new DBContext().getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setString(1, user.getFull_name());
        stmt.setString(2, user.getGender());
        stmt.setString(3, user.getEmail());
        stmt.setString(4, user.getPassword_hash()); // Đã mã hóa trước khi truyền vào
        stmt.setString(5, user.getPhone_number());
        stmt.setString(6, user.getAddress());
        stmt.setInt(7, user.getRole_id()); // Mặc định là 2 nếu user bình thường
        stmt.setBoolean(8, true); // is_active (mặc định true)
        stmt.setBoolean(9, false); // is_verified (mặc định false)
        stmt.setTimestamp(10, new Timestamp(System.currentTimeMillis())); // created_at hiện tại

        return stmt.executeUpdate() > 0; // Trả về true nếu thêm thành công
    } catch (SQLException e) {
        LOGGER.log(Level.SEVERE, "Lỗi SQL khi đăng ký", e);
    } catch (Exception e) {
        LOGGER.log(Level.SEVERE, "Lỗi kết nối CSDL", e);
    }
    return false;
}
    // Hàm hỗ trợ: Chuyển ResultSet thành User
    private User extractUser(ResultSet rs) throws SQLException {
        return new User(
                rs.getInt("user_id"),
                rs.getString("full_name"),
                rs.getString("gender"),
                rs.getString("email"),
                rs.getString("password_hash"),
                rs.getString("phone_number"),
                rs.getString("address"),
                rs.getString("avatar_url"),
                rs.getInt("role_id"),
                rs.getBoolean("is_active"),
                rs.getBoolean("is_verified"),
                rs.getString("reset_token"),
                rs.getTimestamp("reset_token_expiry"),
                rs.getTimestamp("created_at"),
                rs.getTimestamp("updated_at"),
                rs.getTimestamp("last_login")
        );
    }
    public static void main(String[] args) {
        // Tạo một đối tượng User để kiểm thử
        User testUser = new User();
        testUser.setFull_name("Nguyen Van A");
        testUser.setGender("Male");
        testUser.setEmail("testuser@example.com");
        testUser.setPassword_hash("hashedpassword123"); // Giả sử đã mã hóa
        testUser.setPhone_number("0123456789");
        testUser.setAddress("123 Đường ABC, TP.HCM");
        testUser.setRole_id(2); // Vai trò mặc định của user bình thường

        // Khởi tạo đối tượng chứa phương thức register
        UserDao userService = new UserDao();

        // Kiểm tra đăng ký
        boolean isRegistered = userService.register(testUser);

        // In kết quả ra console
        if (isRegistered) {
            System.out.println("Đăng ký thành công!");
        } else {
            System.out.println("Đăng ký thất bại!");
        }
    }
}

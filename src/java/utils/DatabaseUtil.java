package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseUtil {

    // Thông tin kết nối cơ sở dữ liệu
    private static final String SERVER_NAME = "localhost";
    private static final String DATABASE_NAME = "swp391_sping25";
    private static final String PORT = "1433";
    private static final String USER = "sa";
    private static final String PASSWORD = "123";
    private static final String JDBC_URL = "jdbc:sqlserver://" + SERVER_NAME + ":" + PORT + ";databaseName=" + DATABASE_NAME + ";encrypt=false";

    // Biến lưu kết nối Singleton
    private static Connection connection = null;

    // Tạo kết nối đến database
    public static Connection getConnection() {
        if (connection == null) {
            try {
                // Tải driver SQL Server
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                
                // Kết nối cơ sở dữ liệu
                connection = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
                System.out.println("✅ Kết nối thành công!");
            } catch (ClassNotFoundException e) {
                System.err.println("❌ Không tìm thấy Driver SQL Server!");
                e.printStackTrace();
            } catch (SQLException e) {
                System.err.println("❌ Lỗi kết nối CSDL!");
                e.printStackTrace();
            }
        }
        return connection;
    }

    // Đóng kết nối
    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("✅ Đã đóng kết nối!");
            } catch (SQLException e) {
                System.err.println("❌ Lỗi khi đóng kết nối!");
                e.printStackTrace();
            } finally {
                connection = null;
            }
        }
    }

    // Kiểm tra kết nối
    public static void main(String[] args) {
        Connection conn = DatabaseUtil.getConnection();
        if (conn != null) {
            System.out.println("🔹 Kết nối đang hoạt động: " + conn);
        }
        DatabaseUtil.closeConnection();
    }
}

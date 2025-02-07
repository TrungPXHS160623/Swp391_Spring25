package utils;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseUtil {

    // Thông tin kết nối cơ sở dữ liệu
    private final String serverName = "localhost";
    private final String dbName = "Swp391_Spring25";
    private final String portNumber = "1433";
    private final String instance = ""; // Nếu không có instance, để trống
    private final String userID = "sa";
    private final String password = "123";

    public static Connection getConnection() throws Exception {
        String url = "jdbc:sqlserver://" + "localhost" + ":" + "1433";
        if (!"".equals("")) {
            url += "\\" + "";
        }
        url += ";databaseName=Swp391_Spring25;encrypt=false"; // Thêm encrypt=false để tránh lỗi SSL.

        // Tải driver SQL Server
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        return DriverManager.getConnection(url, "sa", "123"); // Sử dụng tài khoản `sa` và mật khẩu là `123`
    }

    public static void main(String[] args) {
        try {
            Connection conn = new DatabaseUtil().getConnection();
            if (conn != null) {
                System.out.println("✅ Kết nối thành công: " + conn);
            } else {
                System.out.println("❌ Kết nối thất bại!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

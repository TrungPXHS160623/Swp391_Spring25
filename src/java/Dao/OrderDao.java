package Dao;

import Context.DBContext;
import java.sql.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import Dto.OrderDto;

public class OrderDao {

    private static final Logger LOGGER = Logger.getLogger(OrderDao.class.getName());

    // Lấy danh sách đơn hàng của một user theo userId
    public List<OrderDto> getOrdersByUserId(int userId) {
        Map<Integer, OrderDto> orderMap = new HashMap<>();
        String sql = "SELECT o.order_id, o.created_at, p.product_name, o.total_amount, o.order_status "
                + "FROM Orders o "
                + "JOIN Order_Items oi ON o.order_id = oi.order_id "
                + "JOIN Products p ON oi.product_id = p.product_id "
                + "WHERE o.user_id = ? "
                + "ORDER BY o.order_id";
        
        try (Connection conn = new DBContext().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int orderId = rs.getInt("order_id");
                    OrderDto order = orderMap.get(orderId);

                    if (order == null) {
                        Timestamp orderDate = rs.getTimestamp("created_at");
                        double totalCost = rs.getDouble("total_amount");
                        String status = rs.getString("order_status");
                        order = new OrderDto(orderId, orderDate, new ArrayList<>(), totalCost, status);
                        orderMap.put(orderId, order);
                    }

                    // Thêm sản phẩm vào danh sách productNames
                    order.getProductNames().add(rs.getString("product_name"));
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Lỗi SQL khi lấy danh sách đơn hàng", e);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Lỗi kết nối CSDL", e);
        }
        
        return new ArrayList<>(orderMap.values());
    }
    public OrderDto getOrderById(int orderId) {
    OrderDto order = null;
    String sql = "SELECT o.order_id, o.created_at, p.product_name, o.total_amount, o.order_status "
               + "FROM Orders o "
               + "JOIN Order_Items oi ON o.order_id = oi.order_id "
               + "JOIN Products p ON oi.product_id = p.product_id "
               + "WHERE o.order_id = ?";

    try (Connection conn = new DBContext().getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setInt(1, orderId);
        try (ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                if (order == null) {
                    Timestamp orderDate = rs.getTimestamp("created_at");
                    double totalCost = rs.getDouble("total_amount");
                    String status = rs.getString("order_status");
                    order = new OrderDto(orderId, orderDate, new ArrayList<>(), totalCost, status);
                }
                order.getProductNames().add(rs.getString("product_name"));
            }
        }
    } catch (SQLException e) {
        LOGGER.log(Level.SEVERE, "Lỗi SQL khi tìm đơn hàng theo ID", e);
    }
    catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Lỗi kết nối CSDL", e);
        }
    return order;
}
public List<OrderDto> getOrdersByStatus(String status) {
    Map<Integer, OrderDto> orderMap = new HashMap<>();
    String sql = "SELECT o.order_id, o.created_at, p.product_name, o.total_amount, o.order_status "
               + "FROM Orders o "
               + "JOIN Order_Items oi ON o.order_id = oi.order_id "
               + "JOIN Products p ON oi.product_id = p.product_id "
               + "WHERE o.order_status = ? "
               + "ORDER BY o.order_id";

    try (Connection conn = new DBContext().getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setString(1, status);
        try (ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                int orderId = rs.getInt("order_id");
                OrderDto order = orderMap.get(orderId);
                if (order == null) {
                    Timestamp orderDate = rs.getTimestamp("created_at");
                    double totalCost = rs.getDouble("total_amount");
                    order = new OrderDto(orderId, orderDate, new ArrayList<>(), totalCost, status);
                    orderMap.put(orderId, order);
                }
                order.getProductNames().add(rs.getString("product_name"));
            }
        }
    } catch (SQLException e) {
        LOGGER.log(Level.SEVERE, "Lỗi SQL khi lọc đơn hàng theo trạng thái", e);
    }catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Lỗi kết nối CSDL", e);
        }
    return new ArrayList<>(orderMap.values());
}
public List<OrderDto> getOrdersByDateRange(Timestamp startDate, Timestamp endDate) {
    Map<Integer, OrderDto> orderMap = new HashMap<>();
    String sql = "SELECT o.order_id, o.created_at, p.product_name, o.total_amount, o.order_status "
               + "FROM Orders o "
               + "JOIN Order_Items oi ON o.order_id = oi.order_id "
               + "JOIN Products p ON oi.product_id = p.product_id "
               + "WHERE o.created_at BETWEEN ? AND ? "
               + "ORDER BY o.order_id";

    try (Connection conn = new DBContext().getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setTimestamp(1, startDate);
        stmt.setTimestamp(2, endDate);
        try (ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                int orderId = rs.getInt("order_id");
                OrderDto order = orderMap.get(orderId);
                if (order == null) {
                    Timestamp orderDate = rs.getTimestamp("created_at");
                    double totalCost = rs.getDouble("total_amount");
                    String status = rs.getString("order_status");
                    order = new OrderDto(orderId, orderDate, new ArrayList<>(), totalCost, status);
                    orderMap.put(orderId, order);
                }
                order.getProductNames().add(rs.getString("product_name"));
            }
        }
    } catch (SQLException e) {
        LOGGER.log(Level.SEVERE, "Lỗi SQL khi tìm đơn hàng theo khoảng ngày", e);
    }
    catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Lỗi kết nối CSDL", e);
        }
    return new ArrayList<>(orderMap.values());
}
public List<OrderDto> getOrdersWithPagination(int offset, int limit) {
    Map<Integer, OrderDto> orderMap = new HashMap<>();
    String sql = "SELECT o.order_id, o.created_at, p.product_name, o.total_amount, o.order_status "
               + "FROM Orders o "
               + "JOIN Order_Items oi ON o.order_id = oi.order_id "
               + "JOIN Products p ON oi.product_id = p.product_id "
               + "ORDER BY o.order_id "
               + "OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";

    try (Connection conn = new DBContext().getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setInt(1, offset);
        stmt.setInt(2, limit);
        try (ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                int orderId = rs.getInt("order_id");
                OrderDto order = orderMap.get(orderId);
                if (order == null) {
                    Timestamp orderDate = rs.getTimestamp("created_at");
                    double totalCost = rs.getDouble("total_amount");
                    String status = rs.getString("order_status");
                    order = new OrderDto(orderId, orderDate, new ArrayList<>(), totalCost, status);
                    orderMap.put(orderId, order);
                }
                order.getProductNames().add(rs.getString("product_name"));
            }
        }
    } catch (SQLException e) {
        LOGGER.log(Level.SEVERE, "Lỗi SQL khi phân trang danh sách đơn hàng", e);
    }catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Lỗi kết nối CSDL", e);
        }
    return new ArrayList<>(orderMap.values());
}
public int getTotalPages(int userId, int pageSize) {
    int totalOrders = 0;
    String sql = "SELECT COUNT(*) FROM Orders WHERE user_id = ?";

    try (Connection conn = new DBContext().getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setInt(1, userId);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            totalOrders = rs.getInt(1);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Lỗi kết nối CSDL", e);
        }

    // Tính tổng số trang
    return (int) Math.ceil((double) totalOrders / pageSize);
}

    public static void main(String[] args) {
    OrderDao orderDao = new OrderDao();
    
//    int testUserId = 3; // Thay bằng ID thực tế trong database
//    List<OrderDto> orders = orderDao.getOrdersByUserId(testUserId);
//
//    if (orders.isEmpty()) {
//        System.out.println("Không có đơn hàng nào cho userId: " + testUserId);
//    } else {
//        for (OrderDto order : orders) {
//            System.out.println("Đơn hàng ID: " + order.getOrderId());
//            System.out.println("Ngày tạo: " + order.getOrderDate());
//            System.out.println("Sản phẩm: " + order.getProductNames());
//            System.out.println("Tổng tiền: " + order.getTotalCost());
//            System.out.println("Trạng thái: " + order.getStatus());
//            System.out.println("----------------------------");
//        }
//    }
    
//    // 1️⃣ Test tìm kiếm đơn hàng theo ID
//        int testOrderId = 2;
//        System.out.println("🔍 Tìm đơn hàng theo ID: " + testOrderId);
//        OrderDto orderById = orderDao.getOrderById(testOrderId);
//        printOrder(orderById);

        // 2️⃣ Test lọc đơn hàng theo trạng thái
        String testStatus = "Completed"; // Thay bằng trạng thái có sẵn trong DB
        System.out.println("\n📌 Lọc đơn hàng theo trạng thái: " + testStatus);
        List<OrderDto> ordersByStatus = orderDao.getOrdersByStatus(testStatus);
        printOrderList(ordersByStatus);
//
//        // 3️⃣ Test tìm đơn hàng theo khoảng ngày
//        Timestamp startDate = Timestamp.valueOf("2024-01-01 00:00:00");
//        Timestamp endDate = Timestamp.valueOf("2024-12-31 23:59:59");
//        System.out.println("\n📅 Tìm đơn hàng từ " + startDate + " đến " + endDate);
//        List<OrderDto> ordersByDate = orderDao.getOrdersByDateRange(startDate, endDate);
//        printOrderList(ordersByDate);
//
//        // 4️⃣ Test phân trang danh sách đơn hàng
//        int offset = 0; // Lấy từ bản ghi thứ 0
//        int limit = 5;  // Lấy 5 bản ghi
//        System.out.println("\n📄 Phân trang danh sách đơn hàng (offset: " + offset + ", limit: " + limit + ")");
//        List<OrderDto> paginatedOrders = orderDao.getOrdersWithPagination(offset, limit);
//        printOrderList(paginatedOrders);
}
    // 📌 Hàm hỗ trợ in thông tin một đơn hàng
    private static void printOrder(OrderDto order) {
        if (order == null) {
            System.out.println("⚠ Không tìm thấy đơn hàng!");
            return;
        }
        System.out.println("🆔 Order ID: " + order.getOrderId());
        System.out.println("📅 Created At: " + order.getOrderDate());
        System.out.println("📦 Product Names: " + order.getProductNames());
        System.out.println("💰 Total Amount: " + order.getTotalCost());
        System.out.println("🔖 Status: " + order.getStatus());
        System.out.println("---------------------------------");
    }

    // 📌 Hàm hỗ trợ in danh sách đơn hàng
    private static void printOrderList(List<OrderDto> orders) {
        if (orders.isEmpty()) {
            System.out.println("⚠ Không có đơn hàng nào!");
            return;
        }
        for (OrderDto order : orders) {
            printOrder(order);
        }
    }
}

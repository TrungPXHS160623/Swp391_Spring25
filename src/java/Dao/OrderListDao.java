/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dao;

import Context.DBContext;
import Dto.OrderListDto;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author LENOVO
 */
public class OrderListDao {

    private static final Logger LOGGER = Logger.getLogger(OrderListDao.class.getName());

    public List<OrderListDto> getAllOrderList() {
        List<OrderListDto> list = new ArrayList<>();

        // Giả sử bạn dùng SQL Server 2017+ với STRING_AGG
        String sql = "SELECT o.order_id, o.created_at as orderDate, u.full_name as customerName, "
                + "STRING_AGG(p.product_name, ', ') as productList, "
                + "o.total_amount as totalCost, o.order_status "
                + "FROM Orders o "
                + "LEFT JOIN Users u ON o.user_id = u.user_id "
                + "LEFT JOIN Order_Items oi ON o.order_id = oi.order_id "
                + "LEFT JOIN Products p ON oi.product_id = p.product_id "
                + "GROUP BY o.order_id, o.created_at, u.full_name, o.total_amount, o.order_status "
                + "ORDER BY o.created_at DESC";

        try (Connection conn = new DBContext().getConnection(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                OrderListDto dto = new OrderListDto();
                dto.setOrderId(rs.getInt("order_id"));
                dto.setOrderDate(rs.getTimestamp("orderDate"));
                dto.setCustomerName(rs.getString("customerName")); // Nếu không join Users, bạn bỏ nó
                dto.setProductList(rs.getString("productList"));
                dto.setTotalCost(rs.getDouble("totalCost"));
                dto.setOrderStatus(rs.getString("order_status"));
                list.add(dto);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Lỗi SQL khi lấy danh sách Orders", e);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Lỗi SQL khi lấy danh sách Orders", e);
        }
        return list;
    }
}

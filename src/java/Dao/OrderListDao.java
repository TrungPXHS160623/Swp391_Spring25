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

    public List<OrderListDto> getOrderListDynamic(
            Timestamp fromDate,
            Timestamp toDate,
            String status,
            String searchKey,
            int page,
            int pageSize
    ) {
        List<OrderListDto> list = new ArrayList<>();

        // Dùng STRING_AGG cho SQL Server 2017+
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT o.order_id, o.created_at as orderDate, ");
        sql.append("u.full_name as customerName, ");
        sql.append("STRING_AGG(p.product_name, ', ') as productList, ");
        sql.append("o.total_amount as totalCost, o.order_status ");
        sql.append("FROM Orders o ");
        sql.append("LEFT JOIN Users u ON o.user_id = u.user_id ");
        sql.append("LEFT JOIN Order_Items oi ON o.order_id = oi.order_id ");
        sql.append("LEFT JOIN Products p ON oi.product_id = p.product_id ");
        sql.append("WHERE 1=1 ");

        // Thêm điều kiện fromDate, toDate
        if (fromDate != null) {
            sql.append("AND o.created_at >= ? ");
        }
        if (toDate != null) {
            sql.append("AND o.created_at < ? ");
        }
        // Lọc status
        if (status != null && !status.trim().isEmpty()) {
            sql.append("AND o.order_status = ? ");
        }
        // Tìm kiếm
        if (searchKey != null && !searchKey.trim().isEmpty()) {
            sql.append("AND (CONVERT(VARCHAR, o.order_id) LIKE ? OR u.full_name LIKE ?) ");
        }

        sql.append("GROUP BY o.order_id, o.created_at, u.full_name, o.total_amount, o.order_status ");
        sql.append("ORDER BY o.created_at DESC ");
        sql.append("OFFSET ? ROWS FETCH NEXT ? ROWS ONLY");

        try (Connection conn = new DBContext().getConnection(); PreparedStatement stmt = conn.prepareStatement(sql.toString())) {

            int index = 1;
            if (fromDate != null) {
                stmt.setTimestamp(index++, fromDate);
            }
            if (toDate != null) {
                stmt.setTimestamp(index++, toDate);
            }
            if (status != null && !status.trim().isEmpty()) {
                stmt.setString(index++, status.trim());
            }
            if (searchKey != null && !searchKey.trim().isEmpty()) {
                String pattern = "%" + searchKey.trim() + "%";
                stmt.setString(index++, pattern);
                stmt.setString(index++, pattern);
            }
            // Phân trang
            int offset = (page - 1) * pageSize;
            stmt.setInt(index++, offset);
            stmt.setInt(index++, pageSize);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    OrderListDto dto = new OrderListDto();
                    dto.setOrderId(rs.getInt("order_id"));
                    dto.setOrderDate(rs.getTimestamp("orderDate"));
                    dto.setCustomerName(rs.getString("customerName"));
                    dto.setProductList(rs.getString("productList"));
                    dto.setTotalCost(rs.getDouble("totalCost"));
                    dto.setOrderStatus(rs.getString("order_status"));
                    list.add(dto);
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Lỗi SQL khi lấy danh sách Orders", e);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Lỗi SQL khi lấy danh sách Orders", e);
        }
        return list;
    }
}

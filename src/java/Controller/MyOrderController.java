/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import Dao.OrderDao;
import Dto.OrderDto;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.sql.Timestamp;
import java.time.format.DateTimeParseException;
import java.util.Arrays;

/**
 *
 * @author Acer
 */
public class MyOrderController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet MyOrderController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet MyOrderController at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private OrderDao orderDAO = new OrderDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("userId");

        if (userId == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        // Nhận tham số tìm kiếm
        String orderIdStr = request.getParameter("orderId"); // Tìm kiếm theo ID
        String status = request.getParameter("status"); // Lọc theo trạng thái
        // Lấy tham số từ request
        String fromDateStr = request.getParameter("fromDate");
        String toDateStr = request.getParameter("toDate");

        LocalDate fromLocalDate = null;
        LocalDate toLocalDate = null;

        try {
            if (fromDateStr != null && !fromDateStr.isEmpty()) {
                fromLocalDate = LocalDate.parse(fromDateStr); // Chuyển đổi từ String sang LocalDate
            }
            if (toDateStr != null && !toDateStr.isEmpty()) {
                toLocalDate = LocalDate.parse(toDateStr);
            }
        } catch (DateTimeParseException e) {
            System.out.println("Lỗi parse ngày: " + e.getMessage());
        }

        // Kiểm tra nếu ngày hợp lệ thì chuyển sang Timestamp
        Timestamp timestampFrom = (fromLocalDate != null) ? Timestamp.valueOf(fromLocalDate.atStartOfDay()) : null;
        Timestamp timestampTo = (toLocalDate != null) ? Timestamp.valueOf(toLocalDate.atStartOfDay()) : null;

        String pageStr = request.getParameter("page"); // Phân trang
        int page = (pageStr != null) ? Integer.parseInt(pageStr) : 0;
        int pageSize = 10;

        List<OrderDto> orders = new ArrayList<>();

        if (orderIdStr != null && !orderIdStr.isEmpty()) {
            int orderId = Integer.parseInt(orderIdStr);
            OrderDto order = orderDAO.getOrderById(orderId);
            if (order != null) {
                orders.add(order);
            }
        } else if (status != null && !status.isEmpty()) {
            orders = orderDAO.getOrdersByStatus(status);
        } else if (timestampFrom != null && timestampTo != null) {
            orders = orderDAO.getOrdersByDateRange(timestampFrom, timestampTo);
        } else {
            orders = orderDAO.getOrdersWithPagination(page, pageSize);
        }

        // Lấy danh sách trạng thái   
        List<String> orderStatuses = Arrays.asList("Pending", "Processing", "Shipped", "Delivered", "Canceled");
        request.setAttribute("orderStatuses", orderStatuses);

        // Đẩy dữ liệu sang JSP
        request.setAttribute("orders", orders);
        request.setAttribute("orderStatuses", orderStatuses);
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", orderDAO.getTotalPages(userId, pageSize));

        request.getRequestDispatcher("/CustomerPage/MyOrders.jsp").forward(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}

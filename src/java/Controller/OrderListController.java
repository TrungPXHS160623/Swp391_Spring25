/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import Dao.OrderListDao;
import Dto.OrderListDto;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

/**
 *
 * @author LENOVO
 */
public class OrderListController extends HttpServlet {

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
            out.println("<title>Servlet OrderListController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet OrderListController at " + request.getContextPath() + "</h1>");
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
    OrderListDao orderDao = new OrderListDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Lấy tham số từ request
        String fromDateParam = request.getParameter("fromDate");
        String toDateParam = request.getParameter("toDate");
        String status = request.getParameter("status") != null ? request.getParameter("status").trim() : "";
        String searchKey = request.getParameter("searchKey") != null ? request.getParameter("searchKey").trim() : "";

        // Chuyển sang Timestamp
        Timestamp fromDate = null, toDate = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            if (fromDateParam != null && !fromDateParam.isEmpty()) {
                fromDate = new Timestamp(sdf.parse(fromDateParam).getTime());
            }
            if (toDateParam != null && !toDateParam.isEmpty()) {
                // Để bao gồm cả ngày toDate, ta có thể cộng thêm 1 ngày
                toDate = new Timestamp(sdf.parse(toDateParam).getTime() + 24 * 60 * 60 * 1000);
            }
        } catch (ParseException e) {
            // bỏ qua hoặc set null
        }

        // Phân trang
        int page = 1;
        int pageSize = 5;
        String pageParam = request.getParameter("page");
        String pageSizeParam = request.getParameter("pageSize");
        if (pageParam != null && !pageParam.isEmpty()) {
            try {
                page = Integer.parseInt(pageParam);
            } catch (NumberFormatException e) {
            }
        }
        if (pageSizeParam != null && !pageSizeParam.isEmpty()) {
            try {
                pageSize = Integer.parseInt(pageSizeParam);
            } catch (NumberFormatException e) {
            }
        }

        // Gọi DAO
        List<OrderListDto> orderList = orderDao.getOrderListDynamic(fromDate, toDate, status, searchKey, page, pageSize);

        // Tạm thời set totalCount = 50 (hoặc gọi hàm count)
        int totalCount = 50;
        int totalPages = (int) Math.ceil((double) totalCount / pageSize);

        // Đưa vào request attribute
        request.setAttribute("orderList", orderList);
        request.setAttribute("currentPage", page);
        request.setAttribute("pageSize", pageSize);
        request.setAttribute("totalPages", totalPages);

        // Giữ lại giá trị filter, search
        request.setAttribute("fromDate", fromDateParam);
        request.setAttribute("toDate", toDateParam);
        request.setAttribute("status", status);
        request.setAttribute("searchKey", searchKey);

        RequestDispatcher dispatcher = request.getRequestDispatcher("AdminPage/OrderList.jsp");
        dispatcher.forward(request, response);
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
        doGet(request, response);
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

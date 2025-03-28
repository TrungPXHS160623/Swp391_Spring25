/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import Dao.UserDtoDao;
import Dto.UserDto;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;

/**
 *
 * @author LENOVO
 */
public class AdminUserListController extends HttpServlet {

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
            out.println("<title>Servlet AdminUserListController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet AdminUserListController at " + request.getContextPath() + "</h1>");
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
    UserDtoDao userDao = new UserDtoDao();
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Lấy các tham số từ request và trim khoảng trắng
        String keyword = request.getParameter("keyword") != null ? request.getParameter("keyword").trim() : "";
        String gender = request.getParameter("gender") != null ? request.getParameter("gender").trim() : "";
        String role = request.getParameter("role") != null ? request.getParameter("role").trim() : "";
        String status = request.getParameter("status") != null ? request.getParameter("status").trim() : "";
        String sortField = request.getParameter("sortField") != null ? request.getParameter("sortField").trim() : "";
        String sortDirection = request.getParameter("sortDirection") != null ? request.getParameter("sortDirection").trim() : "";

        // Phân trang: mặc định trang 1, 7 bản ghi/trang
        int page = 1;
        int pageSize = 7;
        String pageParam = request.getParameter("page") != null ? request.getParameter("page").trim() : "";
        String pageSizeParam = request.getParameter("pageSize") != null ? request.getParameter("pageSize").trim() : "";
        if (!pageParam.isEmpty()) {
            try {
                page = Integer.parseInt(pageParam);
            } catch (NumberFormatException e) {
                // giữ giá trị mặc định
            }
        }
        if (!pageSizeParam.isEmpty()) {
            try {
                pageSize = Integer.parseInt(pageSizeParam);
            } catch (NumberFormatException e) {
                // giữ giá trị mặc định
            }
        }

        List<UserDto> userList = userDao.getUserListDynamic(keyword, gender, role, status, sortField, sortDirection, page, pageSize);

        // Lấy tổng số user theo điều kiện (sử dụng getUserCount của DAO)
        int totalCount = userDao.getUserCount(keyword, gender, role, status);
        int totalPages = (int) Math.ceil((double) totalCount / pageSize);

        // Đẩy dữ liệu và tham số về JSP
        request.setAttribute("userList", userList);
        request.setAttribute("currentPage", page);
        request.setAttribute("pageSize", pageSize);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("keyword", keyword);
        request.setAttribute("gender", gender);
        request.setAttribute("role", role);
        request.setAttribute("status", status);
        request.setAttribute("sortField", sortField);
        request.setAttribute("sortDirection", sortDirection);

        RequestDispatcher dispatcher = request.getRequestDispatcher("AdminPage/UserList.jsp");
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
        // Xử lý theo cách tương tự nếu yêu cầu POST
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

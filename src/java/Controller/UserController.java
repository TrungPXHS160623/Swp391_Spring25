/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import Dao.UserDAO;
import Entity.User;
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
public class UserController extends HttpServlet {

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
            out.println("<title>Servlet UserController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet UserController at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }
    UserDAO uDao = new UserDAO();

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String searchKeyword = request.getParameter("searchKeyword");
        String Id_sort = request.getParameter("Id_sort");
        String fullName_sort = request.getParameter("fullName_sort");
        String userGender_sort = request.getParameter("userGender_sort");
        String address_sort = request.getParameter("address_sort");
        String email_sort = request.getParameter("email_sort");
        String phone_sort = request.getParameter("phone_sort");
        String userRole_sort = request.getParameter("userRole_sort");
        String userStatus_sort = request.getParameter("userStatus_sort");

        String userGender_filter = request.getParameter("userGender_filter");
        String userRole_filter = request.getParameter("userRole_filter");
        String userStatus_filter = request.getParameter("userStatus_filter");

        int pageSize = 7;  // Số lượng người dùng mỗi trang
        int pageNumber = 1;  // Mặc định là trang 1
        // Lấy số trang từ request, nếu có
        String pageParam = request.getParameter("page");
        if (pageParam != null && !pageParam.isEmpty()) {
            try {
                pageNumber = Integer.parseInt(pageParam);
            } catch (NumberFormatException e) {
                pageNumber = 1;
            }
        }

        List<User> users;
        List<String> genders = uDao.getAllGenders();
        List<String> roles = uDao.getAllRoles();
        List<String> is_actives = uDao.getAllStatuses();

        request.setAttribute("genders", genders);
        request.setAttribute("roles", roles);
        request.setAttribute("is_actives", is_actives);

        if (searchKeyword != null && !searchKeyword.isEmpty()) {
            // Tìm kiếm sản phẩm theo từ khóa
            users = uDao.searchUser(searchKeyword);
        } else if (Id_sort != null) {
            // Kiểm tra sắp xếp theo Id người dùng
            boolean id_ascending = "asc".equals(Id_sort);
            users = uDao.getAllUserSortedById(id_ascending);
        } else if (fullName_sort != null) {
            // Kiểm tra sắp xếp theo tên người dùng
            boolean fullName_ascending = "asc".equals(fullName_sort);
            users = uDao.getAllUserSortedByName(fullName_ascending);
        } else if (userGender_sort != null) {
            // Kiểm tra sắp xếp theo giới tính
            boolean gender_ascending = "asc".equals(userGender_sort);
            users = uDao.getAllUserSortedByGender(gender_ascending);
        } else if (address_sort != null) {
            // Kiểm tra sắp xếp theo địa chỉ
            boolean address_ascending = "asc".equals(address_sort);
            users = uDao.getAllUserSortedByAddress(address_ascending);
        } else if (email_sort != null) {
            // Kiểm tra sắp xếp theo email
            boolean email_ascending = "asc".equals(email_sort);
            users = uDao.getAllUserSortedByEmail(email_ascending);
        } else if (phone_sort != null) {
            // Kiểm tra sắp xếp theo số điện thoại
            boolean phone_ascending = "asc".equals(phone_sort);
            users = uDao.getAllUserSortedByPhone_Number(phone_ascending);
        } else if (userRole_sort != null) {
            // Kiểm tra sắp xếp theo vai trò
            boolean role_ascending = "asc".equals(userRole_sort);
            users = uDao.getAllUserSortedByRole(role_ascending);
        } else if (userStatus_sort != null) {
            // Kiểm tra sắp xếp theo trạng thái
            boolean status_ascending = "asc".equals(userStatus_sort);
            users = uDao.getAllUserSortedByStatus(status_ascending);
        } else if (userGender_filter != null && !userGender_filter.isEmpty()) {
            // Lọc theo gender và từ khóa tìm kiếm nếu có
            users = uDao.getFilteredUserGender(userGender_filter);
        } else if (userRole_filter != null && !userRole_filter.isEmpty()) {
            // Lọc theo role và từ khóa tìm kiếm nếu có
            users = uDao.getFilteredUserRole(userRole_filter);
        } else if (userStatus_filter != null && !userStatus_filter.isEmpty()) {
            // Lọc theo status và từ khóa tìm kiếm nếu có
            users = uDao.getFilteredUserStatus(userStatus_filter);
        } else {
            // Lấy tất cả sản phẩm nếu không có từ khóa tìm kiếm
            users = uDao.getAllUser(pageNumber, pageSize);
        }

        int totalUsers = uDao.getTotalUserCount();
        int totalPages = (int) Math.ceil((double) totalUsers / pageSize);

        request.setAttribute("currentPage", pageNumber);
        request.setAttribute("totalPages", totalPages);

        request.setAttribute("UserController", users);
        request.getRequestDispatcher("AdminPage/UserList.jsp").forward(request, response);
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

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
        // Lấy các tham số tìm kiếm, lọc, sắp xếp và phân trang từ request
        String keyword = request.getParameter("keyword");
        String gender = request.getParameter("gender");
        String role = request.getParameter("role");
        String status = request.getParameter("status");
        String sortField = request.getParameter("sortField");
        String sortDirection = request.getParameter("sortDirection");

        // Phân trang: mặc định trang 1, 7 bản ghi/trang
        int page = 1;
        int pageSize = 7;
        String pageParam = request.getParameter("page");
        String pageSizeParam = request.getParameter("pageSize");
        if (pageParam != null && !pageParam.trim().isEmpty()) {
            try {
                page = Integer.parseInt(pageParam);
            } catch (NumberFormatException e) {
                // Giữ giá trị mặc định
            }
        }
        if (pageSizeParam != null && !pageSizeParam.trim().isEmpty()) {
            try {
                pageSize = Integer.parseInt(pageSizeParam);
            } catch (NumberFormatException e) {
                // Giữ giá trị mặc định
            }
        }

        // Lựa chọn phương thức DAO phù hợp dựa trên tham số nhập vào:
        List<UserDto> userList = null;
        if (keyword != null && !keyword.trim().isEmpty()) {
            // Nếu có từ khóa tìm kiếm, sử dụng searchUserList
            userList = userDao.searchUserList(keyword, page, pageSize);
        } else if ((gender != null && !gender.trim().isEmpty())
                || (role != null && !role.trim().isEmpty())
                || (status != null && !status.trim().isEmpty())) {
            // Nếu có ít nhất một tham số lọc, sử dụng filterUserList
            userList = userDao.filterUserList(gender, role, status, page, pageSize);
        } else if (sortField != null && !sortField.trim().isEmpty() && !sortField.equals("user_id")) {
            // Nếu có yêu cầu sắp xếp khác mặc định, sử dụng sortUserList
            userList = userDao.sortUserList(sortField, sortDirection, page, pageSize);
        } else {
            // Nếu không có tham số nào, trả về tất cả
            userList = userDao.getAllUserList(page, pageSize);
        }

        // --- Phân trang: tính tổng số trang ---
        // Giả sử bạn có phương thức getUserCount trong UserDtoDao, ví dụ:
        // int totalCount = userDao.getUserCount(keyword, gender, role, status);
        // Ở đây minh họa bằng giá trị tạm (ví dụ 12)
        int totalCount = 12; // <-- Bạn cần thay thế bằng cách lấy từ DB
        int totalPages = (int) Math.ceil((double) totalCount / pageSize);

        // Đẩy các tham số và dữ liệu về JSP
        request.setAttribute("userList", userList);
        request.setAttribute("currentPage", page);
        request.setAttribute("pageSize", pageSize);
        request.setAttribute("totalPages", totalPages);

        // Đẩy lại các tham số tìm kiếm, lọc và sắp xếp để duy trì trên giao diện
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

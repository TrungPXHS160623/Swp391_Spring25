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
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 *
 * @author LENOVO
 */
@MultipartConfig // Hỗ trợ upload file nếu cần
public class AdminUserDetailController extends HttpServlet {

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
            out.println("<title>Servlet AdminUserDetailController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet AdminUserDetailController at " + request.getContextPath() + "</h1>");
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
        // Lấy tham số action và userId từ request
        String action = request.getParameter("action") != null ? request.getParameter("action").trim() : "view";
        String userIdParam = request.getParameter("userId") != null ? request.getParameter("userId").trim() : "";
        UserDto user = null;

        if (!userIdParam.isEmpty() && (action.equalsIgnoreCase("view") || action.equalsIgnoreCase("edit"))) {
            try {
                int userId = Integer.parseInt(userIdParam);
                user = userDao.getUserById(userId);
            } catch (NumberFormatException e) {
                // Nếu không hợp lệ, để user là null
            }
        }
        if (user == null) {
            user = new UserDto();
        }

        request.setAttribute("user", user);
        request.setAttribute("action", action);
        RequestDispatcher dispatcher = request.getRequestDispatcher("AdminPage/UserDetail.jsp");
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
        // Đảm bảo mã hóa UTF-8 cho dữ liệu
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action") != null ? request.getParameter("action").trim() : "";

        // Nếu ở chế độ View, không cho phép cập nhật, chỉ chuyển sang chế độ xem
        if ("view".equalsIgnoreCase(action)) {
            doGet(request, response);
            return;
        }

        // Nếu ở chế độ Edit, thực hiện cập nhật
        String avatarUrl = request.getParameter("avatarUrl");
        String fullName = request.getParameter("fullName");
        String gender = request.getParameter("gender");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String phone = request.getParameter("phone");
        String role = request.getParameter("role");
        String address = request.getParameter("address");
        String status = request.getParameter("status");

        UserDto user = new UserDto();
        user.setAvatarUrl(avatarUrl);
        user.setFullName(fullName);
        user.setGender(gender);
        user.setEmail(email);
        user.setPassword(password);
        user.setPhoneNumber(phone);
        user.setRole(role);
        user.setAddress(address);
        user.setStatus(status);

        // Chỉ xử lý cập nhật nếu action là "edit"
        if ("edit".equalsIgnoreCase(action)) {
            String userIdParam = request.getParameter("userId");
            if (userIdParam == null || userIdParam.trim().isEmpty()) {
                request.setAttribute("message", "User ID không hợp lệ.");
                request.setAttribute("user", user);
                request.setAttribute("action", action);
                RequestDispatcher dispatcher = request.getRequestDispatcher("AdminPage/UserDetail.jsp");
                dispatcher.forward(request, response);
                return;
            }
            try {
                int userId = Integer.parseInt(userIdParam.trim());
                user.setUserId(userId);
                boolean result = userDao.updateUser(user);
                request.setAttribute("message", result ? "Cập nhật thành công" : "Cập nhật thất bại");
            } catch (NumberFormatException e) {
                request.setAttribute("message", "User ID không hợp lệ.");
            }
        } else { // action "add"
            boolean result = userDao.addUser(user);
            request.setAttribute("message", result ? "Thêm mới thành công" : "Thêm mới thất bại");
        }
        // Sau khi cập nhật, chuyển về chế độ xem chi tiết
        // Lưu ý: Nếu cập nhật thành công, bạn có thể cần lấy lại thông tin user từ DB
        RequestDispatcher dispatcher = request.getRequestDispatcher("AdminUserDetailController?action=view&userId=" + user.getUserId());
        dispatcher.forward(request, response);
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

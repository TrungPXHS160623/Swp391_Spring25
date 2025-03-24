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
                // Không hợp lệ, user vẫn null
            }
        }

        // Nếu action là "add" hoặc không có userId, tạo đối tượng user mới (rỗng)
        if (user == null) {
            user = new UserDto(); // Các trường sẽ rỗng, dùng cho add
        }

        // Đẩy thông tin và action về JSP
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
        // Chú ý: nếu form có upload file, cần đảm bảo enctype="multipart/form-data" trong JSP
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action") != null ? request.getParameter("action").trim() : "add";

        // Lấy các trường thông tin từ form
        String avatarUrl = request.getParameter("avatarUrl");
        // Nếu có upload file, bạn có thể xử lý phần file ở đây (ví dụ: lưu file và lấy đường dẫn lưu vào avatarUrl)
        String fullName = request.getParameter("fullName");
        String gender = request.getParameter("gender");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String phoneNumber = request.getParameter("phoneNumber");
        String role = request.getParameter("role");
        String address = request.getParameter("address");
        String status = request.getParameter("status");

        UserDto user = new UserDto(avatarUrl, fullName, gender, email, password, phoneNumber, role, address, status);

        if (action.equalsIgnoreCase("edit")) {
            // Nếu là chỉnh sửa, cần lấy userId và set vào user
            String userIdParam = request.getParameter("userId");
            try {
                int userId = Integer.parseInt(userIdParam);
                user.setUserId(userId);
                boolean result = userDao.updateUser(user);
                request.setAttribute("message", result ? "Cập nhật thành công" : "Cập nhật thất bại");
            } catch (NumberFormatException e) {
                request.setAttribute("message", "UserId không hợp lệ");
            }
        } else {
            // Nếu là thêm mới
            boolean result = userDao.addUser(user);
            request.setAttribute("message", result ? "Thêm mới thành công" : "Thêm mới thất bại");
        }
        // Sau khi xử lý, chuyển về trang chi tiết của user vừa được cập nhật/ thêm mới
        // Có thể chuyển về danh sách hoặc chi tiết, ở đây ta chuyển về chi tiết
        RequestDispatcher dispatcher = request.getRequestDispatcher("AdminUserDetailController?action=view&userId=" + (user.getUserId()));
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

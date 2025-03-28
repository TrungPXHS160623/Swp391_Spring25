/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import Dao.UserDao;
import Entity.User;
import Util.HashUtil;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Acer
 */
@WebServlet(name = "RegisterController", urlPatterns = {"/registercontroller"})
public class RegisterController extends HttpServlet {

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
            out.println("<title>Servlet RegisterController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet RegisterController at " + request.getContextPath() + "</h1>");
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
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
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
        // Lấy dữ liệu từ form
        String fullName = getParameterSafe(request, "fullname");
        String gender = getParameterSafe(request, "gender");
        String email = getParameterSafe(request, "email");
        String password = getParameterSafe(request, "password");
        String confirmPassword = getParameterSafe(request, "confirm_password");
        String phoneNumber = getParameterSafe(request, "phone");
        String address = getParameterSafe(request, "address");

        // Kiểm tra validation
        List<String> errors = new ArrayList<>();
        if (fullName.isEmpty()) {
           errors.add("Full name cannot be empty.");
        }
        if (!gender.equals("Male") && !gender.equals("Female") && !gender.equals("Other") ) {
            errors.add("Invalid gender.");
        }
        if (!isValidEmail(email)) {
            errors.add("Invalid email.");
        }
        if (!isValidPassword(password)) {
            errors.add("Password must be at least 6 characters long.");
        }
        if (!password.equals(confirmPassword)) {
            errors.add("Passwords do not match.");
        }
        if (!isValidPhoneNumber(phoneNumber)) {
            errors.add("Phone cannot be empty.");
        }
        if (address.isEmpty()) {
            errors.add("Address cannot be empty.");
        }

        // Nếu có lỗi -> Quay về trang đăng ký
        if (!errors.isEmpty()) {
            request.setAttribute("errors", errors);
            request.getRequestDispatcher("UserPage/Register.jsp").forward(request, response);
            return;
        }

        // Kiểm tra email đã tồn tại chưa
        UserDao userDAO = new UserDao();
        if (userDAO.isEmailRegistered(email)) {
            errors.add("This email has already been used.");
            request.setAttribute("errors", errors);
            request.getRequestDispatcher("UserPage/Register.jsp").forward(request, response);
            return;
        }

        // Mã hóa mật khẩu trước khi lưu
        String hashedPassword = HashUtil.hashPassword(password);
        

        // Tạo đối tượng User
        User user = new User(fullName, gender, email, hashedPassword, phoneNumber, address, 2, true, false);

        // Đăng ký tài khoản
        if (userDAO.register(user)) {
            response.sendRedirect("UserPage/Login.jsp?registerSuccess=true");
        } else {
            errors.add("An error occurred during registration. Please try again.");
            request.setAttribute("errors", errors);
            request.getRequestDispatcher("UserPage/Register.jsp").forward(request, response);
        }
    }

    private boolean isValidEmail(String email) {
        String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        return email.matches(regex);
    }

    private boolean isValidPassword(String password) {
        return password.length() >= 6;
    }

    private boolean isValidPhoneNumber(String phone) {
        return phone.matches("\\d+"); // Chỉ chứa số
    }

    private String getParameterSafe(HttpServletRequest request, String paramName) {
        String value = request.getParameter(paramName);
        return (value != null) ? value.trim() : "";
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

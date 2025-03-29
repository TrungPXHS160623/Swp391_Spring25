/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import Dao.CartDetailDao;
import Dao.UserDao;
import Entity.User;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import Util.HashUtil;

/**
 *
 * @author Acer
 */
@WebServlet(name = "LoginController", urlPatterns = {"/logincontroller"})
public class LoginController extends HttpServlet {

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
            out.println("<title>Servlet LoginController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet LoginController at " + request.getContextPath() + "</h1>");
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
        RequestDispatcher dispatcher = request.getRequestDispatcher("/UserPage/Login.jsp");
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
        
        //lấy thông tin bên view từ trang home
        String email = request.getParameter("username");
        String password = request.getParameter("password");
        String rememberMe = request.getParameter("rememberMe");

        // Validate: Kiểm tra email có đúng định dạng không
        if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            request.setAttribute("errorMessage", "Invalid email format.");
            request.getRequestDispatcher("/UserPage/Login.jsp").forward(request, response);
            return;
        }

        // Validate: Mật khẩu không được để trống
        if (password == null || password.trim().isEmpty()) {
            request.setAttribute("errorMessage", "Password cannot be empty.");
            request.getRequestDispatcher("/UserPage/Login.jsp").forward(request, response);
            return;
        }

        UserDao userDAO = new UserDao();
        //User user = userDAO.login(email, password);
        User user = userDAO.getUserByEmail(email); // 🔥 Lấy user bằng email, không truyền password nữa

        // Kiểm tra user có tồn tại không và kiểm tra mật khẩu đã hash
        if (user != null && HashUtil.checkPassword(password, user.getPassword_hash())) { // 🔥 Dùng checkpw() để kiểm tra mật khẩu

        HttpSession session = request.getSession();
        session.setAttribute("user", user);
        session.setAttribute("userId", user.getUser_id());
        

        // Lấy số lượng sản phẩm trong giỏ hàng
        CartDetailDao cartDao = new CartDetailDao();
        int cartCount = cartDao.getCartItemCount(user.getUser_id());
        session.setAttribute("cartCount", cartCount);


        // 🔥 Nếu Remember Me được chọn, lưu email + password + rememberMe vào cookie
        if ("on".equals(rememberMe)) {
            Cookie emailCookie = new Cookie("rememberedEmail", email);
            Cookie passwordCookie = new Cookie("rememberedPassword", password);
            Cookie rememberCookie = new Cookie("rememberMe", "true");
            
            emailCookie.setMaxAge(60 * 60 * 24 * 30); // Lưu trong 30 ngày
            passwordCookie.setMaxAge(60 * 60 * 24 * 30);
            rememberCookie.setMaxAge(60 * 60 * 24 * 30);
            
            emailCookie.setPath("/"); // Áp dụng cho toàn bộ ứng dụng
            passwordCookie.setPath("/");
            rememberCookie.setPath("/");
            
            response.addCookie(emailCookie);
            response.addCookie(passwordCookie);
            response.addCookie(rememberCookie);
        } else {
            // 🔥 Nếu không chọn Remember Me, xóa cookie cũ nếu có
            Cookie emailCookie = new Cookie("rememberedEmail", "");
            Cookie passwordCookie = new Cookie("rememberedPassword", "");
            Cookie rememberCookie = new Cookie("rememberMe", "");

            emailCookie.setMaxAge(0);
            passwordCookie.setMaxAge(0);
            rememberCookie.setMaxAge(0);
            
            emailCookie.setPath("/"); // Áp dụng cho toàn bộ ứng dụng
            passwordCookie.setPath("/");
            rememberCookie.setPath("/");

            response.addCookie(emailCookie);
            response.addCookie(passwordCookie);
            response.addCookie(rememberCookie);
        }

            int roleId = user.getRole_id();
            
            if (roleId == 2) { // Customer role
                response.sendRedirect(request.getContextPath() + "/UserPage/Home.jsp");
                return;
            } else if (roleId == 1 || roleId == 4) { // Admin or Marketing role
                response.sendRedirect(request.getContextPath() + "/admin/customers");
                return;
            } else {
                // Default redirect for any other role
                response.sendRedirect(request.getContextPath() + "/UserPage/Home.jsp");
                return;
            }

//        // Remember Me (lưu email vào cookie)
//        if ("on".equals(rememberMe)) {
//            Cookie cookie = new Cookie("rememberedEmail", email);
//            cookie.setMaxAge(7 * 24 * 60 * 60);
//            response.addCookie(cookie);
//
//        } else {
//            Cookie cookie = new Cookie("rememberedEmail", "");
//            cookie.setMaxAge(0);
//            response.addCookie(cookie);
//        }

       // response.sendRedirect(request.getContextPath() + "/UserPage/Home.jsp");
    } else {
        request.setAttribute("errorMessage", "Invalid email or password.");
        request.getRequestDispatcher("/UserPage/Login.jsp").forward(request, response);
    }
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
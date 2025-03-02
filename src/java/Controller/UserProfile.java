package Controller;

import Dao.UserDao;
import Entity.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "UserProfile", urlPatterns = {"/userprofile"})
public class UserProfile extends HttpServlet {
    
    private final UserDao userDAO = new UserDao();
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("userId");

        // Ghi log kiểm tra session
        System.out.println("UserProfile: userId from session = " + userId);
        
        if (userId == null) {
            response.sendRedirect(request.getContextPath() + "/UserPage/Login.jsp");
            return;
        }
        
        User user = userDAO.getUserById(userId);
        request.setAttribute("user", user);
        request.getRequestDispatcher("/UserPage/UserProfile.jsp").forward(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("userId");
        
        if (userId == null) {
            response.sendRedirect(request.getContextPath() + "/UserPage/Login.jsp");
            return;
        }
        
        String fullName = request.getParameter("full_name");
        String gender = request.getParameter("gender");
        String phoneNumber = request.getParameter("phone_number");
        String address = request.getParameter("address");
        String email = request.getParameter("email");

        // Regex kiểm tra dữ liệu nhập vào
        //String emailRegex = "^[a-zA-Z0-9]+@(fpt\\.edu\\.vn|gmail\\.com)$";
        String phoneRegex = "^(0|\\+84)[3-9][0-9]{8}$";
        String addressRegex = "^.{5,}$";
        String fullNameRegex = "^[A-Za-zÀ-Ỹà-ỹ\\s]{1,50}$";

//        // Kiểm tra hợp lệ
//        if (!email.matches(emailRegex)) {
//            session.setAttribute("updateMessage", "❌ Email không hợp lệ! Chỉ chấp nhận email FPT hoặc Gmail.");
//            response.sendRedirect("userprofile");
//            return;
//        }
        if (!fullName.matches(fullNameRegex)) {
            session.setAttribute("updateMessage", "❌ Tên chứa số, ký tự đặc biệt hoặc quá dài!");
            response.sendRedirect("userprofile");
            return;
        }
        
        if (!phoneNumber.matches(phoneRegex)) {
            session.setAttribute("updateMessage", "❌ Số điện thoại không hợp lệ!");
            response.sendRedirect("userprofile");
            return;
        }
        
        if (!address.matches(addressRegex)) {
            session.setAttribute("updateMessage", "❌ Địa chỉ phải có ít nhất 5 ký tự!");
            response.sendRedirect("userprofile");
            return;
        }
        
        User updatedUser = new User(userId, fullName, gender, phoneNumber, address);
        boolean updateSuccess = userDAO.updateUserProfile(updatedUser);
        
        if (updateSuccess) {
            // Cập nhật session với thông tin mới
            session.setAttribute("user", updatedUser);
            session.setAttribute("updateMessage", "Cập nhật thông tin thành công!");
        } else {
            session.setAttribute("updateMessage", "Cập nhật thất bại, vui lòng thử lại!");
        }
        
        response.sendRedirect("userprofile");
    }
    
    @Override
    public String getServletInfo() {
        return "Servlet xử lý hiển thị và cập nhật thông tin người dùng";
    }
}

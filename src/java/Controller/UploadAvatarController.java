/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package Controller;

import Dao.UserDao;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import java.io.File;
import java.nio.file.Paths;

/**
 *
 * @author Acer
 */
@MultipartConfig(maxFileSize = 1024 * 1024 * 5) // Giới hạn ảnh tối đa 5MB
public class UploadAvatarController extends HttpServlet {
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
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
            out.println("<title>Servlet UploadAvatarController</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet UploadAvatarController at " + request.getContextPath () + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    } 

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
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
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("userId");

        if (userId == null) {
            response.sendRedirect("UserPage/Login.jsp");
            return;
        }

        Part filePart = request.getPart("avatar"); // Lấy file ảnh từ request
        String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();

        // Kiểm tra định dạng ảnh
        if (!fileName.matches(".*\\.(jpg|jpeg|png|gif)$")) {
            session.setAttribute("updateMessage", "Chỉ chấp nhận ảnh JPG, PNG, GIF.");
            response.sendRedirect("userprofile");
            return;
        }

        // Định nghĩa thư mục lưu ảnh
        String uploadPath = getServletContext().getRealPath("") + File.separator + "uploads";
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) uploadDir.mkdir(); // Tạo thư mục nếu chưa có

        // Lưu file vào thư mục uploads/
        String filePath = uploadPath + File.separator + fileName;
        filePart.write(filePath);

        // Lưu đường dẫn ảnh vào database
        UserDao userDAO = new UserDao();
        boolean updated = userDAO.updateUserAvatar(userId, "uploads/" + fileName);

        if (updated) {
            session.setAttribute("updateMessage", "Cập nhật ảnh thành công!");
        } else {
            session.setAttribute("updateMessage", "Cập nhật ảnh thất bại.");
        }

        response.sendRedirect("userprofile");
    }
    

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}

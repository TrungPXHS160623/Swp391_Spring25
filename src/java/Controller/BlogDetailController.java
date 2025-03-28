/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import Dao.BlogDao;
import Dto.PostDto;
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
public class BlogDetailController extends HttpServlet {

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
            out.println("<title>Servlet BlogDetailController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet BlogDetailController at " + request.getContextPath() + "</h1>");
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
    private BlogDao blogDao = new BlogDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String keyword = request.getParameter("keyword") != null ? request.getParameter("keyword").trim() : "";
        String category = request.getParameter("category") != null ? request.getParameter("category").trim() : "";

        // Lấy danh sách tên danh mục từ DB
        List<String> categoryList = blogDao.getAllCategoryNames();
        request.setAttribute("categoryList", categoryList);

        // Lấy postId từ request, nếu không có thì chuyển hướng về BlogListController
        String postIdParam = request.getParameter("postId");
        if (postIdParam == null || postIdParam.trim().isEmpty()) {
            response.sendRedirect("BlogListController");
            return;
        }

        int postId;
        try {
            postId = Integer.parseInt(postIdParam.trim());
        } catch (NumberFormatException e) {
            response.sendRedirect("BlogListController");
            return;
        }
        // Lấy latest 3 blog
        List<PostDto> latest3Blogs = blogDao.latest3Bloglink();
        request.setAttribute("latest3Blogs", latest3Blogs);

        // Lấy chi tiết blog theo postId
        PostDto blogDetail = blogDao.getBlogDetail(postId);
        if (blogDetail == null) {
            request.setAttribute("error", "Không tìm thấy blog với postId = " + postId);
        } else {
            request.setAttribute("blogDetail", blogDetail);
        }

        // Chuyển tiếp sang trang JSP hiển thị chi tiết blog
        RequestDispatcher dispatcher = request.getRequestDispatcher("CommonPage/BlogDetail.jsp");
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

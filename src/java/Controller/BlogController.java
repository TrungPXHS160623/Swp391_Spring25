/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import Dao.BlogDAO;
import Entity.Post;
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
public class BlogController extends HttpServlet {

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
            out.println("<title>Servlet BlogController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet BlogController at " + request.getContextPath() + "</h1>");
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
    private BlogDAO blogDAO = new BlogDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // 1. Lấy thông tin phân trang: page và pageSize (mặc định: 1 và 3)
        int page = 1;
        int pageSize = 3;
        try {
            String pageParam = request.getParameter("page");
            if (pageParam != null) {
                page = Integer.parseInt(pageParam);
            }
        } catch (NumberFormatException e) {
            // giữ nguyên giá trị mặc định page = 1
        }
        try {
            String pageSizeParam = request.getParameter("pageSize");
            if (pageSizeParam != null) {
                pageSize = Integer.parseInt(pageSizeParam);
            }
        } catch (NumberFormatException e) {
            // giữ nguyên giá trị mặc định pageSize = 3
        }

        // 2. Xử lý action: search, category, hoặc mặc định là getAllBlogs
        String action = request.getParameter("action");
        List<Post> listBlogs = null;
        if ("search".equalsIgnoreCase(action)) {
            String keyword = request.getParameter("keyword");
            if (keyword == null) {
                keyword = "";
            }
            listBlogs = blogDAO.searchBlogs(keyword, page, pageSize);
        } else if ("category".equalsIgnoreCase(action)) {
            String category = request.getParameter("category");
            if (category == null || category.trim().isEmpty()) {
                listBlogs = blogDAO.getAllBlogs(page, pageSize);
            } else {
                listBlogs = blogDAO.getBlogsByCategory(category, page, pageSize);
            }
        } else {
            listBlogs = blogDAO.getAllBlogs(page, pageSize);
        }

        // 3. Lấy danh sách blog mới nhất (Latest Blogs) với giới hạn 3 blog
        List<Post> latestBlogs = blogDAO.getLatestBlogs(3);

        // 4. Lấy danh sách các Category (cho combobox)
        List<String> categories = blogDAO.getAllCategoryNames();

        // 5. Đặt các attribute để JSP sử dụng
        request.setAttribute("listBlogs", listBlogs);
        request.setAttribute("latestBlogs", latestBlogs);
        request.setAttribute("categories", categories);

        // 6. Forward sang JSP hiển thị (blogList.jsp nằm trong thư mục home)
        request.getRequestDispatcher("/CommonPage/BlogList.jsp").forward(request, response);
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

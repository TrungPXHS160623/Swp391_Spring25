/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package Controller.post;

import dao.PostDAO;
import dao.CategoryDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.io.File;
import java.sql.Date;
import model.BlogPost;
/**
 *
 * @author KieuVietPhuoc
 */
@WebServlet(name="BlogListController", urlPatterns={"/blog-list"})
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 10,
        maxFileSize = 1024 * 1024 * 50,
        maxRequestSize = 1024 * 1024 * 100
)
public class BlogListController extends HttpServlet {
   
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
            out.println("<title>Servlet BlogListController</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet BlogListController at " + request.getContextPath () + "</h1>");
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
        String search = request.getParameter("search");
        String category = request.getParameter("category");
        String status = request.getParameter("status");
        String page = request.getParameter("page");
        int intPage = -1;
        if(page == null){
            page = "1";
        }
        try {
            intPage = Integer.parseInt(page);
        } catch (Exception e) {
            response.sendRedirect("blog-list");
        }
        PostDAO blogDAO = new PostDAO();
        CategoryDAO cate = new CategoryDAO();
        request.setAttribute("listBlog", blogDAO.listBlog(search, intPage, 10, category, status));
        int totalPage = blogDAO.listBlog(search, intPage, 2000000000, category, status).size()/10;
        if (blogDAO.listBlog(search, intPage, 2000000000, category, status).size() % 10 != 0) {
            totalPage += 1;
        }
        request.setAttribute("totalPage", totalPage);
        request.setAttribute("search", search);
        request.setAttribute("listCategory", cate.getAllCategories());
        request.setAttribute("category", category);
        request.setAttribute("status", status);
        request.setAttribute("page", page);
        request.setAttribute("pagination_url", "blog-list?");
        request.getSession().setAttribute("backlink", "blog-list");
        request.getRequestDispatcher("bloglist.jsp").forward(request, response);
        
        
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
        String postTitle = request.getParameter("postTitle");
        String postCategory = request.getParameter("postCategory");
        String postStatus = request.getParameter("postStatus");
        String postBrief = request.getParameter("postBrief");
        String postContent = request.getParameter("postContent");
        String postThumbnail = uploadFile(request);
        String featuredPost = request.getParameter("featuredPost");
        PostDAO blogDAO = new PostDAO();
        try{
            BlogPost post = new BlogPost();
            post.setTitle(postTitle);
            post.setPostCategories_id(Integer.parseInt(postCategory));
            post.setStatus(Integer.parseInt(postStatus));
            post.setBrief_info(postBrief);
            post.setDetails(postContent);
            post.setThumbnail(postThumbnail);
            post.setFlag_feature(Boolean.parseBoolean(featuredPost));
            post.setUser_id(1);
            long createdDate = System.currentTimeMillis();
            Date updatedDate = new Date(createdDate);
            post.setUpdatedDate(updatedDate);
            blogDAO.addPost(post);
            response.sendRedirect("blog-list");
        }
        catch(Exception e) {
            System.out.println(e.getMessage());
        }
        
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }

    public String uploadFile(HttpServletRequest request) throws IOException, ServletException {
        String fileName = "";
        String uploadPath = getServletContext().getRealPath("") + File.separator + "blog";
        File uploadDir = new File(uploadPath);

        if (!uploadDir.exists()) {
            boolean created = uploadDir.mkdir();
        }

        for (Part part : request.getParts()) {
            fileName = getFileName(part);
            if (!fileName.isEmpty()) {
                part.write(uploadPath + File.separator + fileName);
                return fileName;
            }
        }
        return "";
    }

    private String getFileName(Part part) {
        String contentDisposition = part.getHeader("content-disposition");
        String[] tokens = contentDisposition.split(";");
        for (String token : tokens) {
            if (token.trim().startsWith("filename")) {
                return token.substring(token.indexOf("=") + 2, token.length() - 1);
            }
        }
        return "";
    }

}

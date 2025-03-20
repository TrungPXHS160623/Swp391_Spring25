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
import model.BlogPost;


@WebServlet(name="EditBlogController", urlPatterns={"/edit-post"})
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 10,
        maxFileSize = 1024 * 1024 * 50,
        maxRequestSize = 1024 * 1024 * 100
)
public class EditBlogController extends HttpServlet {
   
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
            out.println("<title>Servlet EditBlogController</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet EditBlogController at " + request.getContextPath () + "</h1>");
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
        String id = request.getParameter("id");
        String action = request.getParameter("action");
        int userId = -1;
        try {
            userId = Integer.parseInt(id);
        } catch (Exception e) {
            response.sendRedirect("blog-list");
        }
        
        PostDAO daoPost = new PostDAO();
        BlogPost blog = new BlogPost();
        CategoryDAO cate = new CategoryDAO();
        if(action.equals("del")){
            daoPost.deleteBlogById(id);
            response.sendRedirect("blog-list");
        }else{
        blog = daoPost.getPostbyID(userId);
        request.setAttribute("listCategory", cate.getAllCategories());
        request.setAttribute("blog", blog);
        request.getRequestDispatcher("blogedit.jsp").forward(request, response);
        }
        
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
        try {
            String postTitle = request.getParameter("postTitle");
            String postBrief = request.getParameter("postBrief");
            String postContent = request.getParameter("postContent");
            String postStatus = request.getParameter("postStatus");
            String postCategory = request.getParameter("postCategory");
            String postID = request.getParameter("postID");
            String featuredPost = request.getParameter("featuredPost");
            String postImage = uploadFile(request);
            if (featuredPost.equals("on")){
                featuredPost = "1";
            }else{
                featuredPost = "0";
            }
            PostDAO dao = new PostDAO();
            BlogPost newItem = new BlogPost();
            newItem.setTitle(postTitle);
            newItem.setBrief_info(postBrief);
            newItem.setDetails(postContent);
            newItem.setStatus(Integer.parseInt(postStatus));
            newItem.setPostCategories_id(Integer.parseInt(postCategory));
            newItem.setId(Integer.parseInt(postID));
            newItem.setThumbnail(postImage);
            newItem.setFlag_feature(Boolean.parseBoolean(featuredPost));
            dao.updatePost(newItem);
            response.sendRedirect("edit-post?id="+postID);
        }catch(Exception e){
            response.sendRedirect("blog-list");
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

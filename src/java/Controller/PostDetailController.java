package Controller;

import Dao.PostDao;
import Dto.PostDto;
import Entity.CategoryPost;
import Entity.Post_2;
import java.io.File;
import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;

@MultipartConfig(
    fileSizeThreshold = 1024 * 1024, // 1 MB
    maxFileSize = 1024 * 1024 * 10, // 10 MB
    maxRequestSize = 1024 * 1024 * 15 // 15 MB
)
@WebServlet(name="PostDetailController", urlPatterns={"/PostDetailController"})
public class PostDetailController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        // Check if user is logged in
        HttpSession session = request.getSession();
        Entity.User loggedInUser = (Entity.User) session.getAttribute("user");
        
        // If not logged in, redirect to login page
        if (loggedInUser == null) {
            // Store the original requested URL to redirect back after login
            session.setAttribute("redirectURL", request.getRequestURL().toString() + (request.getQueryString() != null ? "?" + request.getQueryString() : ""));
            response.sendRedirect(request.getContextPath() + "/logincontroller");
            return;
        }
        
        try {
            String action = request.getParameter("action");
            
            // Initialize DAO
            PostDao postDao = new PostDao();
            
            if ("view".equals(action) || "edit".equals(action)) {
                // Get post ID
                int postId = Integer.parseInt(request.getParameter("id"));
                
                // Get post details
                PostDto post = postDao.getPostById(postId);
                
                if (post != null) {
                    // Get categories for dropdown
                    List<CategoryPost> categories = postDao.getAllCategories();
                    
                    // Set attributes for the JSP
                    request.setAttribute("post", post);
                    request.setAttribute("categories", categories);
                    request.setAttribute("mode", action); // "view" or "edit" mode
                    
                    // Forward to the JSP page
                    request.getRequestDispatcher("/AdminPage/PostDetail.jsp").forward(request, response);
                    return;
                } else {
                    // Post not found
                    request.setAttribute("errorMessage", "Post not found with ID: " + postId);
                    request.getRequestDispatcher("/AdminPage/error.jsp").forward(request, response);
                    return;
                }
            } else if ("create".equals(action)) {
                // Get categories for dropdown
                List<Entity.CategoryPost> categories = postDao.getAllCategories();
                
                // Set attributes for the JSP
                request.setAttribute("categories", categories);
                request.setAttribute("mode", "create");
                
                // Forward to the JSP page
                request.getRequestDispatcher("/AdminPage/PostDetail.jsp").forward(request, response);
                return;
            }
            
            // Default: redirect to post list
            response.sendRedirect(request.getContextPath() + "/PostListController");
            
        } catch (Exception e) {
            // Log the exception
            e.printStackTrace();
            
            // Set error message
            request.setAttribute("errorMessage", "An error occurred: " + e.getMessage());
            request.getRequestDispatcher("/AdminPage/error.jsp").forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Check if user is logged in
        HttpSession session = request.getSession();
        Entity.User loggedInUser = (Entity.User) session.getAttribute("user");
        
        // If not logged in, redirect to login page
        if (loggedInUser == null) {
            session.setAttribute("errorMessage", "You must be logged in to perform this action.");
            response.sendRedirect(request.getContextPath() + "/logincontroller");
            return;
        }
        
        String action = request.getParameter("action");
        
        try {
            if ("save".equals(action)) {
                // Get parameters
                String postIdStr = request.getParameter("postId");
                String title = request.getParameter("title");
                String content = request.getParameter("content");
                int categoryId = Integer.parseInt(request.getParameter("categoryId"));
                boolean status = "true".equals(request.getParameter("status"));
                
                // Get the upload path for thumbnails
                String uploadPath = getServletContext().getRealPath("") + File.separator + "uploads" + File.separator + "posts";
                File uploadDir = new File(uploadPath);
                if (!uploadDir.exists()) {
                    uploadDir.mkdirs();
                }
                
                // Handle thumbnail upload
                String thumbnailUrl = null;
                Part filePart = request.getPart("thumbnail");
                if (filePart != null && filePart.getSize() > 0) {
                    String fileName = System.currentTimeMillis() + "_" + getFileName(filePart);
                    String filePath = uploadPath + File.separator + fileName;
                    filePart.write(filePath);
                    thumbnailUrl = "uploads/posts/" + fileName;
                } else {
                    // Keep existing thumbnail if not uploading a new one
                    thumbnailUrl = request.getParameter("existingThumbnail");
                }
                
                // Get user ID from session (authenticated user) - already verified above
                int userId = loggedInUser.getUser_id();
                
                PostDao postDao = new PostDao();
                boolean success = false;
                
                if (postIdStr != null && !postIdStr.isEmpty()) {
                    // Update existing post
                    int postId = Integer.parseInt(postIdStr);
                    Post_2 post = new Post_2();
                    post.setPostId(postId);
                    post.setTitle(title);
                    post.setContent(content);
                    post.setThumbnailUrl(thumbnailUrl);
                    post.setUserId(userId);
                    post.setCategoryId(categoryId);
                    post.setStatus(status);
                    
                    success = postDao.updatePost(post);
                    
                    if (success) {
                        session.setAttribute("successMessage", "Post updated successfully!");
                    } else {
                        session.setAttribute("errorMessage", "Failed to update post.");
                    }
                } else {
                    // Create new post
                    Post_2 post = new Post_2();
                    post.setTitle(title);
                    post.setContent(content);
                    post.setThumbnailUrl(thumbnailUrl);
                    post.setUserId(userId);
                    post.setCategoryId(categoryId);
                    post.setStatus(status);
                    
                    int newPostId = postDao.insertPost(post);
                    
                    if (newPostId > 0) {
                        session.setAttribute("successMessage", "Post created successfully!");
                        success = true;
                    } else {
                        session.setAttribute("errorMessage", "Failed to create post.");
                    }
                }
                
                // Redirect based on result
                if (success) {
                    response.sendRedirect(request.getContextPath() + "/PostListController");
                } else {
                    response.sendRedirect(request.getHeader("Referer"));
                }
                return;
            }
        } catch (Exception e) {
            request.getSession().setAttribute("errorMessage", "An error occurred: " + e.getMessage());
            response.sendRedirect(request.getContextPath() + "/PostListController");
            return;
        }
        
        processRequest(request, response);
    }
    
    // Helper method to extract file name from part
    private String getFileName(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        String[] tokens = contentDisp.split(";");
        for (String token : tokens) {
            if (token.trim().startsWith("filename")) {
                return token.substring(token.indexOf("=") + 2, token.length() - 1);
            }
        }
        return "";
    }

    @Override
    public String getServletInfo() {
        return "Post Detail Controller";
    }
}

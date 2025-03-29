/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import Dao.PostDao;
import Dto.PostDto;
import Dto.PostDto2;
import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 *
 * @author Admin
 */
@WebServlet(name = "PostListController", urlPatterns = {"/PostListController"})
public class PostListController extends HttpServlet {

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
            // Get parameters for pagination, filtering, and sorting
            String pageStr = request.getParameter("page");
            int page = 1;
            try {
                if (pageStr != null && !pageStr.isEmpty()) {
                    page = Math.max(1, Integer.parseInt(pageStr)); // Ensure page is at least 1
                }
            } catch (NumberFormatException e) {
                // Default to page 1 if invalid
            }

            Integer pageSizeSession = (Integer) session.getAttribute("rowsPerPage");

            int pageSize = 5;
            if (pageSizeSession != null && pageSizeSession >= 1) {
                pageSize = pageSizeSession;
            } else {
                session.setAttribute("rowsPerPage", pageSize);
            }

            // Get filter parameters
            String categoryId = request.getParameter("categoryId");
            String authorId = request.getParameter("authorId");
            String status = request.getParameter("status");
            String searchTitle = request.getParameter("searchTitle");

            // Get sorting parameters
            String sortBy = request.getParameter("sortBy");
            String sortOrder = request.getParameter("sortOrder");

            // Initialize DAO
            PostDao postDao = new PostDao();

            // Get categories and authors for filter dropdowns - Do this before any filtering
            List<Entity.CategoryPost> categories = postDao.getAllCategories();
            List<Entity.User> authors = postDao.getAllAuthors();

            // Set dropdown data
            request.setAttribute("categories", categories);
            request.setAttribute("authors", authors);

            // Handle category filter as integer if not empty
            if (categoryId != null && !categoryId.isEmpty()) {
                try {
                    Integer.parseInt(categoryId); // Validate it's a valid integer
                } catch (NumberFormatException e) {
                    categoryId = ""; // Reset if not a valid integer
                }
            }

            // Get total count for pagination before applying pagination to validate current page
            int totalPosts = postDao.countPosts(categoryId, authorId, status, searchTitle);
            int totalPages = (int) Math.ceil((double) totalPosts / pageSize);

            // Validate page number against total pages
            if (totalPages > 0 && page > totalPages) {
                page = totalPages;
            }

            // Get posts according to filters and pagination
            List<PostDto2> posts = postDao.getPosts(page, pageSize, categoryId, authorId, status, searchTitle, sortBy, sortOrder);

            // Set attributes for the JSP
            request.setAttribute("posts", posts);
            request.setAttribute("currentPage", page);
            request.setAttribute("pageSize", pageSize);
            request.setAttribute("totalPages", totalPages);
            request.setAttribute("totalPosts", totalPosts);

            // Set filter attributes
            request.setAttribute("categoryId", categoryId);
            request.setAttribute("authorId", authorId);
            request.setAttribute("status", status);
            request.setAttribute("searchTitle", searchTitle);

            // Set sorting attributes
            request.setAttribute("sortBy", sortBy);
            request.setAttribute("sortOrder", sortOrder);

            // Forward to the JSP page
            request.getRequestDispatcher("/AdminPage/PostList.jsp").forward(request, response);

        } catch (Exception e) {
            // Log the exception
            e.printStackTrace();

            // Set error message
            request.setAttribute("errorMessage", "An error occurred: " + e.getMessage());
            request.getRequestDispatcher("/AdminPage/error.jsp").forward(request, response);
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
            PostDao postDao = new PostDao();

            if ("updateStatus".equals(action)) {
                // Get parameters
                int postId = Integer.parseInt(request.getParameter("postId"));
                boolean status = Boolean.parseBoolean(request.getParameter("status"));

                // Update post status
                boolean success = postDao.updatePostStatus(postId, status);

                // Set success/error message
                if (success) {
                    request.getSession().setAttribute("successMessage", "Post status updated successfully!");
                } else {
                    request.getSession().setAttribute("errorMessage", "Failed to update post status.");
                }

                // Redirect back to post list without filters but keep pagination
                response.sendRedirect(request.getContextPath() + "/PostListController" + buildMinimalQueryString(request));
                return;
            }

            // If no specific action, process as a normal request
            processRequest(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.getSession().setAttribute("errorMessage", "An error occurred: " + e.getMessage());
            response.sendRedirect(request.getContextPath() + "/PostListController");
        }
    }

    // Helper method to preserve only pagination parameters when redirecting
    private String buildMinimalQueryString(HttpServletRequest request) {
        StringBuilder queryString = new StringBuilder("?");

        // Keep only page and pageSize parameters
        String page = request.getParameter("page");
        if (page != null && !page.isEmpty()) {
            queryString.append("page=").append(page);
        } else {
            queryString.append("page=1"); // Default to first page if not specified
        }

        String pageSize = request.getParameter("pageSize");
        if (pageSize != null && !pageSize.isEmpty()) {
            queryString.append("&pageSize=").append(pageSize);
        }

        return queryString.toString();
    }

    // Helper method to preserve query parameters when redirecting
    private String buildQueryString(HttpServletRequest request) {
        StringBuilder queryString = new StringBuilder("?");

        String page = request.getParameter("page");
        if (page != null && !page.isEmpty()) {
            queryString.append("page=").append(page).append("&");
        }

        String pageSize = request.getParameter("pageSize");
        if (pageSize != null && !pageSize.isEmpty()) {
            queryString.append("pageSize=").append(pageSize).append("&");
        }

        String categoryId = request.getParameter("categoryId");
        if (categoryId != null && !categoryId.isEmpty()) {
            queryString.append("categoryId=").append(categoryId).append("&");
        }

        String authorId = request.getParameter("authorId");
        if (authorId != null && !authorId.isEmpty()) {
            queryString.append("authorId=").append(authorId).append("&");
        }

        String status = request.getParameter("status");
        if (status != null && !status.isEmpty()) {
            queryString.append("status=").append(status).append("&");
        }

        String searchTitle = request.getParameter("searchTitle");
        if (searchTitle != null && !searchTitle.isEmpty()) {
            queryString.append("searchTitle=").append(searchTitle).append("&");
        }

        String sortBy = request.getParameter("sortBy");
        if (sortBy != null && !sortBy.isEmpty()) {
            queryString.append("sortBy=").append(sortBy).append("&");
        }

        String sortOrder = request.getParameter("sortOrder");
        if (sortOrder != null && !sortOrder.isEmpty()) {
            queryString.append("sortOrder=").append(sortOrder);
        }

        // Remove trailing '&' if exists
        String result = queryString.toString();
        if (result.endsWith("&")) {
            result = result.substring(0, result.length() - 1);
        }

        return result;
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Post List Controller";
    }// </editor-fold>

}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package Controller;

import Dao.FeedbackDao;
import Dto.FeedbackDto;
import Entity.User;
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
@WebServlet(name="FeedbackList", urlPatterns={"/FeedbackList"})
public class FeedbackList extends HttpServlet {
   
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
        request.setCharacterEncoding("UTF-8");
        
        // Check if user is logged in and has admin/staff privileges
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null || user.getRole_id() != 1) { // Assuming role_id 1 is for admin
            response.sendRedirect(request.getContextPath() + "/logincontroller");
            return;
        }
        
        try {
            // Get parameters for filtering and pagination
            String searchKeyword = request.getParameter("search");
            
            Integer productId = null;
            if (request.getParameter("productId") != null && !request.getParameter("productId").isEmpty()) {
                productId = Integer.parseInt(request.getParameter("productId"));
            }
            
            Integer rating = null;
            if (request.getParameter("rating") != null && !request.getParameter("rating").isEmpty()) {
                rating = Integer.parseInt(request.getParameter("rating"));
            }
            
            Boolean status = null;
            if (request.getParameter("status") != null && !request.getParameter("status").isEmpty()) {
                status = "true".equals(request.getParameter("status"));
            }
            
            // Get sorting parameters
            String sortBy = request.getParameter("sortBy");
            String sortOrder = request.getParameter("sortOrder");
            
            // Pagination parameters
            int page = 1;
            int pageSize = 10;
            
            if (request.getParameter("page") != null) {
                page = Integer.parseInt(request.getParameter("page"));
            }
            
            if (request.getParameter("pageSize") != null) {
                pageSize = Integer.parseInt(request.getParameter("pageSize"));
            }
            
            FeedbackDao feedbackDao = new FeedbackDao();
            
            // Get total count for pagination
            int totalFeedbacks = feedbackDao.getTotalFeedbacks(searchKeyword, productId, rating, status);
            int totalPages = (int) Math.ceil((double) totalFeedbacks / pageSize);
            
            // Get feedback list with filters and pagination
            List<FeedbackDto> feedbackList = feedbackDao.getFeedbackList(
                    page, pageSize, searchKeyword, productId, rating, status, sortBy, sortOrder);
            
            // Get all products for the filter dropdown
            List<FeedbackDao.Product> productList = feedbackDao.getAllProducts();
            
            // Set attributes for the JSP
            request.setAttribute("feedbackList", feedbackList);
            request.setAttribute("productList", productList);
            request.setAttribute("totalPages", totalPages);
            request.setAttribute("currentPage", page);
            request.setAttribute("searchKeyword", searchKeyword);
            request.setAttribute("selectedProductId", productId);
            request.setAttribute("selectedRating", rating);
            request.setAttribute("selectedStatus", status);
            request.setAttribute("sortBy", sortBy);
            request.setAttribute("sortOrder", sortOrder);
            
            // Forward to JSP
            request.getRequestDispatcher("/AdminPage/FeedbackList.jsp").forward(request, response);
            
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "An error occurred: " + e.getMessage());
            request.getRequestDispatcher("/AdminPage/error.jsp").forward(request, response);
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
        processRequest(request, response);
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Feedback management servlet";
    }// </editor-fold>

}

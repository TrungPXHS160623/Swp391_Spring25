package Controller;

import Dao.FeedbackDao;
import Dto.FeedbackDto;
import Entity.User;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
@WebServlet(name="FeedbackDetail", urlPatterns={"/admin/feedback"})

public class FeedbackDetail extends HttpServlet {

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
        
        String action = request.getParameter("action");
        if (action == null) {
            action = "view";
        }
        
        try {
            int reviewId = Integer.parseInt(request.getParameter("id"));
            FeedbackDao feedbackDao = new FeedbackDao();
            
            switch (action) {
                case "view":
                    FeedbackDto feedback = feedbackDao.getFeedbackById(reviewId);
                    if (feedback != null) {
                        request.setAttribute("feedback", feedback);
                        request.getRequestDispatcher("/AdminPage/FeedbackDetail.jsp").forward(request, response);
                    } else {
                        request.setAttribute("errorMessage", "Feedback not found");
                        request.getRequestDispatcher("/AdminPage/error.jsp").forward(request, response);
                    }
                    break;
                    
                case "changeStatus":
                    boolean newStatus = Boolean.parseBoolean(request.getParameter("status"));
                    boolean updated = feedbackDao.updateFeedbackStatus(reviewId, newStatus);
                    
                    if (updated) {
                        // Get the page to redirect back to
                        String page = request.getParameter("page");
                        if (page == null || page.isEmpty()) {
                            page = "1";
                        }
                        
                        // Redirect back to the feedback list with success message
                        response.sendRedirect(request.getContextPath() + 
                                             "/admin/feedbacks?page=" + page + 
                                             "&statusChanged=true&reviewId=" + reviewId);
                    } else {
                        request.setAttribute("errorMessage", "Failed to update feedback status");
                        request.getRequestDispatcher("/AdminPage/error.jsp").forward(request, response);
                    }
                    break;
                    
                default:
                    response.sendRedirect(request.getContextPath() + "/admin/feedbacks");
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
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
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Feedback detail and status change servlet";
    }
}

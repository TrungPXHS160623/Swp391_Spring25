package controller;

import dao.FeedbackDAO;
import model.Feedback;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/feedback")
public class FeedbackServlet extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(FeedbackServlet.class.getName());
    private FeedbackDAO feedbackDAO;

    @Override
    public void init() throws ServletException {
        feedbackDAO = new FeedbackDAO();
    }

     @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    String action = request.getParameter("action");
    if (action == null) {
        action = "list";
    }

    try {
        if ("view".equals(action)) {
            int id = Integer.parseInt(request.getParameter("id"));
            Feedback feedback = feedbackDAO.getFeedbackById(id);

            if (feedback == null) {
                request.setAttribute("error", "Không tìm thấy phản hồi!");
                request.getRequestDispatcher("/view/FeedbackList.jsp").forward(request, response);
                return;
            }

            request.setAttribute("feedback", feedback);
            request.getRequestDispatcher("/view/FeedbackDetail.jsp").forward(request, response);
        } else {
            List<Feedback> feedbacks = feedbackDAO.getAllFeedbacks("", "", "feedback_id", 1, 5);
            request.setAttribute("feedbacks", feedbacks);
            request.getRequestDispatcher("/view/FeedbackList.jsp").forward(request, response);
        }
    } catch (NumberFormatException e) {
        request.setAttribute("error", "ID không hợp lệ!");
        request.getRequestDispatcher("/view/FeedbackList.jsp").forward(request, response);
    } catch (Exception e) {
        LOGGER.log(Level.SEVERE, "Lỗi xử lý GET", e);
        throw new ServletException(e);
    }
}


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int feedbackId = Integer.parseInt(request.getParameter("feedbackId"));
            String newStatus = request.getParameter("status");

            if (feedbackDAO.updateFeedbackStatus(feedbackId, newStatus)) {
                request.setAttribute("success", "Cập nhật trạng thái thành công!");
            } else {
                request.setAttribute("error", "Không thể cập nhật trạng thái!");
            }
            response.sendRedirect("feedback?action=view&id=" + feedbackId);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Lỗi khi cập nhật feedback", e);
            throw new ServletException(e);
        }
    }
}

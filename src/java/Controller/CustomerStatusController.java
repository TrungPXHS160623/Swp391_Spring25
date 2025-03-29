package Controller;

import Dao.CustomerDao;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Controller for changing customer status (activate/deactivate)
 * @author Admin
 */
@WebServlet(name = "CustomerStatusController", urlPatterns = {"/admin/customers/status"})
public class CustomerStatusController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        // Check if user is logged in and has admin role
        HttpSession session = request.getSession();
        Object userObj = session.getAttribute("user");
        if (userObj == null) {
            response.sendRedirect(request.getContextPath() + "/logincontroller");
            return;
        }
        
        // Get parameters
        String idParam = request.getParameter("id");
        String action = request.getParameter("action"); // "activate" or "deactivate"
        
        if (idParam == null || action == null || (!action.equals("activate") && !action.equals("deactivate"))) {
            session.setAttribute("errorMessage", "Invalid parameters");
            response.sendRedirect(request.getContextPath() + "/admin/customers");
            return;
        }
        
        try {
            int customerId = Integer.parseInt(idParam);
            CustomerDao customerDao = new CustomerDao();
            
            boolean success;
            if (action.equals("activate")) {
                success = customerDao.updateCustomerStatus(customerId, true);
            } else {
                success = customerDao.updateCustomerStatus(customerId, false);
            }
            
            if (success) {
                session.setAttribute("successMessage", 
                    action.equals("activate") ? "Customer activated successfully" : "Customer deactivated successfully");
            } else {
                session.setAttribute("errorMessage", "Failed to update customer status");
            }
            
            // Redirect back to the customer view page
            response.sendRedirect(request.getContextPath() + "/admin/customers/view?id=" + customerId);
            
        } catch (NumberFormatException e) {
            session.setAttribute("errorMessage", "Invalid customer ID");
            response.sendRedirect(request.getContextPath() + "/admin/customers");
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
        return "Customer Status Controller";
    }
}

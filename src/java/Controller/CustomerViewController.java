package Controller;

import Dao.CustomerDao;
import Dto.CustomerDto;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Controller for viewing customer details
 * @author Admin
 */
@WebServlet(name = "CustomerViewController", urlPatterns = {"/admin/customers/view"})
public class CustomerViewController extends HttpServlet {

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
        
        // Get customer ID from request parameter
        String idParam = request.getParameter("id");
        if (idParam == null || idParam.trim().isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/admin/customers");
            return;
        }
        
        try {
            int customerId = Integer.parseInt(idParam);
            CustomerDao customerDao = new CustomerDao();
            CustomerDto customer = customerDao.getCustomerById(customerId);
            
            if (customer == null) {
                response.sendRedirect(request.getContextPath() + "/admin/customers");
                return;
            }
            
            // Set customer object as request attribute and forward to view page
            request.setAttribute("customer", customer);
            request.getRequestDispatcher("/AdminPage/CustomerView.jsp").forward(request, response);
            
        } catch (NumberFormatException e) {
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
        return "Customer View Controller";
    }
}

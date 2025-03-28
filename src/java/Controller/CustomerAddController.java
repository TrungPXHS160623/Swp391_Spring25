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
 * Controller for adding a new customer
 * @author Admin
 */
@WebServlet(name = "CustomerAddController", urlPatterns = {"/admin/customers/add"})
public class CustomerAddController extends HttpServlet {

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
        
        // For GET request - show add form
        if (request.getMethod().equalsIgnoreCase("GET")) {
            request.getRequestDispatcher("/AdminPage/CustomerAdd.jsp").forward(request, response);
            return;
        }
        
        // For POST request - process form submission
        if (request.getMethod().equalsIgnoreCase("POST")) {
            // Get form parameters
            String fullName = request.getParameter("fullName");
            String gender = request.getParameter("gender");
            String email = request.getParameter("email");
            String password = request.getParameter("password"); // You would typically hash this
            String phoneNumber = request.getParameter("phoneNumber");
            String address = request.getParameter("address");
            boolean isActive = "on".equals(request.getParameter("isActive")) || "true".equals(request.getParameter("isActive"));
            boolean isVerified = "on".equals(request.getParameter("isVerified")) || "true".equals(request.getParameter("isVerified"));

            // Validate inputs
            if(fullName == null || fullName.trim().isEmpty() || 
               email == null || email.trim().isEmpty() ||
               password == null || password.trim().isEmpty()) {
                
                session.setAttribute("errorMessage", "Required fields cannot be empty");
                response.sendRedirect(request.getContextPath() + "/admin/customers/add");
                return;
            }

            // Create customer in database
            try {
                CustomerDao customerDao = new CustomerDao();
                boolean created = customerDao.addCustomer(fullName, gender, email, password, phoneNumber, address, isActive, isVerified);
                
                if (created) {
                    session.setAttribute("successMessage", "Customer created successfully");
                    response.sendRedirect(request.getContextPath() + "/admin/customers");
                } else {
                    session.setAttribute("errorMessage", "Failed to create customer. Email might already be in use.");
                    response.sendRedirect(request.getContextPath() + "/admin/customers/add");
                }
            } catch (Exception e) {
                session.setAttribute("errorMessage", "Error creating customer: " + e.getMessage());
                response.sendRedirect(request.getContextPath() + "/admin/customers/add");
            }
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
        return "Customer Add Controller";
    }
}

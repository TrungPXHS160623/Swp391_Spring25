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
 * Controller for editing customer information
 * @author Admin
 */
@WebServlet(name = "CustomerEditController", urlPatterns = {"/admin/customers/edit"})
public class CustomerEditController extends HttpServlet {

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
        
        CustomerDao customerDao = new CustomerDao();
        
        // For GET request - show edit form
        if (request.getMethod().equalsIgnoreCase("GET")) {
            String idParam = request.getParameter("id");
            if (idParam == null || idParam.trim().isEmpty()) {
                response.sendRedirect(request.getContextPath() + "/admin/customers");
                return;
            }
            
            try {
                int customerId = Integer.parseInt(idParam);
                CustomerDto customer = customerDao.getCustomerById(customerId);
                
                if (customer == null) {
                    response.sendRedirect(request.getContextPath() + "/admin/customers");
                    return;
                }
                
                // Set customer object as request attribute and forward to edit page
                request.setAttribute("customer", customer);
                request.getRequestDispatcher("/AdminPage/CustomerEdit.jsp").forward(request, response);
                
            } catch (NumberFormatException e) {
                response.sendRedirect(request.getContextPath() + "/admin/customers");
            }
            return;
        }
        
        // For POST request - process form submission
        if (request.getMethod().equalsIgnoreCase("POST")) {
            // Get form parameters
            String userIdStr = request.getParameter("userId");
            String fullName = request.getParameter("fullName");
            String gender = request.getParameter("gender");
            String email = request.getParameter("email");
            String phoneNumber = request.getParameter("phoneNumber");
            String address = request.getParameter("address");
            String isActiveStr = request.getParameter("isActive");
            String isVerifiedStr = request.getParameter("isVerified");
            
            try {
                int userId = Integer.parseInt(userIdStr);
                boolean isActive = "on".equals(isActiveStr) || "true".equals(isActiveStr);
                boolean isVerified = "on".equals(isVerifiedStr) || "true".equals(isVerifiedStr);
                
                // Create CustomerDto object
                CustomerDto customer = new CustomerDto();
                customer.setUserId(userId);
                customer.setFullName(fullName);
                customer.setGender(gender);
                customer.setEmail(email);
                customer.setPhoneNumber(phoneNumber);
                customer.setAddress(address);
                customer.setIsActive(isActive);
                customer.setIsVerified(isVerified);
                
                // Update customer in database
                boolean updated = customerDao.updateCustomer(customer);
                
                if (updated) {
                    session.setAttribute("successMessage", "Customer updated successfully");
                } else {
                    session.setAttribute("errorMessage", "Failed to update customer");
                }
                
                response.sendRedirect(request.getContextPath() + "/admin/customers/view?id=" + userId);
                
            } catch (NumberFormatException e) {
                session.setAttribute("errorMessage", "Invalid customer ID");
                response.sendRedirect(request.getContextPath() + "/admin/customers");
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
        return "Customer Edit Controller";
    }
}

package Controller;

import Dao.CustomerDao;
import Dto.CustomerDto;
import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet(name = "CustomerListController", urlPatterns = {"/admin/customers"})
public class CustomerListController extends HttpServlet {

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
        
        // Get parameters for filtering, sorting and pagination
        String searchText = request.getParameter("search");
        String status = request.getParameter("status");
        String sortBy = request.getParameter("sortBy");
        String sortOrder = request.getParameter("sortOrder");
        
        int pageNumber = 1;
        int pageSize = 10;
        
        try {
            if (request.getParameter("page") != null) {
                pageNumber = Integer.parseInt(request.getParameter("page"));
                if (pageNumber < 1) pageNumber = 1;
            }
            
            if (request.getParameter("pageSize") != null) {
                pageSize = Integer.parseInt(request.getParameter("pageSize"));
                if (pageSize < 1) pageSize = 10;
            }
        } catch (NumberFormatException e) {
            pageNumber = 1;
            pageSize = 10;
        }
        
        CustomerDao customerDao = new CustomerDao();
        
        // Get total number of customers for pagination
        int totalCustomers = customerDao.getTotalCustomers(searchText, status);
        int totalPages = (int) Math.ceil((double) totalCustomers / pageSize);
        
        if (pageNumber > totalPages && totalPages > 0) {
            pageNumber = totalPages;
        }
        
        // Get customers for current page
        List<CustomerDto> customers = customerDao.getCustomers(pageNumber, pageSize, searchText, status, sortBy, sortOrder);
        
        // Set request attributes
        request.setAttribute("customers", customers);
        request.setAttribute("currentPage", pageNumber);
        request.setAttribute("pageSize", pageSize);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("totalCustomers", totalCustomers);
        request.setAttribute("search", searchText);
        request.setAttribute("status", status);
        request.setAttribute("sortBy", sortBy);
        request.setAttribute("sortOrder", sortOrder);
        
        // Forward to the JSP view
        request.getRequestDispatcher("/AdminPage/CustomerList.jsp").forward(request, response);
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
        return "Customer List Controller";
    }
}

package controller;

import dao.CustomerDAO;
import model.Customer;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/customers")
public class CustomerController extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        CustomerDAO customerDAO = new CustomerDAO();

        // Lấy các tham số từ request (filtter, search, sort)
        String status = request.getParameter("status");
        String search = request.getParameter("search");
        String sort = request.getParameter("sort");

        // Xử lý phân trang
        int page = 1;
        int recordsPerPage = 10; // Số lượng khách hàng trên mỗi trang
        if (request.getParameter("page") != null)
            page = Integer.parseInt(request.getParameter("page"));

        List<Customer> customers = customerDAO.getAllCustomers(status, search, sort, page, recordsPerPage);

        // Lấy tổng số khách hàng để tính số trang
        int noOfRecords = customerDAO.getCustomersCount(status, search);
        int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);

        // Gửi dữ liệu tới JSP
        request.setAttribute("customers", customers);
        request.setAttribute("noOfPages", noOfPages);
        request.setAttribute("currentPage", page);
        request.getRequestDispatcher("views/customers.jsp").forward(request, response);
    }
}

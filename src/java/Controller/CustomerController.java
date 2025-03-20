//package controller;
//
//import dao.CustomerDAO;
//import model.Customer;
//import model.CustomerHistory;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.annotation.WebServlet;
//import jakarta.servlet.http.HttpServlet;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.sql.Connection;
//import java.util.List;
//
//@WebServlet("/customers")
//public class CustomerController extends HttpServlet {
//    private CustomerDAO customerDAO;
//
//    @Override
//    public void init() {
//        Connection conn = (Connection) getServletContext().getAttribute("DBConnection");
//        //customerDAO = new CustomerDAO(conn);
//    }
//
//    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        try {
//            String status = request.getParameter("status");
//            String search = request.getParameter("search");
//            String sort = request.getParameter("sort");
//            int page = Integer.parseInt(request.getParameter("page"));
//            
//            //List<Customer> customers = customerDAO.getAllCustomers(status, search, sort, page, 10);
//            //request.setAttribute("customers", customers);
//            request.getRequestDispatcher("/views/customer-list.jsp").forward(request, response);
//        } catch (Exception e) {
//            throw new ServletException(e);
//        }
//    }
//}

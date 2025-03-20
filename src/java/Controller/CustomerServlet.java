//// CustomerServlet.java (Controller Layer)
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
//@WebServlet("/customer")
//public class CustomerServlet extends HttpServlet {
//    private CustomerDAO customerDAO;
//
//    @Override
//    public void init() {
//        Connection conn = (Connection) getServletContext().getAttribute("DBConnection");
//        //customerDAO = new CustomerDAO(conn);
//    }
//
////    @Override
////    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
////        String action = request.getParameter("action");
////        if (action == null) action = "list";
////        
////        try {
////            switch (action) {
////                case "view":
////                    viewCustomer(request, response);
////                    break;
////                case "list":
////                default:
////                    listCustomers(request, response);
////                    break;
////            }
////        } catch (Exception e) {
////            throw new ServletException(e);
////        }
////    }
//
//    private void listCustomers(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        try {
//            String status = request.getParameter("status") != null ? request.getParameter("status") : "1";
//            String search = request.getParameter("search") != null ? request.getParameter("search") : "";
//            String sort = request.getParameter("sort") != null ? request.getParameter("sort") : "full_name";
//            int page = request.getParameter("page") != null ? Integer.parseInt(request.getParameter("page")) : 1;
//            
//            //List<Customer> customers = customerDAO.getAllCustomers(status, search, sort, page, 10);
//            //request.setAttribute("customers", customers);
//            request.getRequestDispatcher("/views/customer-list.jsp").forward(request, response);
//        } catch (Exception e) {
//            throw new ServletException(e);
//        }
//    }
//
////    private void viewCustomer(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
////        try {
////            int customerId = Integer.parseInt(request.getParameter("id"));
////            //Customer customer = customerDAO.getCustomerById(customerId);
////            List<CustomerHistory> history = customerDAO.getCustomerHistory(customerId);
////            
////            if (customer == null) {
////                request.setAttribute("error", "Không tìm thấy khách hàng");
////                request.getRequestDispatcher("/views/error.jsp").forward(request, response);
////                return;
////            }
////            
////            request.setAttribute("customer", customer);
////            request.setAttribute("history", history);
////            request.getRequestDispatcher("/views/customer-details.jsp").forward(request, response);
////        } catch (NumberFormatException e) {
////            request.setAttribute("error", "ID khách hàng không hợp lệ");
////            request.getRequestDispatcher("/views/error.jsp").forward(request, response);
////        } catch (Exception e) {
////            throw new ServletException(e);
////        }
////    }
//}

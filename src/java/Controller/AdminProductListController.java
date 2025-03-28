/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package Controller;

import Dao.AdminProductDao;
import Dto.AdminProductDto;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;

/**
 *
 * @author LENOVO
 */
public class AdminProductListController extends HttpServlet {
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet AdminProductListController</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet AdminProductListController at " + request.getContextPath () + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    } 

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    AdminProductDao productDao = new AdminProductDao();
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        String keyword = request.getParameter("keyword") != null ? request.getParameter("keyword").trim() : "";
        String category = request.getParameter("category") != null ? request.getParameter("category").trim() : "";
        String featured = request.getParameter("featured") != null ? request.getParameter("featured").trim() : "";
        String status = request.getParameter("status") != null ? request.getParameter("status").trim() : "";
        String sortField = request.getParameter("sortField") != null ? request.getParameter("sortField").trim() : "";
        String sortDirection = request.getParameter("sortDirection") != null ? request.getParameter("sortDirection").trim() : "";

        int page = 1;
        int pageSize = 7;
        String pageParam = request.getParameter("page") != null ? request.getParameter("page").trim() : "";
        String pageSizeParam = request.getParameter("pageSize") != null ? request.getParameter("pageSize").trim() : "";
        if (!pageParam.isEmpty()) {
            try {
                page = Integer.parseInt(pageParam);
            } catch (NumberFormatException e) { }
        }
        if (!pageSizeParam.isEmpty()) {
            try {
                pageSize = Integer.parseInt(pageSizeParam);
            } catch (NumberFormatException e) { }
        }

        List<AdminProductDto> productList;
        if (!keyword.isEmpty()) {
            productList = productDao.searchProductList(keyword, page, pageSize);
        } else if (!category.isEmpty() || !featured.isEmpty() || !status.isEmpty()) {
            productList = productDao.filterProductList(category, featured, status, page, pageSize);
        } else if (!sortField.isEmpty()) {
            productList = productDao.sortProductList(sortField, sortDirection, page, pageSize);
        } else {
            productList = productDao.getAllProductList(page, pageSize);
        }

        int totalCount = 100; // Thay thế bằng lời gọi DAO nếu có
        int totalPages = (int) Math.ceil((double) totalCount / pageSize);

        request.setAttribute("productList", productList);
        request.setAttribute("currentPage", page);
        request.setAttribute("pageSize", pageSize);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("keyword", keyword);
        request.setAttribute("category", category);
        request.setAttribute("featured", featured);
        request.setAttribute("status", status);
        request.setAttribute("sortField", sortField);
        request.setAttribute("sortDirection", sortDirection);

        RequestDispatcher dispatcher = request.getRequestDispatcher("AdminPage/ProductList.jsp");
        dispatcher.forward(request, response);
    } 

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        doGet(request, response);
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}

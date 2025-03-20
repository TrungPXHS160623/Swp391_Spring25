/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import Dao.ProductDAO;
import Entity.Product;
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
public class ProductController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
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
            out.println("<title>Servlet ProductController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ProductController at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    ProductDAO pDao = new ProductDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String searchKeyword = request.getParameter("search");
        String name_sort = request.getParameter("name_sort");
        String category_sort = request.getParameter("caterogy_sort");
        String listprice_sort = request.getParameter("listprice_sort");
        String saleprice_sort = request.getParameter("saleprice_sort");
        String featured_sort = request.getParameter("featured_sort");
        String status_sort = request.getParameter("status_sort");
        String categoryName = request.getParameter("category");
        String featured = request.getParameter("featured");
        String status = request.getParameter("status");
        List<Product> products;

        List<String> categories = pDao.getAllCategories(); // Gọi DAO để lấy danh sách category
        List<String> featured2 = pDao.getAllFeatured(); // Gọi DAO để lấy danh sách featured
        List<String> status2 = pDao.getAllStatus(); // Gọi DAO để lấy danh sách status
        request.setAttribute("categories", categories);
        request.setAttribute("featured2", featured2);
        request.setAttribute("status2", status2);
        // Lọc sản phẩm theo category nếu có
        if (categoryName != null && !categoryName.isEmpty()) {
            // Lọc theo category và từ khóa tìm kiếm nếu có
            products = pDao.getFilteredProducts(categoryName);
        } else if (featured != null && !featured.isEmpty()) {
            // Lọc theo featured và từ khóa tìm kiếm nếu có
            products = pDao.getFilteredProductsbyFeatured(featured);
        } else if (status != null && !status.isEmpty()) {
            // Lọc theo status và từ khóa tìm kiếm nếu có
            products = pDao.getFilteredProductsbyStatus(status);
        } else if (name_sort != null) {
            // Kiểm tra sắp xếp theo tên sản phẩm
            boolean name_ascending = "asc".equals(name_sort);
            products = pDao.getAllProductSortedByName(name_ascending);
        } // Xử lý sắp xếp theo category
        else if (category_sort != null) {
            boolean category_ascending = "asc".equals(category_sort);
            products = pDao.getAllProductSortedByCategory(category_ascending);
        } // Xử lý sắp xếp theo giá list
        else if (listprice_sort != null) {
            boolean listprice_ascending = "asc".equals(listprice_sort);
            products = pDao.getAllProductSortedByListPrice(listprice_ascending);
        } // Xử lý sắp xếp theo giá sale
        else if (saleprice_sort != null) {
            boolean saleprice_ascending = "asc".equals(saleprice_sort);
            products = pDao.getAllProductSortedBySalePrice(saleprice_ascending);
        } // Xử lý sắp xếp theo featured
        else if (featured_sort != null) {
            boolean featured_ascending = "asc".equals(featured_sort);
            products = pDao.getAllProductSortedByFeatured(featured_ascending);
        } // Xử lý sắp xếp theo status
        else if (status_sort != null) {
            boolean status_ascending = "asc".equals(status_sort);
            products = pDao.getAllProductSortedByStatus(status_ascending);
        } else if (searchKeyword != null && !searchKeyword.isEmpty()) {
            // Tìm kiếm sản phẩm theo từ khóa
            products = pDao.searchProduct(searchKeyword);
        } else {
            // Lấy tất cả sản phẩm nếu không có từ khóa tìm kiếm
            products = pDao.getAllProduct();
        }

        request.setAttribute("ProductController", products);
        request.getRequestDispatcher("AdminPage/ProductList.jsp").forward(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}

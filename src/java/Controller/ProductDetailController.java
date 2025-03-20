/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import Dao.ProductDAO;
import Entity.Product;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.io.File;
import java.nio.file.Paths;

/**
 *
 * @author LENOVO
 */
public class ProductDetailController extends HttpServlet {

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
            out.println("<title>Servlet ProductDetailController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ProductDetailController at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    private ProductDAO productDAO = new ProductDAO();

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        String idParam = request.getParameter("id");
        int productId = (idParam != null && !idParam.isEmpty()) ? Integer.parseInt(idParam) : -1;

        if ("edit".equals(action) || "view".equals(action)) {
            Product product = productDAO.getProductById(productId);
            request.setAttribute("product", product);
        }

        request.setAttribute("action", action);
        request.getRequestDispatcher("AdminPage/ProductDetail.jsp").forward(request, response);
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
        String action = request.getParameter("action");

        String imageUrl = request.getParameter("image_url");
        String productName = request.getParameter("product_name");
        String subcategoryName = request.getParameter("subcategory_name");
        String description = request.getParameter("description");
        float listPrice = Float.parseFloat(request.getParameter("list_price"));
        float salePrice = Float.parseFloat(request.getParameter("sale_price"));
        int stock = Integer.parseInt(request.getParameter("stock"));
        int status = Integer.parseInt(request.getParameter("status"));
        int featured = Integer.parseInt(request.getParameter("featured"));

        Product product = new Product();
//        product.setImage_url(imageUrl);
        product.setProduct_name(productName);
        product.setSubcategory_name(subcategoryName);
        product.setList_price(listPrice);
        product.setSale_price(salePrice);
        product.setStock(stock);
        product.setStatus(status);
        product.setFeatured(featured);

        if ("add".equals(action)) {
            productDAO.addProduct(product);
        }

        response.sendRedirect("AdminPage/ProductDetail.jsp");
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

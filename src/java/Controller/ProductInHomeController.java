/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package Controller;

import Dao.ProductDao;
import Dto.ProductDto;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Acer
 */
@WebServlet(name="ProductInHomeController", urlPatterns={"/productinhomecontroller"})
public class ProductInHomeController extends HttpServlet {
   
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
            out.println("<title>Servlet ProductInHomeController</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ProductInHomeController at " + request.getContextPath () + "</h1>");
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
    
    private static final long serialVersionUID = 1L;
    private ProductDao productDao;

    @Override
    public void init() throws ServletException {
        productDao = new ProductDao();
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
//        int productsPerPage = 6; // Số sản phẩm mỗi trang
//        int currentPage = 1;
//
//        // Lấy trang hiện tại từ request (mặc định là 1)
//        String pageParam = request.getParameter("page");
//        if (pageParam != null) {
//            try {
//                currentPage = Integer.parseInt(pageParam);
//            } catch (NumberFormatException e) {
//                currentPage = 1;
//            }
//        }
//
//        // Lấy danh sách sản phẩm từ ProductDao
//        List<ProductDto> allProducts = productDao.getAllProducts();
//        int totalProducts = allProducts.size();
//        int totalPages = (int) Math.ceil((double) totalProducts / productsPerPage);
//
//        // Xác định phạm vi sản phẩm cần hiển thị trên trang hiện tại
//        int startIndex = (currentPage - 1) * productsPerPage;
//        int endIndex = Math.min(startIndex + productsPerPage, totalProducts);
//        List<ProductDto> productsOnPage = allProducts.subList(startIndex, endIndex);
//
//        // Đẩy dữ liệu lên request để truyền vào JSP
//        request.setAttribute("productList", productsOnPage);
//        request.setAttribute("currentPage", currentPage);
//        request.setAttribute("totalPages", totalPages);
//
//        
//        //Thay forward() bằng include(), vì include() chỉ chèn nội dung mà không ghi đè trang chính.
//        RequestDispatcher rd = request.getRequestDispatcher("/CommonPage/ProductInHome.jsp");
//        rd.include(request, response);
        // Lấy tham số từ request (nếu có)
        String keyword = request.getParameter("keyword");
        String[] categoryIdsArr = request.getParameterValues("categoryIds");
        Double minPrice = parseDouble(request.getParameter("minPrice"));
        Double maxPrice = parseDouble(request.getParameter("maxPrice"));
        Integer rating = parseInt(request.getParameter("rating"));
        boolean inStock = "true".equals(request.getParameter("inStock"));
        boolean outOfStock = "true".equals(request.getParameter("outOfStock"));
        boolean isDiscounted = "true".equals(request.getParameter("isDiscounted"));
        boolean isBestseller = "true".equals(request.getParameter("isBestseller"));

        // Chuyển categoryIds từ String[] thành List<Integer>
        List<Integer> categoryIds = new ArrayList<>();
        if (categoryIdsArr != null) {
            for (String id : categoryIdsArr) {
                categoryIds.add(Integer.parseInt(id));
            }
        }

        List<ProductDto> products;
        if (keyword != null || categoryIds.size() > 0 || minPrice != null || maxPrice != null || rating != null ||
                inStock || outOfStock || isDiscounted || isBestseller) {
            // Nếu có bộ lọc, gọi phương thức filterProducts()
            products = productDao.filterProducts(keyword, categoryIds, minPrice, maxPrice, rating, inStock, outOfStock, isDiscounted, isBestseller);
        } else {
            // Nếu không có bộ lọc, lấy tất cả sản phẩm
            products = productDao.getAllProducts();
        }

        request.setAttribute("productList", products);
        RequestDispatcher rd = request.getRequestDispatcher("/CommonPage/ProductInHome.jsp");
        rd.include(request, response);
    }

    private Double parseDouble(String value) {
        try {
            return (value != null && !value.isEmpty()) ? Double.parseDouble(value) : null;
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private Integer parseInt(String value) {
        try {
            return (value != null && !value.isEmpty()) ? Integer.parseInt(value) : null;
        } catch (NumberFormatException e) {
            return null;
        }
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
        processRequest(request, response);
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

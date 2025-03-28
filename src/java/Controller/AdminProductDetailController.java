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
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@MultipartConfig
/**
 *
 * @author LENOVO
 */
public class AdminProductDetailController extends HttpServlet {

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
            out.println("<title>Servlet AdminProductDetailController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet AdminProductDetailController at " + request.getContextPath() + "</h1>");
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
    AdminProductDao productDao = new AdminProductDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Lấy tham số action và productId từ request
        String action = request.getParameter("action") != null ? request.getParameter("action").trim() : "view";
        String productIdParam = request.getParameter("productId") != null ? request.getParameter("productId").trim() : "";
        AdminProductDto productDto = null;

        if (!productIdParam.isEmpty() && (action.equalsIgnoreCase("view") || action.equalsIgnoreCase("edit"))) {
            try {
                int productId = Integer.parseInt(productIdParam);
                // Lấy chi tiết sản phẩm bao gồm danh sách media
                productDto = productDao.getProductDetail(productId);
            } catch (NumberFormatException e) {
                // nếu không hợp lệ, productDto vẫn null
            }
        }
        if (productDto == null) {
            // Nếu chế độ add hoặc không có product, tạo đối tượng mới (rỗng)
            productDto = new AdminProductDto();
        }
        request.setAttribute("product", productDto);
        request.setAttribute("action", action);
        RequestDispatcher dispatcher = request.getRequestDispatcher("AdminPage/ProductDetail.jsp");
        dispatcher.forward(request, response);
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
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action") != null ? request.getParameter("action").trim() : "add";

        if ("view".equalsIgnoreCase(action)) {
            doGet(request, response);
            return;
        }

        AdminProductDto product = new AdminProductDto();
        product.setProductName(request.getParameter("productName"));
        product.setDescription(request.getParameter("description"));
        try {
            product.setPrice(Double.parseDouble(request.getParameter("price")));
        } catch (NumberFormatException e) {
            product.setPrice(0);
        }
        try {
            product.setStockQuantity(Integer.parseInt(request.getParameter("stockQuantity")));
        } catch (NumberFormatException e) {
            product.setStockQuantity(0);
        }
        try {
            product.setSubcategoryId(Integer.parseInt(request.getParameter("subcategoryId")));
        } catch (NumberFormatException e) {
            product.setSubcategoryId(0);
        }
        try {
            product.setDiscountPrice(Double.parseDouble(request.getParameter("discountPrice")));
        } catch (NumberFormatException e) {
            product.setDiscountPrice(0);
        }
        try {
            product.setDiscountPercentage(Double.parseDouble(request.getParameter("discountPercentage")));
        } catch (NumberFormatException e) {
            product.setDiscountPercentage(0);
        }
        try {
            product.setSoldQuantity(Integer.parseInt(request.getParameter("soldQuantity")));
        } catch (NumberFormatException e) {
            product.setSoldQuantity(0);
        }
        try {
            product.setAverageRating(Double.parseDouble(request.getParameter("averageRating")));
        } catch (NumberFormatException e) {
            product.setAverageRating(0);
        }
        // Xử lý primary image từ form: ưu tiên file upload, nếu không có thì lấy URL
        String primaryImageUrl = request.getParameter("primaryImageUrl");
        product.setPrimaryImageUrl(primaryImageUrl);
        product.setCategory(request.getParameter("category")); // subcategory name

        // Xử lý media upload: Nếu có file upload hoặc URL media, bạn cần xử lý lưu file và/hoặc lấy URL, sau đó cập nhật vào mediaList của product.
        // Ở đây ví dụ đơn giản không xử lý nhiều file, bạn có thể mở rộng sau.
        if ("edit".equalsIgnoreCase(action)) {
            String productIdParam = request.getParameter("productId");
            if (productIdParam != null && !productIdParam.trim().isEmpty()) {
                try {
                    int productId = Integer.parseInt(productIdParam.trim());
                    product.setProductId(productId);
                    boolean result = productDao.updateProduct(product);
                    request.setAttribute("message", result ? "Cập nhật sản phẩm thành công" : "Cập nhật sản phẩm thất bại");
                } catch (NumberFormatException e) {
                    request.setAttribute("message", "Product ID không hợp lệ");
                }
            }
        } else { // add
            boolean result = productDao.addProduct(product);
            request.setAttribute("message", result ? "Thêm sản phẩm thành công" : "Thêm sản phẩm thất bại");
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("AdminProductDetailController?action=view&productId=" + product.getProductId());
        dispatcher.forward(request, response);
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

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import Dao.CartDetailDao;
import Dto.CartDetailDto;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Acer
 */
@WebServlet(name = "CartDetailController", urlPatterns = {"/cartdetailcontroller"})
public class CartDetailController extends HttpServlet {

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
            out.println("<title>Servlet CartDetailController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet CartDetailController at " + request.getContextPath() + "</h1>");
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
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("userId");

        if (userId == null) {
            response.sendRedirect("/UserPage/Login.jsp"); // Nếu chưa đăng nhập, chuyển hướng đến trang login
            return;
        }

        // Lấy danh sách giỏ hàng từ CartDetailDao
        CartDetailDao cartDetailDao = new CartDetailDao();
        List<CartDetailDto> cartDetails = cartDetailDao.getCartDetails(userId);

        // Gửi dữ liệu đến JSP
        request.setAttribute("cartDetails", cartDetails);
        request.getRequestDispatcher("/CustomerPage/CartDetail.jsp").forward(request, response);
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
        CartDetailDao dao = new CartDetailDao();
        HttpSession session = request.getSession();

        if ("updateSelection".equals(action)) {
            // Lấy id giỏ hàng của người dùng
            int cartId = Integer.parseInt(request.getParameter("cartId"));
            // Lấy danh sách id sản phẩm được chọn checkbox
            // có thể có nhiều sản phẩm được chọn
            String[] selectedProductIds = request.getParameterValues("selectedProducts");
            double beforePrice = 0.0;
            List<Integer> selectedProductsList = new ArrayList<>();
            //nếu phát hiện mảng có data
            if (selectedProductIds != null) {
                for (String productIdStr : selectedProductIds) {
                    int productId = Integer.parseInt(productIdStr);
                    double productTotal = dao.getProductTotalPrice(productId, cartId);
                    beforePrice += productTotal;
                    selectedProductsList.add(productId); // Chuyển đổi sang List<Integer>
                }
            }
            session.setAttribute("selectedProducts", selectedProductsList); // Lưu vào session dưới dạng List<Integer>
            session.setAttribute("beforePrice", beforePrice);

            // Xác định mức giảm giá dựa trên tổng tiền
            double discountRate = 0.0;
            if (beforePrice > 100_000_000) {
                discountRate = 0.10; // 10%
            } else if (beforePrice > 50_000_000) {
                discountRate = 0.07; // 7%
            } else if (beforePrice > 10_000_000) {
                discountRate = 0.05; // 5%
            }

            // Tính toán số tiền giảm giá và giá cuối cùng
            double discountAmount = beforePrice * discountRate;
            double finalTotalPrice = beforePrice - discountAmount;

            double remainingForNextDiscount = 0.0;

            if (beforePrice < 10_000_000) {
                remainingForNextDiscount = 10_000_000 - beforePrice;
            } else if (beforePrice < 50_000_000) {
                remainingForNextDiscount = 50_000_000 - beforePrice;
            } else if (beforePrice < 100_000_000) {
                remainingForNextDiscount = 100_000_000 - beforePrice;
            } else {
                remainingForNextDiscount = 0; // Đã đạt mức cao nhất
            }

            // Lưu vào session
            session.setAttribute("remainingForNextDiscount", remainingForNextDiscount);
            session.setAttribute("discountRate", discountRate * 100); // Lưu % giảm giá
            session.setAttribute("discountAmount", discountAmount);
            session.setAttribute("finalPrice", finalTotalPrice);
            response.sendRedirect(request.getContextPath() + "/cartdetailcontroller");
            return;
        }

        int productId = Integer.parseInt(request.getParameter("productId"));
        int cartId = Integer.parseInt(request.getParameter("cartId"));

        // Lấy số lượng hiện tại từ request thay vì từ database
        int quantity = Integer.parseInt(request.getParameter("quantity"));

        if ("increase".equals(action)) {
            quantity++; // Tăng số lượng
        } else if ("decrease".equals(action) && quantity > 1) {
            quantity--; // Giảm số lượng, nhưng không được nhỏ hơn 1
        } else if ("remove".equals(action)) {
            int cartItemId = Integer.parseInt(request.getParameter("cartItemId"));

            boolean success = dao.removeFromCart(cartItemId);
            if (success) {
                request.getSession().setAttribute("message", "Sản phẩm đã được xóa khỏi giỏ hàng!");
            } else {
                request.getSession().setAttribute("message", "Xóa sản phẩm thất bại!");
            }
        }

        // Cập nhật số lượng mới vào database
        dao.updateQuantity(cartId, productId, quantity);

        // 🔹 Cập nhật lại cartCount sau khi thay đổi số lượng
        int userId = (int) request.getSession().getAttribute("userId");
        int cartCount = dao.getCartItemCount(userId);
        request.getSession().setAttribute("cartCount", cartCount);

        // Quay lại trang giỏ hàng
        response.sendRedirect(request.getContextPath() + "/cartdetailcontroller");
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

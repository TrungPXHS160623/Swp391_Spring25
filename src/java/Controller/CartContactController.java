/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package Controller;

import Dao.CartContactDao;
import Dao.CartDetailDao;
import Dto.CartContactDto;
import Dto.CartDetailDto;
import Dto.CartDetailDto2;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Controller for handling the checkout contact information page
 */
@WebServlet(name="CartContactController", urlPatterns={"/cartcontactcontroller"})
public class CartContactController extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(CartContactController.class.getName());
   
    /**
     * Handles the HTTP GET method - displays checkout form with selected products
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("userId");
        
        if (userId == null) {
            LOGGER.info("User not logged in, redirecting to login");
            response.sendRedirect(request.getContextPath() + "/logincontroller");
            return;
        }
        
        // Get the list of selected product IDs from request parameters
        String[] selectedProductsArray = request.getParameterValues("selectedProducts");
        
        // Debug information
        LOGGER.info("CartContactController doGet called");
        LOGGER.info("Selected products from request: " + (selectedProductsArray == null ? "null" : selectedProductsArray.length + " items"));
        
        // Try to get selected products from session if not in request
        List<Integer> selectedProductIds = new ArrayList<>();
        
        if (selectedProductsArray != null && selectedProductsArray.length > 0) {
            // Use products from request parameters
            for (String productId : selectedProductsArray) {
                try {
                    selectedProductIds.add(Integer.parseInt(productId));
                } catch (NumberFormatException e) {
                    LOGGER.warning("Invalid product ID: " + productId);
                }
            }
            LOGGER.info("Using product IDs from request: " + selectedProductIds);
        } else {
            // If no products in request, try session
            Object sessionProducts = session.getAttribute("selectedProducts");
            if (sessionProducts instanceof List<?>) {
                try {
                    @SuppressWarnings("unchecked")
                    List<Integer> sessionProductIds = (List<Integer>) sessionProducts;
                    selectedProductIds.addAll(sessionProductIds);
                    LOGGER.info("Using product IDs from session: " + selectedProductIds);
                } catch (ClassCastException e) {
                    LOGGER.warning("Session product IDs not of expected type: " + e.getMessage());
                }
            }
        }
        
        if (selectedProductIds.isEmpty()) {
            // Still no products, redirect back to cart
            LOGGER.warning("No products selected, redirecting to cart");
            session.setAttribute("message", "Please select at least one product before checkout.");
            response.sendRedirect(request.getContextPath() + "/cartdetailcontroller");
            return;
        }
        
        // Lấy thông tin giá trị đơn hàng từ request hoặc session:
        Double beforePrice = 0.0;
        Double discountRate = 0.0;
        Double discountAmount = 0.0;
        Double finalPrice = 0.0;
        
        try {
            beforePrice = Double.parseDouble(request.getParameter("beforePrice"));
            discountRate = Double.parseDouble(request.getParameter("discountRate"));
            discountAmount = Double.parseDouble(request.getParameter("discountAmount"));
            finalPrice = Double.parseDouble(request.getParameter("finalPrice"));
        } catch (NumberFormatException | NullPointerException e) {
            LOGGER.warning("Error parsing price information: " + e.getMessage());
            // Try to get from session
            beforePrice = (Double) session.getAttribute("beforePrice");
            discountRate = (Double) session.getAttribute("discountRate");
            discountAmount = (Double) session.getAttribute("discountAmount");
            finalPrice = (Double) session.getAttribute("finalPrice");
        }
        
        // Tìm cartId của user hiện tại
        CartDetailDao cartDao = new CartDetailDao();
        List<CartDetailDto> userCart = cartDao.getCartDetails(userId);
        int cartId = userCart.isEmpty() ? 1 : userCart.get(0).getCartId();
        
        // Lấy chi tiết sản phẩm được chọn từ CartContactDao
        CartContactDao dao = new CartContactDao();
        List<CartDetailDto> cartDetails = dao.getSelectedProducts(selectedProductIds, cartId);
        
        // Forward tới JSP để hiển thị (form nhập thông tin giao hàng):
        request.setAttribute("cartDetails", cartDetails);
        request.setAttribute("beforePrice", beforePrice);
        request.setAttribute("discountRate", discountRate);
        request.setAttribute("discountAmount", discountAmount);
        request.setAttribute("finalPrice", finalPrice);
        request.setAttribute("selectedProducts", selectedProductIds);
        
        request.getRequestDispatcher("/CustomerPage/CartContact.jsp").forward(request, response);
    }

    /**
     * Handles the HTTP POST method - processes the order with customer information
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        //Xác thực người dùng đăng nhập:
        Integer userId = (Integer) session.getAttribute("userId");
        
        if (userId == null) {
            response.sendRedirect(request.getContextPath() + "/logincontroller");
            return;
        }
        
        try {
            // Get contact information from form
            String fullName = request.getParameter("fullName");
            String gender = request.getParameter("gender");
            String email = request.getParameter("email");
            String phone = request.getParameter("phone");
            String address = request.getParameter("address");
            String notes = request.getParameter("notes");
            
            // Debug form values
            LOGGER.info("Form values - fullName: " + fullName + ", gender: " + gender + 
                        ", email: " + email + ", phone: " + phone);
            
            // Basic form validation
            if (fullName == null || fullName.trim().isEmpty() || 
                gender == null || gender.trim().isEmpty() ||
                email == null || email.trim().isEmpty() ||
                phone == null || phone.trim().isEmpty() ||
                address == null || address.trim().isEmpty()) {
                
                request.setAttribute("error", "Please fill in all required fields.");
                doGet(request, response);
                return;
            }
            
            //Lấy danh sách sản phẩm được chọn từ request hoặc session
            String[] selectedProductsArray = request.getParameterValues("selectedProducts");
            if (selectedProductsArray == null || selectedProductsArray.length == 0) {
                request.setAttribute("error", "No products selected for checkout. Please select at least one product.");
                doGet(request, response);
                return;
            }
            
            // Handle price information - First try getting from session if form value fails
            double finalPrice;
            String finalPriceStr = request.getParameter("finalPrice");
            LOGGER.info("Final price from form: " + finalPriceStr);
            
            try {
                // Clean up price string by removing all non-numeric characters except decimal point
                if (finalPriceStr != null) {
                    finalPriceStr = finalPriceStr.replaceAll("[^0-9.]", "");
                }
                
                finalPrice = Double.parseDouble(finalPriceStr);
            } catch (NumberFormatException | NullPointerException e) {
                LOGGER.warning("Failed to parse price from form: " + e.getMessage());
                // Try to get from session as fallback
                Double sessionPrice = (Double) session.getAttribute("finalPrice");
                if (sessionPrice != null) {
                    finalPrice = sessionPrice;
                    LOGGER.info("Using price from session: " + finalPrice);
                } else {
                    // Last resort, calculate from cart
                    CartDetailDao cartDao = new CartDetailDao();
                    List<CartDetailDto> cartItems = cartDao.getCartDetails(userId);
                    
                    finalPrice = 0.0;
                    for (CartDetailDto item : cartItems) {
                        finalPrice += item.getTotalPrice();
                    }
                    LOGGER.info("Calculated price from cart: " + finalPrice);
                }
            }
            
            // Create DTO for contact information
            CartContactDto contactInfo = new CartContactDto(fullName, gender, email, phone, address, notes);
            
            // Create list of product IDs
            List<Integer> selectedProductIds = new ArrayList<>();
            for (String productId : selectedProductsArray) {
                selectedProductIds.add(Integer.parseInt(productId));
            }
            
            // Process order
            CartContactDao dao = new CartContactDao();
            int orderId = dao.createOrder(userId, selectedProductIds, finalPrice, contactInfo);
            
            if (orderId > 0) {
                // Order created successfully
                session.setAttribute("orderId", orderId);
                session.setAttribute("contactInfo", contactInfo);
                session.setAttribute("orderAmount", finalPrice);
                
                // Get order details for display on success page
                List<CartDetailDto2> orderDetails = dao.getOrderDetails(orderId);
                session.setAttribute("orderDetails", orderDetails);
                
                // Redirect to success page
                response.sendRedirect(request.getContextPath() + "/CustomerPage/CheckoutSuccessfully.jsp");
            } else {
                // Order creation failed
                request.setAttribute("error", "Failed to create order. Please try again.");
                
                // Restore form data and return to form
                request.setAttribute("contactInfo", contactInfo);
                doGet(request, response);
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error processing checkout: " + e.getMessage(), e);
            request.setAttribute("error", "An error occurred during checkout: " + e.getMessage());
            doGet(request, response);
        }
    }
}
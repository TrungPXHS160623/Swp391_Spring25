/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author LENOVO
 */
public class ProductHideController extends HttpServlet {

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
            out.println("<title>Servlet ProductHideController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ProductHideController at " + request.getContextPath() + "</h1>");
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
        // Lấy tham số action ("hide" hoặc "show") và productId từ request
        String action = request.getParameter("action");
        String productIdParam = request.getParameter("productId");
        int productId = 0;
        try {
            productId = Integer.parseInt(productIdParam);
        } catch (NumberFormatException e) {
            // Nếu không parse được, chuyển hướng về danh sách sản phẩm
            response.sendRedirect("AdminProductListController");
            return;
        }

        // Lấy session và danh sách các productId bị ẩn
        HttpSession session = request.getSession();
        List<Integer> hiddenProducts = (List<Integer>) session.getAttribute("hiddenProducts");
        if (hiddenProducts == null) {
            hiddenProducts = new ArrayList<>();
        }

        if ("hide".equalsIgnoreCase(action)) {
            // Nếu sản phẩm chưa bị ẩn, thêm vào danh sách
            if (!hiddenProducts.contains(productId)) {
                hiddenProducts.add(productId);
            }
        } else if ("show".equalsIgnoreCase(action)) {
            // Nếu sản phẩm đang ẩn, xóa khỏi danh sách
            hiddenProducts.remove(Integer.valueOf(productId));
        }

        // Lưu lại danh sách trong session
        session.setAttribute("hiddenProducts", hiddenProducts);

        // Chuyển hướng về AdminProductListController để cập nhật giao diện
        response.sendRedirect("AdminProductListController");
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

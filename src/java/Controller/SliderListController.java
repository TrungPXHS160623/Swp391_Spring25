/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import Dao.SliderDao;
import Entity.Slider;
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
@WebServlet(name = "SliderListController", urlPatterns = {"/sliderlistcontroller"})
public class SliderListController extends HttpServlet {

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
            out.println("<title>Servlet SliderListController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet SliderListController at " + request.getContextPath() + "</h1>");
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
        String searchTitle = request.getParameter("searchTitle");
        String searchLink = request.getParameter("searchLink");
        String sliderStatus = request.getParameter("sliderStatus");

        SliderDao sliderDao = new SliderDao();
        List<Slider> sliderList = sliderDao.getAllSliders(); // Lấy toàn bộ danh sách trước

        // Lọc theo title nếu có nhập
        if (searchTitle != null && !searchTitle.isEmpty()) {
            sliderList = sliderList.stream()
                    .filter(slider -> slider.getTitle().toLowerCase().contains(searchTitle.toLowerCase()))
                    .toList();
        }

        // Lọc theo backlink nếu có nhập
        if (searchLink != null && !searchLink.isEmpty()) {
            sliderList = sliderList.stream()
                    .filter(slider -> slider.getBackLink().toLowerCase().contains(searchLink.toLowerCase()))
                    .toList();
        }

        // Lọc theo status nếu người dùng chọn
        if (sliderStatus != null && !sliderStatus.isEmpty()) {
            int status = Integer.parseInt(sliderStatus); // Chuyển status từ String thành int
            sliderList = sliderList.stream()
                    .filter(slider -> slider.getStatus() == status) // So sánh int với int
                    .toList();
        }
        if (sliderList.isEmpty()) {
            request.getSession().setAttribute("updateMessage", "Không tìm thấy dữ liệu phù hợp.");
        }
        // Gửi lại dữ liệu tìm kiếm để JSP giữ lại giá trị
        request.setAttribute("searchTitle", searchTitle);
        request.setAttribute("searchLink", searchLink);
        request.setAttribute("sliderStatus", sliderStatus);
        request.setAttribute("sliderList", sliderList);
        request.getRequestDispatcher("/AdminPage/SliderList.jsp").forward(request, response);
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
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("userId"); // Lấy userId từ session

        if (userId == null) {
            response.sendRedirect("login.jsp"); // Nếu chưa đăng nhập, chuyển hướng về trang đăng nhập
            return;
        }

        SliderDao sliderDao = new SliderDao();

        if ("add".equals(action)) {
            String title = request.getParameter("title");
            String imageUrl = request.getParameter("imageUrl");
            String backLink = request.getParameter("backlink");
            String notes = request.getParameter("notes");
            int status = Integer.parseInt(request.getParameter("status"));

            List<String> errors = new ArrayList<>();

//            // Validate độ dài title & notes
//            if (title.length() > 255) {
//                errors.add("Tiêu đề không được quá 255 ký tự.");
//            }
//            if (notes.length() > 500) {
//                errors.add("Ghi chú không được quá 500 ký tự.");
//            }
//
//            // Kiểm tra định dạng URL
//            String urlPattern = "^(https?://)?([\\w\\-]+\\.)+[\\w\\-]+(/.*)?$";
//            //http://sub.domain.vn
//            //https://example.com
//            
//            //k hop le
//            //ftp://example.com
//            //www.example.com
//            if (!imageUrl.matches(urlPattern)) {
//                errors.add("URL hình ảnh không hợp lệ.");
//            }
//            if (!backLink.matches(urlPattern)) {
//                errors.add("Backlink không hợp lệ.");
//            }
//
//            // Nếu có lỗi thì lưu vào session và redirect
//            if (!errors.isEmpty()) {
//                session.setAttribute("updateErrors", errors);
//                response.sendRedirect("userprofile");
//                return;
//            }

            Slider newSlider = new Slider(imageUrl, title, backLink, status, notes, userId);

            boolean success = sliderDao.addSlider(newSlider);
            session.setAttribute("updateMessage", success ? "Thêm slider thành công!" : "Thêm slider thất bại!");

        } else if ("update".equals(action)) {
            int sliderId = Integer.parseInt(request.getParameter("sliderId"));
            String title = request.getParameter("title");
            String imageUrl = request.getParameter("imageUrl");
            String backLink = request.getParameter("backlink");
            String notes = request.getParameter("notes");
            int status = Integer.parseInt(request.getParameter("status"));

            Slider updatedSlider = new Slider(sliderId, imageUrl, title, backLink, status, notes, userId);
            boolean success = sliderDao.updateSlider(updatedSlider);
            session.setAttribute("updateMessage", success ? "Cập nhật slider thành công!" : "Cập nhật slider thất bại!");
        } else if ("load".equals(action)) {
            int sliderId = Integer.parseInt(request.getParameter("sliderId"));

            // Lấy thông tin slider từ database
            Slider slider = sliderDao.getSliderById(sliderId);

            if (slider != null) {
                request.setAttribute("slider", slider);
                request.getRequestDispatcher("/AdminPage/SliderDetail.jsp").forward(request, response);
            } else {
                response.sendRedirect("sliderlist.jsp?error=Slider không tồn tại");
            }
        } else if ("activate".equals(action)) {
            int sliderId = Integer.parseInt(request.getParameter("sliderId"));
            sliderDao.activateSlider(sliderId);
            session.setAttribute("updateMessage", "Slider đã được kích hoạt!");

        } else if ("deactivate".equals(action)) {
            int sliderId = Integer.parseInt(request.getParameter("sliderId"));
            sliderDao.deactivateSlider(sliderId);
            session.setAttribute("updateMessage", "Slider đã bị vô hiệu hóa!");
        }

        response.sendRedirect("sliderlistcontroller"); // Reload lại danh sách sliders
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

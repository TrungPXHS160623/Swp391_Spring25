package Controller;

import Dao.ProductDao;
import Dto.ProductDto;
import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.RequestDispatcher;

@WebServlet(name="FilterProduct", urlPatterns={"/filterproduct"})
public class FilterProduct extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ProductDao productDao;

    @Override
    public void init() throws ServletException {
        productDao = new ProductDao();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        // Nhận dữ liệu từ request
        String keyword = request.getParameter("keyword");
        String[] categories = request.getParameterValues("category");
        String minPriceStr = request.getParameter("minPrice");
        String maxPriceStr = request.getParameter("maxPrice");
        double minPrice = (minPriceStr != null && !minPriceStr.isEmpty()) ? Double.parseDouble(minPriceStr) : 0.0;
        double maxPrice = (maxPriceStr != null && !maxPriceStr.isEmpty()) ? Double.parseDouble(maxPriceStr) : Double.MAX_VALUE;
        String rating = request.getParameter("rating");
        String[] status = request.getParameterValues("status");
        String[] discount = request.getParameterValues("discount");

        // Gọi DAO để lấy danh sách sản phẩm theo bộ lọc
        List<ProductDto> filteredProducts = productDao.getFilteredProducts(keyword, categories, minPrice, maxPrice, rating, status, discount);

        // Đưa danh sách sản phẩm vào request
        request.setAttribute("filteredProducts", filteredProducts);

        // Chuyển hướng đến trang hiển thị kết quả
        RequestDispatcher dispatcher = request.getRequestDispatcher("filteredProducts.jsp");
        dispatcher.forward(request, response);
    }
    
}

package Controller;

import Dao.ProductDao;
import Dto.ProductDto;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
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
        // ✅ 1. Lấy dữ liệu từ request
        String keyword = request.getParameter("keyword");
        String[] categoryIds = request.getParameterValues("category");
        String minPriceStr = request.getParameter("minPrice");
        String maxPriceStr = request.getParameter("maxPrice");
        String ratingStr = request.getParameter("rating");
        String[] stockStatus = request.getParameterValues("stock");
        String discounted = request.getParameter("discounted");
        String bestseller = request.getParameter("bestseller");

        // ✅ 2. Chuyển đổi dữ liệu
        Double minPrice = (minPriceStr != null && !minPriceStr.isEmpty()) ? Double.parseDouble(minPriceStr) : null;
        Double maxPrice = (maxPriceStr != null && !maxPriceStr.isEmpty()) ? Double.parseDouble(maxPriceStr) : null;
        Integer rating = (ratingStr != null) ? Integer.parseInt(ratingStr) : null;
        List<Integer> categoryList = (categoryIds != null) ? Arrays.stream(categoryIds).map(Integer::parseInt).toList() : new ArrayList<>();
        boolean inStock = (stockStatus != null && Arrays.asList(stockStatus).contains("inStock"));
        boolean outOfStock = (stockStatus != null && Arrays.asList(stockStatus).contains("outOfStock"));
        boolean isDiscounted = (discounted != null);
        boolean isBestseller = (bestseller != null);

        // ✅ 3. Gọi DAO để lấy danh sách sản phẩm phù hợp
        List<ProductDto> filteredProducts = productDao.filterProducts(keyword, categoryList, minPrice, maxPrice, rating, inStock, outOfStock, isDiscounted, isBestseller);

        // ✅ 4. Gửi danh sách sản phẩm về trang JSP
        request.setAttribute("filteredProducts", filteredProducts);
        RequestDispatcher dispatcher = request.getRequestDispatcher("filteredProducts.jsp");
        dispatcher.forward(request, response);
    }
    
}

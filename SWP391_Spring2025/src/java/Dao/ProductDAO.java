/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dao;

import Context.DBContext;
import Entity.Product;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author LENOVO
 */
public class ProductDAO {

    private static final Logger LOGGER = Logger.getLogger(ProductDAO.class.getName());

    // Liệt kê tất cả các sản phẩm
    public List<Product> getAllProduct() {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT [product_id],[image_url],[product_name],[subcategory_id],"
                + " [status],[listPrice],[salePrice],[featured]\n"
                + "  FROM [swp391_sping25].[dbo].[Products]";
        try (Connection conn = new DBContext().getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                products.add(mapResultSetToProduct(rs));
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Lỗi SQL khi lấy danh sách sản phẩm", e);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Lỗi không mong muốn khi lấy danh sách sản phẩm", e);
        }
        return products;
    }

    // Tìm kiếm theo tên sản phẩm
    public List<Product> searchProduct(String keyword) {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM Products WHERE product_name LIKE ?";
        try (Connection conn = new DBContext().getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + keyword + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    products.add(mapResultSetToProduct(rs));
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Lỗi SQL khi tìm kiếm sản phẩm", e);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Lỗi không mong muốn khi tìm kiếm sản phẩm", e);
        }
        return products;
    }

    // chức năng Phân trang sản phẩm
    public List<Product> getAllProduct2(int page, int limit) {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM Products ORDER BY product_id DESC OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
        try (Connection conn = new DBContext().getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, (page - 1) * limit);
            stmt.setInt(2, limit);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    products.add(mapResultSetToProduct(rs));
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Lỗi khi phân trang danh mục", e);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Lỗi kết nối CSDL khi Phân trang danh mục", e);
        }
        return products;
    }

    // Chuyển đổi từ ResultSet thành Product Object
    private Product mapResultSetToProduct(ResultSet rs) throws SQLException {
        Product product = new Product();
        product.setProduct_id(rs.getInt("product_id"));
        product.setImage_url(rs.getString("image_url"));
        product.setProduct_name(rs.getString("product_name"));
        product.setSubcategory_id(rs.getInt("subcategory_id"));
        product.setStatus(rs.getInt("status"));
        product.setList_price(rs.getFloat("listPrice"));
        product.setSale_price(rs.getFloat("salePrice"));
        product.setFeatured(rs.getInt("featured"));
        return product;
    }

    // Thêm chức năng sắp xếp theo tên sản phẩm
    public List<Product> getAllProductSortedByName(boolean ascending) {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT [product_id],[image_url],[product_name],[subcategory_id],"
                + " [status],[listPrice],[salePrice],[featured] "
                + "FROM [swp391_sping25].[dbo].[Products] "
                + "ORDER BY product_name " + (ascending ? "ASC" : "DESC");

        try (Connection conn = new DBContext().getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                products.add(mapResultSetToProduct(rs));
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Lỗi SQL khi lấy danh sách sản phẩm sắp xếp", e);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Lỗi không mong muốn khi lấy danh sách sản phẩm sắp xếp", e);
        }
        return products;
    }

    public static void main(String[] args) {
        ProductDAO pDAO = new ProductDAO();

        // Tính tổng sổ sản phẩm hiện có
        List<Product> products = pDAO.getAllProduct();
        System.out.println("Tổng số danh mục: " + products.size());

        // In ra toàn bộ thông tin cần thiết của 1 sản phẩm
        for (Product product : products) {
            System.out.println("---------------------------------------------------");
            System.out.println("ID sản phẩm: " + product.getProduct_id());
            System.out.println("Tên sản phẩm: " + product.getProduct_name());
            System.out.println("URL ảnh: " + product.getImage_url());
            System.out.println("ID phân loại con: " + product.getSubcategory_id());
            System.out.println("Trạng thái: " + product.getStatus());
            System.out.println("Giá niêm yết: " + product.getList_price());
            System.out.println("Giá bán: " + product.getSale_price());
            System.out.println("Sản phẩm nổi bật: " + product.getFeatured());
        }

        // Test phân trang sản phẩm (Kiểm tra tối đa 5)
        List<Product> paginatedCategories = pDAO.getAllProduct2(1, 5);
        System.out.println("////////////////////////////////////////////////////////");
        System.out.println("Danh mục trang 1 (tối đa 5): " + paginatedCategories.size());

        // Test tìm kiểm 1 sản phẩm theo tên
        List<Product> searchResults = pDAO.searchProduct("1");
        System.out.println("Tìm kiếm 'Test': " + searchResults.size() + " kết quả");

        // Kiểm tra sắp xếp tăng dần theo tên sản phẩm
        List<Product> sortedAscProducts = pDAO.getAllProductSortedByName(true);
        System.out.println("Sắp xếp tăng dần theo tên sản phẩm:");
        for (Product product : sortedAscProducts) {
            System.out.println(product.getProduct_name());
        }

        // Kiểm tra sắp xếp giảm dần theo tên sản phẩm
        List<Product> sortedDescProducts = pDAO.getAllProductSortedByName(false);
        System.out.println("Sắp xếp giảm dần theo tên sản phẩm:");
        for (Product product : sortedDescProducts) {
            System.out.println(product.getProduct_name());
        }
    }
}

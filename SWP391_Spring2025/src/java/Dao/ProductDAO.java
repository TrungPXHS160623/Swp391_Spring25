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

    public List<Product> getAllProduct() {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT TOP (1000) [product_id],[image_url],[product_name],[subcategory_id],"
                + " [status],[listPrice],[salePrice],[featured]\n"
                + "  FROM [swp391_sping25].[dbo].[Products]";
        try (Connection conn = new DBContext().getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                products.add(mapResultSetToProduct(rs));
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Lỗi SQL khi lấy danh sách danh mục", e);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Lỗi không mong muốn khi lấy danh sách danh mục", e);
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

    public static void main(String[] args) {
        ProductDAO pDAO = new ProductDAO();
        
        //Tính tổng sổ product hiện có
        List<Product> products = pDAO.getAllProduct();
        System.out.println("Tổng số danh mục: " + products.size());

        //In ra toàn bộ thông tin cần thiết của 1 product
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
    }
}

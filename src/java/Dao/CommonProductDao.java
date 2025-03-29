/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dao;

import Context.DBContext;
import Dto.CommonProductDto;
import Entity.ProductImage;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author LENOVO
 */
public class CommonProductDao {

    private static final Logger LOGGER = Logger.getLogger(CommonProductDao.class.getName());

    // Lấy thông tin sản phẩm (read-only) + danh sách toàn bộ ảnh
    public CommonProductDto getProductDetail(int productId) {
        CommonProductDto dto = null;
        // Câu truy vấn lấy thông tin sản phẩm và tên subcategory
        String sqlProduct = "SELECT p.product_id, p.product_name, p.description, p.price, p.stock_quantity, c.subcategory_name, "
                + "p.created_at, p.updated_at, p.discount_price, p.discount_percentage, p.sold_quantity, p.average_rating "
                + "FROM Products p "
                + "LEFT JOIN SubCategories c ON p.subcategory_id = c.subcategory_id "
                + "WHERE p.product_id = ?";
        // Câu truy vấn lấy danh sách tất cả ảnh (và video nếu có) cho sản phẩm
        String sqlImages = "SELECT image_id, product_id, image_url, created_at, updated_at, is_primary "
                + "FROM ProductImages "
                + "WHERE product_id = ? ORDER BY is_primary DESC, image_id ASC";
        try (Connection conn = new DBContext().getConnection(); PreparedStatement stmtProduct = conn.prepareStatement(sqlProduct); PreparedStatement stmtImages = conn.prepareStatement(sqlImages)) {

            stmtProduct.setInt(1, productId);
            try (ResultSet rs = stmtProduct.executeQuery()) {
                if (rs.next()) {
                    dto = new CommonProductDto();
                    dto.setProductId(rs.getInt("product_id"));
                    dto.setProductName(rs.getString("product_name"));
                    dto.setDescription(rs.getString("description"));
                    dto.setPrice(rs.getDouble("price"));
                    dto.setStockQuantity(rs.getInt("stock_quantity"));
                    dto.setCategory(rs.getString("subcategory_name")); // Lấy tên subcategory
                    dto.setCreatedAt(rs.getTimestamp("created_at"));
                    dto.setUpdatedAt(rs.getTimestamp("updated_at"));
                    dto.setDiscountPrice(rs.getDouble("discount_price"));
                    dto.setDiscountPercentage(rs.getDouble("discount_percentage"));
                    dto.setSoldQuantity(rs.getInt("sold_quantity"));
                    dto.setAverageRating(rs.getDouble("average_rating"));
                    // Tính trạng thái: "Sale" nếu stock_quantity > 0, "Soldout" nếu không.
                    dto.computeStatus();
                }
            }

            if (dto != null) {
                List<ProductImage> imageList = new ArrayList<>();
                stmtImages.setInt(1, productId);
                try (ResultSet rsImages = stmtImages.executeQuery()) {
                    while (rsImages.next()) {
                        ProductImage img = new ProductImage();
                        img.setImage_id(rsImages.getInt("image_id"));
                        img.setProduct_id(rsImages.getInt("product_id"));
                        img.setImage_url(rsImages.getString("image_url"));
                        img.setCreatedAt(rsImages.getTimestamp("created_at"));
                        img.setUpdatedAt(rsImages.getTimestamp("updated_at"));
                        img.setIs_primary(rsImages.getBoolean("is_primary"));
                        imageList.add(img);
                    }
                }
                dto.setImageList(imageList);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Lỗi SQL khi lấy chi tiết sản phẩm", e);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Lỗi SQL khi lấy thông tin sản phẩm (read-only)", e);
        }
        return dto;
    }
}

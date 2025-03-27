/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dto;

import Entity.ProductImage;
import java.sql.Timestamp;
import java.util.List;

/**
 *
 * @author LENOVO
 */
public class CommonProductDto {

    private int productId;
    private String productName;
    private String description;
    private double price;
    private int stockQuantity;
    private String category; // Lấy từ subcategory_name của bảng SubCategories
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private double discountPrice;
    private double discountPercentage;
    private int soldQuantity;
    private double averageRating;

    // Danh sách tất cả ảnh (và video) của sản phẩm
    private List<ProductImage> imageList;

    // Trường derived: trạng thái sản phẩm (Sale nếu còn hàng, Soldout nếu hết)
    private String status;

    public CommonProductDto() {
    }

    // Getters and Setters
    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    public double getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(double discountPrice) {
        this.discountPrice = discountPrice;
    }

    public double getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(double discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    public int getSoldQuantity() {
        return soldQuantity;
    }

    public void setSoldQuantity(int soldQuantity) {
        this.soldQuantity = soldQuantity;
    }

    public double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(double averageRating) {
        this.averageRating = averageRating;
    }

    public List<ProductImage> getImageList() {
        return imageList;
    }

    public void setImageList(List<ProductImage> imageList) {
        this.imageList = imageList;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // Phương thức tính trạng thái dựa trên stockQuantity
    public void computeStatus() {
        this.status = (this.stockQuantity > 0) ? "Sale" : "Soldout";
    }

    @Override
    public String toString() {
        return "CommonProductDto{"
                + "productId=" + productId
                + ", productName='" + productName + '\''
                + ", description='" + description + '\''
                + ", price=" + price
                + ", stockQuantity=" + stockQuantity
                + ", category='" + category + '\''
                + ", createdAt=" + createdAt
                + ", updatedAt=" + updatedAt
                + ", discountPrice=" + discountPrice
                + ", discountPercentage=" + discountPercentage
                + ", soldQuantity=" + soldQuantity
                + ", averageRating=" + averageRating
                + ", imageList=" + imageList
                + ", status='" + status + '\''
                + '}';
    }
}

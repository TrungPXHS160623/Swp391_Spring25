/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dto;

import Entity.ProductImage;
import java.security.Timestamp;
import java.util.List;

/**
 *
 * @author LENOVO
 */
public class AdminProductDto {

    // Thông tin sản phẩm
    private int productId;
    private String productName;
    private String description;
    private double price;
    private int stockQuantity;
    private int subcategoryId;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private double discountPrice;
    private double discountPercentage;
    private int soldQuantity;
    private double averageRating;

    // Thông tin hiển thị: Ảnh chính (primary image) và danh sách các ảnh (có thể bao gồm cả video)
    private String primaryImageUrl;  // Ảnh chính của sản phẩm
    private List<ProductImage> mediaList; // Danh sách các ảnh (và video nếu có)

    public AdminProductDto() {
    }

    public AdminProductDto(int productId, String productName, String description, double price, int stockQuantity,
            int subcategoryId, Timestamp createdAt, Timestamp updatedAt, double discountPrice,
            double discountPercentage, int soldQuantity, double averageRating,
            String primaryImageUrl, List<ProductImage> mediaList) {
        this.productId = productId;
        this.productName = productName;
        this.description = description;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.subcategoryId = subcategoryId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.discountPrice = discountPrice;
        this.discountPercentage = discountPercentage;
        this.soldQuantity = soldQuantity;
        this.averageRating = averageRating;
        this.primaryImageUrl = primaryImageUrl;
        this.mediaList = mediaList;
    }

    // Getters & Setters
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

    public int getSubcategoryId() {
        return subcategoryId;
    }

    public void setSubcategoryId(int subcategoryId) {
        this.subcategoryId = subcategoryId;
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

    public String getPrimaryImageUrl() {
        return primaryImageUrl;
    }

    public void setPrimaryImageUrl(String primaryImageUrl) {
        this.primaryImageUrl = primaryImageUrl;
    }

    public List<ProductImage> getMediaList() {
        return mediaList;
    }

    public void setMediaList(List<ProductImage> mediaList) {
        this.mediaList = mediaList;
    }

    @Override
    public String toString() {
        return "AdminProductDto{"
                + "productId=" + productId
                + ", productName='" + productName + '\''
                + ", description='" + description + '\''
                + ", price=" + price
                + ", stockQuantity=" + stockQuantity
                + ", subcategoryId=" + subcategoryId
                + ", createdAt=" + createdAt
                + ", updatedAt=" + updatedAt
                + ", discountPrice=" + discountPrice
                + ", discountPercentage=" + discountPercentage
                + ", soldQuantity=" + soldQuantity
                + ", averageRating=" + averageRating
                + ", primaryImageUrl='" + primaryImageUrl + '\''
                + ", mediaList=" + mediaList
                + '}';
    }
}

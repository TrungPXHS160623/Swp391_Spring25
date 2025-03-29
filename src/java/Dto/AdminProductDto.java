/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dto;

import Entity.ProductImage;
import java.sql.Timestamp;
import java.util.List;

public class AdminProductDto {

    // Thông tin sản phẩm cơ bản
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

    // Ảnh chính (thumbnail)
    private String primaryImageUrl;

    // Danh sách media (ảnh/video) dùng cho ProductDetail
    private List<ProductImage> mediaList;

    // Trường bổ sung tính toán
    private String featured; // "Yes" nếu soldQuantity>=50 và averageRating>4, ngược lại "No"
    private String status;   // "Sale" nếu stockQuantity>0, "Soldout" nếu không

    // Thêm trường Category để hiển thị tên subcategory
    private String category;

    // Flag “featured” do admin bật/tắt
    private boolean featuredFlag;

    public boolean isFeaturedFlag() {
        return featuredFlag;
    }

    public void setFeaturedFlag(boolean featuredFlag) {
        this.featuredFlag = featuredFlag;
    }

    // Constructor mặc định
    public AdminProductDto() {
    }

    // Getters & Setters cho tất cả các trường
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

    public String getFeatured() {
        return featured;
    }

    public void setFeatured(String featured) {
        this.featured = featured;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // Setter and getter cho category
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    // Phương thức tính toán các trường derived (có thể gọi sau khi set dữ liệu)
    public void computeDerivedFields() {
        this.featured = (this.soldQuantity >= 50 && this.averageRating > 4) ? "Yes" : "No";
        this.status = (this.stockQuantity > 0) ? "Sale" : "Soldout";
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
                + ", featured='" + featured + '\''
                + ", status='" + status + '\''
                + ", category='" + category + '\''
                + '}';
    }
}

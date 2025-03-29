/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dto;

import Entity.FeedbackImage;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Admin
 */
public class FeedbackDto {
    private int reviewId;
    private int productId;
    private String productName;
    private int userId;
    private String userFullName;
    private int rating;
    private String comment;
    private Timestamp createdAt;
    private boolean status;
    private List<String> imageUrls = new ArrayList<>();
    
    public FeedbackDto() {
    }
    
    public FeedbackDto(int reviewId, int productId, String productName, int userId, 
                      String userFullName, int rating, String comment, 
                      Timestamp createdAt, boolean status) {
        this.reviewId = reviewId;
        this.productId = productId;
        this.productName = productName;
        this.userId = userId;
        this.userFullName = userFullName;
        this.rating = rating;
        this.comment = comment;
        this.createdAt = createdAt;
        this.status = status;
    }
    
    public void addImageUrl(String imageUrl) {
        this.imageUrls.add(imageUrl);
    }
    
    public void setImageUrls(List<String> imageUrls) {
        this.imageUrls = imageUrls;
    }
    
    public List<String> getImageUrls() {
        return imageUrls;
    }

    public int getReviewId() {
        return reviewId;
    }

    public void setReviewId(int reviewId) {
        this.reviewId = reviewId;
    }

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

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
    
    public String getUserFullName() {
        return userFullName;
    }

    public void setUserFullName(String userFullName) {
        this.userFullName = userFullName;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
    
    public String getStatusText() {
        return status ? "Active" : "Inactive";
    }
}

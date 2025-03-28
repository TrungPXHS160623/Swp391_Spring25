/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dto;

import java.sql.Timestamp;

/**
 *
 * @author Admin
 */
public class PostDto {
    private int postId;
    private String title;
    private String content;
    private String thumbnailUrl;
    private int userId;
    private String authorName;
    private int categoryId;
    private String categoryName;
    private boolean status; // Changed from String to boolean
    private Timestamp createdAt;

    public PostDto() {
    }

    public PostDto(int postId, String title, String content, String thumbnailUrl, 
                  int userId, String authorName, int categoryId, String categoryName, 
                  boolean status, Timestamp createdAt) {
        this.postId = postId;
        this.title = title;
        this.content = content;
        this.thumbnailUrl = thumbnailUrl;
        this.userId = userId;
        this.authorName = authorName;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.status = status;
        this.createdAt = createdAt;
    }

    // Getters and setters
    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
    
    // Helper method to get brief content (first 100 characters)
    public String getBriefContent() {
        if (content != null && content.length() > 100) {
            return content.substring(0, 100) + "...";
        }
        return content;
    }
}

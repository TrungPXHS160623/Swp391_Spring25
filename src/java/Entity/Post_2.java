package Entity;

import java.sql.Timestamp;

public class Post_2 {
    private int postId;
    private String title;
    private String content;
    private String thumbnailUrl;
    private int userId;
    private int categoryId;
    private boolean status;
    private Timestamp createdAt;
    
    // Default constructor
    public Post_2() {
    }

    // Full constructor
    public Post_2(int postId, String title, String content, String thumbnailUrl, int userId, int categoryId, boolean status, Timestamp createdAt) {
        this.postId = postId;
        this.title = title;
        this.content = content;
        this.thumbnailUrl = thumbnailUrl;
        this.userId = userId;
        this.categoryId = categoryId;
        this.status = status;
        this.createdAt = createdAt;
    }
    
    // Constructor for adding new post
    public Post_2(String title, String content, String thumbnailUrl, int userId, int categoryId, boolean status) {
        this.title = title;
        this.content = content;
        this.thumbnailUrl = thumbnailUrl;
        this.userId = userId;
        this.categoryId = categoryId;
        this.status = status;
    }

    // Getters and Setters
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

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
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

    @Override
    public String toString() {
        return "Post{" + "postId=" + postId + ", title=" + title + ", content=" + content + 
               ", thumbnailUrl=" + thumbnailUrl + ", userId=" + userId + 
               ", categoryId=" + categoryId + ", status=" + status + 
               ", createdAt=" + createdAt + '}';
    }
}

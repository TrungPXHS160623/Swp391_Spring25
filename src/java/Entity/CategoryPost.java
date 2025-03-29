package Entity;

import java.sql.Timestamp;

public class CategoryPost {
    private int categoryId;
    private String categoryName;
    private String description;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private boolean isActive;
    
    public CategoryPost() {
    }

    public CategoryPost(int categoryId, String categoryName, String description, Timestamp createdAt, Timestamp updatedAt, boolean isActive) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.description = description;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.isActive = isActive;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public boolean isIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    @Override
    public String toString() {
        return "CategoryPost{" + "categoryId=" + categoryId + ", categoryName=" + categoryName + 
               ", description=" + description + ", createdAt=" + createdAt + 
               ", updatedAt=" + updatedAt + ", isActive=" + isActive + '}';
    }
}

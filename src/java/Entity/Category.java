
/*

      * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entity;

import java.time.LocalDateTime;

/**
 *
 * @author Acer
 */
public class Category {
   private int categoryId ;
   private String categoryName ; 
   private String description ; 
   private LocalDateTime createdAt;
   private LocalDateTime updatedAt;
   private int status;          // Trạng thái danh mục (1: Hoạt động, 0: Ẩn)
   private Integer updatedBy;   // ID của người cập nhật danh mục (có thể NULL)
   private LocalDateTime deletedAt; // Thời điểm xóa mềm (có thể NULL)

    public Category(int categoryId, String categoryName, String description, LocalDateTime createdAt, LocalDateTime updatedAt, int status, Integer updatedBy, LocalDateTime deletedAt) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.description = description;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.status = status;
        this.updatedBy = updatedBy;
        this.deletedAt = deletedAt;
    }

    public Category(String categoryName, String description, LocalDateTime createdAt, int status) {
        this.categoryName = categoryName;
        this.description = description;
        this.createdAt = createdAt;
        this.status = status;
    }
   

    public Category() {
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setUpdatedBy(Integer updatedBy) {
        this.updatedBy = updatedBy;
    }

    public void setDeletedAt(LocalDateTime deletedAt) {
        this.deletedAt = deletedAt;
    }

    public int getStatus() {
        return status;
    }

    public Integer getUpdatedBy() {
        return updatedBy;
    }

    public LocalDateTime getDeletedAt() {
        return deletedAt;
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

    public void setDescription(String Description) {
        this.description = Description;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "Category{" + "categoryId=" + categoryId + ", categoryName=" + categoryName + ", description=" + description + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + ", status=" + status + ", updatedBy=" + updatedBy + ", deletedAt=" + deletedAt + '}';
    }
}

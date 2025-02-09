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
public class SubCategory {
    private int subcategoryId ;
    private String subcategoryName ;
    private int categoryId ;
    private String description ; 
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private int status;          // Trạng thái danh mục (1: Hoạt động, 0: Ẩn)
    private Integer updatedBy;   // ID của người cập nhật danh mục (có thể NULL)
    private LocalDateTime deletedAt; // Thời điểm xóa mềm (có thể NULL)

    public SubCategory() {
    }

    public SubCategory(int subcategoryId, String subcategoryName, int categoryId, String description, LocalDateTime createdAt, LocalDateTime updatedAt, int status, Integer updatedBy, LocalDateTime deletedAt) {
        this.subcategoryId = subcategoryId;
        this.subcategoryName = subcategoryName;
        this.categoryId = categoryId;
        this.description = description;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.status = status;
        this.updatedBy = updatedBy;
        this.deletedAt = deletedAt;
    }

    public int getSubcategoryId() {
        return subcategoryId;
    }

    public String getSubcategoryName() {
        return subcategoryName;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
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

    public void setSubcategoryId(int subcategoryId) {
        this.subcategoryId = subcategoryId;
    }

    public void setSubcategoryName(String subcategoryName) {
        this.subcategoryName = subcategoryName;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
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

    @Override
    public String toString() {
        return "SubCategory{" + "subcategoryId=" + subcategoryId + ", subcategoryName=" + subcategoryName + ", categoryId=" + categoryId + ", description=" + description + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + ", status=" + status + ", updatedBy=" + updatedBy + ", deletedAt=" + deletedAt + '}';
    }
    
    
}

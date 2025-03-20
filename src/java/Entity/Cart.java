/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entity;

import java.util.Date;

/**
 *
 * @author Acer
 */
public class Cart {
    private int cartId;
    private int userId;
    private Date createdAt;
    private Date updatedAt;
    private String status; // Có thể là "Pending", "Completed", "Cancelled", etc.

    public Cart(int cartId, int userId, Date createdAt, Date updatedAt, String status) {
        this.cartId = cartId;
        this.userId = userId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.status = status;
    }

    public int getCartId() {
        return cartId;
    }

    public void setCartId(int cartId) {
        this.cartId = cartId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Cart{" + "cartId=" + cartId + ", userId=" + userId + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + ", status=" + status + '}';
    }
    
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entity;

import java.sql.Timestamp;

/**
 *
 * @author Acer
 */
public class Orders {
    private int orderId;
    private int userId;
    private String orderStatus;
    private double totalAmount;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    public Orders(int orderId, int userId, String orderStatus, double totalAmount, Timestamp createdAt, Timestamp updatedAt) {
        this.orderId = orderId;
        this.userId = userId;
        this.orderStatus = orderStatus;
        this.totalAmount = totalAmount;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Orders() {
    }

    public int getOrderId() {
        return orderId;
    }

    public int getUserId() {
        return userId;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "Orders{" + "orderId=" + orderId + ", userId=" + userId + ", orderStatus=" + orderStatus + ", totalAmount=" + totalAmount + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + '}';
    }
    
}

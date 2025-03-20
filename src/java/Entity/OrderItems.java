/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entity;

/**
 *
 * @author Acer
 */
public class OrderItems {
    private int orderItemId;
    private int orderId;
    private int productId;
    private int quantity;
    private double price;
    private double subtotal;

    public OrderItems() {
    }

    public OrderItems(int orderItemId, int orderId, int productId, int quantity, double price, double subtotal) {
        this.orderItemId = orderItemId;
        this.orderId = orderId;
        this.productId = productId;
        this.quantity = quantity;
        this.price = price;
        this.subtotal = subtotal;
    }

    public int getOrderItemId() {
        return orderItemId;
    }

    public int getOrderId() {
        return orderId;
    }

    public int getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setOrderItemId(int orderItemId) {
        this.orderItemId = orderItemId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    @Override
    public String toString() {
        return "OrderItems{" + "orderItemId=" + orderItemId + ", orderId=" + orderId + ", productId=" + productId + ", quantity=" + quantity + ", price=" + price + ", subtotal=" + subtotal + '}';
    }
    
    
    
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dto;

/**
 *
 * @author FPT SHOP
 */
public class CartDetailDto2 {

    private int cartItemId;
    private int cartId;
    private int productId;
    private String productName;
    private int quantity;
    private double price;
    private double discountPrice;
    private double totalPrice;
    private String imageUrl;
    private int orderId;  // For order display

    public CartDetailDto2(int cartItemId, int cartId, int productId, String productName, int quantity, double price, double discountPrice, double totalPrice, String imageUrl, int orderId) {
        this.cartItemId = cartItemId;
        this.cartId = cartId;
        this.productId = productId;
        this.productName = productName;
        this.quantity = quantity;
        this.price = price;
        this.discountPrice = discountPrice;
        this.totalPrice = totalPrice;
        this.imageUrl = imageUrl;
        this.orderId = orderId;
    }

    public CartDetailDto2() {
    }

    public int getCartItemId() {
        return cartItemId;
    }

    public void setCartItemId(int cartItemId) {
        this.cartItemId = cartItemId;
    }

    public int getCartId() {
        return cartId;
    }

    public void setCartId(int cartId) {
        this.cartId = cartId;
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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(double discountPrice) {
        this.discountPrice = discountPrice;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    @Override
    public String toString() {
        return "CartDetailDto2{" + "cartItemId=" + cartItemId + ", cartId=" + cartId + ", productId=" + productId + ", productName=" + productName + ", quantity=" + quantity + ", price=" + price + ", discountPrice=" + discountPrice + ", totalPrice=" + totalPrice + ", imageUrl=" + imageUrl + ", orderId=" + orderId + '}';
    }
    
    
}

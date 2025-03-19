/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dto;

/**
 *
 * @author Acer
 */
public class CartDetailDto {
    private int cartItemId;
    private int cartId;
    private int productId;
    private String productName;
    private String imageUrl;
    private int quantity;
    private double price;
    private double discountPrice;
    private double totalPrice;

    public CartDetailDto() {
    }

    public CartDetailDto(int cartItemId, int cartId, int productId, String productName, String imageUrl, int quantity, double price, double discountPrice, double totalPrice) {
        this.cartItemId = cartItemId;
        this.cartId = cartId;
        this.productId = productId;
        this.productName = productName;
        this.imageUrl = imageUrl;
        this.quantity = quantity;
        this.price = price;
        this.discountPrice = discountPrice;
        this.totalPrice = totalPrice;
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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
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
    
    public void setTotalPrice() {
    // Nếu sản phẩm có giá giảm thì lấy discountPrice, nếu không thì lấy price
    this.totalPrice = (discountPrice > 0 ? discountPrice : price) * quantity;
}

    @Override
    public String toString() {
        return "CardDetailDto{" + "cartItemId=" + cartItemId + ", cartId=" + cartId + ", productId=" + productId + ", productName=" + productName + ", imageUrl=" + imageUrl + ", quantity=" + quantity + ", price=" + price + ", discountPrice=" + discountPrice + ", totalPrice=" + totalPrice + '}';
    }
    
}

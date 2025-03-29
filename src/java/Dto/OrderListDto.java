/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dto;

import java.sql.Timestamp;
import java.util.List;

/**
 *
 * @author LENOVO
 */
public class OrderListDto {

    private int orderId;
    private Timestamp orderDate;
    private String customerName; // Tùy vào logic (có thể join với bảng Users)
    private String productList;  // Chuỗi gộp tên sản phẩm: "Product A, Product B"
    private double totalCost;
    private String orderStatus;

    public OrderListDto() {
    }

    public OrderListDto(int orderId, Timestamp orderDate, String customerName, String productList, double totalCost, String orderStatus) {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.customerName = customerName;
        this.productList = productList;
        this.totalCost = totalCost;
        this.orderStatus = orderStatus;
    }
// Getters & Setters

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public Timestamp getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Timestamp orderDate) {
        this.orderDate = orderDate;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getProductList() {
        return productList;
    }

    public void setProductList(String productList) {
        this.productList = productList;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }
}

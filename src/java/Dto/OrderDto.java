package Dto;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class OrderDto {
    private int orderId;
    private Timestamp orderDate;
    private List<String> productNames;
    private double totalCost;
    private String status;

    public OrderDto() {
        this.productNames = new ArrayList<>(); // Tránh null
    }

    public OrderDto(int orderId, Timestamp orderDate, List<String> productNames, double totalCost, String status) {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.productNames = (productNames != null) ? productNames : new ArrayList<>();
        this.totalCost = totalCost;
        this.status = status;
    }

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

    public List<String> getProductNames() {
        return productNames;
    }

    public void setProductNames(List<String> productNames) {
        this.productNames = (productNames != null) ? productNames : new ArrayList<>();
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "OrderDto{" +
                "orderId=" + orderId +
                ", orderDate=" + orderDate +
                ", productNames=" + (productNames != null ? productNames.toString() : "[]") +
                ", totalCost=" + totalCost +
                ", status='" + status + '\'' +
                '}';
    }
}

<%-- 
    Document   : CheckoutSuccessfully
    Created on : Mar 23, 2025, 11:22:05 PM
    Author     : Admin
--%>

<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Checkout Successful</title>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
        <style>
            body {
                font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
                background-color: #f8f9fa;
                margin: 0;
                padding: 0;
                color: #333;
            }
            
            .success-container {
                max-width: 800px;
                margin: 50px auto;
                padding: 30px;
                background-color: #fff;
                border-radius: 10px;
                box-shadow: 0 0 20px rgba(0, 0, 0, 0.1);
                text-align: center;
            }
            
            .success-icon {
                font-size: 80px;
                color: #4CAF50;
                margin-bottom: 20px;
            }
            
            .success-title {
                font-size: 32px;
                color: #4CAF50;
                margin-bottom: 15px;
            }
            
            .success-message {
                font-size: 18px;
                margin-bottom: 25px;
                line-height: 1.5;
            }
            
            .order-details {
                border-top: 2px solid #eee;
                padding-top: 20px;
                margin-top: 20px;
                text-align: left;
            }
            
            .order-summary {
                border: 1px solid #ddd;
                border-radius: 8px;
                padding: 20px;
                margin: 20px 0;
                background-color: #f9f9f9;
            }
            
            .order-info {
                display: flex;
                justify-content: space-between;
                margin-bottom: 10px;
            }
            
            .product-list {
                margin: 20px 0;
                border-collapse: collapse;
                width: 100%;
            }
            
            .product-list th, .product-list td {
                padding: 12px;
                border-bottom: 1px solid #ddd;
                text-align: left;
            }
            
            .product-list th {
                background-color: #f2f2f2;
                font-weight: 600;
            }
            
            .total-price {
                font-size: 18px;
                font-weight: bold;
                margin-top: 20px;
                text-align: right;
            }
            
            .btn {
                display: inline-block;
                padding: 12px 24px;
                margin: 15px 5px;
                background-color: #4CAF50;
                color: white;
                border-radius: 5px;
                text-decoration: none;
                font-weight: bold;
                transition: background-color 0.3s;
            }
            
            .btn:hover {
                background-color: #45a049;
            }
            
            .btn-secondary {
                background-color: #6c757d;
            }
            
            .btn-secondary:hover {
                background-color: #5a6268;
            }
            
            .product-image {
                width: 50px;
                height: 50px;
                object-fit: cover;
                border-radius: 5px;
            }
        </style>
    </head>
    <body>
        <div class="success-container">
            <div class="success-icon">
                <i class="fas fa-check-circle"></i>
            </div>
            <h1 class="success-title">Order Successful!</h1>
            <p class="success-message">
                Thank you for your purchase. Your order has been received and is being processed.
                <br>You will receive an email confirmation shortly.
            </p>
            
            <div class="order-summary">
                <div class="order-info">
                    <span><strong>Order ID:</strong></span>
                    <span>#${sessionScope.orderId}</span>
                </div>
                <div class="order-info">
                    <span><strong>Order Date:</strong></span>
                    <span><fmt:formatDate value="<%= new java.util.Date() %>" pattern="dd-MM-yyyy HH:mm:ss" /></span>
                </div>
                <div class="order-info">
                    <span><strong>Name:</strong></span>
                    <span>${sessionScope.contactInfo.fullName}</span>
                </div>
                <div class="order-info">
                    <span><strong>Email:</strong></span>
                    <span>${sessionScope.contactInfo.email}</span>
                </div>
                <div class="order-info">
                    <span><strong>Phone:</strong></span>
                    <span>${sessionScope.contactInfo.phone}</span>
                </div>
                <div class="order-info">
                    <span><strong>Delivery Address:</strong></span>
                    <span>${sessionScope.contactInfo.address}</span>
                </div>
                <c:if test="${not empty sessionScope.contactInfo.notes}">
                    <div class="order-info">
                        <span><strong>Notes:</strong></span>
                        <span>${sessionScope.contactInfo.notes}</span>
                    </div>
                </c:if>
            </div>
            
            <div class="order-details">
                <h3>Order Items</h3>
                <table class="product-list">
                    <thead>
                        <tr>
                            <th>Product</th>
                            <th>Image</th>
                            <th>Price</th>
                            <th>Quantity</th>
                            <th>Total</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="item" items="${sessionScope.orderDetails}">
                            <tr>
                                <td>${item.productName}</td>
                                <td>
                                    <c:if test="${not empty item.imageUrl}">
                                        <img src="${item.imageUrl}" alt="${item.productName}" class="product-image">
                                    </c:if>
                                    <c:if test="${empty item.imageUrl}">
                                        <div class="no-image">No Image</div>
                                    </c:if>
                                </td>
                                <td><fmt:formatNumber value="${item.price}" type="number"/> VND</td>
                                <td>${item.quantity}</td>
                                <td><fmt:formatNumber value="${item.totalPrice}" type="number"/> VND</td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
                
                <div class="total-price">
                    <p>Total: <fmt:formatNumber value="${sessionScope.orderAmount}" type="number"/> VND</p>
                </div>
            </div>
            
            <div class="action-buttons">
                <a href="${pageContext.request.contextPath}/homecontroller" class="btn">Continue Shopping</a>
                <a href="${pageContext.request.contextPath}/myordercontroller" class="btn btn-secondary">My Orders</a>
            </div>
        </div>
    </body>
</html>

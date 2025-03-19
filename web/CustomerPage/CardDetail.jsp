<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <title>Cart Detail</title>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
        <link rel="stylesheet" href="styles.css">
        <style>
            /* Tổng thể */
            body {
                font-family: Arial, sans-serif;
                background: #f4f4f4;
                margin: 0;
                padding: 0;
                display: flex;
                justify-content: center;
                align-items: center;
                min-height: 100vh;
            }

            /* Container chính */
            .cart-container {
                width: 80%;
                background: #fff;
                padding: 20px;
                border-radius: 10px;
                box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            }

            /* Header */
            header {
                display: flex;
                justify-content: space-between;
                align-items: center;
                padding-bottom: 20px;
                border-bottom: 2px solid #ddd;
            }

            /* Nút chung */
            .btn {
                background: #007bff;
                color: white;
                padding: 8px 15px;
                border: none;
                border-radius: 5px;
                cursor: pointer;
            }

            .btn:hover {
                background: #0056b3;
            }

            /* Phân chia layout */
            .cart-wrapper {
                display: flex;
                gap: 20px;
            }

            /* Phần cart-content (chiếm 60%) */
            .cart-content {
                flex: 6;
                background: #f9f9f9;
                padding: 15px;
                border-radius: 8px;
            }

            /* Bảng sản phẩm */
            .cart-table {
                width: 100%;
                border-collapse: collapse;
                background: white;
            }

            .cart-table th, .cart-table td {
                padding: 10px;
                border-bottom: 1px solid #ddd;
                text-align: center;
            }

            .cart-table th {
                background: #007bff;
                color: white;
            }

            /* Phần discount-info (chiếm 40%) */
            .discount-info {
                flex: 4;
                background: #fffae6;
                padding: 15px;
                border-radius: 8px;
                text-align: center;
            }

            .discount-info h3 {
                color: #ff9800;
            }

            /* Danh sách giảm giá */
            .discount-info ul {
                list-style: none;
                padding: 0;
            }

            .discount-info li {
                padding: 8px;
                margin: 5px 0;
                border-radius: 5px;
            }

            .green {
                background: #c8e6c9;
            }
            .blue {
                background: #bbdefb;
            }
            .red {
                background: #ffcdd2;
            }

            /* Phân trang */
            .pagination {
                display: flex;
                justify-content: center;
                margin-top: 10px;
            }

            .pagination button {
                background: #ddd;
                border: none;
                padding: 5px 10px;
                margin: 0 5px;
                cursor: pointer;
            }

            .pagination .active {
                background: #007bff;
                color: white;
            }
        </style>
    </head>
    <body>

        <div class="cart-container">
            <header>
                <div class="logo">Page Logo</div>
                <button class="btn">Card Detail</button>
                <input type="text" class="search-box" placeholder="Search...">
                <button class="btn">Search</button>
            </header>

            <div class="cart-wrapper">
                <!-- Phần giỏ hàng (6 phần) -->
                <div class="cart-content">
                    <table class="cart-table">
                        <thead>
                            <tr>
                                <th><input type="checkbox"></th>
                                <th>ID</th>
                                <th>Product Name</th>
                                <th>Price</th>
                                <th>Qty</th>
                                <th>Total</th>
                                <th>Action</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="item" items="${cartItems}">
                                <tr>
                                    <td><input type="checkbox"></td>
                                    <td>${item.productId}</td>
                                    <td>${item.productName}</td>
                                    <td>${item.price} VND</td>
                                    <td>
                                        <input type="number" value="${item.quantity}" min="1">
                                    </td>
                                    <td>${item.totalPrice} VND</td>
                                    <td><button class="remove-btn">❌</button></td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>

                    <div class="pagination">
                        <button>&lt;</button>
                        <button class="active">1</button>
                        <button>2</button>
                        <button>...</button>
                        <button>&gt;</button>
                    </div>

                    <div class="promo-section">
                        🎁 Promotion Code: <input type="text" id="promo-code"> 
                        <button class="btn">✅ Apply</button>
                    </div>

                    <div class="price-summary">
                        🏷️ Before Price: <b>${beforePrice} VND</b>
                        <br>
                        🏷️ Final Total Price: <b>${finalPrice} VND</b>
                    </div>

                    <button class="btn">➕ Choose More Products</button>
                    <button class="btn checkout-btn">✅ Check Out</button>
                </div>

                <!-- Phần giảm giá (4 phần) -->
                <div class="discount-info">
                    <h3>DISCOUNTS FOR PURCHASES</h3>
                    <ul>
                        <li class="green">🟢 From 10 million VND → 5% Discount</li>
                        <li class="blue">🔵 From 50 million VND → 7% Discount ✅ (You are here!)</li>
                        <li class="red">🔴 From 100 million VND → 10% Discount (Add ${remainingForNextDiscount} VND more to reach! 🚀)</li>
                    </ul>
                </div>
            </div>
        </div>

    </body>
</html>

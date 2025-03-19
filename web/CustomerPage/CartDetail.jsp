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

            .cart-container {
                width: 80%;
                background: #fff;
                padding: 20px;
                border-radius: 10px;
                box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            }

            header {
                display: flex;
                justify-content: space-between;
                align-items: center;
                padding-bottom: 20px;
                border-bottom: 2px solid #ddd;
            }

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

            .cart-wrapper {
                display: flex;
                gap: 20px;
            }

            .cart-content {
                flex: 6;
                background: #f9f9f9;
                padding: 15px;
                border-radius: 8px;
            }

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
            .btn-back {
                display: inline-block;
                background-color: #28a745; /* Màu xanh lá cây */
                color: white;
                padding: 10px 20px;
                text-decoration: none;
                border-radius: 5px;
                font-size: 16px;
                font-weight: bold;
                border: none;
                cursor: pointer;
                margin-bottom: 15px;
            }

            .btn-back:hover {
                background-color: #218838; /* Màu xanh đậm khi hover */
            }
        </style>
    </head>
    <body>

        <div class="cart-container">
            <header>
                <button onclick="window.location.href = 'homecontroller'" class="btn btn-back">
                    ⬅ Back to Home
                </button>
                <div class="logo">Page Logo</div>
                <button class="btn">Cart Detail</button>
                <input type="text" class="search-box" placeholder="Search...">
                <button class="btn">Search</button>
            </header>
            <%
String message = (String) session.getAttribute("message");
if (message != null) {
%>
    <div id="deleteAlert" class="alert alert-success" style="position: fixed; top: 20px; left: 50%; transform: translateX(-50%); background-color: #28a745; color: white; padding: 15px; border-radius: 5px; z-index: 1000;">
        <strong>Thông báo:</strong> <%= message %>
        <button type="button" onclick="this.parentElement.style.display = 'none';" style="background: none; border: none; color: white; font-size: 18px; margin-left: 10px;">✖</button>
    </div>

    <script>
        setTimeout(function () {
            var alertBox = document.getElementById('deleteAlert');
            if (alertBox) {
                alertBox.style.display = 'none';
            }
        }, 3000);
    </script>
<%
    session.removeAttribute("message"); // Xóa thông báo sau khi hiển thị
}
%>
            <div class="cart-wrapper">
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
                            <c:choose>
                                <c:when test="${empty cartDetails}">
                                    <tr>
                                        <td colspan="7" style="text-align:center; color:red;">Your cart is empty!</td>
                                    </tr>
                                </c:when>
                                <c:otherwise>
                                    <c:forEach var="item" items="${cartDetails}">
                                        <tr>
                                            <td><input type="checkbox"></td>
                                            <td>${item.productId}</td>
                                            <td>${item.productName}</td>
                                            <td>${item.price} VND</td>
                                            <td>
                                                <form action="cartdetailcontroller" method="post">
                                                    <input type="hidden" name="productId" value="${item.productId}">

                                                    <button type="submit" name="action" value="decrease">-</button>
                                                    <input type="number" name="quantity" value="${item.quantity}" min="1" readonly>
                                                    <button type="submit" name="action" value="increase">+</button>

                                                    <input type="hidden" name="cartId" value="${item.cartId}">
                                                </form>
                                            </td>
                                            <td>${item.totalPrice} VND</td>
                                            <td>
                                                <form action="cartdetailcontroller" method="post">
                                                    <input type="hidden" name="action" value="remove">
                                                    <input type="hidden" name="productId" value="${item.productId}">
                                                    <input type="hidden" name="cartId" value="${item.cartId}">
                                                    <input type="hidden" name="quantity" value="${item.quantity}">
                                                    <input type="hidden" name="cartItemId" value="${item.cartItemId}">
                                                    <button type="submit" class="remove-btn">❌</button>
                                                </form>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </c:otherwise>
                            </c:choose>
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
                        🏷️ Before Price: <b><c:out value="${beforePrice}" default="0"/> VND</b>
                        <br>
                        🏷️ Final Total Price: <b><c:out value="${finalPrice}" default="0"/> VND</b>
                    </div>

                    <button class="btn">➕ Choose More Products</button>
                    <button class="btn checkout-btn" onclick="window.location.href = '/checkout'">✅ Check Out</button>
                </div>

                <div class="discount-info">
                    <h3>DISCOUNTS FOR PURCHASES</h3>
                    <ul>
                        <li class="green">🟢 From 10 million VND → 5% Discount</li>
                        <li class="blue">🔵 From 50 million VND → 7% Discount ✅ (You are here!)</li>
                        <li class="red">🔴 From 100 million VND → 10% Discount (Add <c:out value="${remainingForNextDiscount}" default="0"/> VND more to reach! 🚀)</li>
                    </ul>
                </div>
            </div>
        </div>

    </body>
</html>

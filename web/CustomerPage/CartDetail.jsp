<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="java.util.List, java.util.ArrayList" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
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
            .update-cart-btn {
                background-color: #ff6600; /* Màu cam nổi bật */
                color: white;
                font-size: 18px;
                font-weight: bold;
                padding: 12px 20px;
                border: none;
                border-radius: 8px;
                cursor: pointer;
                display: block;
                margin: 20px auto; /* Tạo khoảng trắng phía trên & căn giữa */
                box-shadow: 0px 4px 8px rgba(0, 0, 0, 0.2);
                transition: background 0.3s ease, transform 0.2s ease;
            }

            .update-cart-btn:hover {
                background-color: #e65c00; /* Màu cam đậm hơn khi hover */
                transform: scale(1.05);
            }

            .update-cart-btn:active {
                transform: scale(0.98);
            }

            /* Thêm chú thích phía trên nút */
            .update-cart-reminder {
                text-align: center;
                font-size: 14px;
                color: #666;
                margin-bottom: 10px;
                font-style: italic;
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
                    <form action="cartdetailcontroller" method="post" id="cartUpdateForm">
                        <table class="cart-table">
                            <thead>
                                <tr>
                                    <th><input type="checkbox" id="selectAll" onclick="toggleCheckboxes(this)"><label for="selectAll"> Choose All!!!</label></th>
                                    <th>ID</th>
                                    <th>Product Name</th>
                                    <th>Price</th>
                                    <th>Discount Price</th>
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
                                                <td>
                                                    <input type="checkbox" name="selectedProducts" value="${item.productId}" 
                                                           ${selectedProducts.contains(item.productId) ? "checked" : ""}>
                                                </td>
                                                <td>${item.productId}</td>
                                                <td>${item.productName}</td>
                                                <td><fmt:formatNumber value="${item.price}" type="number" groupingUsed="true"/> VND</td>
                                                <td>
                                                    <c:choose>
                                                        <c:when test="${item.discountPrice == 0.0}">
                                                            Not discounted yet ❌💰
                                                        </c:when>
                                                        <c:otherwise>
                                                            <fmt:formatNumber value="${item.discountPrice}" type="number" groupingUsed="true"/> VND
                                                        </c:otherwise>
                                                    </c:choose>
                                                </td>
                                                <td>
                                                    <form action="cartdetailcontroller" method="post">
                                                        <input type="hidden" name="productId" value="${item.productId}">

                                                        <div style="display: flex; align-items: center; gap: 5px;">
                                                            <button type="submit" name="action" value="decrease">-</button>
                                                            <input type="number" name="quantity" value="${item.quantity}" min="1" readonly 
                                                                   style="width: 40px; text-align: center; font-size: 14px; padding: 2px;">
                                                            <button type="submit" name="action" value="increase">+</button>
                                                        </div>

                                                        <input type="hidden" name="cartId" value="${item.cartId}">
                                                    </form>
                                                </td>
                                                <td><fmt:formatNumber value="${item.totalPrice}" type="number" groupingUsed="true"/> VND</td>

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
                        <input type="hidden" name="action" value="updateSelection">
                        <div class="update-cart-container">
                            <p class="update-cart-reminder">⚠️ Remember to update your cart to apply the latest discounts!</p>
                            <button type="submit" class="update-cart-btn">Refresh Cart Prices</button>
                        </div>
                    </form>
                    
                    <div class="pagination">
                        <button>&lt;</button>
                        <button class="active">1</button>
                        <button>2</button>
                        <button>...</button>
                        <button>&gt;</button>
                    </div>

                    <div class="promo-section" style="margin-top: 20px;">
                        🎁 Promotion Code: <input type="text" id="promo-code"> 
                        <button class="btn">✅ Apply</button>
                    </div>

                    <div class="price-summary">
                        <br>
                        <%-- Hiển thị tổng giá trước giảm giá --%>
                        💰 <b>Total Before Discount:</b> <fmt:formatNumber value="${beforePrice}" type="number" groupingUsed="true" /> VND

                        <%-- Hiển thị mức giảm giá nếu có --%>
                        <c:if test="${discountRate > 0}">
                            <br>🎯 <b>You have reached a discount level!</b>
                            <br> Your total is over 
                            <c:choose>
                                <c:when test="${beforePrice > 100000000}">100 million VND</c:when>
                                <c:when test="${beforePrice > 50000000}">50 million VND</c:when>
                                <c:when test="${beforePrice > 10000000}">10 million VND</c:when>
                            </c:choose>
                            → <b><fmt:formatNumber value="${discountRate}" type="number" maxFractionDigits="2"/>% Discount</b>

                            <br>💰 Discount Applied: | -<fmt:formatNumber value="${discountAmount}" type="number" groupingUsed="true"/> VND
                        </c:if>

                        <br>
                        🏷️ <b>Final Total Price:</b> <fmt:formatNumber value="${finalPrice}" type="number" groupingUsed="true"/> VND
                    </div>

                    <button class="btn" onclick="window.location.href='homecontroller'">➕ Choose More Products</button>
                </div>

                <div class="discount-info">
                    <h3>DISCOUNTS FOR PURCHASES</h3>
                    <ul>
                        <li class="green">
                            🟢 From 10 million VND → 5% Discount 
                            <c:if test="${beforePrice >= 10000000 && beforePrice < 50000000}">✅ (You are here!)</c:if>
                            <c:if test="${beforePrice < 10000000}">
                                (Add <fmt:formatNumber value="${10000000 - beforePrice}" type="number" groupingUsed="true"/> VND more to reach! 🚀)
                            </c:if>
                        </li>
                        <li class="blue">
                            🔵 From 50 million VND → 7% Discount  
                            <c:if test="${beforePrice >= 50000000 && beforePrice < 100000000}">✅ (You are here!)</c:if>
                            <c:if test="${beforePrice < 50000000}">
                                (Add <fmt:formatNumber value="${50000000 - beforePrice}" type="number" groupingUsed="true"/> VND more to reach! 🚀)
                            </c:if>
                        </li>
                        <li class="red">
                            🔴 From 100 million VND → 10% Discount  
                            <c:if test="${beforePrice >= 100000000}">✅ (You are here!)</c:if>
                            <c:if test="${beforePrice < 100000000}">
                                (Add <fmt:formatNumber value="${100000000 - beforePrice}" type="number" groupingUsed="true"/> VND more to reach! 🚀)
                            </c:if>
                        </li>
                    </ul>
                </div>


            </div>
            
            <!-- Place checkout form completely outside cart-wrapper -->
            <div style="margin-top: 20px; text-align: center;">
                <form id="checkoutForm" action="cartcontactcontroller" method="get" style="display: inline-block; width: 50%;" onsubmit="return prepareCheckout()">
                    <!-- The selected products will be added by JavaScript before form submission -->
                    <input type="hidden" name="beforePrice" value="${beforePrice}">
                    <input type="hidden" name="discountRate" value="${discountRate}">
                    <input type="hidden" name="discountAmount" value="${discountAmount}">
                    <input type="hidden" name="finalPrice" value="${finalPrice}">
                    <button type="submit" class="btn" style="background-color: #28a745; padding: 12px 25px; font-size: 18px; width: 100%;">
                        ✅ Proceed to Checkout
                    </button>
                </form>
            </div>
        </div>

    </body>
</html>
<script>
    function toggleCheckboxes(source) {
        let checkboxes = document.getElementsByName("selectedProducts");
        for (let i = 0; i < checkboxes.length; i++) {
            checkboxes[i].checked = source.checked;
        }
    }
    
    // Function to prepare the checkout form before submission
    function prepareCheckout() {
        // Get all selected product checkboxes
        const checkboxes = document.querySelectorAll('input[name="selectedProducts"]:checked');
        
        if (checkboxes.length === 0) {
            alert('Please select at least one product before checkout');
            return false;
        }
        
        // Get the checkout form
        const form = document.getElementById('checkoutForm');
        
        // Remove any existing selectedProducts inputs to avoid duplicates
        const existingInputs = form.querySelectorAll('input[name="selectedProducts"]');
        existingInputs.forEach(input => input.remove());
        
        // Create hidden inputs for each selected product
        checkboxes.forEach(checkbox => {
            const input = document.createElement('input');
            input.type = 'hidden';
            input.name = 'selectedProducts';
            input.value = checkbox.value;
            form.appendChild(input);
        });
        
        console.log('Form submitting to:', form.action);
        console.log('Selected products:', Array.from(checkboxes).map(cb => cb.value));
        
        return true;
    }
</script>

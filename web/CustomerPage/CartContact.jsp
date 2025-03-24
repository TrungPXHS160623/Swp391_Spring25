<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="java.util.List, java.util.ArrayList" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <title>Checkout - Customer Information</title>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
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
                margin: 20px auto;
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
                transition: background-color 0.3s;
            }

            .btn:hover {
                background: #0056b3;
            }

            .cart-wrapper {
                display: flex;
                gap: 20px;
                margin-top: 20px;
                flex-direction: column; /* Stack components vertically on smaller screens */
            }

            @media (min-width: 992px) {
                .cart-wrapper {
                    flex-direction: row; /* Side by side on larger screens */
                }
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
                margin-bottom: 20px;
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

            .customer-info {
                flex: 4;
                background: #f0f8ff;
                padding: 15px;
                border-radius: 8px;
            }

            .customer-info h3 {
                color: #0056b3;
                text-align: center;
                margin-bottom: 20px;
                border-bottom: 2px solid #0056b3;
                padding-bottom: 10px;
            }

            .form-group {
                margin-bottom: 15px;
            }

            .form-group label {
                display: block;
                margin-bottom: 5px;
                font-weight: bold;
                color: #333;
            }

            .form-control {
                width: 100%;
                padding: 8px;
                border: 1px solid #ccc;
                border-radius: 4px;
                box-sizing: border-box;
            }

            .form-control:focus {
                border-color: #0056b3;
                outline: none;
                box-shadow: 0 0 5px rgba(0, 86, 179, 0.5);
            }

            textarea.form-control {
                resize: vertical;
                min-height: 100px;
            }

            .btn-back {
                display: inline-block;
                background-color: #28a745;
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
                background-color: #218838;
            }

            .price-summary {
                background: #f0f0f0;
                padding: 10px 15px;
                border-radius: 5px;
                margin: 15px 0;
                line-height: 1.6;
            }

            .required {
                color: red;
            }

            .submit-order-btn {
                background: #28a745;
                color: white;
                font-size: 18px;
                padding: 12px 20px;
                border: none;
                border-radius: 5px;
                cursor: pointer;
                width: 100%;
                margin-top: 15px;
                transition: background-color 0.3s;
            }

            .submit-order-btn:hover {
                background: #218838;
            }

            .radio-group {
                display: flex;
                gap: 15px;
            }

            .radio-group label {
                display: flex;
                align-items: center;
                cursor: pointer;
            }

            .radio-group input[type="radio"] {
                margin-right: 5px;
            }

            /* Error message styling */
            .error-message {
                color: #dc3545;
                font-size: 14px;
                margin-top: 5px;
            }

            /* Product thumbnail */
            .product-thumbnail {
                width: 50px;
                height: 50px;
                object-fit: cover;
                border-radius: 4px;
            }
            
            .checkout-steps {
                display: flex;
                justify-content: space-between;
                margin-bottom: 20px;
                padding: 10px;
                background-color: #f8f9fa;
                border-radius: 5px;
            }
            
            .step {
                flex: 1;
                text-align: center;
                padding: 10px;
                position: relative;
            }
            
            .step.active {
                font-weight: bold;
                color: #007bff;
            }
            
            .step.completed {
                color: #28a745;
            }
            
            .step:not(:last-child):after {
                content: '';
                position: absolute;
                top: 50%;
                right: 0;
                width: 20px;
                height: 2px;
                background-color: #ddd;
                transform: translateX(50%);
            }
        </style>
    </head>
    <body>
        <div class="cart-container">
            <header>
                <button onclick="window.location.href = 'cartdetailcontroller'" class="btn btn-back">
                    ⬅ Back to Cart
                </button>
                <div class="logo">Page Logo</div>
                <button class="btn">Checkout</button>
                <input type="text" class="search-box" placeholder="Search...">
                <button class="btn">Search</button>
            </header>
           
            <%
                String error = (String) request.getAttribute("error");
                if (error != null) {
            %>
            <div id="errorAlert" class="alert alert-danger" style="position: fixed; top: 20px; left: 50%; transform: translateX(-50%); background-color: #dc3545; color: white; padding: 15px; border-radius: 5px; z-index: 1000;">
                <strong>Error:</strong> <%= error %>
                <button type="button" onclick="this.parentElement.style.display = 'none';" style="background: none; border: none; color: white; font-size: 18px; margin-left: 10px;">✖</button>
            </div>

            <script>
                setTimeout(function () {
                    var alertBox = document.getElementById('errorAlert');
                    if (alertBox) {
                        alertBox.style.display = 'none';
                    }
                }, 3000);
            </script>
            <% } %>
            
            <div class="cart-wrapper">
                <div class="cart-content">
                    <h3>Selected Products</h3>
                    <table class="cart-table">
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Image</th>
                                <th>Product Name</th>
                                <th>Price</th>
                                <th>Discount Price</th>
                                <th>Qty</th>
                                <th>Total</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:choose>
                                <c:when test="${empty cartDetails}">
                                    <tr>
                                        <td colspan="7" style="text-align:center; color:red;">No products selected!</td>
                                    </tr>
                                </c:when>
                                <c:otherwise>
                                    <c:forEach var="item" items="${cartDetails}">
                                        <tr>
                                            <td>${item.productId}</td>
                                            <td>
                                                <c:if test="${not empty item.imageUrl}">
                                                    <img src="${item.imageUrl}" alt="${item.productName}" class="product-thumbnail">
                                                </c:if>
                                                <c:if test="${empty item.imageUrl}">
                                                    <div style="width: 50px; height: 50px; background: #eee; display: flex; align-items: center; justify-content: center; border-radius: 4px;">
                                                        <i class="fas fa-image"></i>
                                                    </div>
                                                </c:if>
                                            </td>
                                            <td>${item.productName}</td>
                                            <td><fmt:formatNumber value="${item.price}" type="number" groupingUsed="true"/> VND</td>
                                            <td>
                                                <c:choose>
                                                    <c:when test="${item.discountPrice == 0.0}">
                                                        <span style="color: #999;">No discount</span>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <fmt:formatNumber value="${item.discountPrice}" type="number" groupingUsed="true"/> VND
                                                    </c:otherwise>
                                                </c:choose>
                                            </td>
                                            <td>${item.quantity}</td>
                                            <td><fmt:formatNumber value="${item.totalPrice}" type="number" groupingUsed="true"/> VND</td>
                                        </tr>
                                    </c:forEach>
                                </c:otherwise>
                            </c:choose>
                        </tbody>
                    </table>

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
                </div>

                <div class="customer-info">
                    <h3>CUSTOMER INFORMATION</h3>
                    <form action="cartcontactcontroller" method="post" id="checkoutForm" onsubmit="return validateForm()">
                        <div class="form-group">
                            <label for="fullName">Full Name <span class="required">*</span></label>
                            <input type="text" id="fullName" name="fullName" class="form-control" required
                                   value="${contactInfo.fullName}" placeholder="Enter your full name">
                            <div id="fullNameError" class="error-message"></div>
                        </div>
                        
                        <div class="form-group">
                            <label>Gender <span class="required">*</span></label>
                            <div class="radio-group">
                                <label>
                                    <input type="radio" name="gender" value="Male" ${contactInfo.gender eq 'Male' ? 'checked' : ''} required> Male
                                </label>
                                <label>
                                    <input type="radio" name="gender" value="Female" ${contactInfo.gender eq 'Female' ? 'checked' : ''}> Female
                                </label>
                                <label>
                                    <input type="radio" name="gender" value="Other" ${contactInfo.gender eq 'Other' ? 'checked' : ''}> Other
                                </label>
                            </div>
                            <div id="genderError" class="error-message"></div>
                        </div>
                        
                        <div class="form-group">
                            <label for="email">Email <span class="required">*</span></label>
                            <input type="email" id="email" name="email" class="form-control" required
                                   value="${contactInfo.email}" placeholder="example@email.com">
                            <div id="emailError" class="error-message"></div>
                        </div>
                        
                        <div class="form-group">
                            <label for="phone">Phone Number <span class="required">*</span></label>
                            <input type="tel" id="phone" name="phone" class="form-control" required
                                   value="${contactInfo.phone}" placeholder="Enter your phone number">
                            <div id="phoneError" class="error-message"></div>
                        </div>
                        
                        <div class="form-group">
                            <label for="address">Shipping Address <span class="required">*</span></label>
                            <textarea id="address" name="address" class="form-control" required
                                      placeholder="Enter your complete shipping address">${contactInfo.address}</textarea>
                            <div id="addressError" class="error-message"></div>
                        </div>
                        
                        <div class="form-group">
                            <label for="notes">Order Notes</label>
                            <textarea id="notes" name="notes" class="form-control"
                                      placeholder="Special instructions for delivery (optional)">${contactInfo.notes}</textarea>
                        </div>
                        
                        <%-- Hidden fields to pass selected products and price information --%>
                        <c:forEach var="prodId" items="${selectedProducts}">
                            <input type="hidden" name="selectedProducts" value="${prodId}">
                        </c:forEach>
                        <input type="hidden" name="beforePrice" value="${beforePrice}">
                        <input type="hidden" name="discountRate" value="${discountRate}">
                        <input type="hidden" name="discountAmount" value="${discountAmount}">
                        <input type="hidden" name="finalPrice" value="${finalPrice}">
                        
                        <button type="submit" class="submit-order-btn">✅ Place Order</button>
                    </form>
                </div>
            </div>
        </div>

        <script>
            function validateForm() {
                let isValid = true;
                
                // Reset error messages
                document.querySelectorAll('.error-message').forEach(el => {
                    el.textContent = '';
                });
                
                // Validate full name
                const fullName = document.getElementById('fullName').value.trim();
                if (!fullName) {
                    document.getElementById('fullNameError').textContent = 'Full name is required';
                    isValid = false;
                }
                
                // Validate gender
                const genders = document.getElementsByName('gender');
                let genderSelected = false;
                for (let i = 0; i < genders.length; i++) {
                    if (genders[i].checked) {
                        genderSelected = true;
                        break;
                    }
                }
                if (!genderSelected) {
                    document.getElementById('genderError').textContent = 'Please select a gender';
                    isValid = false;
                }
                
                // Validate email
                const email = document.getElementById('email').value.trim();
                if (!email) {
                    document.getElementById('emailError').textContent = 'Email is required';
                    isValid = false;
                } else {
                    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
                    if (!emailRegex.test(email)) {
                        document.getElementById('emailError').textContent = 'Please enter a valid email address';
                        isValid = false;
                    }
                }
                
                // Validate phone
                const phone = document.getElementById('phone').value.trim();
                if (!phone) {
                    document.getElementById('phoneError').textContent = 'Phone number is required';
                    isValid = false;
                } else {
                    const phoneRegex = /^[0-9]{10,12}$/;
                    if (!phoneRegex.test(phone.replace(/[\s-]/g, ''))) {
                        document.getElementById('phoneError').textContent = 'Please enter a valid phone number (10-12 digits)';
                        isValid = false;
                    }
                }
                
                // Validate address
                const address = document.getElementById('address').value.trim();
                if (!address) {
                    document.getElementById('addressError').textContent = 'Shipping address is required';
                    isValid = false;
                }
                
                return isValid;
            }
        </script>
    </body>
</html>

<%-- 
    Document   : ProductHomeDetail
    Created on : Mar 27, 2025, 5:58:30 PM
    Author     : LENOVO
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Product Detail</title>
        <!-- Bootstrap CSS -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <style>
            body {
                background-color: #f0f8ff;
                font-family: Arial, sans-serif;
            }
            .container-detail {
                margin-top: 30px;
            }
            .left-panel {
                background-color: #ffffff;
                padding: 20px;
                border-radius: 5px;
                box-shadow: 0 0 10px rgba(0,0,0,0.1);
            }
            .right-panel {
                background-color: #ffffff;
                padding: 20px;
                border-radius: 5px;
                box-shadow: 0 0 10px rgba(0,0,0,0.1);
                margin-top: 20px;
            }
            .thumbnail {
                width: 80px;  /* Chiều rộng cố định */
                height: 80px; /* Chiều cao cố định */
                object-fit: cover; /* Giữ tỷ lệ và cắt bớt phần thừa nếu cần */
            }
            .main-image {
                width: 300px;
                height: 300px;
                object-fit: cover;
            }
            .product-info h2 {
                margin-top: 0;
            }
            .btn-buy, .btn-add-cart {
                padding: 10px 20px;
                margin-right: 10px;
                border: none;
                border-radius: 5px;
                cursor: pointer;
            }
            .btn-buy {
                background-color: #ff9800;
                color: #fff;
            }
            .btn-add-cart {
                background-color: #4caf50;
                color: #fff;
            }
        </style>
        <script>
            // Hàm chuyển đổi ảnh khi nhấn thumbnail
            function showMainImage(url) {
                document.getElementById("mainImage").src = url;
            }
        </script>
    </head>
    <body>
        <div class="container container-detail">
            <c:if test="${not empty errorMessage}">
                <div class="alert alert-danger text-center">${errorMessage}</div>
            </c:if>
            <c:if test="${not empty productDetail}">
                <div class="row">
                    <!-- Left Panel: Thông tin sản phẩm -->
                    <div class="col-md-8 left-panel">
                        <!-- Ảnh chính -->
                        <img id="mainImage" class="main-image" 
                             src="<c:choose>
                                 <c:when test='${not empty productDetail.imageList and productDetail.imageList.size() > 0}'>
                                     ${productDetail.imageList[0].image_url}
                                 </c:when>
                                 <c:otherwise>
                                     default-thumbnail.jpg
                                 </c:otherwise>
                             </c:choose>" 
                             alt="${productDetail.productName}">

                        <!-- Danh sách ảnh (thumbnail) -->
                        <div class="mb-3">
                            <c:if test="${not empty productDetail.imageList}">
                                <c:forEach var="img" items="${productDetail.imageList}">
                                    <img src="${img.image_url}" alt="Image" class="thumbnail" onclick="showMainImage('${img.image_url}')">
                                </c:forEach>
                            </c:if>
                        </div>

                        <!-- Thông tin sản phẩm -->
                        <div class="product-info">
                            <h2>${productDetail.productName}</h2>
                            <p><strong>Category:</strong> ${productDetail.category}</p>
                            <p><strong>List Price:</strong> ${productDetail.price}</p>
                            <p><strong>Sale Price:</strong> ${productDetail.discountPrice}</p>
                            <p><strong>Status:</strong> ${productDetail.status}</p>
                            <p><strong>Description:</strong></p>
                            <p>${productDetail.description}</p>
                            <!-- Có thể thêm các nút Buy Now, Add to Cart nếu cần -->
                            <button class="btn-buy">Buy Now</button>
                            <button class="btn-add-cart">Add to Cart</button>
                        </div>
                    </div>
                    <!-- Right Panel: Thông tin bổ sung (ví dụ: related products, contact, policy) -->
                    <div class="col-md-4 right-panel">
                        <h4>Related Products</h4>
                        <ul>
                            <li>Product A</li>
                            <li>Product B</li>
                            <li>Product C</li>
                        </ul>
                        <hr>
                        <h4>Contact Us</h4>
                        <p>Email: support@example.com</p>
                        <p>Phone: 0123-456-789</p>
                        <hr>
                        <h4>Policy</h4>
                        <ul>
                            <li>Privacy Policy</li>
                            <li>Return Policy</li>
                            <li>Terms of Service</li>
                        </ul>
                    </div>
                </div>
            </c:if>
        </div>

        <!-- Bootstrap Bundle JS -->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>

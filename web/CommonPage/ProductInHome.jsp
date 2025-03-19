<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css">
        <link rel="stylesheet" href="css/all.min.css">
        <title>Product Section</title>
        <style>
            .product-section {
                text-align: center;
                padding: 20px;
            }
            .product-container {
                display: flex;
                justify-content: center;
                gap: 20px;
                flex-wrap: wrap;
            }

            .product-item {
                border: 1px solid #ccc;
                padding: 15px;
                text-align: center;
                width: 250px; /* Điều chỉnh độ rộng */
            }

            .product-image {
                width: 100%;  /* Hình ảnh to hơn, chiếm toàn bộ chiều ngang */
                max-height: 200px; /* Điều chỉnh kích thước ảnh */
                object-fit: contain; /* Giữ nguyên tỷ lệ ảnh */
            }

            .product-title {
                font-size: 16px;
                font-weight: bold;
                margin-top: 10px;
            }

            .product-details {
                margin-top: 10px;
                font-size: 14px;
            }

            .product-actions {
                margin-top: 10px;
            }

            .add-to-cart {
                background-color: green;
                color: white;
                border: none;
                padding: 8px 12px;
                cursor: pointer;
                margin-top: 5px;
            }

            .add-to-cart:hover {
                background-color: darkgreen;
            }
            .pagination {
                margin-top: 20px;
            }
            .pagination a {
                padding: 5px 10px;
                border: 1px solid #ccc;
                margin: 2px;
                text-decoration: none;
            }
            .pagination a.active {
                background-color: #000;
                color: #fff;
            }
            .product-rating {
                font-size: 20px; /* Kích thước sao */
                color: gold; /* Màu vàng */
                display: flex; /* Hiển thị sao trên cùng một hàng */
                gap: 2px; /* Khoảng cách giữa các sao */
                align-items: center; /* Căn giữa theo chiều dọc */
                justify-content: center;
            }
        </style>
    </head>
    <body>
        <div class="product-section">
            
            <div class="product-container">
                <c:forEach var="product" items="${productList}">
                    <div class="product-item">
                        <!-- Ảnh lớn hơn -->
                        <img src="${product.imageUrl}" alt="${product.productName}" class="product-image">

                        <!-- Title ngay bên dưới ảnh, cùng độ rộng -->
                        <div class="product-title">${product.productName}</div>

                        <!-- Giá và số lượng bán -->
                        <div class="product-details">
                            <span class="product-price">Price: $${product.price}</span>
                            <span class="product-quantity">Quantity Sold: ${product.soldQuantity}</span>
                        </div>
                        <div class="product-rating">
                            <c:forEach var="i" begin="1" end="${product.averageRating - (product.averageRating % 1)}">
                                <i class="fas fa-star" style="color: gold;"></i> 
                            </c:forEach>
                            <c:if test="${(product.averageRating % 1) >= 0.5}">
                                <i class="fas fa-star-half-alt" style="color: gold;"></i>
                            </c:if>
                        </div>

                        <!-- Giá giảm và nút thêm vào giỏ -->
                        <div class="product-actions">
                            <span class="product-discount">Discount Price: $${product.discountPrice}</span>
                            <button class="add-to-cart">Add To Cart</button>
                        </div>
                    </div>
                </c:forEach>
            </div>

            <!-- Pagination -->
            <div class="pagination">
                <c:if test="${currentPage > 1}">
                    <a href="?page=${currentPage - 1}">&lt; Prev</a>
                </c:if>

                <c:forEach var="i" begin="1" end="${totalPages}">
                    <a href="?page=${i}" class="${i == currentPage ? 'active' : ''}">${i}</a>
                </c:forEach>

                <c:if test="${currentPage < totalPages}">
                    <a href="?page=${currentPage + 1}">Next &gt;</a>
                </c:if>
            </div>
        </div>
    </body>
</html>

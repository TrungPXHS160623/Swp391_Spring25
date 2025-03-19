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
                font-size: 28px;
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
            .no-discount-label {
                font-size: 15px;
                font-weight: bold;
                font-style: italic;
                color: #000; /* Màu xám nhạt để tạo cảm giác "kém may mắn" */
                display: flex;
                align-items: center;
                gap: 10px;
                background: linear-gradient(to right, #888, #bbb); /* Gradient màu nhẹ */
                padding: 10px;
                border-radius: 10px;
                border: 1px solid #ccc;
                box-shadow: 2px 2px 5px rgba(0, 0, 0, 0.1);
                transition: transform 0.3s ease-in-out;
                
            }

            /* Hiệu ứng rung nhẹ khi hover */
            .no-discount-label:hover {
                transform: scale(1.05);
            }
            /* Giá gốc có gạch ngang và mờ đi khi có giảm giá */
            .original-price {
                text-decoration: line-through;
                color: gray;
                font-weight: normal;
            }


            /* Giá giảm nổi bật hơn */
            .highlight-discount {
                color: white;
                font-weight: bold;
                font-size: 1.5em;
                background: linear-gradient(to right, #ff416c, #ff4b2b);
                padding: 8px 15px;
                border-radius: 8px;
                display: inline-block;
                box-shadow: 0px 0px 8px rgba(255, 75, 43, 0.8);
                transition: transform 0.3s ease, box-shadow 0.3s ease;
            }

            /* Hiệu ứng khi di chuột vào giá giảm */
            .highlight-discount:hover {
                transform: scale(1.1);
                box-shadow: 0px 0px 15px rgba(255, 75, 43, 1);
            }

            /* Icon lửa và tag giảm giá */
            .highlight-discount i {
                margin-right: 5px;
            }
            /* Giá gốc nổi bật hơn nếu không có giảm giá */
            .highlighted-price {
                font-size: 1.8em;
                font-weight: bold;
                background: linear-gradient(45deg, #ffcc33, #ff6600);
                -webkit-background-clip: text;
                -webkit-text-fill-color: transparent;
                text-shadow: 0px 0px 8px rgba(255, 165, 0, 0.7);
                animation: glowEffect 1.5s infinite alternate;
            }

            /* Hiệu ứng phát sáng lung linh */
            @keyframes glowEffect {
                0% {
                    text-shadow: 0px 0px 5px rgba(255, 165, 0, 0.8);
                }
                100% {
                    text-shadow: 0px 0px 15px rgba(255, 69, 0, 1);
                }
            }

            /* Khi di chuột vào giá gốc, nó nhấp nháy nhẹ */
            .highlighted-price:hover {
                animation: flicker 0.2s infinite alternate;
            }

            @keyframes flicker {
                0% {
                    opacity: 1;
                }
                100% {
                    opacity: 0.7;
                }
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

                        <div class="product-rating">
                            <c:forEach var="i" begin="1" end="${product.averageRating - (product.averageRating % 1)}">
                                <i class="fas fa-star" style="color: gold;"></i> 
                            </c:forEach>
                            <c:if test="${(product.averageRating % 1) >= 0.5}">
                                <i class="fas fa-star-half-alt" style="color: gold;"></i>
                            </c:if>
                        </div>


                        <div class="product-details">
                            <c:choose>
                                <c:when test="${product.discountPrice > 0}">
                                    <!-- Giá gốc có gạch ngang và mờ đi -->
                                    <span class="product-price original-price">Price: $${product.price}</span>
                                    <!-- Giá giảm nổi bật -->
                                    <span class="product-discount highlight-discount">
                                        <i class="fas fa-fire"></i>Discount Price: $${product.discountPrice}
                                    </span>
                                </c:when>
                                <c:when test="${product.discountPrice == 0.0}">
                                    <!-- Giá gốc nổi bật hơn khi không có giảm giá -->
                                    <span class="product-price highlighted-price">Price: $${product.price}</span>
                                    </br>
                                    <span class="no-discount-label">
                                        No Discount Available 😢
                                    </span>
                                    </br>
                                </c:when>
                                <c:otherwise>
                                    <span class="product-price">Price: $${product.price}</span>
                                </c:otherwise>
                            </c:choose>
                            <span class="product-quantity">Quantity Sold: ${product.soldQuantity}</span>
                        </div>

                        <!-- Giá giảm và nút thêm vào giỏ -->
                        <div class="product-actions"> 
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

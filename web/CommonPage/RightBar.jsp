
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>

        <title>RightBar</title>
        <style>
            .right-bar {
                width: 330px;
                padding: 15px;
                background: #f8f9fa;
                border-radius: 8px;
                box-shadow: 2px 2px 10px rgba(0, 0, 0, 0.1);
                font-family: Arial, sans-serif;
            }

            .right-bar h3 {
                font-size: 16px;
                margin-bottom: 10px;
                color: #333;
                border-bottom: 2px solid #ddd;
                padding-bottom: 5px;
            }

            .product-card {
                background: white;
                padding: 10px;
                margin-bottom: 10px;
                border-radius: 5px;
                box-shadow: 1px 1px 5px rgba(0, 0, 0, 0.1);
                text-align: center;
            }

            .product-card img {
                width: 100%;
                height: auto;
                border-radius: 5px;
                margin-bottom: 5px;
            }

            .product-title {
                font-size: 14px;
                font-weight: bold;
                color: #007bff;
            }

            .product-info {
                font-size: 12px;
                color: #555;
            }

            .product-price {
                font-size: 26px;
                font-weight: bold;
                color: #d9534f;
            }

            .discount-price {
                font-size: 12px;
                color: #28a745;
            }

            .add-to-cart {
                background: #28a745;
                color: white;
                padding: 5px;
                border: none;
                border-radius: 3px;
                cursor: pointer;
                font-size: 12px;
                margin-top: 5px;
            }

            .add-to-cart:hover {
                background: #218838;
            }
        </style>
    </head>
    <body>
        <div class="right-bar">
            <h3>Sản phẩm mới nhất</h3>
            <c:if test="${not empty newestProduct}">
                <div class="product-card">
                    <img src="${newestProduct.imageUrl}" alt="${newestProduct.description}">
                    <div class="product-title">${newestProduct.productName}</div>
                    <div class="product-info">
    Giá: <span class="product-price">
        <fmt:formatNumber value="${newestProduct.price}" type="number" groupingUsed="true"/> VND
    </span>
</div>

                    <button class="add-to-cart">Thêm vào giỏ</button>
                </div>
            </c:if>

            <h3>Giảm giá sâu nhất</h3>
            <c:if test="${not empty bestDiscountedProduct}">
                <div class="product-card">
                    <img src="${bestDiscountedProduct.imageUrl}" alt="${bestDiscountedProduct.description}">
                    <div class="product-title">${bestDiscountedProduct.productName}</div>
                    <div class="product-info">
    Giá gốc: <span class="product-price">
        <fmt:formatNumber value="${bestDiscountedProduct.price}" type="number" groupingUsed="true"/> VND
    </span><br>
    Giá giảm: <span class="discount-price">
        <fmt:formatNumber value="${bestDiscountedProduct.discountPrice}" type="number" groupingUsed="true"/> VND
    </span>
</div>

                    <button class="add-to-cart">Thêm vào giỏ</button>
                </div>
            </c:if>

            <h3>Bán chạy nhất</h3>
            <c:if test="${not empty bestSellingProduct}">
                <div class="product-card">
                    <img src="${bestSellingProduct.imageUrl}" alt="${bestSellingProduct.description}">
                    <div class="product-title">${bestSellingProduct.productName}</div>
                    <div class="product-info">Đã bán: ${bestSellingProduct.soldQuantity} sản phẩm</div>
                    <button class="add-to-cart">Thêm vào giỏ</button>
                </div>
            </c:if>

            <h3>Được đánh giá cao nhất</h3>
            <c:if test="${not empty topRatedProduct}">
                <div class="product-card">
                    <img src="${topRatedProduct.imageUrl}" alt="${topRatedProduct.description}">
                    <div class="product-title">${topRatedProduct.productName}</div>
                    <div class="product-info">Đánh giá: ⭐${topRatedProduct.averageRating} / 5</div>
                    <button class="add-to-cart">Thêm vào giỏ</button>
                </div>
            </c:if>

        </div>
    </body>
</html>

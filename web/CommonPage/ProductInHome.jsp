<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Product Section</title>
    <style>
        .product-section { text-align: center; padding: 20px; }
        .product-container { display: grid; grid-template-columns: repeat(3, 1fr); gap: 15px; justify-content: center; }
        .product-item { border: 1px solid #ccc; padding: 10px; text-align: center; }
        .product-image { width: 100px; height: auto; }
        .pagination { margin-top: 20px; }
        .pagination a { padding: 5px 10px; border: 1px solid #ccc; margin: 2px; text-decoration: none; }
        .pagination a.active { background-color: #000; color: #fff; }
    </style>
</head>
<body>
    <div class="product-section">
        <h2>Product Section</h2>
        <div class="product-container">
            <c:forEach var="product" items="${productList}">
                <div class="product-item">
                    <img src="${product.imageUrl}" alt="${product.productName}" class="product-image">
                    <div class="product-title">${product.productName}</div>
                    <div class="product-price">Price: $${product.price}</div>
                    <div class="product-quantity">Quantity Sold: ${product.soldQuantity}</div>
                    <div class="product-discount">Discount Price: $${product.discountPrice}</div>
                    <button class="add-to-cart">Add To Cart</button>
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

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <style>
            .left-bar {
                width: 350px;
                padding: 15px;
                background: #f8f9fa;
                border-radius: 8px;
                box-shadow: 2px 2px 10px rgba(0, 0, 0, 0.1);
                font-family: Arial, sans-serif;
            }

            .left-bar h3 {
                font-size: 16px;
                margin-bottom: 10px;
                color: #333;
            }

            .left-bar input[type="text"],
            .left-bar button {
                width: 300px;
                padding: 8px;
                margin: 5px 0;
                border: 1px solid #ccc;
                border-radius: 5px;
            }

            .left-bar button {
                background: #28a745;
                color: white;
                cursor: pointer;
                font-weight: bold;
            }

            .left-bar button:hover {
                background: #218838;
            }
        </style>
    </head>
    <body>
        <div class="left-bar">
            <h3>Tìm kiếm</h3>
            <form action="<%= request.getContextPath() %>/filterproduct" method="GET">
                <input type="text" name="keyword" placeholder="Nhập tên sản phẩm...">
                <button type="submit">Tìm kiếm</button>

                <h3>Danh mục</h3>
                <c:forEach var="category" items="${listCate}">
                    <input type="checkbox" name="category" value="${category.categoryId}"> ${category.categoryName} <br>
                </c:forEach>

                <h3>Giá</h3>
                <input type="text" name="minPrice" placeholder="Từ..."> - 
                <input type="text" name="maxPrice" placeholder="Đến...">

                <h3>Đánh giá</h3>
                <input type="radio" name="rating" value="5"> ⭐⭐⭐⭐⭐<br>
                <input type="radio" name="rating" value="4"> ⭐⭐⭐⭐<br>
                <input type="radio" name="rating" value="3"> ⭐⭐⭐<br>
                <input type="radio" name="rating" value="2"> ⭐⭐<br>
                <input type="radio" name="rating" value="1"> ⭐<br>

                <h3>Trạng thái</h3>
                <input type="checkbox" name="status" value="available"> Còn hàng<br>
                <input type="checkbox" name="status" value="preorder"> Đặt trước<br>

                <h3>Giảm giá</h3>
                <input type="checkbox" name="discount" value="onsale"> Đang giảm giá<br>
                <input type="checkbox" name="discount" value="flashsale"> Flash Sale<br>

                <button type="submit">Áp dụng bộ lọc</button>
            </form>
        </div>
    </body>
</html>

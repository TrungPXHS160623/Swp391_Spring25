<%-- 
    Document   : LeftBar
    Created on : Mar 13, 2025, 2:02:13 PM
    Author     : Acer
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
       
        <!-- <title>LeftBar</title> -->
        <style>
            .left-bar {
                width: 250px;
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

            .left-bar input[type="text"] {
                width: 240px;
                
            }
            .left-bar button {
                width: 100%;
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

            .left-bar input[type="checkbox"],
            .left-bar input[type="radio"] {
                margin-right: 5px;
            }

            .left-bar form {
                margin-bottom: 15px;
            }

            .left-bar input[type="radio"] + label {
                display: inline-block;
                cursor: pointer;
            }
        </style>
    </head>
    <body>
        <div class="left-bar">
            <h3>Tìm kiếm</h3>
            <input type="text" placeholder="Search...">
            <button>Tìm kiếm</button>

            <h3>Danh mục</h3>
            <form>
                <c:forEach var = "category" items = "${listCate}">
                    <input type="checkbox" name="category" value="${category.categoryId}"> ${category.categoryName} <br>
                </c:forEach>
            </form>
            <h3>Giá</h3>
            <input type="text" placeholder="Từ..."> - 
            <input type="text" placeholder="Đến...">

            <h3>Đánh giá</h3>
            <form>
                <input type="radio" name="rating" value="5"> ⭐⭐⭐⭐⭐<br>
                <input type="radio" name="rating" value="4"> ⭐⭐⭐⭐<br>
                <input type="radio" name="rating" value="3"> ⭐⭐⭐<br>
                <input type="radio" name="rating" value="2"> ⭐⭐<br>
                <input type="radio" name="rating" value="1"> ⭐<br>
            </form>

            <h3>Trạng thái</h3>
            <form>
                <input type="checkbox" name="status" value="available"> Còn hàng<br>
                <input type="checkbox" name="status" value="preorder"> Đặt trước<br>
            </form>

            <h3>Giảm giá</h3>
            <form>
                <input type="checkbox" name="discount" value="onsale"> Đang giảm giá<br>
                <input type="checkbox" name="discount" value="flashsale"> Flash Sale<br>
            </form>

            <button>Áp dụng bộ lọc</button>
        </div>

    </body>
</html>

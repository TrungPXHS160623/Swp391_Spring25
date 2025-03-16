<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Category Section</title>
        <style>
            .category-container {
                width: 100%;
                max-width: 1300px;
                margin: auto;
                overflow: hidden;
                position: relative;
                border-radius: 10px;
                box-shadow: 0px 4px 10px rgba(0, 0, 0, 0.2);
            }

            .category-list {
                display: flex;
                white-space: nowrap;
                transition: transform 0.5s ease-in-out;
            }

            .category-item {
                flex: 0 0 calc(100% / 3);
                text-align: center;
                margin-right: 10px;
                border: 2px solid #000; /* Thêm border */
                padding: 10px; /* Thêm khoảng cách giữa nội dung và border */
                border-radius: 5px; /* Tùy chọn: bo tròn góc nếu bạn muốn */
            }

            .category-item img {
                width: 100%;
                border-radius: 10px;
                border-bottom: 2px solid #000; /* Border dưới ảnh */
            }
            .category-item:last-child {
                margin-right: 0;
            }

            .category-title {
                margin-top: 10px;
                font-size: 18px;
                font-weight: bold;
                color: #000;
                text-shadow: 2px 2px 5px rgba(150, 150, 150, 0.3);
                
            }

            .prev, .next {
                position: absolute;
                top: 50%;
                transform: translateY(-50%);
                background-color: rgba(0, 0, 0, 0.5);
                color: white;
                border: none;
                padding: 10px;
                cursor: pointer;
                border-radius: 5px;
            }

            .prev {
                left: 10px;
            }
            .next {
                right: 10px;
            }
        </style>
    </head>
    <body>
        <div class="category-container">
            <div class="category-list">
                <c:forEach var="category" items="${categories}">
                    <div class="category-item">
                        <img src="${category.imageUrl}" alt="Category Image">
                        <p class="category-title">${category.categoryName}</p>
                    </div>
                </c:forEach>
            </div>

            <button class="prev" onclick="prevCategory()">❮</button>
            <button class="next" onclick="nextCategory()">❯</button>
        </div>
    </body>
</html>
<script>
    document.addEventListener("DOMContentLoaded", function () {
        let currentIndex = 0;
        const categoryContainer = document.querySelector('.category-list');
        const categories = document.querySelectorAll('.category-item');
        const totalCategories = categories.length;
        const visibleCategories = 3;
        const categoryWidth = categories[0].offsetWidth;

        function updateCategorySlider() {
            categoryContainer.style.transform = `translateX(${-currentIndex * categoryWidth}px)`;
        }

        document.querySelector('.next').addEventListener("click", function () {
            if (currentIndex < totalCategories - visibleCategories) {
                currentIndex++;
                updateCategorySlider();
            }
        });

        document.querySelector('.prev').addEventListener("click", function () {
            if (currentIndex > 0) {
                currentIndex--;
                updateCategorySlider();
            }
        });

        updateCategorySlider();
    });
</script>

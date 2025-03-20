<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Blog Section</title>
        <style>
            .blog-container {
                width: 100%;
                max-width: 1300px;
                margin: auto;
                overflow: hidden;
                position: relative;
                border-radius: 10px;
                box-shadow: 0px 4px 10px rgba(0, 0, 0, 0.2);
            }

            .blog-list {
                display: flex;
                white-space: nowrap;
                transition: transform 0.5s ease-in-out;
            }

            .blog-item {
                flex: 0 0 calc(100% / 3);
                text-align: center;
                margin-right: 10px;
                border: 2px solid #000;
                padding: 10px;
                border-radius: 5px;
            }

            .blog-item img {
                width: 100%;
                border-radius: 10px;
                border-bottom: 2px solid #000;
            }
            
            .blog-title {
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
        <div class="blog-container">
            <h2>Blog Section</h2>
            <div class="blog-list">
                <c:forEach var="blog" items="${blogs}">
                    <div class="blog-item">
                        <img src="${blog.imageUrl}" alt="Blog Image">
                        <p class="blog-title">${blog.title}</p>
                    </div>
                </c:forEach>
            </div>

            <button class="prev" onclick="prevBlog()">❮</button>
            <button class="next" onclick="nextBlog()">❯</button>
        </div>
    </body>
</html>
<script>
    document.addEventListener("DOMContentLoaded", function () {
        let currentIndex = 0;
        const blogContainer = document.querySelector('.blog-list');
        const blogs = document.querySelectorAll('.blog-item');
        const totalBlogs = blogs.length;
        const visibleBlogs = 3;
        const blogWidth = blogs[0].offsetWidth;

        function updateBlogSlider() {
            blogContainer.style.transform = `translateX(${-currentIndex * blogWidth}px)`;
        }

        document.querySelector('.next').addEventListener("click", function () {
            if (currentIndex < totalBlogs - visibleBlogs) {
                currentIndex++;
                updateBlogSlider();
            }
        });

        document.querySelector('.prev').addEventListener("click", function () {
            if (currentIndex > 0) {
                currentIndex--;
                updateBlogSlider();
            }
        });

        updateBlogSlider();
    });
</script>

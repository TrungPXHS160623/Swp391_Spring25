<%-- 
    Document   : blogList
    Created on : Mar 16, 2025, 2:52:59 PM
    Author     : LENOVO
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Blog List</title>
        <style>
            /* Reset mặc định */
            * {
                margin: 0;
                padding: 0;
                box-sizing: border-box;
            }

            body {
                font-family: Arial, sans-serif;
                background-color: #f0f0f0;
            }

            /* Container tổng, chia 2 cột */
            .container {
                width: 1200px;
                margin: 0 auto;
                display: flex;
                padding: 20px 0;
            }

            /* Cột trái - Danh sách Blog */
            .left-content {
                flex: 3;
                margin-right: 20px;
            }

            /* Màu nền xanh cho vùng Blog List (theo ảnh mẫu) */
            .blog-list-wrapper {
                background-color: #62b0e8; /* xanh dương nhạt */
                padding: 15px;
                border-radius: 8px;
            }

            /* Một item blog */
            .blog-item {
                background-color: #fff;
                border-radius: 8px;
                margin-bottom: 15px;
                display: flex;
                overflow: hidden; /* bo gọn ảnh */
                box-shadow: 0 2px 5px rgba(0,0,0,0.1);
            }

            /* Ảnh đại diện blog */
            .blog-item img {
                width: 200px;
                height: auto;
                object-fit: cover;
            }

            /* Phần nội dung blog */
            .blog-text {
                padding: 15px;
                flex: 1;
            }

            .blog-text h3 {
                margin-bottom: 8px;
                font-size: 18px;
                color: #333;
            }
            .blog-text p {
                margin-bottom: 6px;
                color: #555;
            }
            .blog-text small {
                color: #666;
            }

            /* Cột phải - Sidebar */
            .right-sidebar {
                flex: 1;
                background-color: #8ff078; /* xanh lá nhạt */
                border-radius: 8px;
                padding: 15px;
                height: fit-content;
            }
            .sidebar-section {
                margin-bottom: 20px;
            }
            .sidebar-section h3 {
                margin-bottom: 10px;
                font-size: 18px;
                color: #333;
            }
            /* Search box */
            .search-box input[type="text"] {
                width: 100%;
                padding: 6px;
                border-radius: 4px;
                border: 1px solid #ccc;
            }
            .search-box button {
                margin-top: 8px;
                padding: 6px 12px;
                border: none;
                border-radius: 4px;
                background-color: #333;
                color: #fff;
                cursor: pointer;
            }
            .search-box button:hover {
                background-color: #555;
            }

            /* Category list */
            .category-list ul {
                list-style: none;
            }
            .category-list li {
                margin-bottom: 5px;
            }
            .category-list input[type="radio"] {
                margin-right: 5px;
            }

            /* Latest blog */
            .latest-blogs ul {
                list-style: none;
                padding-left: 10px;
            }
            .latest-blogs li {
                margin-bottom: 6px;
            }

            /* Footer link, contact */
            .contact-section {
                margin-top: 10px;
            }
            .contact-section a {
                color: #333;
                text-decoration: none;
            }
            .contact-section a:hover {
                text-decoration: underline;
            }
        </style>
    </head>
    <body>
        <div class="container">
            <!-- Cột trái: Danh sách Blog -->
            <div class="left-content">
                <div class="blog-list-wrapper">
                    <h2 style="color:#fff; margin-bottom:15px;">Blog List</h2>

                    <c:forEach var="post" items="${listBlogs}">
                        <div class="blog-item">
                            <!-- Ảnh đại diện -->
                            <img src="${post.coverMedia.mediaUrl}" alt="Blog Image" style="width: 150px; height: auto;"/>
                            <div class="blog-text">
                                <h3>
                                    <a href="${pageContext.request.contextPath}/BlogDetailController?postId=${post.postId}">
                                        <c:out value="${post.title}"/>
                                    </a>
                                </h3>
                                <p>Category: <c:out value="${post.category_name}"/></p>
                                <p><c:out value="${post.summary}"/></p>
                                <p><small>Day update: <c:out value="${post.updateAt}"/></small></p>
                            </div>
                        </div>
                    </c:forEach>

                    <!-- Phân trang (nếu cần) -->
                    <!-- Ví dụ:
                    <div style="text-align: center; margin-top: 15px;">
                        <a href="?page=1">1</a>
                        <a href="?page=2">2</a>
                        <a href="?page=3">3</a>
                        ...
                    </div>
                    -->
                </div>
            </div>

            <!-- Cột phải: Sidebar -->
            <div class="right-sidebar">
                <!-- Search blog -->
                <div class="sidebar-section search-box">
                    <h3>Search blog:</h3>
                    <form action="${pageContext.request.contextPath}/BlogController" method="get">
                        <input type="hidden" name="action" value="search"/>
                        <input type="text" name="keyword" placeholder="Enter title here..."/>
                        <button type="submit">Search</button>
                    </form>
                </div>

                <!-- Category blog -->
                <div class="sidebar-section category-list">
                    <h3>Category blog:</h3>
                    <form action="${pageContext.request.contextPath}/BlogController" method="get">
                        <input type="hidden" name="action" value="category"/>
                        <select name="category" style="width:100%; padding:5px;">
                            <!-- Duyệt qua list categories -->
                            <option value="">--All--</option>
                            <c:forEach var="cat" items="${categories}">
                                <option value="${cat}">${cat}</option>
                            </c:forEach>
                        </select>
                        <button type="submit" style="margin-top:8px;">Filter</button>
                    </form>
                </div>


                <!-- Latest blog -->
                <div class="sidebar-section latest-blogs">
                    <h3>Latest Blog:</h3>
                    <ul>
                        <c:forEach var="latest" items="${latestBlogs}">
                            <li>
                                <!-- Link tới detail blog -->
                                <a href="${pageContext.request.contextPath}/BlogDetailController?postId=${latest.postId}">
                                    <c:out value="${latest.title}"/>
                                </a>
                            </li>
                        </c:forEach>
                    </ul>
                </div>

                <!-- Contact / Static Links -->
                <div class="sidebar-section contact-section">
                    <h3>Contact / Static Links:</h3>
                    <p><a href="#">Contact</a> | <a href="#">Privacy Policy</a></p>
                </div>
            </div>
        </div>
    </body>
</html>

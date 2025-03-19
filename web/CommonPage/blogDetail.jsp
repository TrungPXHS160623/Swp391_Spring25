<%-- 
    Document   : blogDetail
    Created on : Mar 17, 2025, 2:25:42 PM
    Author     : LENOVO
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Blog Detail</title>
        <style>
            body {
                font-family: Arial, sans-serif;
                background-color: #f7f7f7;
                margin: 0;
                padding: 0;
            }
            .container {
                width: 1200px;
                margin: 30px auto;
                display: flex;
                gap: 20px;
            }
            /* Cột trái: Nội dung chi tiết blog */
            .left-content {
                flex: 3;
                background-color: #fff;
                padding: 20px;
                border-radius: 8px;
                box-shadow: 0 2px 5px rgba(0,0,0,0.1);
            }
            .blog-title {
                font-size: 28px;
                margin-bottom: 10px;
                color: #333;
            }
            .blog-meta {
                font-size: 14px;
                color: #777;
                margin-bottom: 20px;
            }
            .blog-content {
                font-size: 16px;
                color: #444;
                line-height: 1.8;
                margin-bottom: 30px;
            }
            .media-gallery {
                margin-top: 20px;
            }
            .media-gallery h3 {
                font-size: 20px;
                margin-bottom: 10px;
                color: #333;
            }
            .media-item {
                margin-bottom: 15px;
            }
            .media-item img, .media-item video {
                max-width: 100%;
                height: auto;
                display: block;
                margin-bottom: 5px;
            }
            .media-description {
                font-size: 14px;
                color: #555;
            }
            .back-link {
                margin-top: 20px;
            }
            .back-link a {
                text-decoration: none;
                color: #333;
                font-weight: bold;
            }
            .back-link a:hover {
                text-decoration: underline;
            }
            /* Cột phải: Sidebar (giống blog list) */
            .right-sidebar {
                flex: 1;
                background-color: #8ff078;
                border-radius: 8px;
                padding: 15px;
                height: fit-content;
            }
            .sidebar-section {
                margin-bottom: 20px;
            }
            .sidebar-section h3 {
                font-size: 18px;
                margin-bottom: 10px;
                color: #333;
            }
            .search-box input[type="text"],
            .category-list select {
                width: 100%;
                padding: 6px;
                border-radius: 4px;
                border: 1px solid #ccc;
            }
            .search-box button,
            .category-list button {
                margin-top: 8px;
                padding: 6px 12px;
                border: none;
                border-radius: 4px;
                background-color: #333;
                color: #fff;
                cursor: pointer;
            }
            .search-box button:hover,
            .category-list button:hover {
                background-color: #555;
            }
            .latest-blogs ul {
                list-style: none;
                padding-left: 10px;
            }
            .latest-blogs li {
                margin-bottom: 6px;
            }
            .latest-blogs a {
                text-decoration: none;
                color: #333;
            }
            .latest-blogs a:hover {
                text-decoration: underline;
            }
            .contact-section a {
                text-decoration: none;
                color: #333;
            }
            .contact-section a:hover {
                text-decoration: underline;
            }
        </style>
    </head>
    <body>
        <div class="container">
            <!-- Cột trái: Blog Detail -->
            <div class="left-content">
                <c:if test="${not empty postDetail}">
                    <div class="blog-title">${postDetail.title}</div>
                    <div class="blog-meta">
                        <span>Author: ${postDetail.user_name}</span> |
                        <span>Updated: ${postDetail.updateAt}</span> |
                        <span>Category: ${postDetail.category_name}</span>
                    </div>
                    <div class="blog-content">
                        ${postDetail.content}
                    </div>
                    <c:if test="${not empty postDetail.mediaList}">
                        <div class="media-gallery">
                            <h3>Media Gallery</h3>
                            <c:forEach var="media" items="${postDetail.mediaList}">
                                <div class="media-item">
                                    <c:choose>
                                        <c:when test="${media.mediaType eq 'image'}">
                                            <img src="${media.mediaUrl}" alt="Media Image" style="width:150px; height:auto;"/>
                                        </c:when>
                                        <c:when test="${media.mediaType eq 'video'}">
                                            <c:choose>
                                                <c:when test="${fn:contains(media.mediaUrl, 'youtu.be')}">
                                                    <iframe width="560" height="315" 
                                                            src="https://www.youtube.com/embed/${fn:substringAfter(media.mediaUrl, 'https://youtu.be/')}" 
                                                            frameborder="0" allowfullscreen>
                                                    </iframe>
                                                </c:when>
                                                <c:when test="${fn:contains(media.mediaUrl, 'watch?v=')}">
                                                    <iframe width="560" height="315" 
                                                            src="https://www.youtube.com/embed/${fn:substringAfter(media.mediaUrl, 'watch?v=')}" 
                                                            frameborder="0" allowfullscreen>
                                                    </iframe>
                                                </c:when>
                                                <c:otherwise>
                                                    <video controls style="width:560px; height:auto;">
                                                        <source src="${pageContext.request.contextPath}${media.mediaUrl}" type="video/mp4">
                                                        Your browser does not support the video tag.
                                                    </video>
                                                </c:otherwise>
                                            </c:choose>
                                        </c:when>
                                        <c:otherwise>
                                            <p>Unsupported media type</p>
                                        </c:otherwise>
                                    </c:choose>
                                    <div class="media-description">
                                        ${media.description}
                                    </div>
                                </div>
                            </c:forEach>
                        </div>
                    </c:if>
                    <div class="back-link">
                        <a href="${pageContext.request.contextPath}/BlogController">← Back to Blog List</a>
                    </div>
                </c:if>
            </div>

            <!-- Cột phải: Sidebar (sử dụng chung với blog list) -->
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

                <!-- Category filter (Combo Box) -->
                <div class="sidebar-section category-list">
                    <h3>Category blog:</h3>
                    <form action="${pageContext.request.contextPath}/BlogController" method="get">
                        <input type="hidden" name="action" value="category"/>
                        <select name="category">
                            <option value="">--All--</option>
                            <c:forEach var="cat" items="${categories}">
                                <option value="${cat}">${cat}</option>
                            </c:forEach>
                        </select>
                        <button type="submit">Filter</button>
                    </form>
                </div>

                <!-- Latest Blogs -->
                <div class="sidebar-section latest-blogs">
                    <h3>Latest Blog:</h3>
                    <ul>
                        <c:forEach var="latest" items="${latestBlogs}">
                            <li>
                                <a href="${pageContext.request.contextPath}/BlogDetailController?postId=${latest.postId}">
                                    ${latest.title}
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

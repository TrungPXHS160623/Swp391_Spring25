<%-- 
    Document   : BlogDetail
    Created on : Mar 24, 2025, 10:24:14 AM
    Author     : LENOVO
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <title>Blog Detail</title>
        <!-- Bootstrap CSS -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <style>
            .blog-title {
                font-size: 2rem;
                font-weight: bold;
            }
            .blog-meta {
                font-size: 0.9rem;
                color: #666;
                margin-bottom: 20px;
            }
            .blog-content {
                font-size: 1.1rem;
                line-height: 1.6;
            }
            .media-item {
                margin-bottom: 20px;
            }
            .media-item img, .media-item video {
                max-width: 100%;
                height: auto;
            }
        </style>
    </head>
    <body>
        <div class="container mt-5">
            <c:if test="${not empty blogDetail}">
                <h1 class="blog-title">${blogDetail.title}</h1>
                <div class="blog-meta">
                    <span>By ${blogDetail.author}</span> | 
                    <span>Updated on ${blogDetail.dayUpdate}</span> | 
                    <span>Category: ${blogDetail.category}</span>
                </div>
                <!-- Hiển thị tất cả các media -->
                <c:if test="${not empty blogDetail.mediaList}">
                    <div class="row">
                        <c:forEach var="media" items="${blogDetail.mediaList}">
                            <div class="col-md-4 media-item">
                                <c:choose>
                                    <c:when test="${media.mediaType eq 'image'}">
                                        <img src="${media.mediaUrl}" alt="${blogDetail.title}">
                                    </c:when>
                                    <c:when test="${fn:contains(media.mediaUrl, 'youtu')}">
                                        <div class="embed-responsive embed-responsive-16by9">
                                            <iframe class="embed-responsive-item" src="https://www.youtube.com/embed/${media.mediaUrl.substring(media.mediaUrl.lastIndexOf('/')+1)}" allowfullscreen></iframe>
                                        </div>
                                    </c:when>
                                    <c:otherwise>
                                        <video controls>
                                            <source src="${media.mediaUrl}" type="video/mp4">
                                            Your browser does not support the video tag.
                                        </video>
                                    </c:otherwise>
                                </c:choose>
                                <c:if test="${not empty media.description}">
                                    <p class="text-muted">${media.description}</p>
                                </c:if>
                            </div>
                        </c:forEach>
                    </div>
                </c:if>
                <div class="blog-content">
                    ${blogDetail.content}
                </div>
            </c:if>
            <c:if test="${empty blogDetail}">
                <p>Blog post not found.</p>
            </c:if>
            <a href="BlogListController" class="btn btn-secondary mt-4">Back to Blog List</a>
        </div>

        <!-- Bootstrap Bundle JS -->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <title>Blog Home</title>
        <!-- Bootstrap CSS -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <style>
            .blog-item {
                margin-bottom: 30px;
            }
            .blog-thumbnail {
                max-width: 100%;
                height: auto;
            }
            .blog-title {
                font-size: 1.5rem;
                font-weight: bold;
            }
            .blog-summary {
                font-size: 1rem;
            }
            .blog-meta {
                font-size: 0.9rem;
                color: #666;
            }
        </style>
    </head>
    <body>
        <div class="container mt-5">
            <h1 class="mb-4">Blog Home</h1>
            <div class="row">
                <!-- Cột trái: Danh sách blog -->
                <div class="col-md-8">
                    <div class="row">
                        <c:if test="${not empty blogList}">
                            <c:forEach var="blog" items="${blogList}">
                                <div class="col-md-6 blog-item">
                                    <div class="card h-100">
                                        <c:if test="${not empty blog.mediaUrl}">
                                            <img src="${blog.mediaUrl}" class="card-img-top blog-thumbnail" alt="${blog.title}">
                                        </c:if>
                                        <div class="card-body">
                                            <h5 class="card-title blog-title">
                                                <a href="BlogDetailController?postId=${blog.postId}" class="text-decoration-none text-dark">
                                                    ${blog.title}
                                                </a>
                                            </h5>
                                            <p class="card-text blog-summary">${blog.summary}</p>
                                        </div>
                                        <div class="card-footer">
                                            <small class="text-muted blog-meta">
                                                Updated on ${blog.dayUpdate} | Category: ${blog.category}
                                            </small>
                                        </div>
                                    </div>
                                </div>
                            </c:forEach>
                        </c:if>
                        <c:if test="${empty blogList}">
                            <div class="col-12">
                                <p>No blog posts available.</p>
                            </div>
                        </c:if>
                    </div>
                </div>
                <!-- Cột phải: Sidebar -->
                <div class="col-md-4">
                    <jsp:include page="BlogSideBar.jsp" />
                </div>
            </div>
        </div>

        <!-- Bootstrap Bundle JS -->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>

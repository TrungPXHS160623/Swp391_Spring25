<%-- 
    Document   : BlogSideBar
    Created on : Mar 24, 2025, 11:33:11 AM
    Author     : LENOVO
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<div class="card mb-4">
    <div class="card-header">
        <h5>Search & Filter</h5>
    </div>
    <div class="card-body">
        <!-- Form tìm kiếm -->
        <form action="BlogListController" method="get" class="mb-3">
            <div class="input-group">
                <input type="text" name="keyword" class="form-control" placeholder="Search blog..." value="${param.keyword}" />
                <button class="btn btn-primary" type="submit">Search</button>
            </div>
        </form>
        <!-- Combobox lọc theo category -->
        <form action="BlogListController" method="get" class="mb-3">
            <div class="mb-2">
                <label for="categoryFilter" class="form-label">Filter by Category:</label>
                <select name="category" id="categoryFilter" class="form-select">
                    <option value="">-- All Categories --</option>
                    <!-- Giả sử bạn có danh sách category được lấy từ DB -->
                    <c:forEach var="cat" items="${categoryList}">
                        <option value="${cat}" ${cat == param.category ? 'selected' : ''}>${cat}</option>
                    </c:forEach>
                </select>
            </div>
            <button class="btn btn-secondary" type="submit">Filter</button>
        </form>
    </div>
</div>

<div class="card">
    <div class="card-header">
        <h5>Latest Blogs</h5>
    </div>
    <div class="card-body">
        <ul class="list-group">
            <c:forEach var="blog" items="${latest3Blogs}">
                <li class="list-group-item">
                    <a href="BlogDetailController?postId=${blog.postId}" class="text-decoration-none">
                        ${blog.title}
                    </a>
                </li>
            </c:forEach>
        </ul>
    </div>
</div>
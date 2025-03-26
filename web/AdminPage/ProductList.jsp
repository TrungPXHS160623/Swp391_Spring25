<%-- 
    Document   : ProductList
    Created on : Mar 23, 2025, 6:44:03 PM
    Author     : LENOVO
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Product List - Admin</title>
        <!-- Bootstrap CSS -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <style>
            body {
                background-color: #f8f9fa;
            }
            .table thead tr {
                background-color: #343a40;
                color: #fff;
            }
            .pagination {
                margin-top: 20px;
            }
            .filter-form .form-control, .filter-form .form-select {
                max-width: 200px;
                display: inline-block;
                margin-right: 10px;
            }
            .thumbnail {
                max-width: 80px;
            }
        </style>
    </head>
    <body>
        <div class="container mt-4">
            <h1 class="mb-4">Product List - Admin</h1>

            <!-- Search, Filter, Sort Form -->
            <form method="get" action="AdminProductListController" class="filter-form mb-4">
                <input type="text" name="keyword" class="form-control" placeholder="Search..." value="${keyword}" />
                <select name="category" class="form-select">
                    <option value="">-- Subcategory --</option>
                    <option value="Electronics" ${category == 'Electronics' ? 'selected' : ''}>Electronics</option>
                    <option value="Clothing" ${category == 'Clothing' ? 'selected' : ''}>Clothing</option>
                    <option value="Books" ${category == 'Books' ? 'selected' : ''}>Books</option>
                </select>
                <select name="featured" class="form-select">
                    <option value="">-- Featured --</option>
                    <option value="Yes" ${featured == 'Yes' ? 'selected' : ''}>Yes</option>
                    <option value="No" ${featured == 'No' ? 'selected' : ''}>No</option>
                </select>
                <select name="status" class="form-select">
                    <option value="">-- Status --</option>
                    <option value="Sale" ${status == 'Sale' ? 'selected' : ''}>Sale</option>
                    <option value="Soldout" ${status == 'Soldout' ? 'selected' : ''}>Soldout</option>
                </select>
                <select name="sortField" class="form-select">
                    <option value="">-- Sort By --</option>
                    <option value="Title" ${sortField == 'Title' ? 'selected' : ''}>Title</option>
                    <option value="Category" ${sortField == 'Category' ? 'selected' : ''}>Category</option>
                    <option value="ListPrice" ${sortField == 'ListPrice' ? 'selected' : ''}>List Price</option>
                    <option value="SalePrice" ${sortField == 'SalePrice' ? 'selected' : ''}>Sale Price</option>
                    <option value="featured" ${sortField == 'featured' ? 'selected' : ''}>Featured</option>
                    <option value="status" ${sortField == 'status' ? 'selected' : ''}>Status</option>
                </select>
                <select name="sortDirection" class="form-select">
                    <option value="ASC" ${sortDirection == 'ASC' ? 'selected' : ''}>ASC</option>
                    <option value="DESC" ${sortDirection == 'DESC' ? 'selected' : ''}>DESC</option>
                </select>
                <button type="submit" class="btn btn-primary">Apply</button>
            </form>

            <!-- Product Table -->
            <table class="table table-bordered table-striped">
                <thead>
                    <tr>
                        <th>Id</th>
                        <th>Thumbnail</th>
                        <th>Category</th>
                        <th>List Price</th>
                        <th>Sale Price</th>
                        <th>Featured</th>
                        <th>Status</th>
                        <th>Action</th>
                    </tr>
                </thead>
                <tbody>
                    <c:choose>
                        <c:when test="${not empty productList}">
                            <c:forEach var="product" items="${productList}">
                                <tr>
                                    <td>${product.productId}</td>
                                    <td>
                                        <c:if test="${not empty product.primaryImageUrl}">
                                            <img src="${product.primaryImageUrl}" class="thumbnail" alt="${product.productName}">
                                        </c:if>
                                    </td>
                                    <td>${product.category}</td>
                                    <td>${product.price}</td>
                                    <td>${product.discountPrice}</td>
                                    <td>${product.featured}</td>
                                    <td>${product.status}</td>
                                    <td>
                                        <a href="AdminProductDetailController?action=view&productId=${product.productId}" class="btn btn-info btn-sm">View</a>
                                        <a href="AdminProductDetailController?action=edit&productId=${product.productId}" class="btn btn-warning btn-sm">Edit</a>
                                        <a href="ProductHideController?action=hide&productId=${product.productId}" class="btn btn-danger btn-sm">Hide</a>
                                        <!-- Nếu sản phẩm đang ẩn, hiển thị nút Show (ví dụ: nếu có flag hidden, hiển thị nút Show) -->
                                    </td>
                                </tr>
                            </c:forEach>
                        </c:when>
                        <c:otherwise>
                            <tr>
                                <td colspan="8" class="text-center">No products found.</td>
                            </tr>
                        </c:otherwise>
                    </c:choose>
                </tbody>
            </table>

            <!-- Add Product Button -->
            <div class="mt-3">
                <a href="AdminProductDetailController?action=add" class="btn btn-primary">Add Product</a>
            </div>

            <!-- Pagination -->
            <nav aria-label="Page navigation">
                <ul class="pagination justify-content-center">
                    <li class="page-item ${currentPage == 1 ? 'disabled' : ''}">
                        <a class="page-link" href="AdminProductListController?page=${currentPage - 1}&pageSize=${pageSize}&keyword=${keyword}&category=${category}&featured=${featured}&status=${status}&sortField=${sortField}&sortDirection=${sortDirection}">Previous</a>
                    </li>
                    <c:forEach var="i" begin="1" end="${totalPages}">
                        <li class="page-item ${i == currentPage ? 'active' : ''}">
                            <a class="page-link" href="AdminProductListController?page=${i}&pageSize=${pageSize}&keyword=${keyword}&category=${category}&featured=${featured}&status=${status}&sortField=${sortField}&sortDirection=${sortDirection}">${i}</a>
                        </li>
                    </c:forEach>
                    <li class="page-item ${currentPage == totalPages ? 'disabled' : ''}">
                        <a class="page-link" href="AdminProductListController?page=${currentPage + 1}&pageSize=${pageSize}&keyword=${keyword}&category=${category}&featured=${featured}&status=${status}&sortField=${sortField}&sortDirection=${sortDirection}">Next</a>
                    </li>
                </ul>
            </nav>
        </div>

        <!-- Bootstrap Bundle JS -->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>


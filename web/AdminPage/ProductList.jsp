<%-- 
    Document   : product
    Created on : Feb 15, 2025, 7:45:04 AM
    Author     : LENOVO
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Danh sách Sản Phẩm</title>
        <style>
            body {
                font-family: Arial, sans-serif;
                background-color: #4A90E2;
                text-align: center;
                margin: 0;
                padding: 0;
            }
            .container {
                width: 80%;
                margin: 30px auto;
                background: white;
                padding: 20px;
                border-radius: 8px;
                box-shadow: 0px 4px 10px rgba(0, 0, 0, 0.2);
            }
            h2 {
                color: #333;
            }
            .search-section {
                display: flex;
                justify-content: space-between;
                align-items: center;
                margin-bottom: 20px;
                padding: 10px;
                background: #f8f8f8;
                border-radius: 5px;
            }
            .search-section input, .search-section select {
                padding: 8px;
                width: 20%;
            }
            .search-btn {
                background-color: #28a745;
                color: white;
                padding: 10px;
                border: none;
                cursor: pointer;
                border-radius: 5px;
            }
            table {
                width: 100%;
                border-collapse: collapse;
                margin-top: 20px;
            }
            th, td {
                border: 1px solid #ddd;
                padding: 12px;
                text-align: center;
            }
            th {
                background-color: #007bff;
                color: white;
            }
            tr:nth-child(even) {
                background-color: #f2f2f2;
            }
            .pagination {
                margin-top: 20px;
            }
            .pagination button {
                padding: 10px;
                background-color: orange;
                border: none;
                cursor: pointer;
                border-radius: 5px;
            }
            .detail-btn {
                background-color: #ff9800;
                color: white;
                padding: 8px;
                border: none;
                cursor: pointer;
                border-radius: 5px;
            }
        </style>
    </head>
    <body>
        <h2>Danh sách Sản Phẩm</h2>
        <div class="container">
            <form action="ProductController" method="get" class="search-section">
                <select name="category" id="category" onchange="this.form.submit()">
                    <option value="">All Categories</option>
                    <c:forEach var="category" items="${categories}">
                        <option value="${category}" ${category == param.category ? 'selected' : ''}>${category}</option>
                    </c:forEach>
                </select>   
            </form>
            <form action="ProductController" method="get" class="search-section">
                <select name="featured" id="featured" onchange="this.form.submit()">
                    <option value="">All Featured</option>
                    <c:forEach var="featured" items="${featured2}">
                        <option value="${featured}" ${featured == param.featured ? 'selected' : ''}>${featured}</option>
                    </c:forEach>
                </select>   
            </form>
            <form action="ProductController" method="get" class="search-section">
                <select name="status" id="status" onchange="this.form.submit()">
                    <option value="">All Status</option>
                    <c:forEach var="status" items="${status2}">
                        <option value="${status}" ${status == param.status ? 'selected' : ''}>${status}</option>
                    </c:forEach>
                </select>   
            </form>

            <!-- Form tìm kiếm riêng biệt -->
            <form action="ProductController" method="get" class="search-section">
                <label for="search">Search: </label>
                <input type="text" name="search" value="${param.search}"/>             
                <input type="submit" value="Search" class="search-btn"/>
            </form>

            <table>
                <tr>
                    <th>Id</th>
                    <th>Thumbnail</th>
                    <th>Title
                        <c:choose>
                            <c:when test="${param.name_sort == 'desc'}">
                                <a href="ProductController?name_sort=asc&search=${param.search}" title="Sắp xếp tên sản phẩm (Tăng dần)">▲</a>
                            </c:when>
                            <c:otherwise>
                                <a href="ProductController?name_sort=desc&search=${param.search}" title="Sắp xếp tên sản phẩm (Giảm dần)">▼</a>
                            </c:otherwise>
                        </c:choose></th>
                    <th>Category
                        <c:choose>
                            <c:when test="${param.caterogy_sort == 'desc'}">
                                <a href="ProductController?caterogy_sort=asc&search=${param.search}" title="Sắp xếp tên sản phẩm (Tăng dần)">▲</a>
                            </c:when>
                            <c:otherwise>
                                <a href="ProductController?caterogy_sort=desc&search=${param.search}" title="Sắp xếp tên sản phẩm (Giảm dần)">▼</a>
                            </c:otherwise>
                        </c:choose></th>
                    <th>List Price
                        <c:choose>
                            <c:when test="${param.listprice_sort == 'desc'}">
                                <a href="ProductController?listprice_sort=asc&search=${param.search}" title="Sắp xếp tên sản phẩm (Tăng dần)">▲</a>
                            </c:when>
                            <c:otherwise>
                                <a href="ProductController?listprice_sort=desc&search=${param.search}" title="Sắp xếp tên sản phẩm (Giảm dần)">▼</a>
                            </c:otherwise>
                        </c:choose></th>
                    <th>Sale Price
                        <c:choose>
                            <c:when test="${param.saleprice_sort == 'desc'}">
                                <a href="ProductController?saleprice_sort=asc&search=${param.search}" title="Sắp xếp tên sản phẩm (Tăng dần)">▲</a>
                            </c:when>
                            <c:otherwise>
                                <a href="ProductController?saleprice_sort=desc&search=${param.search}" title="Sắp xếp tên sản phẩm (Giảm dần)">▼</a>
                            </c:otherwise>
                        </c:choose></th>
                    <th>Featured
                        <c:choose>
                            <c:when test="${param.featured_sort == 'desc'}">
                                <a href="ProductController?featured_sort=asc&search=${param.search}" title="Sắp xếp tên sản phẩm (Tăng dần)">▲</a>
                            </c:when>
                            <c:otherwise>
                                <a href="ProductController?featured_sort=desc&search=${param.search}" title="Sắp xếp tên sản phẩm (Giảm dần)">▼</a>
                            </c:otherwise>
                        </c:choose></th>
                    <th>Status
                        <c:choose>
                            <c:when test="${param.status_sort == 'desc'}">
                                <a href="ProductController?status_sort=asc&search=${param.search}" title="Sắp xếp tên sản phẩm (Tăng dần)">▲</a>
                            </c:when>
                            <c:otherwise>
                                <a href="ProductController?status_sort=desc&search=${param.search}" title="Sắp xếp tên sản phẩm (Giảm dần)">▼</a>
                            </c:otherwise>
                        </c:choose></th>
                    <th>Action</th>
                </tr>
                <c:forEach var="ProductController" items="${ProductController}">
                    <tr>
                        <td>${ProductController.getProduct_id()}</td>
                        <td><img src="${ProductController.coverProduct.imageUrl}" 
                                 alt="Product Image" style="width: 120px; height: auto;"/></td>
                        <td>${ProductController.getProduct_name()}</td>
                        <td>${ProductController.getSubcategory_name()}</td>
                        <td>${ProductController.getList_price()}</td>
                        <td>${ProductController.getSale_price()}</td>
                        <td>${ProductController.getFeatured() == 1 ? "Yes" : "No"}</td>
                        <td>${ProductController.getStatus() == 1 ? "Sale" : "Sold out"}</td>
                        <td>
                            <a href="ProductDetailController?action=view&id=${ProductController.getProduct_id()}" class="detail-btn">View</a> |
                            <a href="ProductDetailController?action=edit&id=${ProductController.getProduct_id()}" class="detail-btn">Edit</a>
                        </td>
                    </tr>
                </c:forEach>
            </table>
            <a href="ProductDetailController?action=add" class="detail-btn">Add Product</a>
            <div class="pagination">
                <c:forEach begin="1" end="${noOfPages}" var="pageNumber">
                    <a href="ProductController?page=${pageNumber}&search=${param.search}&sort=${param.sort}">${pageNumber}</a>
                </c:forEach>
            </div>
        </div>
    </body>
</html>

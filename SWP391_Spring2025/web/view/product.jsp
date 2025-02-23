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
    </head>
    <body>
        <h2>Danh sách Sản Phẩm</h2>
        <form action="ProductController" method="get">
            <label for="search">Search: </label>
            <input type="text" name="search" value=""/>             
            <input type="submit" value="Search" />
        </form>
        <table border="1">
            <tr>
                <td>Id</td>
                <td>Thumbnail</td>
                <td>Title
                    <c:choose>
                        <c:when test="${param.name_sort == 'desc'}">
                            <a href="ProductController?name_sort=asc&search=${param.search}" title="Sắp xếp tên sản phẩm (Tăng dần)">▲</a>
                        </c:when>
                        <c:otherwise>
                            <a href="ProductController?name_sort=desc&search=${param.search}" title="Sắp xếp tên sản phẩm (Giảm dần)">▼</a>
                        </c:otherwise>
                    </c:choose>
                <td>Category
                <c:choose>
                        <c:when test="${param.caterogy_sort == 'desc'}">
                            <a href="ProductController?caterogy_sort=asc&search=${param.search}" title="Sắp xếp tên sản phẩm (Tăng dần)">▲</a>
                        </c:when>
                        <c:otherwise>
                            <a href="ProductController?caterogy_sort=desc&search=${param.search}" title="Sắp xếp tên sản phẩm (Giảm dần)">▼</a>
                        </c:otherwise>
                    </c:choose></td>
                <td>List Price
                <c:choose>
                        <c:when test="${param.listprice_sort == 'desc'}">
                            <a href="ProductController?listprice_sort=asc&search=${param.search}" title="Sắp xếp tên sản phẩm (Tăng dần)">▲</a>
                        </c:when>
                        <c:otherwise>
                            <a href="ProductController?listprice_sort=desc&search=${param.search}" title="Sắp xếp tên sản phẩm (Giảm dần)">▼</a>
                        </c:otherwise>
                    </c:choose></td>
                <td>Sale Price
                <c:choose>
                        <c:when test="${param.saleprice_sort == 'desc'}">
                            <a href="ProductController?saleprice_sort=asc&search=${param.search}" title="Sắp xếp tên sản phẩm (Tăng dần)">▲</a>
                        </c:when>
                        <c:otherwise>
                            <a href="ProductController?saleprice_sort=desc&search=${param.search}" title="Sắp xếp tên sản phẩm (Giảm dần)">▼</a>
                        </c:otherwise>
                    </c:choose></td>
                <td>Featured
                <c:choose>
                        <c:when test="${param.featured_sort == 'desc'}">
                            <a href="ProductController?featured_sort=asc&search=${param.search}" title="Sắp xếp tên sản phẩm (Tăng dần)">▲</a>
                        </c:when>
                        <c:otherwise>
                            <a href="ProductController?featured_sort=desc&search=${param.search}" title="Sắp xếp tên sản phẩm (Giảm dần)">▼</a>
                        </c:otherwise>
                    </c:choose></td>
                <td>Status
                <c:choose>
                        <c:when test="${param.status_sort == 'desc'}">
                            <a href="ProductController?status_sort=asc&search=${param.search}" title="Sắp xếp tên sản phẩm (Tăng dần)">▲</a>
                        </c:when>
                        <c:otherwise>
                            <a href="ProductController?status_sort=desc&search=${param.search}" title="Sắp xếp tên sản phẩm (Giảm dần)">▼</a>
                        </c:otherwise>
                    </c:choose></td>
                <td>Action</td>
            </tr>
            <c:forEach var="ProductController" items="${ProductController}">
                <tr>
                    <td>${ProductController.getProduct_id()}</td>
                    <td><img src="${ProductController.getImage_url()}" alt="Product Image" style="width: 120px; height: auto;"/></td>
                    <td>${ProductController.getProduct_name()}</td>
                    <td>${ProductController.getSubcategory_name()}</td>
                    <td>${ProductController.getList_price()}</td>
                    <td>${ProductController.getSale_price()}</td>
                    <td>${ProductController.getFeatured()}</td>
                    <td>${ProductController.getStatus()}</td>
                    <td><a href="ProductController">View</a>
                        <a href="ProductController">Edit</a></td>
                </tr>
            </c:forEach>
        </table>
        <div>
            <c:forEach begin="1" end="${noOfPages}" var="pageNumber">
                <a href="ProductController?page=${pageNumber}&search=${param.search}&sort=${param.sort}">${pageNumber}</a>
            </c:forEach>
        </div>
    </body>
</html>

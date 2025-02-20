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
                <td>Title</td>
                <td>Category</td>
                <td>List Price</td>
                <td>Sale Price</td>
                <td>Featured</td>
                <td>Status</td>
                <td>Action</td>
            </tr>
            <tr>
                <c:forEach var="ProductController" items="${products}">
                <td>${products.getProduct_id()}</td>
                <td>${products.getImage_url()}</td>
                <td>${products.getProduct_name()}</td>
                <td>${products.getSubcategory_id()}</td>
                <td>${products.getList_price()}</td>
                <td>${products.getSale_price()}</td>
                <td>${products.getFeatured()}</td>
                <td>${products.getStatus()}</td>
                <td></td>
                </c:forEach>
            </tr>
        </table>
        <div>
            <c:forEach begin="1" end="${noOfPages}" var="pageNumber">
                <a href="customers?page=${pageNumber}&status=${param.status}&search=${param.search}&sort=${param.sort}">${pageNumber}</a>
            </c:forEach>
        </div>
    </body>
</html>

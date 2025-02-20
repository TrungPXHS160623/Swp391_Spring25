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
            <c:forEach var="ProductController" items="${ProductController}">
                <tr>
                    <td>${ProductController.getProduct_id()}</td>
                    <td><img src="${ProductController.getImage_url()}" alt="Product Image" style="width: 120px; height: auto;"/></td>
                    <td>${ProductController.getProduct_name()}</td>
                    <td>${ProductController.getSubcategory_id()}</td>
                    <td>${ProductController.getList_price()}</td>
                    <td>${ProductController.getSale_price()}</td>
                    <td>${ProductController.getFeatured()}</td>
                    <td>${ProductController.getStatus()}</td>
                    <td></td>
                </tr>
            </c:forEach>
        </table>
        <div>
            <c:forEach begin="1" end="${noOfPages}" var="pageNumber">
                <a href="customers?page=${pageNumber}&status=${param.status}&search=${param.search}&sort=${param.sort}">${pageNumber}</a>
            </c:forEach>
        </div>
    </body>
</html>

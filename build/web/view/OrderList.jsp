<%-- 
    Document   : order
    Created on : Feb 20, 2025, 9:35:08 AM
    Author     : LENOVO
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Danh sách Đơn Hàng</title>
    </head>
    <body>
        <h2>Danh sách Đơn Hàng</h2>
        <form action="OrderController" method="get">
            <label for="search">Search: </label>
            <input type="text" name="search" value=""/>             
            <input type="submit" value="Search" />
        </form>
        <table border="1">
            <tr>
                <td>Order Id</td>
                <td>Order Date</td>
                <td>Customer Name</td>
                <td>Product(s)</td>
                <td>Total cost</td>
                <td>Status</td>
                <td>Sale Name</td>
                <td>Action</td>
            </tr>
            <tr>
                <c:forEach var="OrderController" items="${orders}">
                    <td></td>
                </c:forEach>
            </tr>
        </table>
    </body>
</html>

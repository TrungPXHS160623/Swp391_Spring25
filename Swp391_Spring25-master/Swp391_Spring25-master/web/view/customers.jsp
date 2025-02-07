<%-- 
    Document   : customers
    Created on : Feb 5, 2025, 2:59:15 PM
    Author     : FPT SHOP
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Danh sách Khách hàng</title>
</head>
<body>
    <h2>Danh sách Khách hàng</h2>
    <form action="customers" method="get">
        <label for="status">Status: </label>
        <input type="text" name="status" value="${param.status}" />

        <label for="search">Search: </label>
        <input type="text" name="search" value="${param.search}" />

        <label for="sort">Sort by: </label>
        <select name="sort">
            <option value="full_name" <c:if test="${param.sort == 'full_name'}">selected</c:if>>Full Name</option>
            <option value="email" <c:if test="${param.sort == 'email'}">selected</c:if>>Email</option>
            <option value="phone_number" <c:if test="${param.sort == 'phone_number'}">selected</c:if>>Phone</option>
            <option value="status" <c:if test="${param.sort == 'status'}">selected</c:if>>Status</option>
        </select>
        
        <input type="submit" value="Search" />
    </form>

    <table border="1">
        <thead>
            <tr>
                <th>ID</th>
                <th>Full Name</th>
                <th>Email</th>
                <th>Phone Number</th>
                <th>Status</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="customer" items="${customers}">
                <tr>
                    <td>${customer.userId}</td>
                    <td>${customer.fullName}</td>
                    <td>${customer.email}</td>
                    <td>${customer.phoneNumber}</td>
                    <td>${customer.status}</td>
                </tr>
            </c:forEach>
        </tbody>
    </table>

    <div>
        <c:forEach begin="1" end="${noOfPages}" var="pageNumber">
            <a href="customers?page=${pageNumber}&status=${param.status}&search=${param.search}&sort=${param.sort}">${pageNumber}</a>
        </c:forEach>
    </div>
</body>
</html>

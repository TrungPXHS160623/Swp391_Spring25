<%-- 
    Document   : user
    Created on : Mar 4, 2025, 9:23:24 AM
    Author     : LENOVO
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Danh sách người Dùng</title>
    </head>
    <body>
        <h2>Danh sách người Dùng</h2>
        
        <form action="UserController" method="get">
            <select name="userGender_filter" id="userGender_filter" onchange="this.form.submit()">
                <option value="">All Gender</option>
                <c:forEach var="userGender_filter" items="${genders}">
                    <option value="${userGender_filter}" ${userGender_filter == param.userGender_filter ? 'selected' : ''}>${userGender_filter}</option>
                </c:forEach>
            </select>   
        </form>
        <form action="UserController" method="get">
            <select name="userRole_filter" id="userRole_filter" onchange="this.form.submit()">
                <option value="">All Roles</option>
                <c:forEach var="userRole_filter" items="${roles}">
                    <option value="${userRole_filter}" ${userRole_filter == param.userRole_filter ? 'selected' : ''}>${userRole_filter}</option>
                </c:forEach>
            </select>   
        </form>
        <form action="UserController" method="get">
            <select name="userStatus_filter" id="userStatus_filter" onchange="this.form.submit()">
                <option value="">All Status</option>
                <c:forEach var="userStatus_filter" items="${is_actives}">
                    <option value="${userStatus_filter}" ${userStatus_filter == param.userStatus_filter ? 'selected' : ''}>${userStatus_filter}</option>
                </c:forEach>
            </select>   
        </form>
        
        <!-- Form tìm kiếm riêng biệt -->
        <form action="UserController" method="get">
            <label for="searchKeyword">Search: </label>
            <input type="text" name="searchKeyword" value="${param.search}"/>             
            <input type="submit" value="Search" />
        </form>
            
        <table border="1">
            <tr>
                <td>Id
                <c:choose>
                        <c:when test="${param.Id_sort == 'desc'}">
                            <a href="UserController?Id_sort=asc&search=${param.search}" title="Sắp xếp tên người dùng (Tăng dần)">▲</a>
                        </c:when>
                        <c:otherwise>
                            <a href="UserController?Id_sort=desc&search=${param.search}" title="Sắp xếp tên người dùng (Giảm dần)">▼</a>
                        </c:otherwise>
                    </c:choose></td>
                <td>Full Name
                <c:choose>
                        <c:when test="${param.fullName_sort == 'desc'}">
                            <a href="UserController?fullName_sort=asc&search=${param.search}" title="Sắp xếp tên người dùng (Tăng dần)">▲</a>
                        </c:when>
                        <c:otherwise>
                            <a href="UserController?fullName_sort=desc&search=${param.search}" title="Sắp xếp tên người dùng (Giảm dần)">▼</a>
                        </c:otherwise>
                    </c:choose></td>
                <td>Gender
                <c:choose>
                        <c:when test="${param.userGender_sort == 'desc'}">
                            <a href="UserController?userGender_sort=asc&search=${param.search}" title="Sắp xếp tên người dùng (Tăng dần)">▲</a>
                        </c:when>
                        <c:otherwise>
                            <a href="UserController?userGender_sort=desc&search=${param.search}" title="Sắp xếp tên người dùng (Giảm dần)">▼</a>
                        </c:otherwise>
                    </c:choose></td>
                <td>Address
                <c:choose>
                        <c:when test="${param.address_sort == 'desc'}">
                            <a href="UserController?address_sort=asc&search=${param.search}" title="Sắp xếp tên người dùng (Tăng dần)">▲</a>
                        </c:when>
                        <c:otherwise>
                            <a href="UserController?address_sort=desc&search=${param.search}" title="Sắp xếp tên người dùng (Giảm dần)">▼</a>
                        </c:otherwise>
                    </c:choose></td>
                <td>Email
                <c:choose>
                        <c:when test="${param.email_sort == 'desc'}">
                            <a href="UserController?email_sort=asc&search=${param.search}" title="Sắp xếp tên người dùng (Tăng dần)">▲</a>
                        </c:when>
                        <c:otherwise>
                            <a href="UserController?email_sort=desc&search=${param.search}" title="Sắp xếp tên người dùng (Giảm dần)">▼</a>
                        </c:otherwise>
                    </c:choose></td>
                <td>Mobile
                <c:choose>
                        <c:when test="${param.phone_sort == 'desc'}">
                            <a href="UserController?phone_sort=asc&search=${param.search}" title="Sắp xếp tên người dùng (Tăng dần)">▲</a>
                        </c:when>
                        <c:otherwise>
                            <a href="UserController?phone_sort=desc&search=${param.search}" title="Sắp xếp tên người dùng (Giảm dần)">▼</a>
                        </c:otherwise>
                    </c:choose></td>
                <td>Role
                <c:choose>
                        <c:when test="${param.userRole_sort == 'desc'}">
                            <a href="UserController?userRole_sort=asc&search=${param.search}" title="Sắp xếp tên người dùng (Tăng dần)">▲</a>
                        </c:when>
                        <c:otherwise>
                            <a href="UserController?userRole_sort=desc&search=${param.search}" title="Sắp xếp tên người dùng (Giảm dần)">▼</a>
                        </c:otherwise>
                    </c:choose></td>
                <td>Status
                <c:choose>
                        <c:when test="${param.userStatus_sort == 'desc'}">
                            <a href="UserController?userStatus_sort=asc&search=${param.search}" title="Sắp xếp tên người dùng (Tăng dần)">▲</a>
                        </c:when>
                        <c:otherwise>
                            <a href="UserController?userStatus_sort=desc&search=${param.search}" title="Sắp xếp tên người dùng (Giảm dần)">▼</a>
                        </c:otherwise>
                    </c:choose></td>
                <td>Action</td>
            </tr>
            <c:forEach var="UserController" items="${UserController}">
                <tr>
                    <td>${UserController.getUser_id()}</td>
                    <td>${UserController.getFull_name()}</td>
                    <td>${UserController.getGender()}</td>
                    <td>${UserController.getAddress()}</td>
                    <td>${UserController.getEmail()}</td>
                    <td>${UserController.getPhone_number()}</td>
                    <td>${UserController.getRole_name()}</td>
                    <td>${UserController.getIs_active()}</td>
                    <td><a href="UserController">View</a>
                        <a href="UserController">Edit</a></td>
                </tr>   
            </c:forEach>
        </table>
    </body>
</html>

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
        <style>
            body {
                font-family: Arial, sans-serif;
                background-color: #f0f2f5;
                text-align: center;
                margin: 0;
                padding: 0;
            }
            h2 {
                color: #333;
                margin-top: 20px;
                font-size: 24px;
            }
            .container {
                width: 90%;
                max-width: 1200px;
                margin: 30px auto;
                background: white;
                padding: 30px;
                border-radius: 8px;
                box-shadow: 0px 4px 10px rgba(0, 0, 0, 0.1);
            }
            .search-section {
                display: flex;
                justify-content: space-between;
                align-items: center;
                margin-bottom: 20px;
                padding: 10px;
                background: #f8f8f8;
                border-radius: 5px;
                box-shadow: 0 2px 5px rgba(0, 0, 0, 0.05);
            }
            /* Sắp xếp các bộ lọc vào cùng một hàng */
            .filters {
                display: flex;
                justify-content: flex-start;
                gap: 10px;
            }
            .filters select {
                padding: 10px;
                margin: 5px;
                width: 200px;
                border-radius: 5px;
                border: 1px solid #ddd;
                font-size: 14px;
            }
            /* Căn chỉnh phần tìm kiếm sang bên phải */
            .search-form {
                display: flex;
                justify-content: flex-end;
                align-items: center;
            }
            .search-form input[type="text"] {
                padding: 8px;
                width: 250px;
                border-radius: 5px;
                border: 1px solid #ddd;
                font-size: 14px;
            }
            .search-btn {
                background-color: #28a745;
                color: white;
                padding: 12px 20px;
                border: none;
                cursor: pointer;
                border-radius: 5px;
                font-size: 16px;
                margin-left: 10px;
            }
            .search-btn:hover {
                background-color: #218838;
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
                font-size: 14px;
            }
            th {
                background-color: #007bff;
                color: white;
            }
            tr:nth-child(even) {
                background-color: #f9f9f9;
            }
            tr:hover {
                background-color: #f1f1f1;
            }
            .pagination {
                margin-top: 20px;
                display: flex;
                justify-content: center;
                align-items: center;
            }
            .pagination a, .pagination span {
                padding: 10px;
                margin: 0 5px;
                background-color: #007bff;
                color: white;
                text-decoration: none;
                border-radius: 5px;
                font-size: 14px;
            }
            .pagination a:hover {
                background-color: #0056b3;
            }
            .pagination .current {
                background-color: #f39c12;
                color: white;
            }
            .action-btn {
                background-color: #007bff;
                color: white;
                padding: 8px 12px;
                border: none;
                cursor: pointer;
                border-radius: 5px;
                text-decoration: none;
            }
            .action-btn:hover {
                background-color: #0056b3;
            }
            .action-btn.edit {
                background-color: #ff9800;
            }
            .action-btn.edit:hover {
                background-color: #f57c00;
            }
            .action-btn.view {
                background-color: #4caf50;
            }
            .action-btn.view:hover {
                background-color: #388e3c;
            }
            .add-user-btn {
                width: 100%;
                padding: 15px;
                background-color: #007bff;
                color: white;
                font-size: 18px;
                font-weight: bold;
                border: none;
                cursor: pointer;
                border-radius: 5px;
                text-align: center;
                margin-top: 20px;
            }
            .add-user-btn:hover {
                background-color: #0056b3;
            }
            .add-user-btn:focus {
                outline: none;
            }
        </style>
    </head>
    <body>
        <h2>Danh sách người Dùng</h2>

        <!-- Phần chứa các bộ lọc -->
        <div class="search-section">
            <div class="filters">
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
            </div>
            <!-- Phần tìm kiếm lệch sang phải -->
            <div class="search-form">
                <form action="UserController" method="get">
                    <label for="searchKeyword">Search: </label>
                    <input type="text" name="searchKeyword" value="${param.search}"/>             
                    <input type="submit" value="Search" class="search-btn" />
                </form>
            </div>
        </div>

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
                    <td>${UserController.getIs_active() == 1 ? "Active" : "No Active"}</td>
                    <td>
                        <a href="AdminPage/UserDetail.jsp?action=view&userId=${UserController.getUser_id()}">View</a>
                        <a href="AdminPage/UserDetail.jsp?action=edit&userId=${UserController.getUser_id()}">Edit</a>
                    </td>

                </tr>   
            </c:forEach>
        </table>
        <!-- Thay đổi Add User thành nút -->
        <form action="detail/userDetail.jsp" method="get">
            <input type="hidden" name="action" value="add"/>
            <button type="submit" class="add-user-btn">Add User</button>
        </form>

        <!-- Phân trang -->
        <c:if test="${totalPages > 1}">
            <div class="pagination">
                <c:if test="${currentPage > 1}">
                    <a href="UserController?page=${currentPage - 1}">&laquo; Prev</a>
                </c:if>
                <c:forEach begin="1" end="${totalPages}" var="page">
                    <c:if test="${page == currentPage}">
                        <span class="current">${page}</span>
                    </c:if>
                    <c:if test="${page != currentPage}">
                        <a href="UserController?page=${page}">${page}</a>
                    </c:if>
                </c:forEach>
                <c:if test="${currentPage < totalPages}">
                    <a href="UserController?page=${currentPage + 1}">Next &raquo;</a>
                </c:if>
            </div>
        </c:if>
    </body>
</html>

<%-- 
    Document   : UserList
    Created on : Mar 23, 2025, 6:47:17 PM
    Author     : LENOVO
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Danh Sách Người Dùng - Quản Trị</title>
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
        </style>
    </head>
    <body>
        <div class="container mt-4">
            <h1 class="mb-4">Danh Sách Người Dùng</h1>

            <!-- Form Tìm kiếm, Lọc và Sắp xếp -->
            <form method="get" action="AdminUserListController" class="filter-form mb-4">
                <input type="text" name="keyword" class="form-control" placeholder="Tìm kiếm..." value="${keyword}">

                <select name="gender" class="form-select">
                    <option value="">--Giới tính--</option>
                    <option value="Male" ${gender == 'Male' ? 'selected' : ''}>Male</option>
                    <option value="Female" ${gender == 'Female' ? 'selected' : ''}>Female</option>
                </select>

                <select name="role" class="form-select">
                    <option value="">--Vai trò--</option>
                    <option value="Admin" ${role == 'Admin' ? 'selected' : ''}>Admin</option>
                    <option value="User" ${role == 'User' ? 'selected' : ''}>User</option>
                </select>

                <select name="status" class="form-select">
                    <option value="">--Trạng thái--</option>
                    <option value="Active" ${status == 'Active' ? 'selected' : ''}>Active</option>
                    <option value="Inactive" ${status == 'Inactive' ? 'selected' : ''}>Inactive</option>
                </select>

                <select name="sortField" class="form-select">
                    <option value="user_id" ${sortField == 'user_id' ? 'selected' : ''}>ID</option>
                    <option value="full_name" ${sortField == 'full_name' ? 'selected' : ''}>Full Name</option>
                    <option value="gender" ${sortField == 'gender' ? 'selected' : ''}>Gender</option>
                    <option value="address" ${sortField == 'address' ? 'selected' : ''}>Address</option>
                    <option value="email" ${sortField == 'email' ? 'selected' : ''}>Email</option>
                    <option value="phone_number" ${sortField == 'phone_number' ? 'selected' : ''}>Phone</option>
                    <option value="role_name" ${sortField == 'role_name' ? 'selected' : ''}>Role</option>
                    <option value="status" ${sortField == 'status' ? 'selected' : ''}>Status</option>
                </select>

                <select name="sortDirection" class="form-select">
                    <option value="ASC" ${sortDirection == 'ASC' ? 'selected' : ''}>Tăng dần</option>
                    <option value="DESC" ${sortDirection == 'DESC' ? 'selected' : ''}>Giảm dần</option>
                </select>

                <button type="submit" class="btn btn-primary">Áp dụng</button>
            </form>

            <!-- Bảng hiển thị danh sách User -->
            <table class="table table-bordered table-striped">
                <thead>
                    <tr>
                        <th>User ID</th>
                        <th>Full Name</th>
                        <th>Gender</th>
                        <th>Address</th>
                        <th>Email</th>
                        <th>Phone Number</th>
                        <th>Role</th>
                        <th>Status</th>
                    </tr>
                </thead>
                <tbody>
                    <c:choose>
                        <c:when test="${not empty userList}">
                            <c:forEach var="user" items="${userList}">
                                <tr>
                                    <td>${user.userId}</td>
                                    <td>${user.fullName}</td>
                                    <td>${user.gender}</td>
                                    <td>${user.address}</td>
                                    <td>${user.email}</td>
                                    <td>${user.phoneNumber}</td>
                                    <td>${user.role}</td>
                                    <td>${user.status}</td>
                                </tr>
                            </c:forEach>
                        </c:when>
                        <c:otherwise>
                            <tr>
                                <td colspan="8" class="text-center">Không có dữ liệu người dùng nào.</td>
                            </tr>
                        </c:otherwise>
                    </c:choose>
                </tbody>
            </table>

            <!-- Thanh phân trang -->
            <nav aria-label="Page navigation example">
                <ul class="pagination justify-content-center">
                    <!-- Nút Previous -->
                    <li class="page-item ${currentPage == 1 ? 'disabled' : ''}">
                        <a class="page-link" href="AdminUserListController?page=${currentPage - 1}
                           &pageSize=${pageSize}
                           &keyword=${keyword}
                           &gender=${gender}
                           &role=${role}
                           &status=${status}
                           &sortField=${sortField}
                           &sortDirection=${sortDirection}">Previous</a>
                    </li>

                    <!-- Hiển thị số trang -->
                    <c:forEach var="i" begin="1" end="${totalPages}">
                        <li class="page-item ${i == currentPage ? 'active' : ''}">
                            <a class="page-link" href="AdminUserListController?page=${i}
                               &pageSize=${pageSize}
                               &keyword=${keyword}
                               &gender=${gender}
                               &role=${role}
                               &status=${status}
                               &sortField=${sortField}
                               &sortDirection=${sortDirection}">${i}</a>
                        </li>
                    </c:forEach>

                    <!-- Nút Next -->
                    <li class="page-item ${currentPage == totalPages ? 'disabled' : ''}">
                        <a class="page-link" href="AdminUserListController?page=${currentPage + 1}
                           &pageSize=${pageSize}
                           &keyword=${keyword}
                           &gender=${gender}
                           &role=${role}
                           &status=${status}
                           &sortField=${sortField}
                           &sortDirection=${sortDirection}">Next</a>
                    </li>
                </ul>
            </nav>
        </div>

        <!-- Bootstrap Bundle JS -->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>

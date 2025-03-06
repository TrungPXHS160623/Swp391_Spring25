<%-- 
    Document   : userDetail
    Created on : Mar 5, 2025, 9:56:22 PM
    Author     : LENOVO
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="java.util.*, Entity.User" %>
<%@ page import="Dao.UserDAO" %>

<%
    String action = request.getParameter("action");
    String userId = request.getParameter("userId");
    User user = null;

    if (userId != null) {
        UserDAO userDAO = new UserDAO();
        user = userDAO.getUserById(Integer.parseInt(userId));
    }
%>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>User Details</title>
        <style>
            body {
                font-family: Arial, sans-serif;
                background-color: #b0d4d4;
            }
            .container {
                width: 400px;
                padding: 20px;
                background-color: #74a9a9;
                margin: 50px auto;
                border-radius: 10px;
                box-shadow: 2px 2px 10px #555;
            }
            .container label {
                display: block;
                margin-top: 10px;
                font-weight: bold;
            }
            .container input, .container select {
                width: 100%;
                padding: 8px;
                margin-top: 5px;
                border-radius: 5px;
                border: 1px solid #ccc;
            }
            .container button {
                margin-top: 15px;
                padding: 10px;
                width: 100%;
                background-color: #5c8a8a;
                color: white;
                border: none;
                border-radius: 5px;
                cursor: pointer;
            }
            .container button:hover {
                background-color: #417373;
            }
            .avatar-upload {
                border: 2px dashed #ccc;
                padding: 20px;
                text-align: center;
                cursor: pointer;
                background-color: #f8f8f8;
                position: relative;
            }
            .avatar-upload img {
                max-width: 100%;
                display: none;
                border-radius: 5px;
            }
        </style>
    </head>
    <body>
        <div class="container">
            <h2>User Details</h2>

            <c:choose>
                <%-- Xem Chi Tiết --%>
                <c:when test="${param.action == 'view'}">
                    <p><strong>Full Name:</strong> <%= user.getFull_name() %></p>
                    <p><strong>Gender:</strong> <%= user.getGender() %></p>
                    <p><strong>Email:</strong> <%= user.getEmail() %></p>
                    <p><strong>Phone:</strong> <%= user.getPhone_number() %></p>
                    <p><strong>Role:</strong> <%= user.getRole_name() %></p>
                    <p><strong>Address:</strong> <%= user.getAddress() %></p>
                    <p><strong>Status:</strong> <%= user.getIs_active() %></p>
                    <a href="userDetail.jsp?action=edit&userId=${param.userId}">Edit</a>
                </c:when>

                <%-- Chỉnh Sửa hoặc Thêm Mới --%>
                <c:when test="${param.action == 'edit' || param.action == 'add'}">
                    <form action="UserServlet" method="post" enctype="multipart/form-data">
                        <input type="hidden" name="action" value="${param.action}" />
                        <input type="hidden" name="userId" value="${param.userId}" />

                        <label>Avatar:</label>
                        <div class="avatar-upload" id="drop-area">
                            <p>Drag & drop or click to upload</p>
                            <input type="file" name="avatar" accept="image/*" id="avatarInput" hidden>
                            <img id="avatarPreview">
                            
                        </div>

                        <label>Full name:</label>
                        <input type="text" name="fullName" value="<%= (user != null) ? user.getFull_name() : "" %>" required>

                        <label>Gender:</label>
                        <select name="gender">
                            <option value="Male" <%= (user != null && "Male".equals(user.getGender())) ? "selected" : "" %>>Male</option>
                            <option value="Female" <%= (user != null && "Female".equals(user.getGender())) ? "selected" : "" %>>Female</option>
                        </select>

                        <label>Email:</label>
                        <input type="email" name="email" value="<%= (user != null) ? user.getEmail() : "" %>" required>

                        <label>Phone:</label>
                        <input type="text" name="phone" value="<%= (user != null) ? user.getPhone_number() : "" %>" required>

                        <label>Role:</label>
                        <select name="role">
                            <option value="User" <%= (user != null && "User".equals(user.getRole_name())) ? "selected" : "" %>>User</option>
                            <option value="Admin" <%= (user != null && "Admin".equals(user.getRole_name())) ? "selected" : "" %>>Admin</option>
                        </select>

                        <label>Address:</label>
                        <input type="text" name="address" value="<%= (user != null) ? user.getAddress() : "" %>" required>

                        <label>Status:</label>
                        <select name="status">
                            <option value="1" <%= (user != null && user.getIs_active() == 1) ? "selected" : "" %>>Active</option>
                            <option value="0" <%= (user != null && user.getIs_active() == 0) ? "selected" : "" %>>Inactive</option>
                        </select>


                        <button type="submit" name="action" value="${param.action == 'edit' ? 'save' : 'add'}">
                            ${param.action == 'edit' ? 'Save Changes' : 'Add User'}
                        </button>
                    </form>
                </c:when>
            </c:choose>

        </div>

        <script>
            const dropArea = document.getElementById('drop-area');
            const avatarInput = document.getElementById('avatarInput');
            const avatarPreview = document.getElementById('avatarPreview');

            dropArea.addEventListener('click', () => avatarInput.click());
            avatarInput.addEventListener('change', handleFiles);

            function handleFiles() {
                const file = avatarInput.files[0];
                if (file) {
                    const reader = new FileReader();
                    reader.onload = function (e) {
                        avatarPreview.src = e.target.result;
                        avatarPreview.style.display = "block";
                    }
                    reader.readAsDataURL(file);
                }
            }
        </script>

    </body>
</html>

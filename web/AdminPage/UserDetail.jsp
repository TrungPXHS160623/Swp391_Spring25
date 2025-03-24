<%-- 
    Document   : UserDetail
    Created on : Mar 23, 2025, 6:47:23 PM
    Author     : LENOVO
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>User Detail</title>
        <!-- Bootstrap CSS -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <style>
            .drag-drop-zone {
                border: 2px dashed #ccc;
                padding: 20px;
                text-align: center;
                color: #999;
                cursor: pointer;
                margin-bottom: 15px;
            }
        </style>
        <script>
            // JavaScript để hỗ trợ kéo thả file (cơ bản)
            function initDragDrop() {
                var zone = document.getElementById("dragDropZone");
                zone.addEventListener("dragover", function (e) {
                    e.preventDefault();
                    zone.style.borderColor = "#333";
                });
                zone.addEventListener("dragleave", function (e) {
                    e.preventDefault();
                    zone.style.borderColor = "#ccc";
                });
                zone.addEventListener("drop", function (e) {
                    e.preventDefault();
                    zone.style.borderColor = "#ccc";
                    var files = e.dataTransfer.files;
                    if (files.length > 0) {
                        document.getElementById("avatarFile").files = files;
                        document.getElementById("avatarPreview").src = URL.createObjectURL(files[0]);
                    }
                });
            }
            window.onload = initDragDrop;
        </script>
    </head>
    <body>
        <div class="container mt-4">
            <h2>User Detail</h2>
            <c:if test="${not empty message}">
                <div class="alert alert-info">${message}</div>
            </c:if>
            <form action="AdminUserDetailController" method="post" enctype="multipart/form-data">
                <input type="hidden" name="action" value="${action}" />
                <c:if test="${action != 'add'}">
                    <input type="hidden" name="userId" value="${user.userId}" />
                </c:if>

                <!-- Avatar: hiển thị ảnh, cho phép kéo thả file hoặc nhập URL -->
                <div class="mb-3">
                    <label for="avatarUrl" class="form-label">Avatar URL:</label>
                    <input type="text" class="form-control" name="avatarUrl" id="avatarUrl" value="${user.avatarUrl}" placeholder="Nhập URL ảnh nếu không dùng kéo thả">
                </div>
                <div id="dragDropZone" class="drag-drop-zone">
                    Kéo thả ảnh vào đây hoặc nhấn để chọn file
                    <input type="file" name="avatarFile" id="avatarFile" class="form-control" style="display: none;" onchange="document.getElementById('avatarPreview').src = window.URL.createObjectURL(this.files[0])">
                </div>
                <div class="mb-3">
                    <img id="avatarPreview" src="${user.avatarUrl}" alt="Avatar Preview" style="max-width: 150px; max-height: 150px;">
                </div>

                <!-- Các trường thông tin khác -->
                <div class="mb-3">
                    <label class="form-label">Full Name:</label>
                    <input type="text" class="form-control" name="fullName" value="${user.fullName}" required>
                </div>
                <div class="mb-3">
                    <label class="form-label">Gender:</label>
                    <select name="gender" class="form-select">
                        <option value="Male" ${user.gender == 'Male' ? 'selected' : ''}>Male</option>
                        <option value="Female" ${user.gender == 'Female' ? 'selected' : ''}>Female</option>
                        <option value="Other" ${user.gender == 'Other' ? 'selected' : ''}>Other</option>
                    </select>
                </div>
                <div class="mb-3">
                    <label class="form-label">Email:</label>
                    <input type="email" class="form-control" name="email" value="${user.email}" required>
                </div>
                <div class="mb-3">
                    <label class="form-label">Password:</label>
                    <input type="password" class="form-control" name="password" value="${user.password}" required>
                </div>
                <div class="mb-3">
                    <label class="form-label">Phone:</label>
                    <input type="text" class="form-control" name="phone" value="${user.phoneNumber}" required>
                </div>
                <div class="mb-3">
                    <label class="form-label">Role:</label>
                    <select name="role" class="form-select">
                        <option value="User" ${user.role == 'User' ? 'selected' : ''}>User</option>
                        <option value="Seller" ${user.role == 'Seller' ? 'selected' : ''}>Seller</option>
                        <option value="Marketing" ${user.role == 'Marketing' ? 'selected' : ''}>Marketing</option>
                        <option value="Admin" ${user.role == 'Admin' ? 'selected' : ''}>Admin</option>
                    </select>
                </div>
                <div class="mb-3">
                    <label class="form-label">Address:</label>
                    <input type="text" class="form-control" name="address" value="${user.address}" required>
                </div>
                <div class="mb-3">
                    <label class="form-label">Status:</label>
                    <select name="status" class="form-select">
                        <option value="Active" ${user.status == 'Active' ? 'selected' : ''}>Active</option>
                        <option value="NotActive" ${user.status == 'NotActive' ? 'selected' : ''}>NotActive</option>
                    </select>
                </div>

                <button type="submit" class="btn btn-success">Lưu thông tin</button>
                <a href="AdminUserListController" class="btn btn-secondary">Quay lại danh sách</a>
            </form>
        </div>

        <!-- Bootstrap Bundle JS -->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>

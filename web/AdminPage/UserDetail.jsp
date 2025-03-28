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
            .read-only {
                background-color: #e9ecef;
            }
            .drag-drop-zone {
                border: 2px dashed #ccc;
                padding: 20px;
                text-align: center;
                color: #999;
                cursor: pointer;
                margin-bottom: 15px;
            }
            .drag-drop-zone:hover {
                background-color: #f8f9fa;
            }
        </style>
        <script>
            function initDragDrop() {
                var zone = document.getElementById("dragDropZone");
                if (zone) {
                    zone.addEventListener("click", function (e) {
                        document.getElementById("avatarFile").click();
                    });
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
                <c:if test="${action eq 'edit'}">
                    <input type="hidden" name="userId" value="${user.userId}" />
                </c:if>

                <!-- Avatar URL -->
                <div class="mb-3">
                    <label for="avatarUrl" class="form-label">Avatar URL:</label>
                    <input type="text" class="form-control ${action eq 'view' ? 'read-only' : ''}" name="avatarUrl" id="avatarUrl" value="${user.avatarUrl}" 
                           ${action eq 'view' ? 'readonly' : ''}>
                </div>

                <!-- Drag and Drop Zone (chỉ hiển thị khi không ở chế độ view) -->
                <c:if test="${action ne 'view'}">
                    <div id="dragDropZone" class="drag-drop-zone">
                        Kéo thả ảnh vào đây hoặc nhấn để chọn file
                        <input type="file" name="avatarFile" id="avatarFile" class="form-control" style="display: none;" 
                               onchange="document.getElementById('avatarPreview').src = window.URL.createObjectURL(this.files[0])">
                    </div>
                </c:if>

                <!-- Hiển thị ảnh preview -->
                <div class="mb-3">
                    <img id="avatarPreview" src="${user.avatarUrl}" alt="Avatar Preview" style="max-width: 150px; max-height: 150px;">
                </div>

                <!-- Các trường thông tin khác -->
                <div class="mb-3">
                    <label for="fullName" class="form-label">Full Name:</label>
                    <input type="text" class="form-control ${action eq 'view' ? 'read-only' : ''}" name="fullName" id="fullName" value="${user.fullName}" 
                           ${action eq 'view' ? 'readonly' : ''}>
                </div>
                <div class="mb-3">
                    <label for="gender" class="form-label">Gender:</label>
                    <select name="gender" id="gender" class="form-select" ${action eq 'view' ? 'disabled' : ''}>
                        <option value="Male" ${user.gender eq 'Male' ? 'selected' : ''}>Male</option>
                        <option value="Female" ${user.gender eq 'Female' ? 'selected' : ''}>Female</option>
                        <option value="Other" ${user.gender eq 'Other' ? 'selected' : ''}>Other</option>
                    </select>
                </div>
                <div class="mb-3">
                    <label for="email" class="form-label">Email:</label>
                    <input type="email" class="form-control ${action eq 'view' ? 'read-only' : ''}" name="email" id="email" value="${user.email}" 
                           ${action eq 'view' ? 'readonly' : ''}>
                </div>
                <div class="mb-3">
                    <label for="password" class="form-label">Password:</label>
                    <input type="password" class="form-control ${action eq 'view' ? 'read-only' : ''}" name="password" id="password" value="${user.password}" 
                           ${action eq 'view' ? 'readonly' : ''}>
                </div>
                <div class="mb-3">
                    <label for="phone" class="form-label">Phone:</label>
                    <input type="text" class="form-control ${action eq 'view' ? 'read-only' : ''}" name="phone" id="phone" value="${user.phoneNumber}" 
                           ${action eq 'view' ? 'readonly' : ''}>
                </div>
                <div class="mb-3">
                    <label for="role" class="form-label">Role:</label>
                    <select name="role" id="role" class="form-select" ${action eq 'view' ? 'disabled' : ''}>
                        <option value="User" ${user.role eq 'User' ? 'selected' : ''}>User</option>
                        <option value="Seller" ${user.role eq 'Seller' ? 'selected' : ''}>Seller</option>
                        <option value="Marketing" ${user.role eq 'Marketing' ? 'selected' : ''}>Marketing</option>
                        <option value="Admin" ${user.role eq 'Admin' ? 'selected' : ''}>Admin</option>
                        <!-- Các lựa chọn khác nếu cần -->
                    </select>
                </div>
                <div class="mb-3">
                    <label for="address" class="form-label">Address:</label>
                    <input type="text" class="form-control ${action eq 'view' ? 'read-only' : ''}" name="address" id="address" value="${user.address}" 
                           ${action eq 'view' ? 'readonly' : ''}>
                </div>
                <div class="mb-3">
                    <label for="status" class="form-label">Status:</label>
                    <select name="status" id="status" class="form-select" ${action eq 'view' ? 'disabled' : ''}>
                        <option value="Active" ${user.status eq 'Active' ? 'selected' : ''}>Active</option>
                        <option value="NotActive" ${user.status eq 'NotActive' ? 'selected' : ''}>NotActive</option>
                    </select>
                </div>
                <c:if test="${action eq 'edit'}">
                    <button type="submit" class="btn btn-success">Save</button>
                </c:if>
                <c:if test="${action eq 'add'}">
                    <button type="submit" class="btn btn-success">Save</button>
                </c:if>
                <a href="AdminUserListController" class="btn btn-secondary">Back to List</a>
            </form>
        </div>

        <!-- Bootstrap Bundle JS -->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>

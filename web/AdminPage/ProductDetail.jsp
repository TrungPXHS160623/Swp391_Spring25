<%-- 
    Document   : ProductDetail
    Created on : Mar 23, 2025, 6:44:49 PM
    Author     : LENOVO
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Product Detail - Admin</title>
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
            .media-preview {
                max-width: 150px;
                max-height: 150px;
                margin-bottom: 5px;
                border: 1px solid #ddd;
                border-radius: 3px;
            }
        </style>
        <script>
            // Hàm để khởi tạo drag & drop cho media upload
            function initDragDrop() {
                var zone = document.getElementById("mediaDragDropZone");
                if (zone) {
                    zone.addEventListener("click", function () {
                        document.getElementById("mediaFile").click();
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
                            document.getElementById("mediaFile").files = files;
                            // Hiển thị preview cho file đầu tiên
                            var reader = new FileReader();
                            reader.onload = function (e) {
                                document.getElementById("mediaPreview").src = e.target.result;
                            };
                            reader.readAsDataURL(files[0]);
                        }
                    });
                }
            }
            window.onload = initDragDrop;
        </script>
    </head>
    <body>
        <div class="container mt-4">
            <h2>Product Detail - Admin</h2>
            <c:if test="${not empty message}">
                <div class="alert alert-info">${message}</div>
            </c:if>
            <form action="AdminProductDetailController" method="post" enctype="multipart/form-data">
                <!-- Ghi nhận action (view, edit, add) -->
                <input type="hidden" name="action" value="${action}">
                <c:if test="${action ne 'add'}">
                    <input type="hidden" name="productId" value="${product.productId}">
                </c:if>

                <!-- Title -->
                <div class="mb-3">
                    <label for="productName" class="form-label">Title (Product Name):</label>
                    <input type="text" class="form-control ${action eq 'view' ? 'read-only' : ''}" 
                           name="productName" id="productName" value="${product.productName}" 
                           ${action eq 'view' ? 'readonly' : ''}>
                </div>

                <!-- Category (combobox) -->
                <div class="mb-3">
                    <label for="subcategoryId" class="form-label">Category:</label>
                    <select name="subcategoryId" id="subcategoryId" class="form-select" 
                            ${action eq 'view' ? 'disabled' : ''}>
                        <c:forEach var="cat" items="${categoryList}">
                            <option value="${cat.subcategoryId}" 
                                    ${cat.subcategoryId == product.subcategoryId ? 'selected' : ''}>
                                ${cat.subcategoryName}
                            </option>
                        </c:forEach>
                    </select>
                </div>

                <!-- Quantity -->
                <div class="mb-3">
                    <label for="stockQuantity" class="form-label">Quantity:</label>
                    <input type="number" class="form-control ${action eq 'view' ? 'read-only' : ''}" 
                           name="stockQuantity" id="stockQuantity" value="${product.stockQuantity}" 
                           ${action eq 'view' ? 'readonly' : ''}>
                </div>

                <!-- Price & Sale Price -->
                <div class="row mb-3">
                    <div class="col">
                        <label for="price" class="form-label">List Price:</label>
                        <input type="number" step="0.01" class="form-control ${action eq 'view' ? 'read-only' : ''}" 
                               name="price" id="price" value="${product.price}" 
                               ${action eq 'view' ? 'readonly' : ''}>
                    </div>
                    <div class="col">
                        <label for="discountPrice" class="form-label">Sale Price:</label>
                        <input type="number" step="0.01" class="form-control ${action eq 'view' ? 'read-only' : ''}" 
                               name="discountPrice" id="discountPrice" value="${product.discountPrice}" 
                               ${action eq 'view' ? 'readonly' : ''}>
                    </div>
                </div>
                <!-- Discount Percentage có thể được tính tự động sau khi lưu, không cần nhập -->

                <!-- Description -->
                <div class="mb-3">
                    <label for="description" class="form-label">Description:</label>
                    <textarea class="form-control ${action eq 'view' ? 'read-only' : ''}" 
                              name="description" id="description" rows="4"
                              ${action eq 'view' ? 'readonly' : ''}>${product.description}</textarea>
                </div>

                <!-- Featured Checkbox -->
                <div class="mb-3 form-check">
                    <input type="checkbox" class="form-check-input" name="featuredFlag" id="featuredFlag"
                           <c:if test="${product.featuredFlag}">checked</c:if>
                           <c:if test="${action eq 'view'}">disabled</c:if>>
                           <label class="form-check-label" for="featuredFlag">Featured</label>
                    </div>

                    <!-- Media Section: Chỉ hiển thị ở chế độ add/edit -->
                <c:if test="${action ne 'view'}">
                    <div class="mb-3">
                        <label class="form-label">Add Media (Image/Video):</label>
                        <div id="mediaDragDropZone" class="drag-drop-zone">
                            Drag & Drop file here or click to select file
                            <input type="file" name="mediaFiles" id="mediaFile" class="form-control" style="display: none;" multiple>
                        </div>
                        <div class="mb-2">
                            <!-- Preview của file đầu tiên, sẽ được cập nhật qua JavaScript -->
                            <img id="mediaPreview" src="" alt="Media Preview" class="media-preview">
                        </div>
                        <!-- Nếu muốn cho nhập URL cho media -->
                        <div class="mb-3">
                            <label for="mediaUrl" class="form-label">Or enter Media URL (e.g., YouTube link):</label>
                            <input type="text" class="form-control" name="mediaUrl" id="mediaUrl" placeholder="https://">
                        </div>
                        <!-- Input cho mô tả media; nếu hỗ trợ nhiều media, cần xử lý danh sách -->
                        <div class="mb-3">
                            <label for="mediaDescription" class="form-label">Media Description:</label>
                            <input type="text" class="form-control" name="mediaDescription" id="mediaDescription" placeholder="Enter description for this media">
                        </div>
                    </div>
                </c:if>

                <c:if test="${action eq 'edit' || action eq 'add'}">
                    <button type="submit" class="btn btn-success">Save</button>
                </c:if>
                <a href="AdminProductListController" class="btn btn-secondary">Back to List</a>
            </form>
        </div>

        <!-- Bootstrap Bundle JS -->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>

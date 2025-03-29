<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>
            <c:choose>
                <c:when test="${mode == 'create'}">Add New Post</c:when>
                <c:when test="${mode == 'edit'}">Edit Post</c:when>
                <c:otherwise>View Post</c:otherwise>
            </c:choose>
        </title>
        <!-- Bootstrap CSS -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
        <!-- Font Awesome -->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
        <!-- CKEditor 5 -->
        <script src="https://cdn.ckeditor.com/ckeditor5/36.0.1/classic/ckeditor.js"></script>
        <style>
            /* General Styles */
            body {
                font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
                background-color: #f8f9fa;
                color: #343a40;
            }
            .container {
                max-width: 960px;
            }
            h2 {
                color: #495057;
                margin-bottom: 0.75rem;
            }
            /* Card Styles */
            .card {
                border: none;
                box-shadow: 0 0.25rem 0.5rem rgba(0, 0, 0, 0.05);
                border-radius: 0.5rem;
            }
            .card-body {
                padding: 1.5rem;
            }
            /* Form Styles */
            .form-label {
                font-weight: 500;
                color: #495057;
            }
            .form-control,
            .form-select {
                border-radius: 0.3rem;
                border: 1px solid #ced4da;
                padding: 0.5rem 0.75rem;
                font-size: 1rem;
            }
            .form-control:focus,
            .form-select:focus {
                border-color: #80bdff;
                box-shadow: 0 0 0 0.2rem rgba(0, 123, 255, 0.25);
            }
            .required-field::after {
                content: "*";
                color: red;
                margin-left: 4px;
            }
            /* Thumbnail Styles */
            .post-thumbnail {
                max-width: 100%;
                height: auto;
                border-radius: 0.5rem;
                margin-bottom: 1rem;
                box-shadow: 0 0.125rem 0.25rem rgba(0, 0, 0, 0.08);
            }
            /* Status Badge */
            .status-badge {
                padding: 0.4rem 0.75rem;
                border-radius: 1rem;
                font-size: 0.85rem;
            }
            .status-active {
                background-color: #28a745;
                color: white;
            }
            .status-inactive {
                background-color: #dc3545;
                color: white;
            }
            /* Editor Styles */
            .ck-editor__editable {
                min-height: 200px;
                border-radius: 0.3rem;
                border: 1px solid #ced4da;
            }
            /* Created Info */
            .created-info {
                font-size: 0.9rem;
                color: #6c757d;
                margin-top: 1rem;
            }
            /* Form Switch */
            .form-check-label {
                color: #495057;
            }
            .form-switch .form-check-input {
                width: 3em;
                height: 1.5em;
                margin-right: 0.5em;
                background-color: #adb5bd;
                border-color: #adb5bd;
                border-radius: 2em;
                transition: background-color 0.2s ease-in-out, border-color 0.2s ease-in-out;
            }
            .form-switch .form-check-input:checked {
                background-color: #28a745;
                border-color: #28a745;
            }
            .form-switch .form-check-input:focus {
                box-shadow: none;
            }
            /* Button Styles */
            .btn {
                border-radius: 0.3rem;
                padding: 0.6rem 1.2rem;
                font-size: 1rem;
                font-weight: 500;
                transition: all 0.2s ease-in-out;
            }
            .btn-primary {
                background-color: #007bff;
                border-color: #007bff;
            }
            .btn-primary:hover {
                background-color: #0069d9;
                border-color: #0062cc;
            }
            .btn-secondary {
                background-color: #6c757d;
                border-color: #6c757d;
            }
            .btn-secondary:hover {
                background-color: #5a6268;
                border-color: #545b62;
            }
            .btn-warning {
                background-color: #ffc107;
                border-color: #ffc107;
                color: #212529;
            }
            .btn-warning:hover {
                background-color: #e0a800;
                border-color: #d39e00;
            }
            /*Utility classes*/
            .mb-3 {
                margin-bottom: 1.5rem !important;
            }
        </style>
    </head>
    <body>
        <div class="container mt-4">
            <div class="row">
                <div class="col-md-12">
                    <!-- Page Header -->
                    <h2>
                        <c:choose>
                            <c:when test="${mode == 'create'}"><i class="fas fa-plus-circle"></i> Add New Post</c:when>
                            <c:when test="${mode == 'edit'}"><i class="fas fa-edit"></i> Edit Post</c:when>
                            <c:otherwise><i class="fas fa-eye"></i> View Post</c:otherwise>
                        </c:choose>
                    </h2>
                    
                    <!-- Breadcrumb -->
                    <nav aria-label="breadcrumb" class="mb-4">
                        <ol class="breadcrumb">
                            <li class="breadcrumb-item"><a href="${pageContext.request.contextPath}/AdminPage/dashboard.jsp">Dashboard</a></li>
                            <li class="breadcrumb-item"><a href="${pageContext.request.contextPath}/PostListController">Posts</a></li>
                            <li class="breadcrumb-item active">
                                <c:choose>
                                    <c:when test="${mode == 'create'}">Add New Post</c:when>
                                    <c:when test="${mode == 'edit'}">Edit Post</c:when>
                                    <c:otherwise>View Post</c:otherwise>
                                </c:choose>
                            </li>
                        </ol>
                    </nav>
                    
                    <!-- Main Form -->
                    <div class="card">
                        <div class="card-body">
                            <form action="${pageContext.request.contextPath}/PostDetailController" method="post" enctype="multipart/form-data">
                                <input type="hidden" name="action" value="save">
                                
                                <c:if test="${post != null}">
                                    <input type="hidden" name="postId" value="${post.postId}">
                                </c:if>
                                
                                <div class="row">
                                    <!-- Left Column -->
                                    <div class="col-md-8">
                                        <!-- Title -->
                                        <div class="mb-3">
                                            <label for="title" class="form-label required-field">Title</label>
                                            <input type="text" class="form-control" id="title" name="title" 
                                                   value="${post.title}" required
                                                   <c:if test="${mode == 'view'}">readonly</c:if>>
                                        </div>
                                        
                                        <!-- Content -->
                                        <div class="mb-3">
                                            <label for="editor" class="form-label required-field">Content</label>
                                            <c:choose>
                                                <c:when test="${mode == 'view'}">
                                                    <div class="form-control content-display p-3" style="min-height:300px; overflow-y:auto;">
                                                        ${post.content}
                                                    </div>
                                                </c:when>
                                                <c:otherwise>
                                                    <textarea id="editor" name="content" class="form-control" rows="10">${post.content}</textarea>
                                                </c:otherwise>
                                            </c:choose>
                                        </div>
                                    </div>
                                    
                                    <!-- Right Column -->
                                    <div class="col-md-4">
                                        <!-- Thumbnail -->
                                        <div class="mb-3">
                                            <label for="thumbnail" class="form-label">Thumbnail</label>
                                            
                                            <c:if test="${post != null && not empty post.thumbnailUrl}">
                                                <img src="${pageContext.request.contextPath}/${post.thumbnailUrl}" 
                                                     alt="Post Thumbnail" class="post-thumbnail img-fluid d-block">
                                                <input type="hidden" name="existingThumbnail" value="${post.thumbnailUrl}">
                                            </c:if>
                                            
                                            <c:if test="${mode != 'view'}">
                                                <input type="file" class="form-control mt-2" id="thumbnail" name="thumbnail" accept="image/*">
                                                <small class="form-text text-muted">Recommended size: 1200x630 pixels, max 2MB</small>
                                            </c:if>
                                        </div>
                                        
                                        <!-- Category -->
                                        <div class="mb-3">
                                            <label for="categoryId" class="form-label required-field">Category</label>
                                            <select class="form-select" id="categoryId" name="categoryId" required
                                                    <c:if test="${mode == 'view'}">disabled</c:if>>
                                                <option value="">Select Category</option>
                                                <c:forEach items="${categories}" var="category">
                                                    <option value="${category.categoryId}" 
                                                            <c:if test="${post.categoryId == category.categoryId}">selected</c:if>>
                                                        ${category.categoryName}
                                                    </option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                        
                                        <!-- Status -->
                                        <div class="mb-3">
                                            <label class="form-label required-field">Status</label>
                                            <div class="form-check form-switch">
                                                <input class="form-check-input" type="checkbox" id="status" name="status" value="true" 
                                                       ${post.status ? 'checked' : ''} ${mode == 'view' ? 'disabled' : ''}>
                                                <label class="form-check-label" for="status">
                                                    ${post.status ? 'Active' : 'Inactive'}
                                                </label>
                                            </div>
                                            <small class="text-muted">Active posts are visible to users</small>
                                        </div>
                                        
                                        <!-- Remove Featured section -->
                                        
                                        <!-- Author Info (if viewing) -->
                                        <c:if test="${post != null}">
                                            <div class="created-info">
                                                <p><strong>Author:</strong> ${post.authorName}</p>
                                                <p><strong>Created:</strong> <fmt:formatDate value="${post.createdAt}" pattern="dd-MM-yyyy HH:mm" /></p>
                                            </div>
                                        </c:if>
                                    </div>
                                </div>
                                
                                <!-- Action Buttons -->
                                <div class="mt-4 d-flex justify-content-between">
                                    <a href="${pageContext.request.contextPath}/PostListController" class="btn btn-secondary">
                                        <i class="fas fa-arrow-left"></i> Back to List
                                    </a>
                                    
                                    <div>
                                        <c:if test="${mode == 'view'}">
                                            <a href="${pageContext.request.contextPath}/PostDetailController?action=edit&id=${post.postId}" class="btn btn-warning">
                                                <i class="fas fa-edit"></i> Edit
                                            </a>
                                        </c:if>
                                        
                                        <c:if test="${mode != 'view'}">
                                            <button type="submit" class="btn btn-primary">
                                                <i class="fas fa-save"></i> Save
                                            </button>
                                        </c:if>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        
        <!-- Bootstrap JS with Popper -->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
        
        <c:if test="${mode != 'view'}">
            <!-- Initialize CKEditor -->
            <script>
                ClassicEditor
                    .create(document.querySelector('#editor'), {
                        toolbar: ['heading', '|', 'bold', 'italic', 'link', 'bulletedList', 'numberedList', '|', 'outdent', 'indent', '|', 'imageUpload', 'blockQuote', 'insertTable', 'mediaEmbed', 'undo', 'redo']
                    })
                    .catch(error => {
                        console.error(error);
                    });
            </script>
        </c:if>
    </body>
</html>

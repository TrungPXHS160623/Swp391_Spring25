<%-- 
    Document   : blogedit
    Created on : Mar 6, 2025, 10:53:05 AM
    Author     : KieuVietPhuoc
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Chỉnh sửa bài viết - Quản lý blog</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.8.0/font/bootstrap-icons.css">
    <style>
        .sidebar {
            height: 100vh;
            background-color: #343a40;
            color: white;
            position: fixed;
        }
        .content {
            margin-left: 250px;
        }
        .nav-link {
            color: rgba(255, 255, 255, 0.8);
        }
        .nav-link:hover {
            color: white;
        }
        .nav-link.active {
            background-color: #0d6efd;
            color: white;
        }
        .thumbnail-preview {
            max-width: 200px;
            max-height: 150px;
            object-fit: cover;
        }
        @media (max-width: 768px) {
            .sidebar {
                position: static;
                height: auto;
            }
            .content {
                margin-left: 0;
            }
        }
    </style>
</head>
<body>
    <div class="container-fluid">
        <div class="row">
            <!-- Sidebar -->
            <!-- Main Content -->
            <div class=" py-3">
                <nav aria-label="breadcrumb">
                    <ol class="breadcrumb">
                        <li class="breadcrumb-item"><a href="blog-list">Bài viết</a></li>
                        <li class="breadcrumb-item active" aria-current="page">Chỉnh sửa</li>
                    </ol>
                </nav>
                
                <div class="card mb-4">
                    <div class="card-header bg-primary text-white d-flex justify-content-between align-items-center">
                        <h4 class="mb-0">Chỉnh sửa bài viết</h4>
                        <div>
                            <a href="blog-list" class="btn btn-sm btn-outline-light">
                                <i class="bi bi-arrow-left"></i> Quay lại
                            </a>
                        </div>
                    </div>
                    <div class="card-body">
                        <form action="edit-post" method="post" enctype="multipart/form-data">
                            <div class="row mb-4">
                                <div class="col-md-8">
                                    <div class="mb-3">
                                        <label for="postTitle" class="form-label">Tiêu đề <span class="text-danger">*</span></label>
                                        <input name="postTitle" type="text" class="form-control" id="postTitle" value="${requestScope.blog.title}" required>
                                    </div>
                                    
                                    <div class="mb-3">
                                        <label for="postBrief" class="form-label">Mô tả ngắn</label>
                                        <textarea name="postBrief" class="form-control" id="postBrief" rows="2">${requestScope.blog.getBrief_info()}</textarea>
                                    </div>
                                    
                                    <div class="mb-3">
                                        <label for="postContent" class="form-label">Nội dung chi tiết <span class="text-danger">*</span></label>
                                        <textarea name="postContent" class="form-control" id="postContent" rows="12">${requestScope.blog.getDetails()}
</textarea>
                                    </div>
                                </div>
                                
                                <div class="col-md-4">
                                    <div class="card mb-3">
                                        <div class="card-header">Thông tin xuất bản</div>
                                        <div class="card-body">
                                            <div class="mb-3">
                                                <label for="postStatus" class="form-label">Trạng thái</label>
                                                <select name="postStatus" class="form-select" id="postStatus">
                                                  
                                                    <option value="1" ${ requestScope.blog.status == 1 ? "selected" : ""}>Đã đăng</option>
                                                    <option value="0" ${ requestScope.blog.status == 0 ? "selected" : ""}>Nháp</option>
                                                </select>
                                            </div>
                                            
                                            <div class="mb-3">
                                                <label for="postCategory" class="form-label">Danh mục</label>
                                                <select name="postCategory" class="form-select" id="postCategory">
                                                    <c:forEach var="category" items="${requestScope.listCategory}">
                                                        <option ${requestScope.blog.getPostCategories_id() == category.categoryId ? "selected" : ""} value="${category.categoryId}">${category.categoryName}</option>
                                                    </c:forEach>
                                                </select>
                                            </div>
                                            
                                            <div class="mb-3">
                                                <label for="updateDate" class="form-label">Ngày cập nhật</label>
                                                <input name="updateDate" type="date" class="form-control" id="updateDate" value="${requestScope.blog.updatedDate}" disabled>
                                            </div>
                                            
                                            <div class="form-check mb-3">
                                                 <input name="featuredPost" type="hidden" value="off">
                                                <input name="featuredPost" class="form-check-input" type="checkbox" id="featuredPost" ${requestScope.blog.isFlag_feature() ? "checked" : ""}>
                                               
                                                <label class="form-check-label" for="featuredPost">
                                                    Bài viết nổi bật
                                                </label>
                                            </div>
                                        </div>
                                    </div>
                                    
                                    <div class="card mb-3">
                                        <div class="card-header">Ảnh đại diện</div>
                                        <div class="card-body">
                                            <div class="mb-3">
                                                <div class="mb-2">
                                                    <img src="blog/${requestScope.blog.thumbnail}" class="img-thumbnail thumbnail-preview" alt="Ảnh đại diện hiện tại">
                                                </div>
                                                <label for="postThumbnail" class="form-label">Thay đổi ảnh</label>
                                                <input type="file" class="form-control" id="postThumbnail">
                                                <div class="form-text">Kích thước đề xuất: 1200x800px</div>
                                            </div>
                                        </div>
                                    </div>
                                    <input type="hidden" value="${requestScope.blog.id}" name="postID">
                                    <div class="card">
                                        <div class="card-header">Tác giả</div>
                                        <div class="card-body">
                                            <select class="form-select" id="postAuthor" disabled>
                                                <option selected>${requestScope.blog.full_name}</option>

                                            </select>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            
                            <div class="d-flex justify-content-between">
                                <a href="edit-post?id=${requestScope.blog.id}&action=del" class="btn btn-outline-danger" >
                                    <i class="bi bi-trash"></i> Xóa
                                </a>
                                <div>
                                    <a href="post-list.html" class="btn btn-secondary me-2">Hủy</a>
                                    <button type="submit" class="btn btn-success">
                                        <i class="bi bi-save"></i> Lưu thay đổi
                                    </button>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
    
    <!-- Delete Confirmation Modal -->
    

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>


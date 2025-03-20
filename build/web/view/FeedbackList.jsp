<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Danh Sách Phản Hồi</title>

    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <div class="container mt-4">
        <h2 class="text-center text-primary">Danh Sách Phản Hồi</h2>

        <!-- Hiển thị thông báo lỗi hoặc thành công -->
        <c:if test="${not empty success}">
            <div class="alert alert-success">${success}</div>
        </c:if>
        <c:if test="${not empty error}">
            <div class="alert alert-danger">${error}</div>
        </c:if>

        <!-- Form lọc và tìm kiếm -->
        <form action="feedback" method="get" class="mb-3 d-flex">
            <input type="hidden" name="action" value="list">
            <div class="input-group">
                <input type="text" name="search" class="form-control" placeholder="Tìm kiếm theo tên, nội dung..." value="${param.search}">
                <select name="status" class="form-select">
                    <option value="">Tất cả trạng thái</option>
                    <option value="Active" ${param.status == 'Active' ? 'selected' : ''}>Active</option>
                    <option value="Pending" ${param.status == 'Pending' ? 'selected' : ''}>Pending</option>
                    <option value="Rejected" ${param.status == 'Rejected' ? 'selected' : ''}>Rejected</option>
                </select>
                <button class="btn btn-primary">Tìm kiếm</button>
            </div>
        </form>

        <!-- Bảng danh sách feedback -->
        <table class="table table-bordered table-striped table-hover">
            <thead class="table-dark text-center">
                <tr>
                    <th>ID</th>
                    <th>Người dùng</th>
                    <th>Email</th>
                    <th>Số điện thoại</th>
                    <th>Sản phẩm</th>
                    <th>Đánh giá</th>
                    <th>Nội dung</th>
                    <th>Ảnh/Media</th>
                    <th>Trạng thái</th>
                    <th>Hành động</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="fb" items="${feedbacks}">
                    <tr>
                        <td>${fb.feedback_id}</td>
                        <td>${fb.user_name}</td>
                        <td>${fb.email}</td>
                        <td>${fb.phone_number}</td>
                        <td>${fb.product_name}</td>
                        <td>${fb.rating} ★</td>
                        <td>${fb.feedback_content}</td>
                        <td>
                            <c:if test="${not empty fb.media_url}">
                                <img src="${fb.media_url}" class="img-thumbnail" width="80">
                            </c:if>
                        </td>
                        <td>
                            <form action="feedback" method="post">
                                <input type="hidden" name="feedbackId" value="${fb.feedback_id}">
                                <select name="status" class="form-select" onchange="this.form.submit()">
                                    <option value="Active" ${fb.status == 'Active' ? 'selected' : ''}>Active</option>
                                    <option value="Pending" ${fb.status == 'Pending' ? 'selected' : ''}>Pending</option>
                                    <option value="Rejected" ${fb.status == 'Rejected' ? 'selected' : ''}>Rejected</option>
                                </select>
                            </form>
                        </td>
                        <td>
                            <a href="feedback?action=view&id=${fb.feedback_id}" class="btn btn-info btn-sm">Chi tiết</a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

        <!-- Phân trang -->
        <nav>
            <ul class="pagination justify-content-center">
                <c:forEach begin="1" end="${endPage}" var="i">
                    <li class="page-item ${i == currentPage ? 'active' : ''}">
                        <a class="page-link" href="feedback?page=${i}&search=${param.search}&status=${param.status}">${i}</a>
                    </li>
                </c:forEach>
            </ul>
        </nav>
    </div>

    <!-- Bootstrap JS -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>

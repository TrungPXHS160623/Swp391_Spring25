<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Chi Tiết Phản Hồi</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <div class="container mt-4">
        <h2 class="text-center text-primary">Chi Tiết Phản Hồi</h2>

        <c:if test="${not empty error}">
            <div class="alert alert-danger">${error}</div>
        </c:if>

        <table class="table table-bordered">
            <tr><th>ID</th><td>${feedback.feedback_id}</td></tr>
            <tr><th>Người dùng</th><td>${feedback.user_name}</td></tr>
            <tr><th>Email</th><td>${feedback.email}</td></tr>
            <tr><th>Số điện thoại</th><td>${feedback.phone_number}</td></tr>
            <tr><th>Sản phẩm</th><td>${feedback.product_name}</td></tr>
            <tr><th>Đánh giá</th><td>${feedback.rating} ★</td></tr>
            <tr><th>Nội dung</th><td>${feedback.feedback_content}</td></tr>
            <tr>
                <th>Ảnh/Media</th>
                <td>
                    <c:if test="${not empty feedback.media_url}">
                        <img src="${feedback.media_url}" class="img-thumbnail" width="150">
                    </c:if>
                </td>
            </tr>
            <tr><th>Trạng thái</th><td>${feedback.status}</td></tr>
        </table>

        <a href="feedback?action=list" class="btn btn-secondary">Quay lại</a>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>

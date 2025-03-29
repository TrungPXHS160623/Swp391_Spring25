<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Feedback Management</title>
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Font Awesome -->
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css" rel="stylesheet">
    <!-- Google Fonts -->
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700&display=swap" rel="stylesheet">
    <style>
        :root {
            --primary-color: #4e73df;
            --secondary-color: #1cc88a;
            --dark-color: #5a5c69;
            --light-color: #f8f9fc;
        }
        
        body {
            font-family: 'Poppins', sans-serif;
            background: url('https://images.unsplash.com/photo-1557683316-973673baf926?q=80&w=2029&auto=format&fit=crop') no-repeat center center fixed;
            background-size: cover;
            position: relative;
            color: #333;
        }
        
        body::before {
            content: "";
            position: absolute;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            background: linear-gradient(135deg, rgba(255,255,255,0.9) 0%, rgba(255,255,255,0.7) 100%);
            z-index: -1;
        }
        
        h2 {
            color: var(--primary-color);
            font-weight: 600;
            padding: 15px;
            text-align: center;
            margin-bottom: 25px;
            text-shadow: 1px 1px 2px rgba(0,0,0,0.1);
            border-bottom: 2px solid var(--primary-color);
            position: relative;
        }
        
        h2::after {
            content: '';
            position: absolute;
            bottom: -2px;
            left: 50%;
            transform: translateX(-50%);
            width: 100px;
            height: 4px;
            background-color: var(--secondary-color);
            border-radius: 2px;
        }
        
        .feedback-comment {
            max-width: 300px;
            overflow: hidden;
            text-overflow: ellipsis;
            white-space: nowrap;
        }
        
        .sort-icon {
            display: inline-block;
            width: 16px;
        }
        
        .filter-section {
            background-color: rgba(255, 255, 255, 0.9);
            padding: 20px;
            border-radius: 10px;
            margin-bottom: 25px;
            box-shadow: 0 0.15rem 1.75rem 0 rgba(58, 59, 69, 0.15);
            transition: transform 0.3s ease;
            border-left: 4px solid var(--primary-color);
        }
        
        .filter-section:hover {
            transform: translateY(-5px);
        }
        
        .status-badge {
            min-width: 80px;
            text-align: center;
            font-weight: 500;
            padding: 0.35em 0.65em;
            border-radius: 50rem;
        }
        
        .table-responsive {
            background-color: rgba(255, 255, 255, 0.95);
            padding: 25px;
            border-radius: 10px;
            box-shadow: 0 0.15rem 1.75rem 0 rgba(58, 59, 69, 0.15);
            margin-bottom: 25px;
            border-left: 4px solid var(--secondary-color);
        }
        
        .table {
            margin-bottom: 0;
        }
        
        .table thead th {
            color: var(--dark-color);
            font-weight: 600;
            background: rgba(78, 115, 223, 0.05);
            border-bottom: 2px solid var(--primary-color);
        }
        
        .table tbody tr {
            transition: all 0.2s ease;
        }
        
        .table tbody tr:hover {
            background-color: rgba(78, 115, 223, 0.05);
            transform: scale(1.01);
        }
        
        .table tbody td {
            vertical-align: middle;
        }
        
        .pagination {
            margin-top: 20px;
        }
        
        .pagination .page-link {
            color: var(--primary-color);
            background-color: rgba(255, 255, 255, 0.9);
            border: 1px solid #dee2e6;
            padding: 0.6rem 1rem;
            margin: 0 3px;
            border-radius: 5px;
            transition: all 0.2s;
        }
        
        .pagination .page-link:hover {
            background-color: var(--primary-color);
            color: white;
            border-color: var(--primary-color);
            transform: translateY(-2px);
        }
        
        .pagination .page-item.active .page-link {
            background-color: var(--primary-color);
            border-color: var(--primary-color);
            z-index: 1;
        }
        
        .btn {
            font-weight: 500;
            padding: 0.5rem 1.2rem;
            border-radius: 5px;
            transition: all 0.2s;
        }
        
        .btn:hover {
            transform: translateY(-2px);
            box-shadow: 0 5px 10px rgba(0,0,0,0.1);
        }
        
        .btn-primary {
            background-color: var(--primary-color);
            border-color: var(--primary-color);
        }
        
        .btn-secondary {
            background-color: var(--dark-color);
            border-color: var(--dark-color);
        }
        
        .form-control, .form-select {
            border-radius: 5px;
            padding: 0.6rem 1rem;
            border: 1px solid #d1d3e2;
            transition: all 0.2s ease;
        }
        
        .form-control:focus, .form-select:focus {
            border-color: var(--primary-color);
            box-shadow: 0 0 0 0.25rem rgba(78, 115, 223, 0.25);
        }
        
        .form-label {
            color: var(--dark-color);
            font-weight: 500;
            margin-bottom: 0.5rem;
        }
        
        .rating i {
            font-size: 1.1rem;
        }
        
        .alert {
            border-radius: 10px;
            border-left: 4px solid #1cc88a;
            animation: fadeInDown 0.5s ease;
        }
        
        @keyframes fadeInDown {
            from {
                opacity: 0;
                transform: translateY(-20px);
            }
            to {
                opacity: 1;
                transform: translateY(0);
            }
        }
        
        /* Custom scrollbar */
        ::-webkit-scrollbar {
            width: 10px;
        }
        
        ::-webkit-scrollbar-track {
            background: #f1f1f1;
        }
        
        ::-webkit-scrollbar-thumb {
            background: var(--primary-color);
            border-radius: 5px;
        }
        
        ::-webkit-scrollbar-thumb:hover {
            background: #3a5dd9;
        }
        
        /* Action buttons */
        .btn-info {
            background-color: #36b9cc;
            border-color: #36b9cc;
            color: white;
        }
        
        .btn-info:hover {
            background-color: #2ea7b9;
            border-color: #2ea7b9;
            color: white;
        }
    </style>
</head>
<body>
    <div class="container-fluid py-4">
        <div class="row justify-content-center">
            <div class="col-lg-11">
                <h2>Feedback Management</h2>
                
                <c:if test="${param.statusChanged eq 'true'}">
                    <div class="alert alert-success alert-dismissible fade show" role="alert">
                        <i class="fas fa-check-circle me-2"></i> Feedback status updated successfully!
                        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                    </div>
                </c:if>
                
                <!-- Filters Section -->
                <div class="filter-section">
                    <form id="filterForm" action="${pageContext.request.contextPath}/FeedbackList" method="GET" class="row g-3">
                        <div class="col-md-3">
                            <label for="search" class="form-label"><i class="fas fa-search me-2"></i>Search:</label>
                            <input type="text" class="form-control" id="search" name="search" 
                                   placeholder="Search by name or content" value="${searchKeyword}">
                        </div>
                        <div class="col-md-3">
                            <label for="productId" class="form-label"><i class="fas fa-box me-2"></i>Product:</label>
                            <select class="form-select" id="productId" name="productId">
                                <option value="">All Products</option>
                                <c:forEach items="${productList}" var="product">
                                    <option value="${product.productId}" ${product.productId eq selectedProductId ? 'selected' : ''}>
                                        ${product.productName}
                                    </option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="col-md-2">
                            <label for="rating" class="form-label"><i class="fas fa-star me-2"></i>Rating:</label>
                            <select class="form-select" id="rating" name="rating">
                                <option value="">All Ratings</option>
                                <option value="5" ${selectedRating eq 5 ? 'selected' : ''}>5 Stars</option>
                                <option value="4" ${selectedRating eq 4 ? 'selected' : ''}>4 Stars</option>
                                <option value="3" ${selectedRating eq 3 ? 'selected' : ''}>3 Stars</option>
                                <option value="2" ${selectedRating eq 2 ? 'selected' : ''}>2 Stars</option>
                                <option value="1" ${selectedRating eq 1 ? 'selected' : ''}>1 Star</option>
                            </select>
                        </div>
                        <div class="col-md-2">
                            <label for="status" class="form-label"><i class="fas fa-toggle-on me-2"></i>Status:</label>
                            <select class="form-select" id="status" name="status">
                                <option value="">All Status</option>
                                <option value="true" ${selectedStatus eq true ? 'selected' : ''}>Active</option>
                                <option value="false" ${selectedStatus eq false ? 'selected' : ''}>Inactive</option>
                            </select>
                        </div>
                        <div class="col-md-2 d-flex align-items-end">
                            <button type="submit" class="btn btn-primary me-2"><i class="fas fa-filter me-2"></i>Filter</button>
                            <a href="${pageContext.request.contextPath}FeedbackList" class="btn btn-secondary"><i class="fas fa-undo me-2"></i>Reset</a>
                        </div>
                    </form>
                </div>
                
                <!-- Feedback List Table -->
                <div class="table-responsive">
                    <table class="table table-bordered table-hover">
                        <thead>
                            <tr>
                                <th>#</th>
                                <th>
                                    <a href="${pageContext.request.contextPath}/FeedbackList?sortBy=fullName&sortOrder=${sortBy eq 'fullName' && sortOrder eq 'asc' ? 'desc' : 'asc'}&search=${searchKeyword}&productId=${selectedProductId}&rating=${selectedRating}&status=${selectedStatus}" class="text-decoration-none text-dark">
                                        <i class="fas fa-user me-1"></i> Customer Name
                                        <span class="sort-icon">
                                            <c:choose>
                                                <c:when test="${sortBy eq 'fullName' && sortOrder eq 'asc'}">
                                                    <i class="fas fa-sort-up"></i>
                                                </c:when>
                                                <c:when test="${sortBy eq 'fullName' && sortOrder eq 'desc'}">
                                                    <i class="fas fa-sort-down"></i>
                                                </c:when>
                                                <c:otherwise>
                                                    <i class="fas fa-sort"></i>
                                                </c:otherwise>
                                            </c:choose>
                                        </span>
                                    </a>
                                </th>
                                <th>
                                    <a href="${pageContext.request.contextPath}/FeedbackList?sortBy=productName&sortOrder=${sortBy eq 'productName' && sortOrder eq 'asc' ? 'desc' : 'asc'}&search=${searchKeyword}&productId=${selectedProductId}&rating=${selectedRating}&status=${selectedStatus}" class="text-decoration-none text-dark">
                                        <i class="fas fa-box me-1"></i> Product
                                        <span class="sort-icon">
                                            <c:choose>
                                                <c:when test="${sortBy eq 'productName' && sortOrder eq 'asc'}">
                                                    <i class="fas fa-sort-up"></i>
                                                </c:when>
                                                <c:when test="${sortBy eq 'productName' && sortOrder eq 'desc'}">
                                                    <i class="fas fa-sort-down"></i>
                                                </c:when>
                                                <c:otherwise>
                                                    <i class="fas fa-sort"></i>
                                                </c:otherwise>
                                            </c:choose>
                                        </span>
                                    </a>
                                </th>
                                <th>
                                    <a href="${pageContext.request.contextPath}/FeedbackList?sortBy=rating&sortOrder=${sortBy eq 'rating' && sortOrder eq 'asc' ? 'desc' : 'asc'}&search=${searchKeyword}&productId=${selectedProductId}&rating=${selectedRating}&status=${selectedStatus}" class="text-decoration-none text-dark">
                                        <i class="fas fa-star me-1"></i> Rating
                                        <span class="sort-icon">
                                            <c:choose>
                                                <c:when test="${sortBy eq 'rating' && sortOrder eq 'asc'}">
                                                    <i class="fas fa-sort-up"></i>
                                                </c:when>
                                                <c:when test="${sortBy eq 'rating' && sortOrder eq 'desc'}">
                                                    <i class="fas fa-sort-down"></i>
                                                </c:when>
                                                <c:otherwise>
                                                    <i class="fas fa-sort"></i>
                                                </c:otherwise>
                                            </c:choose>
                                        </span>
                                    </a>
                                </th>
                                <th><i class="fas fa-comment me-1"></i> Comment</th>
                                <th><i class="fas fa-calendar me-1"></i> Date</th>
                                <th>
                                    <a href="${pageContext.request.contextPath}/FeedbackList?sortBy=status&sortOrder=${sortBy eq 'status' && sortOrder eq 'asc' ? 'desc' : 'asc'}&search=${searchKeyword}&productId=${selectedProductId}&rating=${selectedRating}&status=${selectedStatus}" class="text-decoration-none text-dark">
                                        <i class="fas fa-toggle-on me-1"></i> Status
                                        <span class="sort-icon">
                                            <c:choose>
                                                <c:when test="${sortBy eq 'status' && sortOrder eq 'asc'}">
                                                    <i class="fas fa-sort-up"></i>
                                                </c:when>
                                                <c:when test="${sortBy eq 'status' && sortOrder eq 'desc'}">
                                                    <i class="fas fa-sort-down"></i>
                                                </c:when>
                                                <c:otherwise>
                                                    <i class="fas fa-sort"></i>
                                                </c:otherwise>
                                            </c:choose>
                                        </span>
                                    </a>
                                </th>
                                <th><i class="fas fa-cog me-1"></i> Actions</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:choose>
                                <c:when test="${empty feedbackList}">
                                    <tr>
                                        <td colspan="8" class="text-center py-4">
                                            <i class="fas fa-search fa-3x text-muted mb-3"></i>
                                            <p class="lead">No feedback found</p>
                                        </td>
                                    </tr>
                                </c:when>
                                <c:otherwise>
                                    <c:forEach items="${feedbackList}" var="feedback" varStatus="status">
                                        <tr>
                                            <td>${(currentPage - 1) * 10 + status.index + 1}</td>
                                            <td>${feedback.userFullName}</td>
                                            <td>${feedback.productName}</td>
                                            <td>
                                                <div class="rating">
                                                    <c:forEach begin="1" end="${feedback.rating}">
                                                        <i class="fas fa-star text-warning"></i>
                                                    </c:forEach>
                                                    <c:forEach begin="${feedback.rating + 1}" end="5">
                                                        <i class="far fa-star text-warning"></i>
                                                    </c:forEach>
                                                </div>
                                            </td>
                                            <td class="feedback-comment">${feedback.comment}</td>
                                            <td><fmt:formatDate value="${feedback.createdAt}" pattern="MM/dd/yyyy HH:mm" /></td>
                                            <td>
                                                <span class="badge ${feedback.status ? 'bg-success' : 'bg-danger'} status-badge">
                                                    ${feedback.status ? 'Active' : 'Inactive'}
                                                </span>
                                            </td>
                                            <td>
                                                <a href="${pageContext.request.contextPath}/admin/feedback?action=view&id=${feedback.reviewId}" 
                                                   class="btn btn-sm btn-info me-1" data-bs-toggle="tooltip" title="View Details">
                                                    <i class="fas fa-eye"></i>
                                                </a>
                                                <a href="${pageContext.request.contextPath}/FeedbackList?action=changeStatus&id=${feedback.reviewId}&status=${!feedback.status}&page=${currentPage}" 
                                                   class="btn btn-sm ${feedback.status ? 'btn-danger' : 'btn-success'}" 
                                                   onclick="return confirm('Are you sure you want to ${feedback.status ? 'deactivate' : 'activate'} this feedback?')"
                                                   data-bs-toggle="tooltip" title="${feedback.status ? 'Deactivate' : 'Activate'}">
                                                    <i class="fas ${feedback.status ? 'fa-ban' : 'fa-check'}"></i>
                                                </a>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </c:otherwise>
                            </c:choose>
                        </tbody>
                    </table>
                </div>
                
                <!-- Pagination -->
                <c:if test="${totalPages > 1}">
                    <nav aria-label="Page navigation">
                        <ul class="pagination justify-content-center">
                            <li class="page-item ${currentPage == 1 ? 'disabled' : ''}">
                                <a class="page-link" href="${pageContext.request.contextPath}/FeedbackList?page=${currentPage - 1}&search=${searchKeyword}&productId=${selectedProductId}&rating=${selectedRating}&status=${selectedStatus}&sortBy=${sortBy}&sortOrder=${sortOrder}">
                                    <i class="fas fa-chevron-left"></i> Previous
                                </a>
                            </li>
                            
                            <c:forEach begin="1" end="${totalPages}" var="i">
                                <li class="page-item ${currentPage == i ? 'active' : ''}">
                                    <a class="page-link" href="${pageContext.request.contextPath}/FeedbackList?page=${i}&search=${searchKeyword}&productId=${selectedProductId}&rating=${selectedRating}&status=${selectedStatus}&sortBy=${sortBy}&sortOrder=${sortOrder}">
                                        ${i}
                                    </a>
                                </li>
                            </c:forEach>
                            
                            <li class="page-item ${currentPage == totalPages ? 'disabled' : ''}">
                                <a class="page-link" href="${pageContext.request.contextPath}/FeedbackList?page=${currentPage + 1}&search=${searchKeyword}&productId=${selectedProductId}&rating=${selectedRating}&status=${selectedStatus}&sortBy=${sortBy}&sortOrder=${sortOrder}">
                                    Next <i class="fas fa-chevron-right"></i>
                                </a>
                            </li>
                        </ul>
                    </nav>
                </c:if>
            </div>
        </div>
    </div>
    
    <!-- Bootstrap JS Bundle with Popper -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"></script>
    <script>
        // Initialize tooltips
        var tooltipTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="tooltip"]'))
        var tooltipList = tooltipTriggerList.map(function (tooltipTriggerEl) {
            return new bootstrap.Tooltip(tooltipTriggerEl)
        });
        
        // Auto dismiss alerts after 5 seconds
        window.setTimeout(function() {
            $('.alert').fadeTo(500, 0).slideUp(500, function(){
                $(this).remove(); 
            });
        }, 5000);
    </script>
</body>
</html>

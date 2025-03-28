<%-- 
    Document   : CustomerList
    Created on : Mar 23, 2025, 11:50:19 PM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Customer Management | Admin Panel</title>
    <!-- Bootstrap 5 CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Font Awesome -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <!-- Google Fonts -->
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700&display=swap" rel="stylesheet">
    <style>
        body {
            font-family: 'Poppins', sans-serif;
            background-image: linear-gradient(135deg, #f5f7fa 0%, #c3cfe2 100%);
            background-attachment: fixed;
            min-height: 100vh;
            position: relative;
        }
        
        .page-wrapper {
            background-image: url('https://img.freepik.com/free-vector/white-abstract-background_23-2148810113.jpg');
            background-size: cover;
            background-position: center;
            position: relative;
            min-height: 100vh;
            padding: 40px 0;
        }
        
        .page-wrapper::before {
            content: '';
            position: absolute;
            top: 0;
            left: 0;
            right: 0;
            bottom: 0;
            background: rgba(255, 255, 255, 0.85);
            z-index: 0;
        }
        
        .container-wrapper {
            position: relative;
            z-index: 1;
            padding: 20px;
        }
        
        .content-container {
            background-color: white;
            border-radius: 15px;
            padding: 30px;
            box-shadow: 0 10px 30px rgba(0,0,0,0.1);
            border: 1px solid rgba(0,0,0,0.05);
            transition: transform 0.3s ease, box-shadow 0.3s ease;
        }
        
        .content-container:hover {
            transform: translateY(-5px);
            box-shadow: 0 15px 35px rgba(0,0,0,0.15);
        }
        
        h2 {
            color: #4e73df;
            font-weight: 600;
            margin-bottom: 1rem;
            position: relative;
            padding-bottom: 10px;
        }
        
        h2:after {
            content: '';
            position: absolute;
            bottom: 0;
            left: 0;
            width: 50px;
            height: 3px;
            background: #4e73df;
            border-radius: 3px;
        }
        
        .table-responsive {
            overflow-x: auto;
        }
        
        .search-container {
            margin-bottom: 20px;
        }
        
        .pagination {
            margin-top: 20px;
            justify-content: center;
        }
        
        .sort-icon {
            margin-left: 5px;
            cursor: pointer;
        }
        
        .customer-actions {
            white-space: nowrap;
        }
        
        .status-badge {
            padding: 5px 10px;
            border-radius: 15px;
            font-size: 12px;
            font-weight: bold;
        }
        
        .status-active {
            background-color: #d4edda;
            color: #155724;
        }
        
        .status-inactive {
            background-color: #f8d7da;
            color: #721c24;
        }
        
        .status-verified {
            background-color: #cce5ff;
            color: #004085;
        }
        
        .status-unverified {
            background-color: #fff3cd;
            color: #856404;
        }
        
        .form-control, .form-select {
            border-radius: 8px;
            padding: 10px 15px;
            border: 1px solid #ced4da;
            transition: border-color 0.2s ease-in-out, box-shadow 0.2s ease-in-out;
        }
        
        .form-control:focus, .form-select:focus {
            border-color: #80bdff;
            box-shadow: 0 0 0 0.2rem rgba(0, 123, 255, 0.25);
        }
        
        .btn {
            padding: 10px 20px;
            border-radius: 8px;
            font-weight: 500;
            transition: all 0.3s ease;
        }
        
        .btn-primary {
            background-color: #4e73df;
            border-color: #4e73df;
        }
        
        .btn-primary:hover {
            background-color: #2e59d9;
            border-color: #2e59d9;
            transform: translateY(-2px);
            box-shadow: 0 5px 15px rgba(78, 115, 223, 0.4);
        }
        
        .btn-secondary {
            background-color: #858796;
            border-color: #858796;
        }
        
        .btn-secondary:hover {
            background-color: #717384;
            border-color: #717384;
            transform: translateY(-2px);
            box-shadow: 0 5px 15px rgba(133, 135, 150, 0.4);
        }
        
        table {
            border-collapse: separate;
            border-spacing: 0;
            width: 100%;
            border-radius: 10px;
            overflow: hidden;
        }
        
        table thead th {
            background-color: #4e73df;
            color: white;
            padding: 15px;
            font-weight: 500;
        }
        
        table tbody tr:nth-child(even) {
            background-color: #f8f9fc;
        }
        
        table tbody tr:hover {
            background-color: #eaecf4;
        }
        
        table tbody td {
            padding: 15px;
            vertical-align: middle;
        }
        
        .btn-sm {
            padding: 5px 10px;
            font-size: 12px;
        }
        
        .btn-info {
            background-color: #36b9cc;
            border-color: #36b9cc;
            color: white;
        }
        
        .btn-info:hover {
            background-color: #2a96a5;
            border-color: #2a96a5;
            color: white;
        }
        
        .btn-warning {
            background-color: #f6c23e;
            border-color: #f6c23e;
            color: white;
        }
        
        .btn-warning:hover {
            background-color: #e0a800;
            border-color: #e0a800;
            color: white;
        }
        
        .pagination .page-link {
            color: #4e73df;
            border-color: #ddd;
        }
        
        .pagination .active .page-link {
            background-color: #4e73df;
            border-color: #4e73df;
        }
    </style>
</head>
<body>
    <div class="page-wrapper">
        <div class="container-wrapper">
            <div class="container-fluid">
                <div class="content-container">
                    <!-- Header -->
                    <div class="d-flex justify-content-between align-items-center mb-4">
                        <h2>Customer Management</h2>
                        <div>
                            <a href="${pageContext.request.contextPath}/PostListController" class="btn btn-primary me-2">
                                <i class="fas fa-file-alt me-1"></i> Post Management
                            </a>
                            <a href="${pageContext.request.contextPath}/FeedbackList" class="btn btn-info me-2">
                                <i class="fas fa-comments me-1"></i> Feedback Management
                            </a>
                            <a href="${pageContext.request.contextPath}/admin/customers/add" class="btn btn-primary">
                                <i class="fas fa-plus me-1"></i> Add New Customer
                            </a>
                        </div>
                    </div>
                    
                    <!-- Search and Filter Section -->
                    <div class="row search-container">
                        <div class="col-md-12">
                            <form action="${pageContext.request.contextPath}/admin/customers" method="get" class="row g-3">
                                <div class="col-md-4">
                                    <div class="input-group">
                                        <input type="text" name="search" class="form-control" placeholder="Search by name, email, or phone..." value="${search}">
                                        <button class="btn btn-outline-secondary" type="submit">
                                            <i class="fas fa-search"></i>
                                        </button>
                                    </div>
                                </div>
                                <div class="col-md-2">
                                    <select name="status" class="form-select">
                                        <option value="" ${status == null || status == '' ? 'selected' : ''}>All Status</option>
                                        <option value="active" ${status == 'active' ? 'selected' : ''}>Active</option>
                                        <option value="inactive" ${status == 'inactive' ? 'selected' : ''}>Inactive</option>
                                        <option value="verified" ${status == 'verified' ? 'selected' : ''}>Verified</option>
                                        <option value="unverified" ${status == 'unverified' ? 'selected' : ''}>Unverified</option>
                                    </select>
                                </div>
                                <div class="col-md-2">
                                    <select name="pageSize" class="form-select">
                                        <option value="10" ${pageSize == 10 ? 'selected' : ''}>10 per page</option>
                                        <option value="20" ${pageSize == 20 ? 'selected' : ''}>20 per page</option>
                                        <option value="50" ${pageSize == 50 ? 'selected' : ''}>50 per page</option>
                                        <option value="100" ${pageSize == 100 ? 'selected' : ''}>100 per page</option>
                                    </select>
                                </div>
                                <div class="col-md-4">
                                    <button type="submit" class="btn btn-primary me-2">Apply Filters</button>
                                    <a href="${pageContext.request.contextPath}/admin/customers" class="btn btn-secondary">Reset Filters</a>
                                </div>
                                
                                <input type="hidden" name="sortBy" value="${sortBy}" id="sortByField">
                                <input type="hidden" name="sortOrder" value="${sortOrder}" id="sortOrderField">
                            </form>
                        </div>
                    </div>
                    
                    <!-- Customer Count -->
                    <div class="row mb-3">
                        <div class="col-12">
                            <p>Showing ${customers.size()} of ${totalCustomers} customers</p>
                        </div>
                    </div>
                    
                    <!-- Customer Table -->
                    <div class="table-responsive">
                        <table class="table table-hover">
                            <thead>
                                <tr>
                                    <th>ID</th>
                                    <th>
                                        <a href="javascript:void(0)" onclick="sortTable('fullName')" class="text-white text-decoration-none">
                                            Full Name
                                            <i class="fas ${sortBy == 'fullName' ? (sortOrder == 'asc' ? 'fa-sort-up' : 'fa-sort-down') : 'fa-sort'}"></i>
                                        </a>
                                    </th>
                                    <th>Gender</th>
                                    <th>
                                        <a href="javascript:void(0)" onclick="sortTable('email')" class="text-white text-decoration-none">
                                            Email
                                            <i class="fas ${sortBy == 'email' ? (sortOrder == 'asc' ? 'fa-sort-up' : 'fa-sort-down') : 'fa-sort'}"></i>
                                        </a>
                                    </th>
                                    <th>
                                        <a href="javascript:void(0)" onclick="sortTable('phone')" class="text-white text-decoration-none">
                                            Mobile
                                            <i class="fas ${sortBy == 'phone' ? (sortOrder == 'asc' ? 'fa-sort-up' : 'fa-sort-down') : 'fa-sort'}"></i>
                                        </a>
                                    </th>
                                    <th>
                                        <a href="javascript:void(0)" onclick="sortTable('status')" class="text-white text-decoration-none">
                                            Status
                                            <i class="fas ${sortBy == 'status' ? (sortOrder == 'asc' ? 'fa-sort-up' : 'fa-sort-down') : 'fa-sort'}"></i>
                                        </a>
                                    </th>
                                    <th>Actions</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:choose>
                                    <c:when test="${empty customers}">
                                        <tr>
                                            <td colspan="7" class="text-center">No customers found</td>
                                        </tr>
                                    </c:when>
                                    <c:otherwise>
                                        <c:forEach var="customer" items="${customers}">
                                            <tr>
                                                <td>${customer.userId}</td>
                                                <td>${customer.fullName}</td>
                                                <td>${customer.gender}</td>
                                                <td>${customer.email}</td>
                                                <td>${customer.phoneNumber}</td>
                                                <td>
                                                    <c:if test="${customer.isActive}">
                                                        <span class="status-badge status-active">Active</span>
                                                    </c:if>
                                                    <c:if test="${!customer.isActive}">
                                                        <span class="status-badge status-inactive">Inactive</span>
                                                    </c:if>
                                                    <c:if test="${customer.isVerified}">
                                                        <span class="status-badge status-verified ms-1">Verified</span>
                                                    </c:if>
                                                    <c:if test="${!customer.isVerified}">
                                                        <span class="status-badge status-unverified ms-1">Unverified</span>
                                                    </c:if>
                                                </td>
                                                <td class="customer-actions">
                                                    <a href="${pageContext.request.contextPath}/admin/customers/view?id=${customer.userId}" class="btn btn-sm btn-info" title="View Customer">
                                                        <i class="fas fa-eye"></i>
                                                    </a>
                                                    <a href="${pageContext.request.contextPath}/admin/customers/edit?id=${customer.userId}" class="btn btn-sm btn-warning ms-1" title="Edit Customer">
                                                        <i class="fas fa-edit"></i>
                                                    </a>
                                                    <c:if test="${customer.isActive}">
                                                        <a href="${pageContext.request.contextPath}/admin/customers/status?id=${customer.userId}&action=deactivate" 
                                                        class="btn btn-sm btn-danger ms-1" 
                                                        onclick="return confirm('Are you sure you want to deactivate this customer?')" 
                                                        title="Deactivate Customer">
                                                            <i class="fas fa-ban"></i>
                                                        </a>
                                                    </c:if>
                                                    <c:if test="${!customer.isActive}">
                                                        <a href="${pageContext.request.contextPath}/admin/customers/status?id=${customer.userId}&action=activate" 
                                                        class="btn btn-sm btn-success ms-1" 
                                                        onclick="return confirm('Are you sure you want to activate this customer?')" 
                                                        title="Activate Customer">
                                                            <i class="fas fa-check"></i>
                                                        </a>
                                                    </c:if>
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
                                <!-- Previous button -->
                                <li class="page-item ${currentPage == 1 ? 'disabled' : ''}">
                                    <a class="page-link" href="${pageContext.request.contextPath}/admin/customers?page=${currentPage - 1}&pageSize=${pageSize}&search=${search}&status=${status}&sortBy=${sortBy}&sortOrder=${sortOrder}" aria-label="Previous">
                                        <span aria-hidden="true">&laquo;</span> Previous
                                    </a>
                                </li>
                                
                                <!-- Page numbers - simpler version -->
                                <c:set var="startPage" value="${Math.max(1, currentPage - 2)}" />
                                <c:set var="endPage" value="${Math.min(totalPages, startPage + 4)}" />
                                
                                <c:if test="${startPage > 1}">
                                    <li class="page-item">
                                        <a class="page-link" href="${pageContext.request.contextPath}/admin/customers?page=1&pageSize=${pageSize}&search=${search}&status=${status}&sortBy=${sortBy}&sortOrder=${sortOrder}">1</a>
                                    </li>
                                    <c:if test="${startPage > 2}">
                                        <li class="page-item disabled">
                                            <span class="page-link">...</span>
                                        </li>
                                    </c:if>
                                </c:if>
                                
                                <c:forEach var="i" begin="${startPage}" end="${endPage}">
                                    <li class="page-item ${currentPage == i ? 'active' : ''}">
                                        <a class="page-link" href="${pageContext.request.contextPath}/admin/customers?page=${i}&pageSize=${pageSize}&search=${search}&status=${status}&sortBy=${sortBy}&sortOrder=${sortOrder}">${i}</a>
                                    </li>
                                </c:forEach>
                                
                                <c:if test="${endPage < totalPages}">
                                    <c:if test="${endPage < totalPages - 1}">
                                        <li class="page-item disabled">
                                            <span class="page-link">...</span>
                                        </li>
                                    </c:if>
                                    <li class="page-item">
                                        <a class="page-link" href="${pageContext.request.contextPath}/admin/customers?page=${totalPages}&pageSize=${pageSize}&search=${search}&status=${status}&sortBy=${sortBy}&sortOrder=${sortOrder}">${totalPages}</a>
                                    </li>
                                </c:if>
                                
                                <!-- Next button -->
                                <li class="page-item ${currentPage == totalPages ? 'disabled' : ''}">
                                    <a class="page-link" href="${pageContext.request.contextPath}/admin/customers?page=${currentPage + 1}&pageSize=${pageSize}&search=${search}&status=${status}&sortBy=${sortBy}&sortOrder=${sortOrder}" aria-label="Next">
                                        Next <span aria-hidden="true">&raquo;</span>
                                    </a>
                                </li>
                            </ul>
                        </nav>
                    </c:if>
                </div>
            </div>
        </div>
    </div>
    
    <!-- Bootstrap and jQuery -->
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
    
    <!-- Sorting Script -->
    <script>
        function sortTable(column) {
            const sortByField = document.getElementById('sortByField');
            const sortOrderField = document.getElementById('sortOrderField');
            
            if (sortByField.value === column) {
                // Toggle sort order if clicking the same column
                sortOrderField.value = sortOrderField.value === 'asc' ? 'desc' : 'asc';
            } else {
                // Default to ascending order for a new column
                sortByField.value = column;
                sortOrderField.value = 'asc';
            }
            
            // Submit the form to apply sorting
            document.querySelector('form').submit();
        }
    </script>
</body>
</html>

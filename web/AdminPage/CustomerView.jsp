<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Customer Details | Admin Panel</title>
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

        .customer-info {
            background-color: white;
            border-radius: 15px;
            padding: 30px;
            margin-bottom: 20px;
            box-shadow: 0 10px 30px rgba(0,0,0,0.1);
            border: 1px solid rgba(0,0,0,0.05);
            transition: transform 0.3s ease, box-shadow 0.3s ease;
        }
        
        .customer-info:hover {
            transform: translateY(-5px);
            box-shadow: 0 15px 35px rgba(0,0,0,0.15);
        }

        .customer-header {
            display: flex;
            align-items: center;
            margin-bottom: 30px;
        }
        
        .customer-avatar {
            width: 120px;
            height: 120px;
            border-radius: 50%;
            object-fit: cover;
            margin-right: 25px;
            border: 4px solid #fff;
            box-shadow: 0 5px 15px rgba(0,0,0,0.15);
        }
        
        .status-badge {
            padding: 6px 12px;
            border-radius: 20px;
            font-size: 12px;
            font-weight: 600;
            margin-right: 8px;
            display: inline-block;
            text-transform: uppercase;
            letter-spacing: 0.5px;
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
        
        .info-label {
            font-weight: 600;
            color: #4e73df;
            min-width: 100px;
            display: inline-block;
        }
        
        .info-value {
            color: #5a5c69;
            font-weight: 500;
        }
        
        .info-row {
            padding: 10px 0;
            border-bottom: 1px solid #f1f1f1;
        }
        
        .info-row:last-child {
            border-bottom: none;
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

        .btn-danger {
            background-color: #e74a3b;
            border-color: #e74a3b;
        }
        
        .btn-danger:hover {
            background-color: #d52a1a;
            border-color: #d52a1a;
            transform: translateY(-2px);
            box-shadow: 0 5px 15px rgba(231, 74, 59, 0.4);
        }
        
        .btn-success {
            background-color: #1cc88a;
            border-color: #1cc88a;
        }
        
        .btn-success:hover {
            background-color: #17a673;
            border-color: #17a673;
            transform: translateY(-2px);
            box-shadow: 0 5px 15px rgba(28, 200, 138, 0.4);
        }
    </style>
</head>
<body>
    <div class="page-wrapper">
        <div class="container-wrapper">
            <div class="container py-4">
                <!-- Header with back button -->
                <div class="d-flex justify-content-between align-items-center mb-4">
                    <h2>Customer Details</h2>
                    <a href="${pageContext.request.contextPath}/admin/customers" class="btn btn-secondary">
                        <i class="fas fa-arrow-left me-1"></i> Back to Customer List
                    </a>
                </div>
                
                <div class="content-container">
                    <!-- Customer Information -->
                    <div class="customer-info">
                        <!-- Customer Header with Avatar -->
                        <div class="customer-header">
                            <img src="${empty customer.avatarUrl ? pageContext.request.contextPath.concat('/resources/images/default-avatar.png') : customer.avatarUrl}" 
                                alt="${customer.fullName}" class="customer-avatar">
                            <div>
                                <h3 style="color: #4e73df; font-weight: 600;">${customer.fullName}</h3>
                                <p class="text-muted mb-2">Customer ID: #${customer.userId}</p>
                                <div class="mt-3">
                                    <c:if test="${customer.isActive}">
                                        <span class="status-badge status-active">Active</span>
                                    </c:if>
                                    <c:if test="${!customer.isActive}">
                                        <span class="status-badge status-inactive">Inactive</span>
                                    </c:if>
                                    <c:if test="${customer.isVerified}">
                                        <span class="status-badge status-verified">Verified</span>
                                    </c:if>
                                    <c:if test="${!customer.isVerified}">
                                        <span class="status-badge status-unverified">Unverified</span>
                                    </c:if>
                                </div>
                            </div>
                        </div>
                        
                        <hr style="border-top: 2px solid #f0f0f0; margin: 20px 0 30px;">
                        
                        <!-- Customer Details -->
                        <div class="row">
                            <div class="col-md-6">
                                <div class="info-row">
                                    <span class="info-label">Email:</span>
                                    <span class="info-value">${customer.email}</span>
                                </div>
                                <div class="info-row">
                                    <span class="info-label">Phone:</span>
                                    <span class="info-value">${customer.phoneNumber}</span>
                                </div>
                                <div class="info-row">
                                    <span class="info-label">Gender:</span>
                                    <span class="info-value">${customer.gender}</span>
                                </div>
                            </div>
                            <div class="col-md-6">
                                <div class="info-row">
                                    <span class="info-label">Address:</span>
                                    <span class="info-value">${customer.address}</span>
                                </div>
                                <div class="info-row">
                                    <span class="info-label">Created:</span>
                                    <span class="info-value">
                                        <fmt:formatDate value="${customer.createdAt}" pattern="dd-MM-yyyy HH:mm" />
                                    </span>
                                </div>
                                <div class="info-row">
                                    <span class="info-label">Last Login:</span>
                                    <span class="info-value">
                                        <c:choose>
                                            <c:when test="${not empty customer.lastLogin}">
                                                <fmt:formatDate value="${customer.lastLogin}" pattern="dd-MM-yyyy HH:mm" />
                                            </c:when>
                                            <c:otherwise>Never</c:otherwise>
                                        </c:choose>
                                    </span>
                                </div>
                            </div>
                        </div>
                        
                        <!-- Action Buttons -->
                        <div class="d-flex justify-content-end mt-4">
                            <a href="${pageContext.request.contextPath}/admin/customers/edit?id=${customer.userId}" class="btn btn-primary me-2">
                                <i class="fas fa-edit me-1"></i> Edit Customer
                            </a>
                            <c:choose>
                                <c:when test="${customer.isActive}">
                                    <a href="${pageContext.request.contextPath}/admin/customers/status?id=${customer.userId}&action=deactivate" 
                                    class="btn btn-danger"
                                    onclick="return confirm('Are you sure you want to deactivate this customer?')">
                                        <i class="fas fa-ban me-1"></i> Deactivate
                                    </a>
                                </c:when>
                                <c:otherwise>
                                    <a href="${pageContext.request.contextPath}/admin/customers/status?id=${customer.userId}&action=activate" 
                                    class="btn btn-success"
                                    onclick="return confirm('Are you sure you want to activate this customer?')">
                                        <i class="fas fa-check me-1"></i> Activate
                                    </a>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    
    <!-- Bootstrap JS Bundle -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Error</title>
        <!-- Bootstrap CSS -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
        <!-- Font Awesome -->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    </head>
    <body>
        <div class="container mt-5">
            <div class="row justify-content-center">
                <div class="col-md-8">
                    <div class="card border-danger">
                        <div class="card-header bg-danger text-white">
                            <h4><i class="fas fa-exclamation-triangle"></i> Error</h4>
                        </div>
                        <div class="card-body">
                            <p class="card-text">
                                ${empty errorMessage ? "An unexpected error occurred. Please try again later." : errorMessage}
                            </p>
                            <a href="javascript:history.back()" class="btn btn-secondary">
                                <i class="fas fa-arrow-left"></i> Go Back
                            </a>
                            <a href="${pageContext.request.contextPath}/PostListController" class="btn btn-primary">
                                <i class="fas fa-list"></i> Post List
                            </a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        
        <!-- Bootstrap JS with Popper -->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>

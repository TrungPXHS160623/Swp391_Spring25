<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Feedback Detail</title>
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Font Awesome -->
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css" rel="stylesheet">
    <!-- Google Fonts -->
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700&display=swap" rel="stylesheet">
    <style>
        :root {
            --primary-color: #4e73df;
            --secondary-color: #f8f9fc;
            --accent-color: #2e59d9;
            --text-color: #5a5c69;
            --card-shadow: 0 0.15rem 1.75rem 0 rgba(58, 59, 69, 0.15);
            --hover-shadow: 0 0.5rem 2rem 0 rgba(58, 59, 69, 0.25);
        }

        body {
            font-family: 'Poppins', sans-serif;
            background: linear-gradient(rgba(0, 0, 0, 0.7), rgba(0, 0, 0, 0.7)), 
                        url('https://images.unsplash.com/photo-1557683316-973673baf926?ixlib=rb-4.0.3&auto=format&fit=crop&w=1920&q=80') no-repeat center center fixed;
            background-size: cover;
            color: var(--text-color);
            min-height: 100vh;
            padding-bottom: 30px;
        }

        .container {
            background-color: rgba(255, 255, 255, 0.85);
            border-radius: 15px;
            padding: 30px;
            box-shadow: 0 10px 30px rgba(0, 0, 0, 0.3);
            backdrop-filter: blur(10px);
            -webkit-backdrop-filter: blur(10px);
            margin-top: 30px;
            margin-bottom: 30px;
            animation: fadeIn 0.6s ease-in-out;
        }

        @keyframes fadeIn {
            from { opacity: 0; transform: translateY(20px); }
            to { opacity: 1; transform: translateY(0); }
        }

        .page-header {
            border-bottom: 2px solid var(--primary-color);
            margin-bottom: 25px;
            padding-bottom: 15px;
            position: relative;
        }

        .page-header h2 {
            color: var(--primary-color);
            font-weight: 600;
            margin-bottom: 0;
        }

        .btn-back {
            border-radius: 30px;
            padding: 8px 20px;
            transition: all 0.3s;
            font-weight: 500;
            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
        }

        .btn-back:hover {
            transform: translateY(-2px);
            box-shadow: 0 5px 15px rgba(0, 0, 0, 0.2);
        }

        .card {
            border: none;
            border-radius: 12px;
            box-shadow: var(--card-shadow);
            transition: all 0.3s ease;
            overflow: hidden;
            margin-bottom: 20px;
        }

        .card:hover {
            box-shadow: var(--hover-shadow);
        }

        .card-header {
            background-color: var(--primary-color);
            color: white;
            font-weight: 500;
            padding: 15px 20px;
            border-bottom: none;
        }

        .card-body {
            padding: 20px;
        }

        .badge {
            padding: 8px 15px;
            border-radius: 30px;
            font-weight: 500;
        }

        .customer-info {
            background-color: rgba(78, 115, 223, 0.05);
            border-radius: 10px;
            padding: 15px;
            margin-bottom: 20px;
        }

        .feedback-images {
            display: flex;
            flex-wrap: wrap;
            gap: 15px;
            margin-top: 15px;
        }

        .feedback-image-container {
            position: relative;
            width: 200px;
            height: 200px;
            overflow: hidden;
            border-radius: 12px;
            box-shadow: 0 3px 15px rgba(0,0,0,0.1);
            cursor: pointer;
            transition: transform 0.3s ease, box-shadow 0.3s ease;
        }

        .feedback-image-container:hover {
            transform: translateY(-8px) scale(1.02);
            box-shadow: 0 12px 20px rgba(0,0,0,0.15);
        }

        .feedback-image {
            width: 100%;
            height: 100%;
            object-fit: contain;
            background-color: #f8f9fa;
        }

        .image-loading {
            position: absolute;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            display: flex;
            justify-content: center;
            align-items: center;
            background-color: rgba(255,255,255,0.7);
            z-index: 1;
        }

        .image-error {
            padding: 20px;
            text-align: center;
            font-size: 12px;
            color: #721c24;
        }

        #imageModal .modal-content {
            border: none;
            border-radius: 15px;
            overflow: hidden;
        }

        #imageModal .modal-header {
            background-color: var(--primary-color);
            color: white;
            border-bottom: none;
        }

        #imageModal .modal-img-container {
            display: flex;
            justify-content: center;
            align-items: center;
            min-height: 300px;
            background-color: #f8f9fa;
        }

        #imageModal .modal-img {
            max-width: 100%;
            max-height: 70vh;
            object-fit: contain;
        }

        #imageModal .btn-close {
            background-color: white;
            opacity: 1;
            box-shadow: 0 0 10px rgba(0,0,0,0.1);
        }

        #imageModal .modal-footer {
            border-top: none;
            padding: 15px 20px;
        }

        .btn-primary {
            background-color: var(--primary-color);
            border-color: var(--primary-color);
            border-radius: 30px;
            padding: 8px 20px;
            transition: all 0.3s;
            font-weight: 500;
        }

        .btn-primary:hover {
            background-color: var(--accent-color);
            border-color: var(--accent-color);
            transform: translateY(-2px);
            box-shadow: 0 5px 15px rgba(46, 89, 217, 0.3);
        }

        .btn-secondary {
            border-radius: 30px;
            padding: 8px 20px;
            transition: all 0.3s;
            font-weight: 500;
        }

        .btn-secondary:hover {
            transform: translateY(-2px);
            box-shadow: 0 5px 15px rgba(108, 117, 125, 0.3);
        }

        .product-info {
            display: flex;
            flex-direction: column;
            height: 100%;
        }

        .product-info h5 {
            font-weight: 600;
            color: var (--primary-color);
            margin-bottom: 15px;
        }

        .product-link {
            text-decoration: none;
            color: var(--primary-color);
            font-weight: 500;
            display: inline-block;
            padding: 8px 20px;
            background-color: rgba(78, 115, 223, 0.1);
            border-radius: 30px;
            transition: all 0.3s;
        }

        .product-link:hover {
            background-color: var(--primary-color);
            color: white;
            transform: translateY(-2px);
            box-shadow: 0 5px 15px rgba(78, 115, 223, 0.3);
        }

        .star-rating {
            font-size: 1.2rem;
            margin-bottom: 15px;
        }

        .comment-card {
            background-color: #f8f9fc;
            border-radius: 10px;
            padding: 20px;
            margin-bottom: 20px;
            border-left: 4px solid var(--primary-color);
        }

        .status-action-btn {
            transition: all 0.3s;
            border-radius: 30px;
            padding: 8px 20px;
            font-weight: 500;
        }

        .status-action-btn:hover {
            transform: translateY(-2px);
            box-shadow: 0 5px 15px rgba(0, 0, 0, 0.2);
        }

        @media (max-width: 768px) {
            .container {
                padding: 15px;
            }
            
            .feedback-image-container {
                width: 100%;
                height: auto;
                aspect-ratio: 1/1;
            }
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="row page-header">
            <div class="col-md-12 d-flex justify-content-between align-items-center">
                <h2><i class="fas fa-comment-dots me-2"></i>Feedback Details</h2>
                <a href="${pageContext.request.contextPath}/admin/feedbacks" class="btn btn-back btn-secondary">
                    <i class="fas fa-arrow-left me-2"></i>Back to Feedbacks
                </a>
            </div>
        </div>
        
        <div class="row">
            <div class="col-lg-8">
                <div class="card">
                    <div class="card-header d-flex justify-content-between align-items-center">
                        <h5 class="mb-0">Review for ${feedback.productName}</h5>
                        <span class="badge ${feedback.status ? 'bg-success' : 'bg-danger'}">
                            ${feedback.status ? 'Active' : 'Inactive'}
                        </span>
                    </div>
                    <div class="card-body">
                        <div class="customer-info">
                            <div class="row">
                                <div class="col-md-6">
                                    <p><i class="fas fa-user me-2"></i><strong>Customer:</strong> ${feedback.userFullName}</p>
                                    <p><i class="fas fa-calendar-alt me-2"></i><strong>Date:</strong> <fmt:formatDate value="${feedback.createdAt}" pattern="MMMM dd, yyyy HH:mm" /></p>
                                    <div class="star-rating">
                                        <strong>Rating: </strong>
                                        <span class="ms-2">
                                            <c:forEach begin="1" end="${feedback.rating}">
                                                <i class="fas fa-star text-warning"></i>
                                            </c:forEach>
                                            <c:forEach begin="${feedback.rating + 1}" end="5">
                                                <i class="far fa-star text-warning"></i>
                                            </c:forEach>
                                        </span>
                                    </div>
                                </div>
                                <div class="col-md-6 text-md-end mt-3 mt-md-0">
                                    <a href="${pageContext.request.contextPath}/admin/feedback?action=changeStatus&id=${feedback.reviewId}&status=${!feedback.status}" 
                                       class="status-action-btn btn ${feedback.status ? 'btn-danger' : 'btn-success'}"
                                       onclick="return confirm('Are you sure you want to ${feedback.status ? 'deactivate' : 'activate'} this feedback?')">
                                        <i class="fas ${feedback.status ? 'fa-ban' : 'fa-check'} me-2"></i>
                                        ${feedback.status ? 'Deactivate' : 'Activate'} Feedback
                                    </a>
                                </div>
                            </div>
                        </div>
                        
                        <div class="comment-card">
                            <h6 class="mb-3"><i class="fas fa-comment me-2"></i>Comment</h6>
                            <p class="mb-0">${feedback.comment}</p>
                        </div>
                        
                        <c:if test="${not empty feedback.imageUrls}">
                            <div class="card">
                                <div class="card-header">
                                    <i class="fas fa-images me-2"></i>Attached Images
                                </div>
                                <div class="card-body">
                                    <div class="feedback-images">
                                        <c:forEach items="${feedback.imageUrls}" var="imageUrl">
                                            <div class="feedback-image-container" onclick="showImageInModal('${imageUrl}')">
                                                <img src="${pageContext.request.contextPath}/${imageUrl}" alt="Feedback Image" class="feedback-image" 
                                                     onload="this.parentNode.classList.add('loaded')"
                                                     onerror="this.onerror=null; this.parentNode.innerHTML='<div class=\'image-error\'><i class=\'fas fa-exclamation-circle mb-2\'></i><br>Image not available</div>'">
                                                <div class="image-loading">
                                                    <div class="spinner-border text-primary" role="status">
                                                        <span class="visually-hidden">Loading...</span>
                                                    </div>
                                                </div>
                                            </div>
                                        </c:forEach>
                                    </div>
                                </div>
                            </div>
                        </c:if>
                    </div>
                </div>
            </div>
            
            <div class="col-lg-4">
                <div class="card h-100">
                    <div class="card-header">
                        <i class="fas fa-box-open me-2"></i>Product Information
                    </div>
                    <div class="card-body d-flex flex-column">
                        <div class="product-info flex-grow-1">
                            <h5>${feedback.productName}</h5>
                            <div class="mt-auto">
                                <a href="${pageContext.request.contextPath}/product?id=${feedback.productId}" class="product-link" target="_blank">
                                    <i class="fas fa-external-link-alt me-2"></i>View Product
                                </a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    
    <!-- Image Modal -->
    <div class="modal fade" id="imageModal" tabindex="-1" aria-labelledby="imageModalLabel" aria-hidden="true">
        <div class="modal-dialog modal-lg modal-dialog-centered">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="imageModalLabel">
                        <i class="fas fa-image me-2"></i>Feedback Image
                    </h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body p-0">
                    <div class="modal-img-container">
                        <img src="" alt="Feedback Image" class="modal-img" id="modalImage">
                    </div>
                </div>
                <div class="modal-footer">
                    <a href="" class="btn btn-primary" id="downloadImageBtn" download target="_blank">
                        <i class="fas fa-download me-2"></i>Download Image
                    </a>
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">
                        <i class="fas fa-times me-2"></i>Close
                    </button>
                </div>
            </div>
        </div>
    </div>
    
    <!-- Bootstrap JS Bundle with Popper -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"></script>
    <script>
        document.addEventListener('DOMContentLoaded', function() {
            // Remove loading spinners for images that are already cached
            document.querySelectorAll('.feedback-image').forEach(img => {
                if (img.complete) {
                    img.parentNode.classList.add('loaded');
                    if (img.parentNode.querySelector('.image-loading')) {
                        img.parentNode.querySelector('.image-loading').style.display = 'none';
                    }
                }
            });
            
            // Apply fade-in animations to cards
            const cards = document.querySelectorAll('.card');
            cards.forEach((card, index) => {
                card.style.opacity = '0';
                card.style.animation = `fadeIn 0.5s ease-in-out ${index * 0.1}s forwards`;
            });
        });

        function showImageInModal(imageUrl) {
            const modalImage = document.getElementById('modalImage');
            const downloadBtn = document.getElementById('downloadImageBtn');
            
            // Show loading state in modal
            modalImage.src = '';
            modalImage.alt = 'Loading...';
            
            // Set the image source and download link
            modalImage.src = imageUrl;
            downloadBtn.href = imageUrl;
            
            // Handle image loading errors
            modalImage.onerror = function() {
                modalImage.onerror = null;
                modalImage.src = 'https://placehold.co/600x400?text=Image+Not+Available';
                modalImage.alt = 'Image not available';
            };
            
            // Show the modal
            const imageModal = new bootstrap.Modal(document.getElementById('imageModal'));
            imageModal.show();
        }
        
        // Hide loading spinner when image loads
        document.querySelectorAll('.feedback-image').forEach(img => {
            img.addEventListener('load', function() {
                if (this.parentNode.querySelector('.image-loading')) {
                    this.parentNode.querySelector('.image-loading').style.display = 'none';
                }
            });
        });
    </script>
</body>
</html>

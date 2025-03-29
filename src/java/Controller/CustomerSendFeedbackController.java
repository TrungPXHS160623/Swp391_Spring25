//package Controller;
//
//import Dao.OrderDao;
//import Entity.FeedbackImage;
//import Entity.Review;
//import Dao.ReviewDao;
//import Dao.ProductDao;
//import Dto.ProductFeedbackDto;
//import Entity.OrderItems;
//import Entity.Orders;
//import java.io.File;
//import java.io.IOException;
//import java.sql.Timestamp;
//import java.util.ArrayList;
//import java.util.List;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.annotation.MultipartConfig;
//import jakarta.servlet.annotation.WebServlet;
//import jakarta.servlet.http.HttpServlet;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import jakarta.servlet.http.HttpSession;
//import jakarta.servlet.http.Part;
//
//@WebServlet(name="CustomerSendFeedbackController", urlPatterns={"/CustomerSendFeedback"})
//@MultipartConfig(
//    fileSizeThreshold = 1024 * 1024, // 1MB
//    maxFileSize = 1024 * 1024 * 15,  // 15MB (increased to accommodate videos)
//    maxRequestSize = 1024 * 1024 * 50 // 50MB
//)
//public class CustomerSendFeedbackController extends HttpServlet {
//   
//    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
//    throws ServletException, IOException {
//        response.setContentType("text/html;charset=UTF-8");
//        // Implement common processing if needed
//    } 
//
//    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response)
//    throws ServletException, IOException {
//        try {
//            // Get order ID from request
//            String orderIdStr = request.getParameter("orderId");
//            if (orderIdStr == null || orderIdStr.isEmpty()) {
//                response.sendRedirect("myordercontroller");
//                return;
//            }
//            
//            int orderId = Integer.parseInt(orderIdStr);
//            
//            // Get session and user ID
//            HttpSession session = request.getSession();
//            Integer userId = (Integer) session.getAttribute("userId");
//            if (userId == null) {
//                response.sendRedirect("login");
//                return;
//            }
//            
//            // Get the order and check if it belongs to the current user and is completed
//            OrderDao orderDao = new OrderDao();
//            Orders order = orderDao.getOrderByIdd(orderId);
//            
//            if (order == null || order.getUserId() != userId || !order.getOrderStatus().equals("Completed")) {
//                response.sendRedirect("myordercontroller");
//                return;
//            }
//            
//            // Get order items
//            List<OrderItems> orderItems = orderDao.getOrderItems(orderId);
//            
//            // Convert to ProductFeedbackDto for display
//            ProductDao productDao = new ProductDao();
//            ReviewDao reviewDao = new ReviewDao();
//            List<ProductFeedbackDto> products = new ArrayList<>();
//            
//            for (OrderItems item : orderItems) {
//                ProductFeedbackDto dto = new ProductFeedbackDto();
//                dto.setId(item.getProductId());
//                
////                // Get product details
////                dto.setName(productDao.getProductNameById(item.getProductId()));
////                dto.setPrice(item.getPrice());
////                dto.setQuantity(item.getQuantity());
////                
////                // Get product image
////                dto.setImageUrl(productDao.getProductMainImageById(item.getProductId()));
//                
//                // Check if user has already reviewed this product in this order
//                int existingReviewId = reviewDao.getUserReviewForProductInOrder(userId, item.getProductId(), orderId);
//                if (existingReviewId > 0) {
//                    // Get existing review to pre-fill the form
//                    Review existingReview = reviewDao.getReviewById(existingReviewId);
//                    if (existingReview != null) {
//                        request.setAttribute("existingReview_" + item.getProductId(), existingReview);
//                        
//                        // Get review images
//                        List<FeedbackImage> images = reviewDao.getImagesByReviewId(existingReviewId);
//                        request.setAttribute("reviewImages_" + item.getProductId(), images);
//                    }
//                }
//                
//                products.add(dto);
//            }
//            
//            // Set attributes for the JSP
//            request.setAttribute("orderId", orderId);
//            request.setAttribute("products", products);
//            
//            // Forward to the feedback JSP
//            request.getRequestDispatcher("CustomerPage/CustomerSendFeedback.jsp").forward(request, response);
//            
//        } catch (Exception e) {
//            e.printStackTrace();
//            response.sendRedirect("myordercontroller");
//        }
//    } 
//
//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response)
//    throws ServletException, IOException {
//        try {
//            // Get parameters from form
//            String orderIdStr = request.getParameter("orderId");
//            String productIdStr = request.getParameter("productId");
//            String ratingStr = request.getParameter("rating");
//            String comment = request.getParameter("comment");
//            String action = request.getParameter("action"); // 'add', 'update', or 'delete'
//            String reviewIdStr = request.getParameter("reviewId"); // For updates/deletes
//            
//            // Validate required fields
//            if (orderIdStr == null || productIdStr == null) {
//                request.setAttribute("errorMessage", "Invalid request parameters");
//                doGet(request, response);
//                return;
//            }
//            
//            // Parse values
//            int orderId = Integer.parseInt(orderIdStr);
//            int productId = Integer.parseInt(productIdStr);
//            
//            // Get user ID from session
//            HttpSession session = request.getSession();
//            Integer userId = (Integer) session.getAttribute("userId");
//            if (userId == null) {
//                response.sendRedirect("login");
//                return;
//            }
//            
//            ReviewDao reviewDao = new ReviewDao();
//            ProductDao productDao = new ProductDao();
//            
//            // Handle delete action
//            if ("delete".equals(action) && reviewIdStr != null) {
//                int reviewId = Integer.parseInt(reviewIdStr);
//                
//                // Verify ownership before deletion
//                Review review = reviewDao.getReviewById(reviewId);
//                if (review != null && review.getUserId() == userId) {
//                    // Delete images first
//                    reviewDao.deleteReviewImages(reviewId);
//                    
//                    // Then delete the review
//                    if (reviewDao.deleteReview(reviewId)) {
//                        request.setAttribute("successMessage", "Your feedback has been deleted successfully.");
//                        
//                        // Update product average rating
////                        productDao.updateProductAverageRating(productId);
//                    } else {
//                        request.setAttribute("errorMessage", "Failed to delete feedback. Please try again.");
//                    }
//                } else {
//                    request.setAttribute("errorMessage", "You don't have permission to delete this feedback.");
//                }
//                
//                doGet(request, response);
//                return;
//            }
//            
//            // For add/update actions, validate rating and comment
//            if (ratingStr == null || comment == null) {
//                request.setAttribute("errorMessage", "Rating and comment are required");
//                doGet(request, response);
//                return;
//            }
//            
//            int rating = Integer.parseInt(ratingStr);
//            
//            // Handle update action
//            if ("update".equals(action) && reviewIdStr != null) {
//                int reviewId = Integer.parseInt(reviewIdStr);
//                
//                // Verify ownership before update
//                Review existingReview = reviewDao.getReviewById(reviewId);
//                if (existingReview != null && existingReview.getUserId() == userId) {
//                    // Update the review
//                    existingReview.setRating(rating);
//                    existingReview.setComment(comment);
//                    existingReview.setCreatedAt(new Timestamp(System.currentTimeMillis()));
//                    existingReview.setStatus(false); // Reset status for re-approval
//                    
//                    if (reviewDao.updateReview(existingReview)) {
//                        // Handle image updates if needed
//                        boolean deleteImages = "true".equals(request.getParameter("deleteImages"));
//                        if (deleteImages) {
//                            reviewDao.deleteReviewImages(reviewId);
//                        }
//                        
//                        // Upload new images
//                        handleImageUploads(request, reviewId, reviewDao);
//                        
//                        request.setAttribute("successMessage", "Your feedback has been updated and is pending approval.");
//                        
//                        // Update product average rating
////                        productDao.updateProductAverageRating(productId);
//                    } else {
//                        request.setAttribute("errorMessage", "Failed to update feedback. Please try again.");
//                    }
//                } else {
//                    request.setAttribute("errorMessage", "You don't have permission to update this feedback.");
//                }
//                
//                doGet(request, response);
//                return;
//            }
//            
//            // Handle add new review
//            Review review = new Review();
//            review.setProductId(productId);
//            review.setUserId(userId);
//            review.setRating(rating);
//            review.setComment(comment);
//            review.setCreatedAt(new Timestamp(System.currentTimeMillis()));
//            review.setStatus(false); // Set to false initially, admin will approve later
//            
//            // Save review to database
//            int reviewId = reviewDao.addReview(review);
//            
//            if (reviewId > 0) {
//                // Handle image uploads
//                handleImageUploads(request, reviewId, reviewDao);
//                
//                request.setAttribute("successMessage", "Your feedback has been submitted and is pending approval. Thank you!");
//                
//                // Update product average rating
////                productDao.updateProductAverageRating(productId);
//            } else {
//                request.setAttribute("errorMessage", "Failed to submit feedback. Please try again.");
//            }
//            
//            // Redirect back to the feedback page
//            doGet(request, response);
//            
//        } catch (Exception e) {
//            e.printStackTrace();
//            request.setAttribute("errorMessage", "An error occurred: " + e.getMessage());
//            doGet(request, response);
//        }
//    }
//    
//    /**
//     * Helper method to handle image and video uploads
//     */
//    private void handleImageUploads(HttpServletRequest request, int reviewId, ReviewDao reviewDao) throws Exception {
//        // Handle media uploads if any
//        List<Part> fileParts = new ArrayList<>();
//        
//        // Debug information
//        System.out.println("Total parts: " + request.getParts().size());
//        for (Part part : request.getParts()) {
//            System.out.println("Part name: " + part.getName() + ", size: " + part.getSize());
//            // Match both "media" and "media[]" names (previously "images" and "images[]")
//            if ((part.getName().equals("media") || part.getName().equals("media[]")) && part.getSize() > 0) {
//                fileParts.add(part);
//                System.out.println("Added file part: " + getFileName(part) + ", size: " + part.getSize());
//            }
//        }
//        
//        System.out.println("Total file parts: " + fileParts.size());
//        
//        // Validate number of files
//        if (fileParts.size() > 5) {
//            throw new Exception("You can only upload a maximum of 5 media files.");
//        }
//        
//        if (!fileParts.isEmpty()) {
//            // Define upload directory with absolute path to ensure it exists
//            String uploadPath = request.getServletContext().getRealPath("/") + "uploads/feedback";
//            File uploadDir = new File(uploadPath);
//            
//            if (!uploadDir.exists()) {
//                if (!uploadDir.mkdirs()) {
//                    System.out.println("Failed to create directory: " + uploadPath);
//                    throw new Exception("Failed to create upload directory.");
//                }
//            }
//            
//            System.out.println("Upload directory: " + uploadPath);
//            
//            int successfulUploads = 0;
//            StringBuilder errorMessages = new StringBuilder();
//            
//            // Save each file (image or video)
//            for (Part filePart : fileParts) {
//                try {
//                    String fileName = getFileName(filePart);
//                    String contentType = filePart.getContentType();
//                    boolean isVideo = false;
//                    
//                    // Determine if the file is a video
//                    if (contentType != null) {
//                        isVideo = contentType.startsWith("video/");
//                    } else {
//                        // Fallback to extension check if content type is null
//                        String extension = fileName.substring(fileName.lastIndexOf('.')).toLowerCase();
//                        isVideo = extension.equals(".mp4") || extension.equals(".webm") || extension.equals(".mov");
//                    }
//                    
//                    // Validate file size based on type (5MB for images, 15MB for videos)
//                    long maxSize = isVideo ? 15 * 1024 * 1024 : 5 * 1024 * 1024;
//                    if (filePart.getSize() > maxSize) {
//                        errorMessages.append("File ").append(fileName)
//                                    .append(" exceeds ").append(isVideo ? "15MB" : "5MB")
//                                    .append(" size limit. Skipped.\n");
//                        continue; // Skip this file if it's too large
//                    }
//                    
//                    // Validate video file type
//                    if (isVideo && !isValidVideoType(contentType)) {
//                        errorMessages.append("File ").append(fileName)
//                                    .append(" has an unsupported video format. Only MP4, WebM, and QuickTime formats are allowed. Skipped.\n");
//                        continue;
//                    }
//                    
//                    // Generate a unique filename to prevent conflicts
//                    String fileExtension = fileName.substring(fileName.lastIndexOf('.'));
//                    String uniqueFileName = System.currentTimeMillis() + "_" + successfulUploads + fileExtension;
//                    String filePath = uploadPath + File.separator + uniqueFileName;
//                    
//                    // Write the file
//                    filePart.write(filePath);
//                    System.out.println("File saved to: " + filePath);
//                    
//                    // Save media info to database (using existing FeedbackImage table for both images and videos)
//                    FeedbackImage media = new FeedbackImage();
//                    media.setReviewId(reviewId);
//                    media.setImageUrl("uploads/feedback/" + uniqueFileName);
//                    media.setCreatedAt(new Timestamp(System.currentTimeMillis()));
//                    
//                    if (reviewDao.addFeedbackImage(media)) {
//                        successfulUploads++;
//                        System.out.println("Media record added to database");
//                    } else {
//                        errorMessages.append("Failed to save media record for ").append(fileName).append("\n");
//                    }
//                } catch (Exception e) {
//                    errorMessages.append("Error processing file ").append(getFileName(filePart))
//                                .append(": ").append(e.getMessage()).append("\n");
//                    e.printStackTrace();
//                }
//            }
//            
//            // Handle error messages if any
//            if (errorMessages.length() > 0) {
//                if (successfulUploads == 0) {
//                    throw new Exception("Failed to upload media: " + errorMessages.toString());
//                } else {
//                    request.setAttribute("warningMessage", errorMessages.toString());
//                }
//            }
//        }
//    }
//    
//    /**
//     * Check if the video file type is supported
//     */
//    private boolean isValidVideoType(String contentType) {
//        if (contentType == null) return false;
//        return contentType.equals("video/mp4") || 
//               contentType.equals("video/webm") || 
//               contentType.equals("video/quicktime");
//    }
//
//    // Improved helper method to get file name from Part
//    private String getFileName(Part part) {
//        String contentDisp = part.getHeader("content-disposition");
//        System.out.println("Content-Disposition: " + contentDisp);
//        
//        String[] tokens = contentDisp.split(";");
//        for (String token : tokens) {
//            if (token.trim().startsWith("filename")) {
//                String fileName = token.substring(token.indexOf("=") + 2, token.length() - 1);
//                // Return a default name if empty
//                return fileName.isEmpty() ? "unknown_file" : fileName;
//            }
//        }
//        return "unknown_file";
//    }
//
//    @Override
//    public String getServletInfo() {
//        return "Customer Feedback Controller";
//    }
//}

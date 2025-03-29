<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <title>My Orders</title>
        <style>
            body {
                font-family: Arial, sans-serif;
                background-color: #4A90E2;
                text-align: center;
                margin: 0;
                padding: 0;
            }
            .container {
                width: 80%;
                margin: 30px auto;
                background: white;
                padding: 20px;
                border-radius: 8px;
                box-shadow: 0px 4px 10px rgba(0, 0, 0, 0.2);
            }
            h2 {
                color: #333;
            }
            .search-section {
                display: flex;
                justify-content: space-between;
                align-items: center;
                margin-bottom: 20px;
                padding: 10px;
                background: #f8f8f8;
                border-radius: 5px;
            }
            .search-section input, .search-section select {
                padding: 8px;
                width: 20%;
            }
            .search-btn {
                background-color: #28a745;
                color: white;
                padding: 10px;
                border: none;
                cursor: pointer;
                border-radius: 5px;
            }
            table {
                width: 100%;
                border-collapse: collapse;
                margin-top: 20px;
            }
            th, td {
                border: 1px solid #ddd;
                padding: 12px;
                text-align: center;
            }
            th {
                background-color: #007bff;
                color: white;
            }
            tr:nth-child(even) {
                background-color: #f2f2f2;
            }
            .pagination {
                margin-top: 20px;
            }
            .pagination button {
                padding: 10px;
                background-color: orange;
                border: none;
                cursor: pointer;
                border-radius: 5px;
            }
            .detail-btn {
                background-color: #ff9800;
                color: white;
                padding: 8px;
                border: none;
                cursor: pointer;
                border-radius: 5px;
            }
            button#applySettings {
                background: #28a745;
                color: white;
                border: none;
                padding: 10px;
                width: 100%;
                margin-top: 15px;
                border-radius: 5px;
                cursor: pointer;
            }
            .modal {
                display: none;
                position: fixed;
                top: 0;
                left: 0;
                width: 100%;
                height: 100%;
                background: rgba(0, 0, 0, 0.5);
                display: flex;
                justify-content: center;
                align-items: center;
            }
            .modal-content {
                background: white;
                padding: 20px;
                border-radius: 8px;
                box-shadow: 0 0 10px rgba(0, 0, 0, 0.3);
                width: 400px;
                text-align: center;
            }
            .customize-container {
                background: #286090;
                padding: 15px;
                border-radius: 5px;
                color: white;
            }
            .columns-container {
                margin-top: 10px;
            }
            .checkbox-group {
                display: flex;
                justify-content: space-between;
                flex-wrap: wrap;
            }
        </style>
    </head>
    <body>
        <div class="container">
            <h2>My Orders</h2>

            <!-- Search & Filter Section -->
            <!-- Search & Filter Section -->
            <form action="${pageContext.request.contextPath}/myordercontroller" method="GET" class="search-form">
                <div class="search-container">
                    <!-- Search by Order ID -->
                    <input type="text" name="orderId" placeholder="Enter Order ID" value="${param.orderId}">
                    <button type="submit" class="search-btn">Search by ID</button>

                    <!-- Filter by Order Status (Auto Submit) -->
                    <select name="status" onchange="this.form.submit()">
                        <option value="">Order Status</option>
                        <c:forEach var="status" items="${orderStatuses}">
                            <option value="${status}" ${param.status == status ? 'selected' : ''}>${status}</option>
                        </c:forEach>
                    </select>

                    <!-- Search by Date Range -->
                    <input type="date" name="startDate" value="${param.startDate}">
                    <input type="date" name="endDate" value="${param.endDate}">
                    <button type="submit" class="search-btn">Search by Date</button>
                </div>
            </form>



            <!-- Orders Table -->
            <table>
                <tr>
                    <th>Id</th>
                    <th>Order Date</th>
                    <th>Product Name</th>
                    <th>Total Cost</th>
                    <th>Status</th>
                    <th>Action</th>
                </tr>

                <c:choose>
                    <c:when test="${empty orders}">
                        <tr>
                            <td colspan="6">No orders found</td>
                        </tr>
                    </c:when>
                    <c:otherwise>
                        <c:forEach var="order" items="${orders}">
                            <tr>
                                <td>${order.orderId}</td>
                                <td>${order.orderDate}</td>
                                <td>${order.productNames}</td>
                                <td>${order.totalCost}</td>
                                <td>${order.status}</td>
                                <td>
                                    <a href="OrderDetailsServlet?orderId=${order.orderId}">
                                        <button class="detail-btn">View Details</button>
                                    </a>
                                    <c:if test="${order.status eq 'Completed'}">
                                        <a href="CustomerSendFeedback?orderId=${order.orderId}">
                                            <button class="detail-btn" style="background-color: #4CAF50;">Feedback</button>
                                        </a>
                                    </c:if>
                                </td>
                            </tr>
                        </c:forEach>
                    </c:otherwise>
                </c:choose>
            </table>

            <!-- Pagination -->
            <div class="pagination">
                <button>&lt;</button>
                <button>1</button>
                <button>2</button>
                <button>...</button>
                <button>&gt;</button>
            </div>

            <!-- Customize Button -->
            <button id="customizeTableBtn">Customize Table</button>
        </div>
                    </div>
        <!-- Popup Customize Table -->
        <div id="customizeTableModal" class="modal">
            <div class="modal-content">
                <h2>Customize Table</h2>
                <div class="customize-container">
                    <label>Rows Per Table:</label>
                    <input type="number" id="rowsPerTable" placeholder="Enter number of rows">

                    <div class="columns-container">
                        <label>Select Columns:</label>
                        <div class="checkbox-group">
                            <label><input type="checkbox" checked> Column A</label>
                            <label><input type="checkbox"> Column B</label>
                            <label><input type="checkbox"> Column C</label>
                            <label><input type="checkbox"> Column ...</label>
                        </div>
                    </div>

                    <button id="applySettings">Apply Settings</button>
                </div>
            </div>
        </div>
    </body>
</html>
<script>
document.addEventListener("DOMContentLoaded", function () {
    const customizeBtn = document.getElementById("customizeTableBtn"); // Nút mở popup
    const popup = document.getElementById("customizeTableModal"); // Popup

    // Đảm bảo popup ẩn khi trang tải lên
    popup.style.display = "none";

    // Mở popup khi ấn vào nút Customize Table
    customizeBtn.addEventListener("click", function () {
        popup.style.display = "flex"; 
    });

    // Đóng popup khi click ra ngoài
    window.addEventListener("click", function (event) {
        if (event.target === popup) {
            popup.style.display = "none";
        }
    });
});
</script>

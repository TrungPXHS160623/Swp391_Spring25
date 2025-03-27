<%-- 
    Document   : OrderList
    Created on : Mar 27, 2025, 9:59:20 PM
    Author     : LENOVO
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>List Order</title>
        <!-- Bootstrap CSS (nếu muốn style) -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <style>
            .filter-form .form-control, .filter-form .form-select {
                max-width: 200px;
                display: inline-block;
                margin-right: 10px;
            }
        </style>
    </head>
    <body>
        <div class="container mt-4">
            <h1 class="mb-4">List Order</h1>
            <!-- Nếu cần form tìm kiếm/ lọc, đặt ở đây, còn nếu không thì bỏ -->
            <!-- 
            <form method="get" action="OrderListController" class="filter-form mb-4">
                <input type="date" name="fromDate" class="form-control" />
                <input type="date" name="toDate" class="form-control" />
                <select name="status" class="form-select">
                    <option value="">-- Status --</option>
                    <option value="Pending">Pending</option>
                    <option value="Shipped">Shipped</option>
                    <option value="Completed">Completed</option>
                    <option value="Canceled">Canceled</option>
                </select>
                <input type="text" name="searchKey" class="form-control" placeholder="Search by Id, Name" />
                <button type="submit" class="btn btn-primary">Filter</button>
            </form>
            -->

            <table class="table table-bordered table-striped">
                <thead>
                    <tr>
                        <th>Order ID</th>
                        <th>Order Date</th>
                        <th>Customer Name</th>
                        <th>Product(s)</th>
                        <th>Total Cost</th>
                        <th>Status</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <c:choose>
                        <c:when test="${not empty orderList}">
                            <c:forEach var="order" items="${orderList}">
                                <tr>
                                    <td>${order.orderId}</td>
                                    <!-- Giả sử orderDate là Timestamp, format dd/MM/yyyy -->
                                    <td>
                                        <fmt:formatDate value="${order.orderDate}" pattern="dd/MM/yyyy" var="formattedDate"/>
                                        ${formattedDate}
                                    </td>
                                    <td>${order.customerName}</td>
                                    <td>${order.productList}</td>
                                    <td>${order.totalCost}</td>
                                    <td>${order.orderStatus}</td>
                                    <td>
                                        <!-- Nút View Detail không dẫn đến đâu -->
                                        <button class="btn btn-info btn-sm">View Details</button>
                                    </td>
                                </tr>
                            </c:forEach>
                        </c:when>
                        <c:otherwise>
                            <tr>
                                <td colspan="7" class="text-center">No Orders Found.</td>
                            </tr>
                        </c:otherwise>
                    </c:choose>
                </tbody>
            </table>

            <!-- Phân trang (nếu cần) -->
            <!-- <nav aria-label="Page navigation">
                <ul class="pagination justify-content-center">
                    ...
                </ul>
            </nav> -->
        </div>
            <!-- Bootstrap Bundle JS -->
            <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
        </body>
    </html>

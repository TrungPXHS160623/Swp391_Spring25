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
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <style>
            .filter-form .form-control, .filter-form .form-select {
                max-width: 180px;
                display: inline-block;
                margin-right: 10px;
            }
        </style>
    </head>
    <body>
        <div class="container mt-4">
            <h1 class="mb-4">List Order</h1>

            <!-- Filter Form -->
            <form method="get" action="OrderListController" class="filter-form mb-4">
                <label>From</label>
                <input type="date" name="fromDate" class="form-control" value="${fromDate}" />
                <label>To</label>
                <input type="date" name="toDate" class="form-control" value="${toDate}" />

                <select name="status" class="form-select">
                    <option value="">-- Status --</option>
                    <option value="Pending"   ${status == 'Pending' ? 'selected' : ''}>Pending</option>
                    <option value="Shipped"   ${status == 'Shipped' ? 'selected' : ''}>Shipped</option>
                    <option value="Completed" ${status == 'Completed' ? 'selected' : ''}>Completed</option>
                    <option value="Canceled"  ${status == 'Canceled' ? 'selected' : ''}>Canceled</option>
                </select>

                <input type="text" name="searchKey" class="form-control" placeholder="Search by Id, Name" value="${searchKey}" />

                <button type="submit" class="btn btn-primary">Filter</button>
            </form>

            <!-- Table -->
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
                                    <td>
                                        <fmt:formatDate value="${order.orderDate}" pattern="dd/MM/yyyy" var="formattedDate"/>
                                        ${formattedDate}
                                    </td>
                                    <td>${order.customerName}</td>
                                    <td>${order.productList}</td>
                                    <td>${order.totalCost}</td>
                                    <td>${order.orderStatus}</td>
                                    <td>
                                        <!-- Nút View Detail không dẫn đi đâu -->
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

            <!-- Pagination -->
            <c:if test="${totalPages > 1}">
                <nav aria-label="Page navigation">
                    <ul class="pagination justify-content-center">
                        <li class="page-item ${currentPage == 1 ? 'disabled' : ''}">
                            <a class="page-link" href="OrderListController?page=${currentPage - 1}&pageSize=${pageSize}
                               &fromDate=${fromDate}&toDate=${toDate}&status=${status}&searchKey=${searchKey}">
                                Previous
                            </a>
                        </li>
                        <c:forEach var="i" begin="1" end="${totalPages}">
                            <li class="page-item ${i == currentPage ? 'active' : ''}">
                                <a class="page-link" href="OrderListController?page=${i}&pageSize=${pageSize}
                                   &fromDate=${fromDate}&toDate=${toDate}&status=${status}&searchKey=${searchKey}">
                                    ${i}
                                </a>
                            </li>
                        </c:forEach>
                        <li class="page-item ${currentPage == totalPages ? 'disabled' : ''}">
                            <a class="page-link" href="OrderListController?page=${currentPage + 1}&pageSize=${pageSize}
                               &fromDate=${fromDate}&toDate=${toDate}&status=${status}&searchKey=${searchKey}">
                                Next
                            </a>
                        </li>
                    </ul>
                </nav>
            </c:if>
        </div>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>

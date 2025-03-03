<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Slider Lists</title>
        <style>
            body {
                background-color: #4682B4;
                font-family: Arial, sans-serif;
                text-align: center;
            }
            .container {
                width: 80%;
                margin: auto;
                background: white;
                padding: 20px;
                border-radius: 10px;
            }
            .search-section {
                display: flex;
                justify-content: space-between;
                margin-bottom: 20px;
            }
            .search-section input, .search-section select {
                padding: 5px;
                font-size: 14px;
            }
            .search-section button {
                background-color: green;
                color: white;
                padding: 8px 15px;
                border: none;
                cursor: pointer;
            }
            table {
                width: 100%;
                border-collapse: collapse;
                margin-top: 10px;
            }
            table, th, td {
                border: 1px solid black;
            }
            th, td {
                padding: 10px;
                text-align: center;
            }
            .button-group {
                display: flex;
                justify-content: space-between; /* Dàn đều 3 nút */
                width: 100%; /* Đảm bảo full ô */
            }

            .button-group form {
                flex: 1; /* Mỗi form chiếm 1 phần bằng nhau */
                display: flex;
                justify-content: center; /* Căn giữa nút */
            }

            .action-btn {
                padding: 10px 15px; /* Nút to hơn */
                font-size: 16px; /* Chữ to hơn */
                width: 90%; /* Đảm bảo nút rộng hơn */
                max-width: 120px; /* Không quá to */
            }

            .active {
                background-color: yellow;
                color: black;
            }

            .deactivate {
                background-color: red;
                color: white;
            }

            .update {
                background-color: green;
                color: white;
            }

            .pagination {
                margin-top: 10px;
            }
            .pagination button {
                background-color: orange;
                padding: 5px 10px;
                border: none;
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
        </style>
    </head>
    <body>
        <div class="container">
            <h2>Slider Lists</h2>
            <div class="search-section">
                <form method="GET" action="${pageContext.request.contextPath}/sliderlistcontroller">
                    <input type="text" name="searchTitle" placeholder="Search by title"
                           value="${searchTitle}">

                    <input type="text" name="searchLink" placeholder="Search by link"
                           value="${searchLink}">

                    <select name="sliderStatus">
                        <option value="">Slider Status</option>
                        <option value="1" ${sliderStatus == '1' ? 'selected' : ''}>Active</option>
                        <option value="0" ${sliderStatus == '0' ? 'selected' : ''}>Inactive</option>
                    </select>

                    <button type="submit">Search</button>
                </form>
                <!-- Add -->
                <a href="${pageContext.request.contextPath}/AdminPage/AddSlider.jsp">
                    <button class="action-btn update">Add</button>
                </a>
            </div>
            <% 
String updateMessage = (String) session.getAttribute("updateMessage");
if (updateMessage != null) {
            %>
            <div id="updateAlert" class="alert alert-info" style="display: flex; align-items: center; background-color: #d1ecf1; border-color: #bee5eb; color: #0c5460; padding: 15px; border-radius: 5px; margin-bottom: 20px;">
                <i class="fas fa-info-circle" style="font-size: 24px; margin-right: 10px;"></i>
                <div>
                    <strong>Thông Báo:</strong> <%= updateMessage %>
                </div>
                <button type="button" onclick="this.parentElement.style.display = 'none';" style="background: transparent; border: none; color: #0c5460; cursor: pointer; margin-left: auto;">
                    <i class="fas fa-times-circle" style="font-size: 24px;"></i>
                </button>
            </div>

            <script>
                // Tự động ẩn thông báo sau 4 giây
                setTimeout(function () {
                    document.getElementById('updateAlert').style.display = 'none';
                }, 4000);
            </script>
            <%
                session.removeAttribute("updateMessage"); // Xóa thông báo sau khi hiển thị
                }
            %>


            <table border="10">
                <tr>
                    <th>Id</th>
                    <th>Title</th>
                    <th>Image</th>
                    <th>BackLink</th>
                    <th>Status</th>
                    <th>Action</th>
                </tr>
                <c:forEach var="slider" items="${sliderList}">
                    <tr>
                        <td>${slider.id}</td>
                        <td>${slider.title}</td>
                        <td><img src="${slider.imageUrl}" alt="Slider Image" width="100"></td>
                        <td>${slider.backLink}</td>
                        <td class="${slider.status == 1 ? '1' : '0'}">
                            ${slider.status == 1 ? 'Active' : 'Inactive'}
                        </td>
                        <td>
                            <div class="button-group">
                                <!-- Active -->
                                <form action="${pageContext.request.contextPath}/sliderlistcontroller" method="post">
                                    <input type="hidden" name="action" value="activate">
                                    <input type="hidden" name="sliderId" value="${slider.id}">
                                    <button type="submit" class="action-btn active">Active</button>
                                </form>

                                <!-- Deactivate -->
                                <form action="${pageContext.request.contextPath}/sliderlistcontroller" method="post">
                                    <input type="hidden" name="action" value="deactivate">
                                    <input type="hidden" name="sliderId" value="${slider.id}">
                                    <button type="submit" class="action-btn deactivate">Deactivate</button>
                                </form>

                                <!-- Update -->
                                <form action="${pageContext.request.contextPath}/sliderlistcontroller" method="post">
                                    <input type="hidden" name="action" value="load">
                                    <input type="hidden" name="sliderId" value="${slider.id}">
                                    <button type="submit" class="action-btn update">Update</button>
                                </form>
                            </div>
                        </td>
                    </tr>
                </c:forEach>
            </table>
            <div class="pagination">
                <button><</button>
                <button>1</button>
                <button>2</button>
                <button>...</button>
                <button>></button>
            </div>
            <button id="customizeTableBtn">Customize Table</button>


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
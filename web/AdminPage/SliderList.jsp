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
            .action-btn {
                padding: 5px 10px;
                border: none;
                cursor: pointer;
            }
            .active {
                background-color: yellow;
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
                <!-- Nút Add Slider -->
                <button onclick="window.location.href = 'AddSlider.jsp'" class="btn btn-success">Add Slider</button>
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
            <table border="1">
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
                            <a href="SliderServlet?action=activate&sliderId=${slider.id}">
                                <button class="action-btn active">Active</button>
                            </a>
                            <a href="SliderServlet?action=deactivate&sliderId=${slider.id}">
                                <button class="action-btn deactivate">Deactivate</button>
                            </a>
                            <a href="UpdateSlider.jsp?sliderId=${slider.id}">
                                <button class="action-btn update">Update</button>
                            </a>
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
            <button style="background-color: coral; margin-top: 10px;">Customize Table</button>
        </div>
    </body>
</html>
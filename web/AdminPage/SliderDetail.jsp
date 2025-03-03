<%@ page import="Entity.Slider" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Slider Detail</title>
        <style>
            body {
                background-color: #4682B4;
                font-family: Arial, sans-serif;
                display: flex;
                justify-content: center;
                align-items: center;
                height: 100vh;
                margin: 0;
            }
            .container {
                width: 60%; /* Điều chỉnh theo ý bạn */
                max-width: 600px; /* Để tránh quá rộng trên màn hình lớn */
                background-color: white;
                padding: 20px;
                border-radius: 10px;
                box-shadow: 0px 4px 10px rgba(0, 0, 0, 0.2);
                overflow: hidden; /* Ngăn nội dung tràn ra ngoài */
            }

            .container input,
            .container select,
            .container textarea {
                width: 100%;
                max-width: 100%; /* Ngăn không cho input vượt quá container */
                box-sizing: border-box; /* Đảm bảo padding không làm tăng kích thước */
            }
            .title {
                text-align: center;
                font-size: 24px;
                font-weight: bold;
                margin-bottom: 20px;
                color: #333;
            }
            .field, select {
                width: 100%;
                padding: 10px;
                margin: 5px 0;
                border: 1px solid #ddd;
                border-radius: 5px;
                font-size: 16px;
            }
            .image-box {
                width: 100%;
                display: flex;
                justify-content: center;
                margin-bottom: 15px;
            }
            .image-box img {
                max-width: 100%;
                height: auto;
                border-radius: 5px;
                box-shadow: 0px 2px 5px rgba(0, 0, 0, 0.2);
            }
            .button-container {
                display: flex;
                justify-content: space-between;
                margin-top: 20px;
            }

            .button {
                padding: 10px 20px;
                border: none;
                border-radius: 5px;
                font-size: 16px;
                cursor: pointer;
                transition: 0.3s;
                text-align: center;
                min-width: 100px; /* Đảm bảo nút có kích thước tối thiểu */
            }

            /* Nút Update */
            .update-button {
                background-color: #28a745;
                color: white;
            }

            .update-button:hover {
                background-color: #218838;
            }

            /* Nút Back */
            .back-button {
                background-color: #17a2b8;
                color: white;
            }

            .back-button:hover {
                background-color: #138496;
            }
            .readonly {
                background-color: #e9ecef;
                cursor: not-allowed;
            }
        </style>
    </head>
    <body>
        <%
        Slider slider = (Slider) request.getAttribute("slider");
        if (slider != null) {
        %>
        <div class="container">
            <div class="title">Slider Detail</div>
            <form action="${pageContext.request.contextPath}/sliderlistcontroller" method="post">
                <input type="hidden" name="action" value="update">
                <input type="hidden" name="sliderId" value="<%= slider.getId() %>">

                <label>Slider ID</label>
                <input type="text" name="sliderId" class="field readonly" value="<%= slider.getId() %>" readonly title="Bạn không thể chinh sửa!">

                <label>Image:</label>
                <div class="image-box">
                    <img src="<%= slider.getImageUrl() %>" alt="Slider Image">
                </div>
                <input type="hidden" name="imageUrl" value="<%= slider.getImageUrl() %>">

                <label>Title:</label>
                <input type="text" class="field" name="title" value="<%= slider.getTitle() %>">

                <label>Backlink:</label>
                <input type="text" class="field" name="backlink" value="<%= slider.getBackLink() %>">

                <label>Status:</label>
                <select name="status" class="field">
                    <option value="1" <%= (slider.getStatus() == 1) ? "selected" : "" %>>Active</option>
                    <option value="0" <%= (slider.getStatus() == 0) ? "selected" : "" %>>Inactive</option>
                </select>

                <label>Notes:</label>
                <input type="text" class="field" name="notes" value="<%= slider.getNotes() %>">

                <label>Created Date</label>
                <input type="text" class="field readonly" name="createdDate" value="<%= slider.getCreatedDate() %>" readonly title="Bạn không thể chinh sửa!">

                <label>Updated Date</label>
                <input type="text" class="field readonly" name="updatedDate" value="<%= slider.getUpdatedDate() %>" readonly title="Hệ thống tự cập nhật!">

                <label>Last Updated By</label>
                <input type="text" class="field readonly" name="lastUpdatedBy" value="<%= slider.getLastUpdatedBy() %>" readonly title="Hệ thống tự cập nhật!>

                       <div class="button-container">
                <button type="submit" class="button update-button">Update</button>
                <button type="button" class="button back-button" onclick="window.location.href = '<%= request.getContextPath() %>/sliderlistcontroller'">Back</button>
        </div>
    </form>
</div>
<% } else { %>
<p>Không tìm thấy slider</p>
<% } %>
</body>
</html>


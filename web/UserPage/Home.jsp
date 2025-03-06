<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ page import="Entity.User" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Home</title>
    <style>
        .btn {
            display: inline-block;
            padding: 10px 15px;
            background-color: #007bff;
            color: white;
            text-decoration: none;
            border-radius: 5px;
            margin: 5px;
        }
        .btn:hover {
            background-color: #0056b3;
        }
    </style>
</head>
<body>
    <h1>Welcome to Home Page</h1>
    <p>This is a test page after login.</p>

    <!-- Nút chuyển đến trang đổi mật khẩu -->
    <a href="ChangePassword.jsp" class="btn">Change Password</a>
    <%
    User user = (User) session.getAttribute("user");
    if (user != null) {
%>
        <p>Logged in as: <%= user.getFull_name() %> (ID: <%= user.getUser_id() %>)</p>
<%
    } else {
%>
        <p>User session is NULL!</p>
<%
    }
%>
    <!-- Nút chuyển đến trang User Profile -->
    <%
    Integer userId = (Integer) session.getAttribute("userId");
%>
<a href="<%= request.getContextPath() %>/userprofile">Go to Profile</a>
<br></br>
<a href="<%= request.getContextPath() %>/sliderlistcontroller">Go to Management Slider</a>
<br></br>
<a href="<%= request.getContextPath() %>/myordercontroller" class="btn">My Orders</a>
</body>
</html>

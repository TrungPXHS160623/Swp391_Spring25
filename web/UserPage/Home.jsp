<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ page import="Entity.User" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Home</title>
        <style>
            * {
                margin: 0;
                padding: 0;
                box-sizing: border-box;
            }

            body {
                font-family: Arial, sans-serif;
                background-color: #f8f9fa;
                padding: 20px;
            }

            .container {
                display: flex;
                justify-content: space-between;
            }

            .leftbar {
                width: 20%;
                background-color: #b3e0ff;
                padding: 15px;
                border-radius: 5px;
            }

            .main-content {
                width: 60%;
                background-color: #ffee58;
                padding: 20px;
                border-radius: 5px;
                box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1);
                background: linear-gradient(135deg, #007bff, #ffcc80);
            }

            .rightbar {
                width: 20%;
                background-color: #b3e0ff;
                padding: 15px;
                border-radius: 5px;
            }

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
            .section-title {
                font-size: 24px;
                font-weight: bold;
                color: #333;
                margin: 30px 0 10px 0; /* Khoảng cách trước và sau tiêu đề */
            }
        </style>
    </head>
    <body>
        <h1>Welcome to Home Page</h1>
       

        <div class="container">
            <!-- Left Sidebar -->
            <div class="leftbar">
                <jsp:include page="/leftbarcontroller" />
            </div>

            <!-- Main Content -->
            <div class="main-content">
                <jsp:include page="/CommonPage/Header.jsp" />
                <h2 class="section-title">Sliders Section</h2>
                <jsp:include page="/sliderinhomecontroller" />
                <h2 class="section-title">Categories Section</h2>
                <jsp:include page="/categoryinhomecontroller" />
                <h2 class="section-title">Products Section</h2>
                <jsp:include page="/productinhomecontroller" />
                <h2 class="section-title">Blogs Section</h2>
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

                <a href="<%= request.getContextPath() %>/userprofile" class="btn">Go to Profile</a>
                <a href="<%= request.getContextPath() %>/sliderlistcontroller" class="btn">Go to Management Slider</a>
                <a href="<%= request.getContextPath() %>/myordercontroller" class="btn">My Orders</a>
                <jsp:include page="/CommonPage/Footer.jsp" />
            </div>

            <!-- Right Sidebar -->
            <div class="rightbar">
                <jsp:include page="/rightbarcontroller" />
            </div>
        </div>
    </body>
</html>

<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Change Password - Electro Mart Management System</title>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
        <style>
            body {
                font-family: 'Roboto', sans-serif;
                background: linear-gradient(to right, #00c6ff, #0072ff);
                display: flex;
                justify-content: center;
                align-items: center;
                height: 100vh;
                margin: 0;
                color: #fff;
            }

            .container {
                background-color: rgba(255, 255, 255, 0.9);
                padding: 40px;
                box-shadow: 0 0 30px rgba(0, 0, 0, 0.2);
                border-radius: 15px;
                width: 400px;
                text-align: center;
            }

            .container h1 {
                color: #0c64dc;
                margin-bottom: 20px;
                font-size: 28px;
                font-weight: bold;
            }

            .input-group {
                position: relative;
                margin-bottom: 20px;
            }

            .input-group input {
                width: 100%;
                padding: 15px 15px 15px 45px;
                border: 1px solid #ccc;
                border-radius: 5px;
                font-size: 16px;
                box-sizing: border-box;
            }

            .input-group i {
                position: absolute;
                left: 15px;
                top: 50%;
                transform: translateY(-50%);
                color: #aaa;
                font-size: 18px;
            }

            .container input[type="submit"] {
                background-color: #0c64dc;
                color: #fff;
                border: none;
                padding: 15px 0;
                border-radius: 5px;
                width: 100%;
                font-size: 18px;
                cursor: pointer;
                transition: background-color 0.3s ease;
            }

            .container input[type="submit"]:hover {
                background-color: #094ea1;
            }

            .container a {
                display: block;
                margin-top: 10px;
                color: #0c64dc;
                text-decoration: none;
                transition: color 0.3s ease;
            }

            .container a:hover {
                color: #0072ff;
            }
        </style>
    </head>
    <body>
        <div class="container">
            <h1>Change Password</h1>
            <% 
            String errorMessagePassChanging = (String) request.getAttribute("errorMessagePassChanging"); 
            String successMessageChanging = (String) request.getAttribute("successMessageChanging");
            %>
            <div style="margin-bottom: 20px;">
                <% if (errorMessagePassChanging != null) { %>
                <div style="color: red; font-weight: bold;"><%= errorMessagePassChanging %></div>
                <% } %>
                <% if (successMessageChanging != null) { %>
                <div style="color: green; font-weight: bold;"><%= successMessageChanging %></div>
                <% } %>
            </div>
            <form action="${pageContext.request.contextPath}/changepasswordcontroller" method="post">
                <div class="input-group">
                    <i class="fas fa-lock"></i>
                    <input type="password" name="currentPassword" placeholder="Current Password" required>
                </div>
                <div class="input-group">
                    <i class="fas fa-key"></i>
                    <input type="password" name="newPassword" placeholder="New Password" required>
                </div>
                <div class="input-group">
                    <i class="fas fa-key"></i>
                    <input type="password" name="confirmPassword" placeholder="Confirm New Password" required>
                </div>
                <input type="submit" value="Change Password">
            </form>
            <a href="home.jsp">Back to Home</a>
        </div>
    </body>
</html>
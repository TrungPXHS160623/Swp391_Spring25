<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Đăng Ký - Electro Mart</title>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
        <style>
            @import url('https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;600&display=swap');

            body {
                font-family: 'Poppins', sans-serif;
                background: linear-gradient(to right, #00c6ff, #0072ff);
                display: flex;
                justify-content: center;
                align-items: center;
                height: 100vh;
                margin: 0;
                color: #fff;
            }

            .register-container {
                background: rgba(255, 255, 255, 0.95);
                padding: 30px;
                box-shadow: 0 10px 30px rgba(0, 0, 0, 0.2);
                border-radius: 12px;
                width: 420px;
                text-align: center;
                position: relative;
                animation: fadeIn 0.8s ease-in-out;
            }

            @keyframes fadeIn {
                from {
                    opacity: 0;
                    transform: translateY(-10px);
                }
                to {
                    opacity: 1;
                    transform: translateY(0);
                }
            }

            .register-container h1 {
                color: #0c64dc;
                font-size: 26px;
                font-weight: 600;
            }

            .register-container form {
                margin-top: 15px;
            }

            .input-group {
                position: relative;
                margin-bottom: 15px;
            }

            .input-group input, .input-group select {
                width: 100%;
                padding: 12px 15px 12px 45px;
                border: 1px solid #ccc;
                border-radius: 8px;
                font-size: 14px;
                box-sizing: border-box;
                transition: border 0.3s ease;
            }

            .input-group input:focus, .input-group select:focus {
                border: 1px solid #0072ff;
                outline: none;
            }

            .input-group i {
                position: absolute;
                left: 15px;
                top: 50%;
                transform: translateY(-50%);
                color: #777;
                font-size: 16px;
            }

            .register-container input[type="submit"] {
                background: #0c64dc;
                color: #fff;
                border: none;
                padding: 12px;
                border-radius: 8px;
                width: 100%;
                font-size: 16px;
                cursor: pointer;
                transition: background 0.3s ease;
            }

            .register-container input[type="submit"]:hover {
                background: #094ea1;
            }

            .register-container a {
                display: block;
                margin-top: 12px;
                color: #0c64dc;
                font-size: 14px;
                text-decoration: none;
                transition: color 0.3s ease;
            }

            .register-container a:hover {
                color: #0072ff;
            }

            .register-container img {
                width: 100px;
                height: auto;
                margin-bottom: 15px;
            }
            .error-message {
                background: #ff4d4d;
                color: white;
                padding: 10px;
                border-radius: 5px;
                text-align: left;
                margin-bottom: 10px;
                list-style: none;
            }
            .error-message ul {
                padding-left: 20px;
            }
            .error-message li {
                margin: 5px 0;
            }
        </style>
    </head>
    <body>
        <%
            List<String> errors = (List<String>) request.getAttribute("errors");
            if (errors != null && !errors.isEmpty()) {
        %>
        <div class="error-message">
            <ul>
                <% for (String error : errors) { %>
                <li><%= error %></li>
                    <% } %>
            </ul>
        </div>
        <% } %>
        <div class="register-container">
            <img src="logo.png" alt="Electro Mart">
            <h1>Register</h1>
            <form action="${pageContext.request.contextPath}/registercontroller"" method="post">
                <div class="input-group">
                    <i class="fas fa-user"></i>
                    <input type="text" name="fullname" placeholder="FullName" required>
                </div>
                <div class="input-group">
                    <i class="fas fa-envelope"></i>
                    <input type="email" name="email" placeholder="Email" required>
                </div>
                <div class="input-group">
                    <i class="fas fa-phone"></i>
                    <input type="text" name="phone" placeholder="PhoneNumber" required>
                </div>
                <div class="input-group">
                    <i class="fas fa-map-marker-alt"></i>
                    <input type="text" name="address" placeholder="Address" required>
                </div>
                <div class="input-group">
                    <i class="fas fa-venus-mars"></i>
                    <select name="gender" required>
                        <option value="" disabled selected>Choose a gender</option>
                        <option value="Male">Male</option>
                        <option value="Female">Female</option>
                        <option value="Other">Other</option>
                    </select>
                </div>
                <div class="input-group">
                    <i class="fas fa-lock"></i>
                    <input type="password" name="password" placeholder="Your Password" required>
                </div>
                <div class="input-group">
                    <i class="fas fa-lock"></i>
                    <input type="password" name="confirm_password" placeholder="Confirm your passowrd" required>
                </div>
                <input type="submit" value="Đăng Ký">
            </form>
            <a href="Login.jsp">Already have an account? Login Right Now</a>
        </div>

    </body>
</html>

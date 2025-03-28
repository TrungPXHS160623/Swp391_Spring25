<%@ page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Login - Electro Mart Management System</title>
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

            .login-container {
                background-color: rgba(255, 255, 255, 0.9);
                padding: 40px;
                box-shadow: 0 0 30px rgba(0, 0, 0, 0.2);
                border-radius: 15px;
                width: 400px;
                text-align: center;
                position: relative;
            }

            .login-container h1 {
                color: #0c64dc;
                margin-bottom: 20px;
                font-size: 32px;
                font-weight: bold;
            }

            .login-container form {
                margin-top: 20px;
            }

            .login-container .input-group {
                position: relative;
                margin-bottom: 20px;
            }

            .login-container .input-group input {
                width: 100%;
                padding: 15px 15px 15px 45px;
                border: 1px solid #ccc;
                border-radius: 5px;
                font-size: 16px;
                box-sizing: border-box;
            }

            .login-container .input-group i {
                position: absolute;
                left: 15px;
                top: 50%;
                transform: translateY(-50%);
                color: #aaa;
                font-size: 18px; /* Điều chỉnh kích thước icon */
            }

            .login-container input[type="submit"] {
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

            .login-container input[type="submit"]:hover {
                background-color: #094ea1;
            }

            .login-container a {
                display: block;
                margin-top: 10px;
                color: #0c64dc;
                text-decoration: none;
                transition: color 0.3s ease;
            }

            .login-container a:hover {
                color: #0072ff;
            }

            .login-container img {
                width: 120px;
                height: auto;
                margin-bottom: 20px;
            }
            .remember-me {
                display: flex;
                align-items: center;
                justify-content: start;
                margin-bottom: 20px;
                font-size: 16px;
                color: #333;
            }

            .remember-me input {
                margin-right: 10px;
                width: 18px;
                height: 18px;
            }

            .remember-me label {
                cursor: pointer;
                color: #555;
            }
            .remember-me label:hover {
                color: #0072ff;
            }
            /* Popup container */
            
            .popup-container {
                position: fixed;
                top: 0;
                left: 0;
                width: 100%;
                height: 100%;
                background: rgba(0, 0, 0, 0.5);
                display: flex;
                justify-content: center;
                align-items: center;
                z-index: 1000; /* Đảm bảo nó nằm trên tất cả các phần tử khác */
            }

            /* Đặt login container có z-index thấp hơn để không che popup */
            .login-container {
                position: relative;
                z-index: 10;
            }

            /* Popup box */
            .popup {
                background: white;
                padding: 20px;
                border-radius: 10px;
                text-align: center;
                box-shadow: 0 0 10px rgba(0, 0, 0, 0.3);
                min-width: 300px;
            }

            .popup p {
                color: red;
                font-size: 16px;
                margin-bottom: 15px;
            }

            .popup button {
                background-color: #0c64dc;
                color: white;
                border: none;
                padding: 10px 20px;
                border-radius: 5px;
                cursor: pointer;
                font-size: 16px;
            }

            .popup button:hover {
                background-color: #094ea1;
            }
        </style>
    </head>
    <body>
        <% String errorMessage = (String) request.getAttribute("errorMessage"); %>
        <div id="errorPopup" class="popup-container" style="display: none;">
            <div class="popup">
                <p><%= errorMessage != null ? errorMessage : "" %></p>
                <button onclick="closePopup()">OK</button>
            </div>
        </div>
        <div class="login-container">

            <img src="" alt="">
            <h1>Login</h1>
            <form action="${pageContext.request.contextPath}/logincontroller" method="post">
                <div class="input-group">
                    <i class="fas fa-user"></i>
                    <input type="text" name="username" placeholder="Email" required>
                </div>
                <div class="input-group">
                    <i class="fas fa-lock"></i>
                    <input type="password" name="password" placeholder="Password" required>
                </div>
                <div class="remember-me">
                    <input type="checkbox" id="rememberMe" name="rememberMe">
                    <label for="rememberMe">Remember Me</label>
                </div>
                <input type="submit" value="Login">
            </form>
            <a href="ResetPassword.jsp">Forgot password?</a>
            <a href="${pageContext.request.contextPath}/UserPage/Register.jsp">Register a new account</a>

        </div>
    </body>
</html>
<script>
    // Hiển thị popup nếu có lỗi
    window.onload = function () {
        var errorMessage = "<%= errorMessage != null ? errorMessage : "" %>";
        if (errorMessage) {
            document.getElementById("errorPopup").style.display = "flex";
        }
    };

    // Ẩn popup khi nhấn OK
    function closePopup() {
        document.getElementById("errorPopup").style.display = "none";
    }
</script>

<%-- 
    Document   : ResetPassword
    Created on : Sep 25, 2024, 5:01:24 AM
    Author     : Acer
--%>

<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Reset Password - Electro Mart Management System</title>
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

        .forgot-password-container {
            background-color: rgba(255, 255, 255, 0.9);
            padding: 40px;
            box-shadow: 0 0 30px rgba(0, 0, 0, 0.2);
            border-radius: 15px;
            width: 400px;
            text-align: center;
            position: relative;
        }

        .forgot-password-container h1 {
            color: #0c64dc;
            margin-bottom: 20px;
            font-size: 32px;
            font-weight: bold;
        }

        .forgot-password-container form {
            margin-top: 20px;
        }

        .forgot-password-container .input-group {
            position: relative;
            margin-bottom: 20px;
        }

        .forgot-password-container .input-group input {
            width: 100%;
            padding: 15px 15px 15px 45px;
            border: 1px solid #ccc;
            border-radius: 5px;
            font-size: 16px;
            box-sizing: border-box;
        }

        .forgot-password-container .input-group i {
            position: absolute;
            left: 15px;
            top: 50%;
            transform: translateY(-50%);
            color: #aaa;
            font-size: 18px;
        }

        .forgot-password-container input[type="submit"] {
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

        .forgot-password-container input[type="submit"]:hover {
            background-color: #094ea1;
        }

        .forgot-password-container a {
            display: block;
            margin-top: 10px;
            color: #0c64dc;
            text-decoration: none;
            transition: color 0.3s ease;
        }

        .forgot-password-container a:hover {
            color: #0072ff;
        }

        .forgot-password-container img {
            width: 120px;
            height: auto;
            margin-bottom: 20px;
        }
    </style>
</head>
<body>

<div class="forgot-password-container">
    <!-- Logo của Nhà Thuốc HP -->
    <img src="https://vectorseek.com/wp-content/uploads/2023/10/FPT-Retail-Nha-thuoc-Long-Chau-Logo-Vector.svg-.png" alt="Nhà Thuốc HP Logo">
    <h1>Forgot password</h1>
    <p>Vui lòng nhập email để khôi phục mật khẩu</p>
    <form action="ForgotPasswordServlet" method="post">
        <div class="input-group">
            <i class="fas fa-envelope"></i>
            <input type="email" name="email" placeholder="Nhập email của bạn" required>
        </div>
        <input type="submit" value="Khôi phục mật khẩu">
    </form>
    <a href="Login.jsp">Quay lại đăng nhập</a>
</div>

</body>
</html>


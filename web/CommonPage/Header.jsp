<%@page contentType="text/html" pageEncoding="UTF-8"%>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css">
<%@ page import="Entity.User" %>
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Header</title>
        <style>
            * {
                margin: 0;
                padding: 0;
                box-sizing: border-box;
            }
            .header {
                display: flex;
                align-items: center;
                justify-content: space-between;
                background-color: #f8f9fa;
                padding: 10px 20px;
                box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
            }
            .logo {
                font-size: 24px;
                font-weight: bold;
                color: #007bff;
            }
            .search-bar {
                flex: 1;
                margin: 0 20px;
                display: flex;
            }
            .search-bar input {
                width: 100%;
                padding: 8px;
                border: 1px solid #ccc;
                border-radius: 5px;
            }
            .search-bar button {
                background-color: #007bff;
                color: white;
                border: none;
                padding: 8px 15px;
                margin-left: 5px;
                cursor: pointer;
                border-radius: 5px;
            }
            .nav-links {
                display: flex;
                align-items: center; /* Căn giữa theo chiều dọc */
                gap: 15px;
            }
            .nav-links a {
                text-decoration: none;
                color: #007bff;
                font-weight: bold;
                padding: 8px 12px;
                border-radius: 5px;
                transition: 0.3s;
            }
            .nav-links a:hover {
                background-color: #007bff;
                color: white;
            }
            .cart-btn {
                position: relative;
                display: inline-block;
                font-size: 18px;
                font-weight: bold;
                color: #fff;
                background-color: #28a745;
                padding: 10px 15px;
                border-radius: 8px;
                text-decoration: none;
            }

            .cart-count {
                background: red;
                color: white;
                font-size: 14px;
                font-weight: bold;
                padding: 4px 8px;
                border-radius: 50%;
                position: absolute;
                top: -5px;
                right: -10px;
            }
            .go-to-profile-btn {
                background-color: #007bff; /* Màu xanh dương */
                color: white !important; /* Giữ màu chữ trắng ngay cả khi không hover */
                padding: 8px 15px 12px 5px;
                border-radius: 5px;
                text-decoration: none;
                font-weight: bold;
                transition: background-color 0.3s ease, color 0.3s ease;
                border: none;
                cursor: pointer;
            }

            .go-to-profile-btn:hover {
                background-color: #0056b3; /* Màu xanh đậm hơn khi hover */
                color: white !important; /* Đảm bảo màu chữ không đổi */
            }
            .logout-btn {
                background-color: #ff4d4d; /* Màu đỏ */
                color: white !important; /* Chữ trắng */
                padding: 10px 18px;
                border-radius: 25px;
                text-decoration: none;
                font-weight: bold;
                display: flex;
                align-items: center;
                gap: 8px; /* Khoảng cách giữa icon và chữ */
                transition: all 0.3s ease-in-out;
                box-shadow: 0px 4px 8px rgba(255, 0, 0, 0.3);
                border: 2px solid transparent;
            }

            .logout-btn i {
                font-size: 16px; /* Cỡ icon */
            }

            .logout-btn:hover {
                background-color: #ffe6e6; /* Nền hồng nhạt để chữ đỏ dễ nhìn hơn */
                color: #ff4d4d; /* Chữ đỏ */
                border: 2px solid #ff4d4d;
                box-shadow: 0px 6px 12px rgba(255, 0, 0, 0.5);
                transform: scale(1.05);
            }
            .logo img {
                width: 120px; /* Điều chỉnh kích thước */
                height: auto; /* Giữ tỷ lệ */
            }



        </style>
    </head>
    <body>
        <div class="header">

            <div class="logo">
                <img src="https://cdn-icons-png.flaticon.com/512/6134/6134346.png" alt="Page Logo">
            </div>
            <div class="nav-links">

                <a href="${pageContext.request.contextPath}/cartdetailcontroller" class="cart-btn">
                    🛒 Cart <span class="cart-count">
                        <%= session.getAttribute("cartCount") != null ? session.getAttribute("cartCount") : 0 %>
                    </span>
                </a>
                <% 
            // Kiểm tra xem user đã đăng nhập hay chưa
            User user = (User) session.getAttribute("user");
            if (user != null) { 
                %>
                <!-- Nếu đã đăng nhập, hiển thị nút Profile + lời chào -->
                <a href="<%= request.getContextPath() %>/userprofile" class="go-to-profile-btn">Go to Profile</a>
                <form id="logoutForm" action="<%= request.getContextPath() %>/logoutcontroller" method="POST">
                    <button type="button" class="logout-btn" onclick="confirmLogout()">
                        <i class="fa-solid fa-right-from-bracket"></i> Logout
                    </button>
                </form>
                <% } else { %>
                <!-- Nếu chưa đăng nhập, hiển thị nút Register/Login -->
                <a href="<%= request.getContextPath() %>/UserPage/Login.jsp">Register/Login</a>
                <% } %>
                <a href="#">Hotline</a>
            </div>
        </div>
    </body>
</html>
<script>
    function confirmLogout() {
        Swal.fire({
            title: "Are you sure you want to log out?",
            text: "You will need to log in again after logging out!",
            icon: "warning",
            showCancelButton: true,
            confirmButtonColor: "#d33",
            cancelButtonColor: "#3085d6",
            confirmButtonText: "Log Out",
            cancelButtonText: "Cancel"
        }).then((result) => {
            if (result.isConfirmed) {
                document.getElementById("logoutForm").submit();
            }
        });
    }
</script>
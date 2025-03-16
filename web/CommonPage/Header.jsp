<%@page contentType="text/html" pageEncoding="UTF-8"%>
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
        </style>
    </head>
    <body>
        <div class="header">
            <div class="logo">Page Logo</div>

            <div class="search-bar">
                <input type="text" placeholder="Search...">
                <button>Search</button>
            </div>

            <div class="nav-links">
                <a href="#">Cart</a>
                <a href="#">Register/Login</a>
                <a href="#">Hotline</a>
            </div>
        </div>
    </body>
</html>

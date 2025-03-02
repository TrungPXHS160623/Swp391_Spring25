<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>User Profile</title>
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
            .profile-container {
                background-color: rgba(255, 255, 255, 0.9);
                padding: 40px;
                box-shadow: 0 0 30px rgba(0, 0, 0, 0.2);
                border-radius: 15px;
                width: 400px;
                text-align: center;
            }
            .profile-container h1 {
                color: #0c64dc;
                margin-bottom: 20px;
                font-size: 28px;
                font-weight: bold;
            }
            .profile-container img {
                width: 120px;
                height: 120px;
                border-radius: 50%;
                margin-bottom: 20px;
                object-fit: cover;
                border: 3px solid #0c64dc;
            }
            .profile-container .input-group {
                position: relative;
                margin-bottom: 20px;
                text-align: left;
            }
            .profile-container .input-group input {
                width: 100%;
                padding: 12px 15px;
                border: 1px solid #ccc;
                border-radius: 5px;
                font-size: 16px;
                box-sizing: border-box;
            }
            .profile-container .input-group label {
                display: block;
                margin-bottom: 5px;
                font-weight: bold;
                color: #333;
            }
            .profile-container input[type="submit"] {
                background-color: #0c64dc;
                color: #fff;
                border: none;
                padding: 12px 0;
                border-radius: 5px;
                width: 100%;
                font-size: 18px;
                cursor: pointer;
                transition: background-color 0.3s ease;
            }
            .profile-container input[type="submit"]:hover {
                background-color: #094ea1;
            }
        </style>
    </head>
    <body>
        <div class="profile-container">
            <h1>User Profile</h1>
            <img src="${user.avatar_url}" alt="User Avatar">
            <form action="${pageContext.request.contextPath}/updateProfile" method="post">
                <div class="input-group">
                    <label>Full Name</label>
                    <input type="text" name="full_name" value="${user.full_name}" required>
                </div>
                <div class="input-group">
                    <label>Gender</label>
                    <select name="gender" required style="width: 200px; height: 40px;">
                        <option value="Male" ${user.gender == "Male" ? "selected" : ""}>Male</option>
                        <option value="Female" ${user.gender == "Female" ? "selected" : ""}>Female</option>
                        <option value="Other" ${user.gender == "Other" ? "selected" : ""}>Other</option>
                    </select>
                </div>
                <div class="input-group">
                    <label>Email</label>
                    <input type="email" name="email" value="${user.email}" readonly>
                </div>
                <div class="input-group">
                    <label>Phone Number</label>
                    <input type="text" name="phone_number" value="${user.phone_number}" required>
                </div>
                <div class="input-group">
                    <label>Address</label>
                    <input type="text" name="address" value="${user.address}" required>
                </div>
                <input type="submit" value="Save Changes">
            </form>
        </div>
    </body>
</html>

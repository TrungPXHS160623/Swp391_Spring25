<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Footer</title>
        <style>
            .footer {
                background-color: #343a40;
                color: white;
                text-align: center;
                padding: 20px 0;
                margin-top: 20px;
            }
            .footer-container {
                display: flex;
                justify-content: space-around;
                flex-wrap: wrap;
                padding: 10px;
            }
            .footer-section {
                flex: 1;
                min-width: 200px;
                margin: 10px;
            }
            .footer a {
                color: #f8d210;
                text-decoration: none;
            }
            .footer a:hover {
                text-decoration: underline;
            }
        </style>
    </head>
    <body>
        <footer class="footer">
            <div class="footer-container">
                <div class="footer-section">
                    <h3>Contact Us</h3>
                    <p>Email: support@example.com</p>
                    <p>Phone: +84 123 456 789</p>
                </div>
                <div class="footer-section">
                    <h3>Address</h3>
                    <p>123 Street, District 1, Ho Chi Minh City</p>
                </div>
                <div class="footer-section">
                    <h3>Connect with us</h3>
                    <p><a href="https://facebook.com">Facebook</a> | <a href="https://twitter.com">Twitter</a></p>
                </div>
                <div class="footer-section">
                    <h3>Quick Links</h3>
                    <p><a href="#">Privacy Policy</a> | <a href="#">Terms of Service</a></p>
                </div>
            </div>
            <p>&copy; 2024 YourCompany. All Rights Reserved.</p>
        </footer>
    </body>
</html>

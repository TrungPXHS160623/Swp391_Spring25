<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Slider Section</title>
        <style>
            .slider-container {
                width: 100%;
                max-width: 1300px;
                margin: auto;
                overflow: hidden;
                position: relative;
                border-radius: 10px;
                box-shadow: 0px 4px 10px rgba(0, 0, 0, 0.2);

            }

            .slides {
                display: flex;
                white-space: nowrap; /* Ngăn xuống hàng */
                transition: transform 0.5s ease-in-out;
            }

            /* Phần tử slide */
            .slide {
                flex: 0 0 calc(100% / 3); /* Đảm bảo đúng 3 ảnh hiển thị */
                text-align: center;
                margin-right: 10px; /* Thêm khoảng cách */
                border: 2px solid #000; /* Thêm border cho slide */
                padding: 10px; /* Thêm khoảng cách giữa nội dung và border */
                border-radius: 5px; /* Tùy chọn: bo tròn góc nếu bạn muốn */
            }

            /* Cấu trúc ảnh trong slide */
            .slide img {
                width: 100%;
                border-radius: 10px; /* Bo tròn các góc ảnh */
                border-bottom: 2px solid #000; /* Thêm border dưới ảnh */
            }

            /* Đảm bảo ảnh cuối không bị lệch */
            .slide:last-child {
                margin-right: 0; /* Ảnh cuối không bị lệch */
            }

            /* Phần caption (tiêu đề) */
            .caption {
                margin-top: 10px; /* Tăng khoảng cách với ảnh */
                font-size: 18px; /* Tăng kích thước chữ */
                font-weight: bold; /* Làm đậm chữ */
                color: #000; /* Chuyển màu chữ thành đen */
                text-shadow: 2px 2px 5px rgba(150, 150, 150, 0.3); /* Đổ bóng nhẹ để nhìn rõ hơn */
                border-top: 2px solid #000; /* Thêm border phía trên tiêu đề */
                padding-top: 10px; /* Thêm khoảng cách với ảnh */
            }

            .prev, .next {
                position: absolute;
                top: 50%;
                transform: translateY(-50%);
                background-color: rgba(0, 0, 0, 0.5);
                color: white;
                border: none;
                padding: 10px;
                cursor: pointer;
                border-radius: 5px;
            }

            .prev {
                left: 10px;
            }
            .next {
                right: 10px;
            }
        </style>
    </head>
    <body>
        <div class="slider-container">
            <div class="slides">
                <c:forEach var="slider" items="${sliders}">
                    <div class="slide">
                        <img src="${slider.imageUrl}" alt="Slider Image">
                        <p class="caption">${slider.title}</p>
                    </div>
                </c:forEach>
            </div>

            <button class="prev" onclick="prevSlide()">❮</button>
            <button class="next" onclick="nextSlide()">❯</button>
        </div>
    </body>
</html>
<script>
    document.addEventListener("DOMContentLoaded", function () {
        let currentIndex = 0;
        const slidesContainer = document.querySelector('.slides');
        const slides = document.querySelectorAll('.slide');
        const totalSlides = slides.length;
        const visibleSlides = 3; // Số ảnh hiển thị cùng lúc
        const slideWidth = slides[0].offsetWidth; // Lấy chiều rộng thực tế

        function updateSlider() {
            slidesContainer.style.transform = `translateX(${-currentIndex * slideWidth}px)`;
        }

        document.querySelector('.next').addEventListener("click", function () {
            if (currentIndex < totalSlides - visibleSlides) {
                currentIndex++;
                updateSlider();
            }
        });

        document.querySelector('.prev').addEventListener("click", function () {
            if (currentIndex > 0) {
                currentIndex--;
                updateSlider();
            }
        });

        updateSlider();
    });

</script>


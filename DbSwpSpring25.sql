create database swp391_sping25

CREATE TABLE Roles (
    role_id INT PRIMARY KEY IDENTITY(1,1), -- ID tự động tăng
    role_name NVARCHAR(50) NOT NULL,       -- Tên vai trò
    description NVARCHAR(255),             -- Mô tả vai trò
    created_at DATETIME DEFAULT GETDATE(), -- Thời gian tạo
    updated_at DATETIME DEFAULT GETDATE()  -- Thời gian cập nhật
);


CREATE TABLE Users (
    user_id INT PRIMARY KEY IDENTITY(1,1), -- ID tự động tăng
    google_id NVARCHAR(255),               -- ID từ Google Sign-In
    email NVARCHAR(255) NOT NULL UNIQUE,   -- Email của người dùng
    full_name NVARCHAR(100),               -- Tên đầy đủ của người dùng
    avatar_url NVARCHAR(255),              -- Đường dẫn ảnh đại diện
    phone_number NVARCHAR(15),             -- Số điện thoại
    address NVARCHAR(255),                 -- Địa chỉ
    role_id INT,                           -- ID vai trò (khóa ngoại)
    created_at DATETIME DEFAULT GETDATE(), -- Thời gian tạo
    updated_at DATETIME DEFAULT GETDATE(), -- Thời gian cập nhật
    FOREIGN KEY (role_id) REFERENCES Roles(role_id) -- Khóa ngoại từ bảng Roles
);

ALTER TABLE Users ADD is_active BIT DEFAULT 1;

CREATE TABLE Permissions (
    permission_id INT PRIMARY KEY IDENTITY(1,1), -- ID duy nhất tự động tăng cho mỗi quyền
    permission_name NVARCHAR(50) NOT NULL,       -- Tên của quyền, mô tả ngắn gọn chức năng của quyền
    description NVARCHAR(255)                    -- Mô tả chi tiết về quyền, giúp hiểu rõ chức năng hoặc phạm vi quyền
);

CREATE TABLE RolePermissions (
    role_id INT,                                 -- ID vai trò, liên kết đến bảng Roles
    permission_id INT,                           -- ID quyền, liên kết đến bảng Permissions
    PRIMARY KEY (role_id, permission_id),        -- Khóa chính kết hợp role_id và permission_id, đảm bảo mỗi cặp vai trò-quyền là duy nhất
    FOREIGN KEY (role_id) REFERENCES Roles(role_id), -- Khóa ngoại tham chiếu đến role_id trong bảng Roles
    FOREIGN KEY (permission_id) REFERENCES Permissions(permission_id) -- Khóa ngoại tham chiếu đến permission_id trong bảng Permissions
);

CREATE TABLE Brands (
    brand_id INT PRIMARY KEY IDENTITY(1,1), -- ID tự động tăng cho mỗi thương hiệu
    brand_name NVARCHAR(100) NOT NULL,      -- Tên thương hiệu
    description NVARCHAR(255),              -- Mô tả về thương hiệu
    logo_url NVARCHAR(255),                 -- URL của logo thương hiệu
    created_at DATETIME DEFAULT GETDATE(),  -- Thời gian tạo thương hiệu
    updated_at DATETIME DEFAULT GETDATE()   -- Thời gian cập nhật thương hiệu
);

CREATE TABLE Categories (
    category_id INT PRIMARY KEY IDENTITY(1,1),        -- ID danh mục tự động tăng
    category_name NVARCHAR(255) NOT NULL,              -- Tên danh mục chính (ví dụ: Laptop, Điện thoại)
    description NVARCHAR(500),                         -- Mô tả cho danh mục chính
    created_at DATETIME DEFAULT GETDATE(),             -- Thời gian tạo danh mục chính
    updated_at DATETIME DEFAULT GETDATE()              -- Thời gian cập nhật danh mục chính
);

CREATE TABLE SubCategories (
    subcategory_id INT PRIMARY KEY IDENTITY(1,1),     -- ID danh mục con tự động tăng
    subcategory_name NVARCHAR(255) NOT NULL,           -- Tên danh mục con (ví dụ: Laptop Gaming)
    category_id INT,                                   -- ID danh mục chính (khóa ngoại)
    description NVARCHAR(500),                         -- Mô tả cho danh mục con
    created_at DATETIME DEFAULT GETDATE(),             -- Thời gian tạo danh mục con
    updated_at DATETIME DEFAULT GETDATE(),             -- Thời gian cập nhật danh mục con
    FOREIGN KEY (category_id) REFERENCES Categories(category_id)  -- Khóa ngoại liên kết với bảng Categories
);

CREATE TABLE Products (
    product_id INT PRIMARY KEY IDENTITY(1,1),         -- ID sản phẩm tự động tăng
    product_name NVARCHAR(255) NOT NULL,               -- Tên sản phẩm
    description NVARCHAR(500),                         -- Mô tả chi tiết sản phẩm
    price DECIMAL(18, 2) NOT NULL,                     -- Giá sản phẩm
    stock_quantity INT NOT NULL,                       -- Số lượng tồn kho
    subcategory_id INT,                                -- ID danh mục con (khóa ngoại)
    brand_id INT,                                      -- ID thương hiệu sản phẩm (khóa ngoại)
    created_at DATETIME DEFAULT GETDATE(),             -- Thời gian tạo sản phẩm
    updated_at DATETIME DEFAULT GETDATE(),             -- Thời gian cập nhật sản phẩm
    FOREIGN KEY (subcategory_id) REFERENCES SubCategories(subcategory_id), -- Khóa ngoại đến bảng SubCategories
    FOREIGN KEY (brand_id) REFERENCES Brands(brand_id)              -- Khóa ngoại đến bảng Brands
);

CREATE TABLE ProductImages (
    image_id INT PRIMARY KEY IDENTITY(1,1),      -- ID ảnh, tự động tăng
    product_id INT,                               -- ID sản phẩm (khóa ngoại)
    image_url NVARCHAR(255),                      -- Đường dẫn tới ảnh sản phẩm
    is_primary BIT DEFAULT 0,                     -- Cờ chỉ ra ảnh này có phải là ảnh chính không (0: không phải, 1: là ảnh chính)
    created_at DATETIME DEFAULT GETDATE(),        -- Thời gian tạo ảnh
    updated_at DATETIME DEFAULT GETDATE(),        -- Thời gian cập nhật ảnh
    FOREIGN KEY (product_id) REFERENCES Products(product_id)  -- Khóa ngoại liên kết đến bảng Products
);

CREATE TABLE Reviews (
    review_id INT PRIMARY KEY IDENTITY(1,1),         -- ID đánh giá tự động tăng
    product_id INT,                                   -- ID sản phẩm (khóa ngoại)
    user_id INT,                                      -- ID người dùng (khóa ngoại)
    rating INT CHECK (rating >= 1 AND rating <= 5),    -- Điểm đánh giá (1-5)
    comment NVARCHAR(500),                            -- Bình luận đánh giá
    created_at DATETIME DEFAULT GETDATE(),            -- Thời gian tạo đánh giá
    FOREIGN KEY (product_id) REFERENCES Products(product_id), -- Khóa ngoại đến bảng Products
    FOREIGN KEY (user_id) REFERENCES Users(user_id)  -- Khóa ngoại đến bảng Users
);

CREATE TABLE Wishlist (
    wishlist_id INT PRIMARY KEY IDENTITY(1,1),       -- ID danh sách yêu thích tự động tăng
    user_id INT,                                      -- ID người dùng (khóa ngoại)
    product_id INT,                                   -- ID sản phẩm (khóa ngoại)
    created_at DATETIME DEFAULT GETDATE(),            -- Thời gian thêm vào danh sách yêu thích
    FOREIGN KEY (user_id) REFERENCES Users(user_id),  -- Khóa ngoại đến bảng Users
    FOREIGN KEY (product_id) REFERENCES Products(product_id)  -- Khóa ngoại đến bảng Products
);

CREATE TABLE Discounts (
    discount_id INT PRIMARY KEY IDENTITY(1,1),       -- ID khuyến mãi tự động tăng
    name NVARCHAR(255) NOT NULL,                       -- Tên khuyến mãi
    description NVARCHAR(500),                         -- Mô tả khuyến mãi
    discount_percent DECIMAL(5, 2),                    -- Tỷ lệ giảm giá (%)
    start_date DATETIME,                               -- Ngày bắt đầu khuyến mãi
    end_date DATETIME                                  -- Ngày kết thúc khuyến mãi
);

CREATE TABLE Orders (
    order_id INT PRIMARY KEY IDENTITY(1,1),         -- ID đơn hàng tự động tăng
    user_id INT,                                    -- ID người dùng (khóa ngoại)
    order_status NVARCHAR(50),                       -- Trạng thái đơn hàng (pending, completed, canceled, etc.)
    total_amount DECIMAL(18, 2),                     -- Tổng số tiền đơn hàng
    created_at DATETIME DEFAULT GETDATE(),           -- Thời gian tạo đơn hàng
    updated_at DATETIME DEFAULT GETDATE(),           -- Thời gian cập nhật đơn hàng
    FOREIGN KEY (user_id) REFERENCES Users(user_id) -- Khóa ngoại đến bảng Users
);

CREATE TABLE Order_Items (
    order_item_id INT PRIMARY KEY IDENTITY(1,1),    -- ID chi tiết đơn hàng tự động tăng
    order_id INT,                                    -- ID đơn hàng (khóa ngoại)
    product_id INT,                                  -- ID sản phẩm (khóa ngoại)
    quantity INT,                                    -- Số lượng sản phẩm
    price DECIMAL(18, 2),                             -- Giá sản phẩm
    subtotal DECIMAL(18, 2),                          -- Tổng tiền cho sản phẩm (quantity * price)
    FOREIGN KEY (order_id) REFERENCES Orders(order_id),       -- Khóa ngoại đến bảng Orders
    FOREIGN KEY (product_id) REFERENCES Products(product_id)  -- Khóa ngoại đến bảng Products
);
CREATE TABLE Cart (
    cart_id INT PRIMARY KEY IDENTITY(1,1),           -- ID giỏ hàng tự động tăng
    user_id INT,                                      -- ID người dùng (khóa ngoại)
    created_at DATETIME DEFAULT GETDATE(),            -- Thời gian tạo giỏ hàng
    updated_at DATETIME DEFAULT GETDATE(),            -- Thời gian cập nhật giỏ hàng
    FOREIGN KEY (user_id) REFERENCES Users(user_id)   -- Khóa ngoại đến bảng Users
);

CREATE TABLE Cart_Items (
    cart_item_id INT PRIMARY KEY IDENTITY(1,1),      -- ID chi tiết giỏ hàng tự động tăng
    cart_id INT,                                      -- ID giỏ hàng (khóa ngoại)
    product_id INT,                                   -- ID sản phẩm (khóa ngoại)
    quantity INT,                                     -- Số lượng sản phẩm trong giỏ
    price DECIMAL(18, 2),                              -- Giá sản phẩm (có thể lấy từ bảng Products)
    subtotal DECIMAL(18, 2),                           -- Tổng tiền cho sản phẩm (quantity * price)
    FOREIGN KEY (cart_id) REFERENCES Cart(cart_id),   -- Khóa ngoại đến bảng Cart
    FOREIGN KEY (product_id) REFERENCES Products(product_id)  -- Khóa ngoại đến bảng Products
);

CREATE TABLE Payments (
    payment_id INT PRIMARY KEY IDENTITY(1,1),  -- ID của thanh toán, tự động tăng (mỗi thanh toán có một ID duy nhất)
    order_id INT,  -- ID của đơn hàng liên quan (Foreign Key tham chiếu bảng Orders)
    payment_method NVARCHAR(50),  -- Phương thức thanh toán (ví dụ: Credit Card, PayPal, Bank Transfer, v.v.)
    payment_status NVARCHAR(50),  -- Trạng thái thanh toán (ví dụ: Completed, Pending, Failed, Refunded)
    amount DECIMAL(18, 2),  -- Số tiền đã thanh toán (định dạng số thập phân với 2 chữ số sau dấu phẩy)
    payment_date DATETIME DEFAULT GETDATE(),  -- Ngày giờ thanh toán (mặc định lấy thời gian hiện tại khi tạo bản ghi)
    FOREIGN KEY (order_id) REFERENCES Orders(order_id)  -- Liên kết với bảng Orders, xác định đơn hàng mà thanh toán này liên quan
);

CREATE TABLE Shipping (
    shipping_id INT PRIMARY KEY IDENTITY(1,1),  -- ID của giao hàng, tự động tăng (mỗi giao hàng có một ID duy nhất)
    order_id INT,  -- ID của đơn hàng liên quan (Foreign Key tham chiếu bảng Orders)
    shipping_address NVARCHAR(500),  -- Địa chỉ giao hàng (lưu trữ địa chỉ đầy đủ)
    shipping_status NVARCHAR(50),  -- Trạng thái giao hàng (ví dụ: Pending, Shipped, Delivered, Canceled)
    tracking_number NVARCHAR(100),  -- Số theo dõi gói hàng (nếu có, cung cấp cho khách hàng để theo dõi)
    shipping_date DATETIME,  -- Ngày gửi hàng (ngày bắt đầu quá trình vận chuyển)
    estimated_delivery DATETIME,  -- Ngày giao hàng dự kiến (ngày dự đoán đơn hàng sẽ được giao)
    FOREIGN KEY (order_id) REFERENCES Orders(order_id)  -- Liên kết với bảng Orders, xác định đơn hàng mà giao hàng này liên quan
);
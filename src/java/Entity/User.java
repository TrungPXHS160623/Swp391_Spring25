/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entity;

import java.sql.Timestamp;

/**
 *
 * @author Acer
 */
public class User {
    private int user_id;
    private String full_name;
    private String gender;
    private String email;
    private String password_hash;
    private String phone_number;
    private String address;
    private String avatar_url;
    private int role_id;
    private boolean is_active;
    private boolean is_verified;
    private String reset_token;
    private java.sql.Timestamp reset_token_expiry;
    private java.sql.Timestamp created_at;
    private java.sql.Timestamp updated_at;
    private java.sql.Timestamp lastLogin;

    public User() {
    }

    public User(int user_id, String full_name, String gender, String email, String password_hash, String phone_number, String address, String avatar_url, int role_id, boolean is_active, boolean is_verified, String reset_token, Timestamp reset_token_expiry, Timestamp created_at, Timestamp updated_at, Timestamp lastLogin) {
        this.user_id = user_id;
        this.full_name = full_name;
        this.gender = gender;
        this.email = email;
        this.password_hash = password_hash;
        this.phone_number = phone_number;
        this.address = address;
        this.avatar_url = avatar_url;
        this.role_id = role_id;
        this.is_active = is_active;
        this.is_verified = is_verified;
        this.reset_token = reset_token;
        this.reset_token_expiry = reset_token_expiry;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.lastLogin = lastLogin;
    }
    
    //for register
    public User(String full_name, String gender, String email, String password_hash, 
            String phone_number, String address, int role_id, boolean is_active, boolean is_verified) {
    this.full_name = full_name;
    this.gender = gender;
    this.email = email;
    this.password_hash = password_hash;
    this.phone_number = phone_number;
    this.address = address;
    this.role_id = role_id;
    this.is_active = is_active;
    this.is_verified = is_verified;
    this.created_at = new Timestamp(System.currentTimeMillis()); // Lưu thời gian hiện tại
}
    //for userprofile
    public User(int userId, String fullName, String gender, String phoneNumber, String address) {
    this.user_id = userId;
    this.full_name = fullName;
    this.gender = gender;
    this.phone_number = phoneNumber;
    this.address = address;
}

    public int getUser_id() {
        return user_id;
    }

    public String getFull_name() {
        return full_name;
    }

    public String getGender() {
        return gender;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword_hash() {
        return password_hash;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public String getAddress() {
        return address;
    }

    public String getAvatar_url() {
        return avatar_url;
    }

    public int getRole_id() {
        return role_id;
    }

    public boolean isIs_active() {
        return is_active;
    }

    public boolean isIs_verified() {
        return is_verified;
    }

    public String getReset_token() {
        return reset_token;
    }

    public Timestamp getReset_token_expiry() {
        return reset_token_expiry;
    }

    public Timestamp getCreated_at() {
        return created_at;
    }

    public Timestamp getUpdated_at() {
        return updated_at;
    }

    public Timestamp getLastLogin() {
        return lastLogin;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword_hash(String password_hash) {
        this.password_hash = password_hash;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setAvatar_url(String avatar_url) {
        this.avatar_url = avatar_url;
    }

    public void setRole_id(int role_id) {
        this.role_id = role_id;
    }

    public void setIs_active(boolean is_active) {
        this.is_active = is_active;
    }

    public void setIs_verified(boolean is_verified) {
        this.is_verified = is_verified;
    }

    public void setReset_token(String reset_token) {
        this.reset_token = reset_token;
    }

    public void setReset_token_expiry(Timestamp reset_token_expiry) {
        this.reset_token_expiry = reset_token_expiry;
    }

    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }

    public void setUpdated_at(Timestamp updated_at) {
        this.updated_at = updated_at;
    }

    public void setLastLogin(Timestamp lastLogin) {
        this.lastLogin = lastLogin;
    }

    @Override
    public String toString() {
        return "User{" + "user_id=" + user_id + ", full_name=" + full_name + ", gender=" + gender + ", email=" + email + ", password_hash=" + password_hash + ", phone_number=" + phone_number + ", address=" + address + ", avatar_url=" + avatar_url + ", role_id=" + role_id + ", is_active=" + is_active + ", is_verified=" + is_verified + ", reset_token=" + reset_token + ", reset_token_expiry=" + reset_token_expiry + ", created_at=" + created_at + ", updated_at=" + updated_at + ", lastLogin=" + lastLogin + '}';
    }

    
 /*
user_id (int, PK, Identity) – Mã định danh duy nhất của người dùng.
full_name (nvarchar) – Họ và tên đầy đủ của người dùng.
gender (bit hoặc nvarchar) – Giới tính của người dùng (ví dụ: 0 - Nam, 1 - Nữ hoặc lưu chuỗi "Nam"/"Nữ").
email (nvarchar, unique) – Địa chỉ email của người dùng, dùng để đăng nhập và xác thực.
password_hash (nvarchar) – Mật khẩu đã được mã hóa của người dùng để bảo mật.
phone_number (nvarchar) – Số điện thoại của người dùng.
address (nvarchar) – Địa chỉ liên lạc của người dùng.
avatar_url (nvarchar) – Đường dẫn đến ảnh đại diện của người dùng.
role_id (int, FK -> Roles) – Mã định danh của vai trò người dùng trong hệ thống (Admin, Giảng viên, Khảo thí,...).
is_active (bit) – Trạng thái hoạt động của tài khoản (1 - Hoạt động, 0 - Bị vô hiệu hóa).
is_verified (bit) – Trạng thái xác minh tài khoản (1 - Đã xác minh, 0 - Chưa xác minh).
reset_token (nvarchar, nullable) – Mã thông báo dùng để đặt lại mật khẩu khi quên mật khẩu.
reset_token_expiry (datetime, nullable) – Thời điểm hết hạn của mã đặt lại mật khẩu.
created_at (datetime, default GETDATE()) – Thời điểm tài khoản được tạo.
updated_at (datetime, nullable) – Thời điểm tài khoản được cập nhật lần cuối.
last_login (datetime, nullable) – Thời điểm người dùng đăng nhập lần cuối.
    */   
    
}

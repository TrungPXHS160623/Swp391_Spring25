/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dto;

/**
 *
 * @author LENOVO
 */
public class UserDto {

    private int userId; // Có thể dùng khi xem/sửa, với trường hợp thêm user thì để mặc định (hoặc 0)
    private String avatarUrl;
    private String fullName;
    private String gender;   // Male, Female, Other
    private String email;
    private String password;
    private String phoneNumber;
    private String role;     // User, Seller, Marketing, Admin
    private String address;
    private String status;   // Active, NotActive

    public UserDto() {
    }

    public UserDto(int userId, String avatarUrl, String fullName, String gender, String email, String password, String phoneNumber, String role, String address, String status) {
        this.userId = userId;
        this.avatarUrl = avatarUrl;
        this.fullName = fullName;
        this.gender = gender;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.role = role;
        this.address = address;
        this.status = status;
    }

    // Constructor cho Userlist chung
    public UserDto(int userId, String fullName, String gender, String email, String phoneNumber, String role, String address, String status) {
        this.userId = userId;
        this.fullName = fullName;
        this.gender = gender;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.role = role;
        this.address = address;
        this.status = status;
    }

    // Constructor không có userId (dùng cho chức năng thêm user)
    public UserDto(String avatarUrl, String fullName, String gender, String email,
            String password, String phoneNumber, String role, String address, String status) {
        this.avatarUrl = avatarUrl;
        this.fullName = fullName;
        this.gender = gender;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.role = role;
        this.address = address;
        this.status = status;
    }

    // Phương thức để cập nhật thông tin (sửa) user dựa trên một đối tượng mới
    public void updateUser(UserDto newInfo) {
        // Lưu ý: userId không được thay đổi
        this.avatarUrl = newInfo.getAvatarUrl();
        this.fullName = newInfo.getFullName();
        this.gender = newInfo.getGender();
        this.email = newInfo.getEmail();
        this.password = newInfo.getPassword();
        this.phoneNumber = newInfo.getPhoneNumber();
        this.role = newInfo.getRole();
        this.address = newInfo.getAddress();
        this.status = newInfo.getStatus();
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // Override toString() để hỗ trợ chức năng xem chi tiết user
    @Override
    public String toString() {
        return "UserDto {"
                + "userId=" + userId
                + ", avatarUrl='" + avatarUrl + '\''
                + ", fullName='" + fullName + '\''
                + ", gender='" + gender + '\''
                + ", email='" + email + '\''
                + ", password='" + password + '\''
                + ", phoneNumber='" + phoneNumber + '\''
                + ", role='" + role + '\''
                + ", address='" + address + '\''
                + ", status='" + status + '\''
                + '}';
    }
}

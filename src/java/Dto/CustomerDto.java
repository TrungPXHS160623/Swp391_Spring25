/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dto;

import java.sql.Timestamp;

/**
 *
 * @author Admin
 */
public class CustomerDto {
    private int userId;
    private String fullName;
    private String gender;
    private String email;
    private String phoneNumber;
    private String address;
    private boolean isActive;
    private boolean isVerified;
    private Timestamp createdAt;
    private Timestamp lastLogin;
    private String avatarUrl;  // Added this field

    // Default constructor
    public CustomerDto() {
    }

    // Full constructor
    public CustomerDto(int userId, String fullName, String gender, String email, String phoneNumber, 
                      String address, boolean isActive, boolean isVerified, Timestamp createdAt, 
                      Timestamp lastLogin, String avatarUrl) {
        this.userId = userId;
        this.fullName = fullName;
        this.gender = gender;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.isActive = isActive;
        this.isVerified = isVerified;
        this.createdAt = createdAt;
        this.lastLogin = lastLogin;
        this.avatarUrl = avatarUrl;
    }

    // Getters and Setters
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public boolean isIsVerified() {
        return isVerified;
    }

    public void setIsVerified(boolean isVerified) {
        this.isVerified = isVerified;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Timestamp lastLogin) {
        this.lastLogin = lastLogin;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    @Override
    public String toString() {
        return "CustomerDto{" + "userId=" + userId + ", fullName=" + fullName + ", gender=" + gender + 
               ", email=" + email + ", phoneNumber=" + phoneNumber + ", address=" + address + 
               ", isActive=" + isActive + ", isVerified=" + isVerified + ", createdAt=" + createdAt + 
               ", lastLogin=" + lastLogin + ", avatarUrl=" + avatarUrl + '}';
    }
}

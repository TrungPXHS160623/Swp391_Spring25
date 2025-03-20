///*
// * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
// * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
// */
//package model;
//
//import java.sql.Timestamp;
//
//public class Customer {
//    private int userId;
//    private int googleId;
//    private String email;
//    private String fullName;
//    private String avatarUrl;
//    private String phoneNumber;
//    private String address;
//    private int role;
//    private Timestamp createdAt;
//    private Timestamp updatedAt;
//    private int isActive;
//    private int gender;
//
//    public Customer() {
//    }
//
//    public Customer(int userId, int googleId, String email, String fullName, String avatarUrl, String phoneNumber, String address, int role, Timestamp createdAt, Timestamp updatedAt, int isActive, int gender) {
//        this.userId = userId;
//        this.googleId = googleId;
//        this.email = email;
//        this.fullName = fullName;
//        this.avatarUrl = avatarUrl;
//        this.phoneNumber = phoneNumber;
//        this.address = address;
//        this.role = role;
//        this.createdAt = createdAt;
//        this.updatedAt = updatedAt;
//        this.isActive = isActive;
//        this.gender = gender;
//    }
//
//
//    public Customer(int userId, String email, String fullName, String phoneNumber, String address, int isActive) {
//        this.userId = userId;
//        this.email = email;
//        this.fullName = fullName;
//        this.phoneNumber = phoneNumber;
//        this.address = address;
//        this.isActive = isActive;
//    }
//    
//    public int getUserId() {
//        return userId;
//    }
//
//    public void setUserId(int userId) {
//        this.userId = userId;
//    }
//
//    public int getGoogleId() {
//        return googleId;
//    }
//
//    public void setGoogleId(int googleId) {
//        this.googleId = googleId;
//    }
//
//    public String getEmail() {
//        return email;
//    }
//
//    public void setEmail(String email) {
//        this.email = email;
//    }
//
//    public String getFullName() {
//        return fullName;
//    }
//
//    public void setFullName(String fullName) {
//        this.fullName = fullName;
//    }
//
//    public String getAvatarUrl() {
//        return avatarUrl;
//    }
//
//    public void setAvatarUrl(String avatarUrl) {
//        this.avatarUrl = avatarUrl;
//    }
//
//    public String getPhoneNumber() {
//        return phoneNumber;
//    }
//
//    public void setPhoneNumber(String phoneNumber) {
//        this.phoneNumber = phoneNumber;
//    }
//
//    public String getAddress() {
//        return address;
//    }
//
//    public void setAddress(String address) {
//        this.address = address;
//    }
//
//    public int getRole() {
//        return role;
//    }
//
//    public void setRole(int role) {
//        this.role = role;
//    }
//
//    public Timestamp getCreatedAt() {
//        return createdAt;
//    }
//
//    public void setCreatedAt(Timestamp createdAt) {
//        this.createdAt = createdAt;
//    }
//
//    public Timestamp getUpdatedAt() {
//        return updatedAt;
//    }
//
//    public void setUpdatedAt(Timestamp updatedAt) {
//        this.updatedAt = updatedAt;
//    }
//
//    public int getIsActive() {
//        return isActive;
//    }
//
//    public void setIsActive(int isActive) {
//        this.isActive = isActive;
//    }
//
//    public int getGender() {
//        return gender;
//    }
//
//    public void setGender(int gender) {
//        this.gender = gender;
//    }
//
//   
//    @Override
//    public String toString() {
//        return "Customer{" + 
//                "userId=" + userId + 
//                ", email='" + email + '\'' + 
//                ", fullName='" + fullName + '\'' + 
//                ", avatarUrl='" + avatarUrl + '\'' + 
//                ", phoneNumber='" + phoneNumber + '\'' + 
//                ", address='" + address + '\'' + 
//                ", role=" + role + 
//                ", createdAt=" + createdAt + 
//                ", updatedAt=" + updatedAt + 
//                ", isActive=" + isActive + 
//                ", gender=" + gender + 
//                '}';
//    }
//    
//
//}
//    
//
//
//    
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dto;

/**
 * DTO for storing customer contact information during checkout
 */
public class CartContactDto {
    private String fullName;
    private String gender;
    private String email;
    private String phone;
    private String address;
    private String notes;
    
    // Default constructor
    public CartContactDto() {
    }
    
    // Constructor with all fields
    public CartContactDto(String fullName, String gender, String email, String phone, String address, String notes) {
        this.fullName = fullName;
        this.gender = gender;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.notes = notes;
    }
    
    // Getters and setters
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public String toString() {
        return "CartContactDto{" + "fullName=" + fullName + ", gender=" + gender + ", email=" + email + 
               ", phone=" + phone + ", address=" + address + ", notes=" + notes + '}';
    }
}

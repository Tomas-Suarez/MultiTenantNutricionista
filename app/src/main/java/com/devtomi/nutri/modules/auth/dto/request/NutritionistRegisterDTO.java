package com.devtomi.nutri.modules.auth.dto.request;

public class NutritionistRegisterDTO {

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String cuit;
    private String licenseNumber;
    private String phoneNumber;

    public NutritionistRegisterDTO(String firstName, String lastName, String email, String password, String cuit) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.cuit = cuit;
    }

    public NutritionistRegisterDTO(String firstName, String lastName, String email, String password, String cuit, String licenseNumber, String phoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.cuit = cuit;
        this.licenseNumber = licenseNumber;
        this.phoneNumber = phoneNumber;
    }

    public NutritionistRegisterDTO() {
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

    public String getCuit() {
        return cuit;
    }

    public void setCuit(String cuit) {
        this.cuit = cuit;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
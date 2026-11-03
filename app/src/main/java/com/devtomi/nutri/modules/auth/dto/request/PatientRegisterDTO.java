package com.devtomi.nutri.modules.auth.dto.request;

public class PatientRegisterDTO {

    private String email;
    private String password;

    public PatientRegisterDTO(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public PatientRegisterDTO() {
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
}

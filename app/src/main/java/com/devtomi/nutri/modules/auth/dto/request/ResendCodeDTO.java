package com.devtomi.nutri.modules.auth.dto.request;

public class ResendCodeDTO {

    private String email;

    public ResendCodeDTO(String email) {
        this.email = email;
    }

    public ResendCodeDTO() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

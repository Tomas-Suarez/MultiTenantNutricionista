package com.devtomi.nutri.modules.auth.dto.request;

public class VerifyCodeDTO {

    private String email;
    private String code;

    public VerifyCodeDTO(String email, String code) {
        this.email = email;
        this.code = code;
    }

    public VerifyCodeDTO() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}

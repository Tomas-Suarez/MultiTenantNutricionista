package com.devtomi.nutri.modules.auth.dto.response;

public class TokenResponse {

    private String token;
    private String refresh;

    public TokenResponse(String token, String refresh) {
        this.token = token;
        this.refresh = refresh;
    }

    public TokenResponse(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRefresh() {
        return refresh;
    }

    public void setRefresh(String refresh) {
        this.refresh = refresh;
    }
}

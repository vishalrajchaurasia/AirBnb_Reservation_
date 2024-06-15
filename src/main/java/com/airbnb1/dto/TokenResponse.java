package com.airbnb1.dto;

public class TokenResponse {//why we create this class send the token and put this new TokenResponse
    private String type="Bearer";
    private String token;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}

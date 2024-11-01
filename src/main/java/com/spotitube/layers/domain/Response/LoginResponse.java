package com.spotitube.layers.domain.Response;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class LoginResponse {
    private String user;
    private String token;

    public LoginResponse(){}

    public LoginResponse(String user, String token) {
        this.user = user;
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public String getUser() {
        return user;
    }

    public void setUsername(String user) {
    }

    public void setToken(String token) {
    }
}


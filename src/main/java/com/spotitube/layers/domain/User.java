package com.spotitube.layers.domain;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class User {
    private String username;
    private String token;

    public User() {}

    public User(String username, String token) {
        this.username = username;
        this.token = token;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}

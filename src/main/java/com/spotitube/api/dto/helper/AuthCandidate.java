package com.spotitube.api.dto.helper;

public class AuthCandidate {
    private final int id;
    private final String username;
    private final String token;

    public AuthCandidate(int id, String username, String token) {
        this.id = id;
        this.username = username;
        this.token = token;
    }

    public int getId() { return id; }
    public String getUsername() { return username; }
    public String getToken() { return token; }
}

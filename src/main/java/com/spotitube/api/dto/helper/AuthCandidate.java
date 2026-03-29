package com.spotitube.api.dto.helper;

public class AuthCandidate {
    private final int id;
    private final String username;

    public AuthCandidate(int id, String username) {
        this.id = id;
        this.username = username;
    }

    public int getId() { return id; }
    public String getUsername() { return username; }
}

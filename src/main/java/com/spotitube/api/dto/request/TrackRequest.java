package com.spotitube.api.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TrackRequest {
    private int id;
    private boolean offlineAvailable;

    public int getId() {
        return id;
    }

    public boolean isOfflineAvailable() {
        return offlineAvailable;
    }
}
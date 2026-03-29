package com.spotitube.api.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PlaylistRequest {
    public int id;
    public String name;
    public String title;
    public String performer;
    public int duration;
    public boolean owner;
    public boolean offlineAvailable;
}
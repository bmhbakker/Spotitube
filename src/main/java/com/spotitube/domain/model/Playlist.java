package com.spotitube.domain.model;

import jakarta.inject.Singleton;

import java.util.ArrayList;
import java.util.List;

@Singleton
public class Playlist {
    private int id;
    private String name;
    private boolean owner;
    private List<Track> tracks;

    public Playlist() {
    }

    public Playlist(int id, String name, boolean owner) {
        this.id = id;
        this.name = name;
        this.owner = owner;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean getOwner() {
        return owner;
    }

    public void setOwner(boolean owner) {
        this.owner = owner;
    }

    public List<Track> getTracks() {
        return tracks;
    }

    public void setTracks(List<Track> tracks) {
        this.tracks = tracks != null ? tracks : new ArrayList<>();
    }
}

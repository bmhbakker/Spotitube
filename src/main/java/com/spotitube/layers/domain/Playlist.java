package com.spotitube.layers.domain;

import jakarta.inject.Singleton;

@Singleton
public class Playlist {
    private int id;
    private String name;
    private boolean isOwner;

    public Playlist(){}

    public Playlist copy() {
        Playlist copy = new Playlist();
        copy.setId(this.id);
        copy.setName(this.name);
        copy.setIsOwner(this.isOwner);
        return copy;
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

    public boolean getIsOwner() {
        return isOwner;
    }

    public void setIsOwner(boolean isOwner) {
        this.isOwner = isOwner;
    }
}

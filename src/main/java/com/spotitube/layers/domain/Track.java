package com.spotitube.layers.domain;

import java.util.List;

import static java.sql.Types.NULL;

public class Track {
    private List<Track> tracks;
    private int id, duration, playCount;
    private String title, performer, album, publicationDate, description;
    private boolean offlineAvailable;

    public Track() {
    }

    public Track copy() {
        Track copy = new Track();
        copy.id = id;
        copy.duration = duration;
        if (playCount <= 0) {
            copy.playCount = NULL;
        } else {
            copy.playCount = playCount;
        }
        copy.title = title;
        copy.performer = performer;
        copy.album = album;
        copy.publicationDate = publicationDate;
        copy.description = description;
        copy.offlineAvailable = offlineAvailable;
        return copy;
    }

    public List<Track> getTracks() {
        return tracks;
    }

    public void setTracks(List<Track> tracks) {
        this.tracks = tracks;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getPlayCount() {
        return playCount;
    }

    public void setPlayCount(int playCount) {
        this.playCount = playCount;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPerformer() {
        return performer;
    }

    public void setPerformer(String performer) {
        this.performer = performer;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(String publicationDate) {
        this.publicationDate = publicationDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isOfflineAvailable() {
        return offlineAvailable;
    }

    public void setOfflineAvailable(boolean offlineAvailable) {
        this.offlineAvailable = offlineAvailable;
    }
}

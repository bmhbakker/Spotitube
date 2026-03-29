package com.spotitube.domain.model;

public class Track {
    private int id;
    private String title;
    private String performer;
    private int duration; //seconds
    private String album;
    private int playcount;
    private String publicationDate; //string in MM-dd-yyyy
    private String description;
    private boolean offlineAvailable;

    public enum TrackType {
        SONG,
        VIDEO
    }

    public Track() {
    }

    public Track(int id, String title, String performer, int duration, boolean offlineAvailable) {
        this.id = id;
        this.title = title;
        this.performer = performer;
        this.duration = duration;
        this.offlineAvailable = offlineAvailable;
    }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getTitle() { return title; }

    public void setTitle(String title) { this.title = title; }

    public String getPerformer() { return performer; }

    public void setPerformer(String performer) { this.performer = performer; }

    public int getDuration() { return duration; }

    public void setDuration(int duration) { this.duration = duration; }

    public String getAlbum() { return album; }

    public void setAlbum(String album) { this.album = album; }

    public int getPlayCount() { return playcount; }

    public void setPlayCount(int playcount) { this.playcount = playcount; }

    public String getPublicationDate() { return publicationDate; }

    public void setPublicationDate(String publicationDate) { this.publicationDate = publicationDate; }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    public void setAvailableOffline(boolean availableOffline) { this.offlineAvailable = offlineAvailable; }

    public boolean getAvailableOffline() { return offlineAvailable; }
}

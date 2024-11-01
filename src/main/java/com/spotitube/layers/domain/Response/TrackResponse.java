package com.spotitube.layers.domain.Response;

import com.spotitube.layers.domain.Track;
import jakarta.inject.Singleton;

import java.util.ArrayList;
import java.util.List;

@Singleton
public class TrackResponse {
    private List<Track> tracks;
    private int length;

    public TrackResponse() {
        this.tracks = new ArrayList<>();
        this.length = 0;
    }

    public List<Track> getTracks() {
        return tracks;
    }

    public void setTracks(List<Track> tracks) {
        this.tracks = tracks;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public void addTrack(Track track) {
        tracks.add(track);
    }

    public void addDuration(int duration) {
        length += duration;
    }

    public void clearTracks() {
        tracks.clear();
        length = 0;
    }
}

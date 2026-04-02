package com.spotitube.api.dto.response;

import com.spotitube.domain.model.Playlist;
import jakarta.inject.Singleton;

import java.util.ArrayList;
import java.util.List;

@Singleton
public class PlaylistResponse {
    private List<Playlist> playlists;
    private int length;

    public PlaylistResponse(){}

    public PlaylistResponse(List<Playlist> playlists, int length) {
        this.playlists = new ArrayList<>();
        this.length = 0;
    }

    public void addPlaylist(Playlist playlist) {
        playlists.add(playlist);
    }

    public void addDuration(int duration) {
        length += duration;
    }

    public List<Playlist> getPlaylists() {
        return playlists;
    }

    public void setPlaylists(List<Playlist> playlists) {
        this.playlists = playlists;
    }

    public int getLength() { return length; }

    public void setLength(int length) { this.length = length; }
}


package com.spotitube.layers.domain.Response;

import com.spotitube.layers.domain.Playlist;
import jakarta.inject.Singleton;

import java.util.ArrayList;
import java.util.List;

@Singleton
public class PlaylistResponse {
    private List<Playlist> playlists;
    private int length;

    public PlaylistResponse() {
        this.playlists = new ArrayList<>();
        this.length = 0;
    }

    public List<Playlist> getPlaylists() {
        return playlists;
    }

    public void setPlaylists(List<Playlist> playlists) {
        this.playlists = playlists;
    }

    public void addPlaylist(Playlist playlist) {
        playlists.add(playlist);
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public void addDuration(int duration) {
        length += duration;
    }
}

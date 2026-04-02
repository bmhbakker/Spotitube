package com.spotitube.dal.repository;

import com.spotitube.domain.model.Playlist;

import java.util.List;

public interface IPlaylistRepository {
    List<Playlist> getPlaylists(int currentUserId);

    List<Playlist> getPlaylist(int playlistId, int currentUserId);

    void addPlaylist(String playlistName, int currentUserId);

    void deletePlaylist(int playlistId);

    void updatePlaylistName(int playlistId, String newPlaylistName);

    void addTrackToPlaylist(int playlistId, int trackId, boolean offlineAvailable);

    void removeTrackFromPlaylist(int playlistId, int trackId);
}

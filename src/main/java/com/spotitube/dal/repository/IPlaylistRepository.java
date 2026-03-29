package com.spotitube.dal.repository;

import com.spotitube.api.dto.response.PlaylistResponse;

public interface IPlaylistRepository {
    PlaylistResponse getPlaylists(int currentUserId);
    PlaylistResponse getPlaylist(int playlistId, int currentUserId);
    PlaylistResponse addPlaylist(String playlistName, int currentUserId);
    PlaylistResponse deletePlaylist(int playlistId, int currentUserId);
    PlaylistResponse updatePlaylistName(int playlistId, String newPlaylistName, int currentUserId);
    PlaylistResponse addTrackToPlaylist(int playlistId, int songId, boolean offlineAvailable, int currentUserId);
    PlaylistResponse removeTrackFromPlaylist(int playlistId, int trackId, int currentUserId);
}

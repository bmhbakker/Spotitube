package com.spotitube.layers.api;

import com.spotitube.layers.domain.Playlist;
import com.spotitube.layers.domain.Response.PlaylistResponse;
import com.spotitube.layers.domain.Response.TrackResponse;

import java.sql.SQLException;

public interface IPlaylistRepository {
    PlaylistResponse getPlaylists(String username) throws SQLException;
    Playlist getPlaylist(int id);
    TrackResponse getAllTracksInPlaylist(int id) throws SQLException;
}

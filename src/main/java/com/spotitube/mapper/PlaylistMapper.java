package com.spotitube.mapper;

import com.spotitube.domain.model.Playlist;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PlaylistMapper {
    public Playlist mapRowToPlaylist(ResultSet rs, int currentUserId) throws SQLException {
        Playlist playlist = new Playlist();

        playlist.setId(rs.getInt("id"));
        playlist.setName(rs.getString("name"));
        playlist.setOwner(currentUserId == rs.getInt("user_id"));
        return playlist;
    }
}


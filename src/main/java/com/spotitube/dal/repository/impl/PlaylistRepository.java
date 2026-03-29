package com.spotitube.dal.repository.impl;

import com.spotitube.api.dto.response.PlaylistResponse;
import com.spotitube.config.DBConnection;
import com.spotitube.dal.repository.IPlaylistRepository;
import com.spotitube.domain.model.Playlist;
import com.spotitube.mapper.PlaylistMapper;
import com.spotitube.service.PlaylistService;
import com.spotitube.shared.exception.DatabaseException;
import jakarta.inject.Inject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PlaylistRepository implements IPlaylistRepository {
    private final DBConnection dbConnection;
    private final PlaylistMapper playlistMapper;
    private final PlaylistService playlistService;

    @Inject
    public PlaylistRepository(DBConnection dbConnection, PlaylistMapper playlistMapper, PlaylistService playlistService) {
        this.dbConnection = dbConnection;
        this.playlistMapper = playlistMapper;
        this.playlistService = playlistService;
    }

    @Override
    public PlaylistResponse getPlaylists(int currentUserId) {
        return refreshPlaylists(currentUserId);
    }

    @Override
    public PlaylistResponse getPlaylist(int playlistId, int currentUserId) {
        //Even though we only need to fetch one playlist here the buildPlaylistResponse works with a list
        List<Playlist> playlists = getAllPlaylistsAsList(currentUserId, playlistId);
        return playlistService.buildPlaylistResponse(playlists);
    }

    @Override
    public PlaylistResponse addPlaylist(String playlistName, int currentUserId) {
        addPlaylistToDB(playlistName, currentUserId);
        return refreshPlaylists(currentUserId);
    }

    @Override
    public PlaylistResponse deletePlaylist(int playlistId, int currentUserId) {
        removePlaylistFromDB(playlistId);
        return refreshPlaylists(currentUserId);
    }

    @Override
    public PlaylistResponse updatePlaylistName(int playlistId, String newPlaylistName, int currentUserId) {
        editPlaylist(playlistId, newPlaylistName);
        return refreshPlaylists(currentUserId);
    }

    @Override
    public PlaylistResponse addTrackToPlaylist(int playlistId, int trackId, boolean offlineAvailable, int currentUserId) {
        String insertSQL = "INSERT INTO playlist_tracks (playlist_id, track_id, offlineAvailable) VALUES (?, ?, ?)";
        executeInsert(playlistId, trackId, offlineAvailable, insertSQL);

        List<Playlist> playlists = getAllPlaylistsAsList(currentUserId, playlistId);
        return playlistService.buildPlaylistResponse(playlists);
    }

    @Override
    public PlaylistResponse removeTrackFromPlaylist(int playlistId, int trackId, int currentUserId) {
        String deleteSQL = "DELETE FROM playlist_tracks WHERE playlist_id = ? AND track_id = ?;";
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pStmt = conn.prepareStatement(deleteSQL);){
            pStmt.setInt(1, playlistId);
            pStmt.setInt(2, trackId);
            pStmt.executeUpdate();
        } catch(SQLException e){
            throw new DatabaseException("Failed to delete playlist", e);
        }

        List<Playlist> playlists = getAllPlaylistsAsList(currentUserId, playlistId);
        return playlistService.buildPlaylistResponse(playlists);
    }


    private List<Playlist> fetchPlaylists(int currentUserId) {
        return getAllPlaylistsAsList(currentUserId, null);
    }

    private List<Playlist> getAllPlaylistsAsList(int currentUserId, Integer playlistId) {
        StringBuilder selectSQL = new StringBuilder("SELECT * FROM playlists");

        if (playlistId != null) {
            selectSQL.append(" WHERE id = ?");
        }

        List<Playlist> playlists = new ArrayList<>();
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pStmt = conn.prepareStatement(selectSQL.toString());) {

            if (playlistId != null) {
                pStmt.setInt(1, playlistId);
            }

            try (ResultSet rs = pStmt.executeQuery()) {

                while (rs.next()) {
                    playlists.add(playlistMapper.mapRowToPlaylist(rs, currentUserId));
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException("Failed to fetch playlists", e);
        }

        return playlists;
    }

    private void addPlaylistToDB(String playlistName, int currentUserId) {
        String insertSQL = "INSERT INTO playlists (name, user_id) VALUES (?, ?)";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pStmt = conn.prepareStatement(insertSQL)) {
            pStmt.setString(1, playlistName);
            pStmt.setInt(2, currentUserId);
            pStmt.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException("Failed to add playlist to database", e);
        }
    }

    private void removePlaylistFromDB(int playlistId) {
        String deleteSQL = "DELETE FROM playlists WHERE id = ?";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pStmt = conn.prepareStatement(deleteSQL)) {
            pStmt.setInt(1, playlistId);
            pStmt.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException("Error deleting playlist from database", e);
        }
    }

    private void editPlaylist(int playlistId, String newPlaylistName) {
        String updateSQL = "UPDATE playlists SET name = ? WHERE id = ?";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pStmt = conn.prepareStatement(updateSQL)) {
            pStmt.setString(1, newPlaylistName);
            pStmt.setInt(2, playlistId);
            pStmt.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException("Error updating name of playlist", e);
        }
    }

    private void executeInsert(int playlistId, int trackId, boolean offlineAvailable, String sql) {
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pStmt = conn.prepareStatement(sql)) {
            pStmt.setInt(1, playlistId);
            pStmt.setInt(2, trackId);
            pStmt.setBoolean(3, offlineAvailable);
            pStmt.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException("Error adding track to playlist", e);
        }
    }

    private PlaylistResponse refreshPlaylists(int currentUserId) {
        return playlistService.buildPlaylistResponse(fetchPlaylists(currentUserId));
    }
}





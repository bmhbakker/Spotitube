package com.spotitube.dal.repository.impl;

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
    private DBConnection dbConnection;
    private PlaylistMapper playlistMapper;

    public PlaylistRepository() { } //ONLY NEEDED FOR PROXYING

    @Inject
    public PlaylistRepository(DBConnection dbConnection, PlaylistMapper playlistMapper) {
        this.dbConnection = dbConnection;
        this.playlistMapper = playlistMapper;
    }

    @Override
    public List<Playlist> getPlaylists(int currentUserId) {
        return fetchPlaylists(currentUserId);
    }

    @Override
    public List<Playlist> getPlaylist(int playlistId, int currentUserId) {
        return getAllPlaylistsAsList(currentUserId, playlistId);
    }

    @Override
    public void addPlaylist(String playlistName, int currentUserId) {
        addPlaylistToDB(playlistName, currentUserId);
    }

    @Override
    public void deletePlaylist(int playlistId) {
        removePlaylistFromDB(playlistId);
    }

    @Override
    public void updatePlaylistName(int playlistId, String newPlaylistName) {
        editPlaylist(playlistId, newPlaylistName);
    }

    @Override
    public void addTrackToPlaylist(int playlistId, int trackId, boolean offlineAvailable) {
        String insertSQL = "INSERT INTO playlist_tracks (playlist_id, track_id, offlineAvailable) VALUES (?, ?, ?)";
        executeInsert(playlistId, trackId, offlineAvailable, insertSQL);
    }

    @Override
    public void removeTrackFromPlaylist(int playlistId, int trackId) {
        String deleteSQL = "DELETE FROM playlist_tracks WHERE playlist_id = ? AND track_id = ?;";
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pStmt = conn.prepareStatement(deleteSQL);){
            pStmt.setInt(1, playlistId);
            pStmt.setInt(2, trackId);
            pStmt.executeUpdate();
        } catch(SQLException e){
            throw new DatabaseException("Failed to delete playlist", e);
        }
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
}





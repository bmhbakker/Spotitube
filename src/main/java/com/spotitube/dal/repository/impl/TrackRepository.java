package com.spotitube.dal.repository.impl;

import com.spotitube.config.DBConnection;
import com.spotitube.dal.repository.ITrackRepository;
import com.spotitube.domain.model.Track;
import com.spotitube.api.dto.response.TrackResponse;
import com.spotitube.mapper.TrackMapper;
import com.spotitube.shared.exception.DatabaseException;
import jakarta.inject.Inject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TrackRepository implements ITrackRepository {
    private final DBConnection dbConnection;
    private final TrackMapper trackMapper;

    @Inject
    public TrackRepository(DBConnection dbConnection, TrackMapper trackMapper) {
        this.dbConnection = dbConnection;
        this.trackMapper = trackMapper;
    }

    @Override
    public TrackResponse getAllTracksInPlaylist(int id) {
        String trackSQL = "SELECT t.id AS trackID, t.title, t.performer, t.duration, pt.offlineAvailable, " +
                "t.trackType, " +
                "t.album, DATE_FORMAT(t.publicationDate, '%m-%d-%Y') AS publicationDate, " +
                "t.playcount, t.description " +
                "FROM playlists AS p " +
                "LEFT JOIN playlist_tracks AS pt ON p.id = pt.playlist_id " +
                "LEFT JOIN tracks AS t ON pt.track_id = t.id " +
                "WHERE playlist_id = ?";

        TrackResponse trackResponse = getTrackResponse(id, trackSQL, true);
        return trackResponse;
    }

    @Override
    public TrackResponse getOptionalTracks(int id) {
        String trackSQL = "SELECT t.id AS trackID, t.title, t.performer, 0 AS offlineAvailable," +
                "t.trackType, t.album, DATE_FORMAT(t.publicationDate, '%m-%d-%Y') AS publicationDate," +
                "t.playcount, t.description, t.duration " +
                "FROM tracks t WHERE NOT EXISTS (SELECT 1 FROM playlist_tracks pt " +
                "WHERE pt.track_id = t.id AND pt.playlist_id = ?)";

        TrackResponse trackResponse = getTrackResponse(id, trackSQL, false);
        return trackResponse;
    }

    private TrackResponse getTrackResponse(int id, String trackSQL, boolean sumDuration) {
        TrackResponse trackResponse = new TrackResponse();
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pStmt = conn.prepareStatement(trackSQL)) {

            pStmt.setInt(1, id);
            try (ResultSet rs = pStmt.executeQuery()) {
                while (rs.next()) {
                    Track track = trackMapper.mapRowToTrack(rs);
                    trackResponse.addTrack(track);
                    if(sumDuration) {
                        trackResponse.addDuration(track.getDuration());
                    }
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException("Failed to fetch all tracks for playlist", e);
        }
        return trackResponse;
    }
}


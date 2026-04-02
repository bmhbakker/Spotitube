package com.spotitube.dal.repository.impl;

import com.spotitube.config.DBConnection;
import com.spotitube.dal.repository.ITrackRepository;
import com.spotitube.domain.model.Track;
import com.spotitube.mapper.TrackMapper;
import com.spotitube.shared.exception.DatabaseException;
import jakarta.inject.Inject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class TrackRepository implements ITrackRepository {
    private final DBConnection dbConnection;
    private final TrackMapper trackMapper;

    @Inject
    public TrackRepository(DBConnection dbConnection, TrackMapper trackMapper) {
        this.dbConnection = dbConnection;
        this.trackMapper = trackMapper;
    }

    @Override
    public List<Track> getAllTracksInPlaylist(int id) {
        StringBuilder trackSQL = new StringBuilder("SELECT t.id AS trackID, t.title, t.performer, t.duration, " +
                "pt.offlineAvailable, t.trackType, t.album, t.playcount, t.description, ");

        appendDateFormat(trackSQL);

        trackSQL.append("FROM playlists AS p " +
                "LEFT JOIN playlist_tracks AS pt ON p.id = pt.playlist_id " +
                "LEFT JOIN tracks AS t ON pt.track_id = t.id " +
                "WHERE playlist_id = ?");


        return executeTrackQuery(id, trackSQL.toString());
    }

    @Override
    public List<Track> getOptionalTracks(int id) {
        StringBuilder trackSQL = new StringBuilder("SELECT t.id AS trackID, t.title, t.performer, t.duration, " +
                "0 AS offlineAvailable, t.trackType, t.album, t.playcount, t.description, ");

        appendDateFormat(trackSQL);

        trackSQL.append("FROM tracks t WHERE NOT EXISTS (SELECT 1 FROM playlist_tracks pt " +
                "WHERE pt.track_id = t.id AND pt.playlist_id = ?)");

        return executeTrackQuery(id, trackSQL.toString());
    }

    private List<Track> executeTrackQuery(int id, String sql) {
        List<Track> tracks = new ArrayList<>();

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pStmt = conn.prepareStatement(sql)) {

            pStmt.setInt(1, id);

            try (ResultSet rs = pStmt.executeQuery()) {
                while (rs.next()) {
                    tracks.add(trackMapper.mapRowToTrack(rs));
                }
            }

        } catch (SQLException e) {
            throw new DatabaseException("Failed to fetch tracks", e);
        }

        return tracks;
    }

    private void appendDateFormat(StringBuilder sql) {
        String usedDb = getDb();

        if ("h2".equals(usedDb)) {
            sql.append("FORMATDATETIME(t.publicationDate, 'MM-dd-yyyy') AS publicationDate ");
        } else {
            sql.append("DATE_FORMAT(t.publicationDate, '%m-%d-%Y') AS publicationDate ");
        }
    }

    private String getDb() {
        Properties props = new Properties();
        try (var inStream = getClass().getClassLoader().getResourceAsStream("config.properties")) {
            if (inStream == null) {
                throw new RuntimeException("config.properties not found!");
            }
            props.load(inStream);
        } catch (Exception e) {
            throw new RuntimeException("Can't load config.properties " + e.getMessage(), e);
        }

        return props.getProperty("db.type").trim();
    }
}


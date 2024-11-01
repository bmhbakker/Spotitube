package com.spotitube.layers.dal;

import com.spotitube.DBConnection;
import com.spotitube.layers.api.IPlaylistRepository;
import com.spotitube.layers.domain.Playlist;
import com.spotitube.layers.domain.Response.PlaylistResponse;
import com.spotitube.layers.domain.Track;
import com.spotitube.layers.domain.Response.TrackResponse;
import jakarta.enterprise.inject.Default;
import jakarta.inject.Inject;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Objects;

@Default
public class PlaylistRepository implements IPlaylistRepository {
    private DBConnection dbConnection;
    private PlaylistResponse playlistResponse;
    private Playlist playlist;
    private TrackResponse trackResponse;
    private Track track;

    @Override
    public PlaylistResponse getPlaylists(String username) throws SQLException {
        ResultSet rs = getAllPlaylistWithDurationResultSet();

        while (rs.next()) {
            Playlist currentPlaylist = playlist.copy();

            boolean isOwner = Objects.equals(rs.getString("username"), username);

            currentPlaylist.setId(rs.getInt("id"));
            currentPlaylist.setName(rs.getString("name"));
            currentPlaylist.setIsOwner(isOwner);
            playlistResponse.addPlaylist(currentPlaylist);
            playlistResponse.addDuration(rs.getInt("duration"));
        }
        return playlistResponse;
    }

    private ResultSet getAllPlaylistWithDurationResultSet() throws SQLException {
        Connection conn = dbConnection.getConnection();
        Statement stmt = conn.createStatement();
        //TODO SIMPLIFY QUERY
        // THIS IS NEEDED FOR THE PLAYLIST (GET * FROM PLAYLIST) AND THE DURATION
        // WHAT DURATION NEEDS TO BE SHOWN AND CAN WE SIMPLIFY THIS?
        return stmt.executeQuery("SELECT  " +
                "    p.id AS id,   " +
                "    p.name AS name,   " +
                "    o.username AS username,   " +
                "    COALESCE(song_duration, 0) + COALESCE(video_duration, 0) AS duration " +
                "FROM  " +
                "    playlist AS p " +
                "LEFT JOIN owner as o on p.owner_id = o.id  " +
                "LEFT JOIN ( " +
                "    SELECT  " +
                "        ps.playlist_id,  " +
                "        SUM(DISTINCT s.duration) AS song_duration " +
                "    FROM  " +
                "        playlist_song AS ps " +
                "    JOIN  " +
                "        song AS s ON ps.song_id = s.id " +
                "    GROUP BY  " +
                "        ps.playlist_id " +
                ") AS song_data ON p.id = song_data.playlist_id " +
                "LEFT JOIN ( " +
                "    SELECT  " +
                "        pv.playlist_id,  " +
                "        SUM(DISTINCT v.duration) AS video_duration " +
                "    FROM  " +
                "        playlist_video AS pv " +
                "    JOIN  " +
                "        video AS v ON pv.video_id = v.id " +
                "    GROUP BY  " +
                "        pv.playlist_id " +
                ") AS video_data ON p.id = video_data.playlist_id ");
    }

    @Override
    public Playlist getPlaylist(int id) {
//        try {
//            Connection conn = dbConnection.getConnection();
//            Statement stmt = conn.createStatement();
//            ResultSet rs = stmt.executeQuery("SELECT * FROM song WHERE id=" + id);
//
//            if (!rs.next()) {
//                throw (new Exception());
//            }
//
//            return mapToTrack(rs);
//
//        } catch (Exception e) {
//            throw new RuntimeException(e);
        return null;
    }

    @Override
    //TODO CHECK IF WE CAN NOT JUST GET THE TRACKS HERE FOR THE PLAYLIST GIVEN IN ID (THIS WAY WE DON'T GET THE SAME DATA MULTIPLE TIMES
    // SAME GOES FOR THE DURATION, MAYBE WE CAN USE A COMBINATION OF QUERIES AND MAPPERS?
    public TrackResponse getAllTracksInPlaylist(int id) throws SQLException {
        trackResponse.clearTracks();
        ResultSet rs = getAllTracksResultSet(id);

        while (rs.next()) {
            boolean offlineAvailable = rs.getInt("offlineAvailable") == 1;
            String datePattern = "MM-dd-yyyy";
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(datePattern);
            String date = null;
            String album = null;
            String description = null;
            int playCount = -1;

            if (Objects.equals(rs.getString("media_type"), "video")) {
                date = simpleDateFormat.format(rs.getDate("publicationDate"));
                playCount = rs.getInt("playCount");
                description = rs.getString("description");

            } else if (Objects.equals(rs.getString("media_type"), "song")) {
                album = rs.getString("album");
            }

            Track currentTrack = track.copy();
            currentTrack.setId(rs.getInt("id"));
            currentTrack.setDuration(rs.getInt("duration"));
            currentTrack.setPlayCount(playCount);
            currentTrack.setTitle(rs.getString("title"));
            currentTrack.setPerformer(rs.getString("performer"));
            currentTrack.setAlbum(album);
            currentTrack.setPublicationDate(date);
            currentTrack.setDescription(description);
            currentTrack.setOfflineAvailable(offlineAvailable);
            trackResponse.addTrack(currentTrack);

            trackResponse.addDuration(rs.getInt("duration"));
        }

        return trackResponse;
    }

    private ResultSet getAllTracksResultSet(int id) throws SQLException {
        Connection conn = dbConnection.getConnection();

        String sql = "SELECT s.id as id, s.title as title, s.duration as duration, s.performer as performer, NULL as playcount, s.album as album, NULL as publicationDate, NULL as description, offlineAvailable, 'song' as media_type " +
                "FROM playlist as p " +
                "LEFT JOIN playlist_song as ps ON p.id = ps.playlist_id " +
                "LEFT JOIN song as s ON ps.song_id = s.id " +
                "WHERE p.id = ? " +

                "UNION ALL " +

                "SELECT v.id as id, v.title as title, v.duration as duration, v.performer as performer, v.playCount as playcount, NULL as album, v.publicationDate as publicationDate, v.description as description, offlineAvailable, 'video' as media_type " +
                "FROM playlist as p " +
                "LEFT JOIN playlist_video as pv ON p.id = pv.playlist_id " +
                "LEFT JOIN video as v ON pv.video_id = v.id " +
                "WHERE p.id = ? " +

                "ORDER BY media_type, id";
        PreparedStatement pStmt = conn.prepareStatement(sql);
        pStmt.setInt(1, id);
        pStmt.setInt(2, id);

        return pStmt.executeQuery();
    }

    @Inject
    public void setDB(DBConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    @Inject
    public void setPlaylistResponse(PlaylistResponse playlistResponse) {
        this.playlistResponse = playlistResponse;
    }

    @Inject
    public void setPlaylist(Playlist playlist) {
        this.playlist = playlist;
    }

    @Inject
    public void setTrackResponse(TrackResponse trackResponse) {
        this.trackResponse = trackResponse;
    }

    @Inject
    public void setTrack(Track track) {
        this.track = track;
    }
}





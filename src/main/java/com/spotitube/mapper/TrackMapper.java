package com.spotitube.mapper;

import com.spotitube.domain.model.Track;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TrackMapper {
    public Track mapRowToTrack(ResultSet rs) throws SQLException {
        Track track = new Track();
        track.setId(rs.getInt("trackID"));
        track.setTitle(rs.getString("title"));
        track.setPerformer(rs.getString("performer"));
        track.setDuration(rs.getInt("duration"));
        track.setAvailableOffline(rs.getBoolean("offlineAvailable"));

        Track.TrackType type = Track.TrackType.valueOf(rs.getString("trackType").toUpperCase());
        if (type == Track.TrackType.SONG) {
            track.setAlbum(rs.getString("album"));
        } else if (type == Track.TrackType.VIDEO) {
            track.setPlayCount(rs.getInt("playcount"));
            track.setPublicationDate(rs.getString("publicationDate"));
            track.setDescription(rs.getString("description"));
        }
        return track;
    }
}





package com.spotitube.dal.repository;

import com.spotitube.domain.model.Track;

import java.util.List;

public interface ITrackRepository {
    List<Track> getAllTracksInPlaylist(int playlistId);

    List<Track> getOptionalTracks(int playlistId);
}

package com.spotitube.dal.repository;

import com.spotitube.api.dto.response.TrackResponse;

public interface ITrackRepository {
    TrackResponse getAllTracksInPlaylist(int playlistId);
    TrackResponse getOptionalTracks(int playlistId);
}

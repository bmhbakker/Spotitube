package com.spotitube.service;

import com.spotitube.api.dto.response.PlaylistResponse;
import com.spotitube.api.dto.response.TrackResponse;
import com.spotitube.dal.repository.impl.TrackRepository;
import com.spotitube.domain.model.Playlist;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;

@ApplicationScoped
public class PlaylistService {
    @Inject
    private TrackRepository trackRepository;

    public PlaylistService(){};

    public PlaylistResponse buildPlaylistResponse(List<Playlist> playlists) {
        PlaylistResponse response = new PlaylistResponse();

        for (Playlist playlist : playlists) {
            TrackResponse tracks = getAllTracksInPlaylist(playlist.getId());
            playlist.setTracks(tracks.getTracks());

            response.addDuration(tracks.getLength());
            response.addPlaylist(playlist);
        }
        return response;
    }

    private TrackResponse getAllTracksInPlaylist(int playlistId) {
        return trackRepository.getAllTracksInPlaylist(playlistId);
    }
}

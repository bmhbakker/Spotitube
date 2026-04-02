package com.spotitube.service;

import com.spotitube.api.dto.response.TrackResponse;
import com.spotitube.dal.repository.ILoginRepository;
import com.spotitube.dal.repository.ITrackRepository;
import com.spotitube.domain.model.Track;
import com.spotitube.domain.model.User;
import jakarta.inject.Inject;

import java.util.List;

public class TrackService {
    private ITrackRepository trackRepository;
    private LoginService loginService;

    public TrackService() {} //ONLY NEEDED FOR PROXYING

    @Inject
    public TrackService(ITrackRepository trackRepository, LoginService loginService) {
        this.trackRepository = trackRepository;
        this.loginService = loginService;
    }

    public TrackResponse getTracksInPlaylist(int id, String token) {
        if (token != null) loginService.validateAuth(token);
        List<Track> tracks = trackRepository.getAllTracksInPlaylist(id);

        TrackResponse response = new TrackResponse();
        for (Track track : tracks) {
            response.addTrack(track);
            response.addDuration(track.getDuration());
        }

        return response;
    }

    public TrackResponse getOptionalTracks(int id, String token) {
        loginService.validateAuth(token);

        List<Track> tracks = trackRepository.getOptionalTracks(id);

        TrackResponse response = new TrackResponse();
        for (Track track : tracks) {
            response.addTrack(track);
        }

        return response;
    }
}
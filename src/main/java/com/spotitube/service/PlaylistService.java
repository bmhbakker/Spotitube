package com.spotitube.service;

import com.spotitube.api.dto.request.PlaylistRequest;
import com.spotitube.api.dto.request.TrackRequest;
import com.spotitube.api.dto.response.PlaylistResponse;
import com.spotitube.api.dto.response.TrackResponse;
import com.spotitube.dal.repository.IPlaylistRepository;
import com.spotitube.domain.model.Playlist;
import com.spotitube.domain.model.User;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;

@ApplicationScoped
public class PlaylistService {
    private LoginService loginService;
    private IPlaylistRepository playlistRepository;
    private TrackService trackService;

    public PlaylistService() {
    } //ONLY NEEDED FOR PROXYING

    @Inject
    public PlaylistService(TrackService trackService, IPlaylistRepository playlistRepository, LoginService loginService) {
        this.trackService = trackService;
        this.playlistRepository = playlistRepository;
        this.loginService = loginService;
    }

    public PlaylistResponse getPlaylists(String requestToken) {
        User user = loginService.validateAuth(requestToken);

        List<Playlist> playlists = playlistRepository.getPlaylists(user.getId());
        return buildPlaylistResponse(playlists);
    }

    public PlaylistResponse getPlaylist(int playlistId, String requestToken) {
        User user = loginService.validateAuth(requestToken);

        List<Playlist> playlists = playlistRepository.getPlaylist(playlistId, user.getId());
        return buildPlaylistResponse(playlists);
    }

    public PlaylistResponse addPlaylist(PlaylistRequest request, String requestToken) {
        User user = loginService.validateAuth(requestToken);
        playlistRepository.addPlaylist(request.name, user.getId());

        List<Playlist> playlists = playlistRepository.getPlaylists(user.getId());
        return buildPlaylistResponse(playlists);
    }

    public PlaylistResponse deletePlaylist(int id, String requestToken) {
        User user = loginService.validateAuth(requestToken);
        playlistRepository.deletePlaylist(id);

        List<Playlist> playlists = playlistRepository.getPlaylists(user.getId());
        return buildPlaylistResponse(playlists);
    }

    public PlaylistResponse updatePlaylistName(int id, PlaylistRequest request, String requestToken) {
        User user = loginService.validateAuth(requestToken);
        playlistRepository.updatePlaylistName(id, request.name);

        List<Playlist> playlists = playlistRepository.getPlaylists(user.getId());
        return buildPlaylistResponse(playlists);
    }

    public PlaylistResponse addTrackToPlaylist(int playlistId, TrackRequest request, String requestToken) {
        User user = loginService.validateAuth(requestToken);
        playlistRepository.addTrackToPlaylist(playlistId, request.getId(), request.isOfflineAvailable());

        List<Playlist> playlists = playlistRepository.getPlaylists(user.getId());
        return buildPlaylistResponse(playlists);
    }

    public PlaylistResponse deleteTrackFromPlaylist(int playlistId, int trackId, String requestToken) {
        User user = loginService.validateAuth(requestToken);
        playlistRepository.removeTrackFromPlaylist(playlistId, trackId);

        List<Playlist> playlists = playlistRepository.getPlaylists(user.getId());
        return buildPlaylistResponse(playlists);
    }

    public PlaylistResponse buildPlaylistResponse(List<Playlist> playlists) {
        PlaylistResponse response = new PlaylistResponse();

        for (Playlist playlist : playlists) {
            TrackResponse tracks = trackService.getTracksInPlaylist(playlist.getId(), null);
            playlist.setTracks(tracks.getTracks());

            response.addDuration(tracks.getLength());
            response.addPlaylist(playlist);
        }
        return response;
    }
}

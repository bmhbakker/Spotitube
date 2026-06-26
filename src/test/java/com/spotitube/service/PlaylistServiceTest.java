package com.spotitube.service;

import com.spotitube.api.dto.request.PlaylistRequest;
import com.spotitube.api.dto.request.TrackRequest;
import com.spotitube.api.dto.response.PlaylistResponse;
import com.spotitube.api.dto.response.TrackResponse;
import com.spotitube.dal.repository.IPlaylistRepository;
import com.spotitube.domain.model.Playlist;
import com.spotitube.domain.model.User;
import jakarta.ws.rs.NotAuthorizedException;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class PlaylistServiceTest {
    @Test
    void getPlaylists_validToken_returnsPlaylistResponse() {
        // Arrange
        LoginService loginServiceMock = mock(LoginService.class);
        IPlaylistRepository playlistRepositoryMock = mock(IPlaylistRepository.class);
        TrackService trackServiceMock = mock(TrackService.class);

        User mockUser = new User();
        mockUser.setId(1);

        List<Playlist> playlists = new ArrayList<>();

        when(loginServiceMock.validateAuth("token")).thenReturn(mockUser);
        when(playlistRepositoryMock.getPlaylists(1)).thenReturn(playlists);
        when(trackServiceMock.getTracksInPlaylist(1, "token")).thenReturn(new TrackResponse());

        PlaylistService sut = new PlaylistService(trackServiceMock, playlistRepositoryMock, loginServiceMock);

        // Act
        PlaylistResponse actualResponse = sut.getPlaylists("token");

        // Assert
        assertNotNull(actualResponse);
        verify(loginServiceMock).validateAuth("token");
        verify(playlistRepositoryMock).getPlaylists(1);
    }

    @Test
    void getPlaylists_invalidToken_throwsNotAuthorizedException() {
        // Arrange
        LoginService loginServiceMock = mock(LoginService.class);
        IPlaylistRepository playlistRepositoryMock = mock(IPlaylistRepository.class);
        TrackService trackServiceMock = mock(TrackService.class);

        when(loginServiceMock.validateAuth("invalidToken")).thenThrow(new NotAuthorizedException("Invalid token"));
        PlaylistService sut = new PlaylistService(trackServiceMock, playlistRepositoryMock, loginServiceMock);

        // Act & Assert
        assertThrows(NotAuthorizedException.class, () -> sut.getPlaylists("invalidToken"));

        // Verify
        verify(loginServiceMock).validateAuth("invalidToken");
        verifyNoInteractions(playlistRepositoryMock);
    }

    @Test
    void getPlaylist_validToken_returnsPlaylistResponse() {
        LoginService loginServiceMock = mock(LoginService.class);
        IPlaylistRepository playlistRepositoryMock = mock(IPlaylistRepository.class);
        TrackService trackServiceMock = mock(TrackService.class);

        User mockUser = new User();
        mockUser.setId(1);

        List<Playlist> playlists = new ArrayList<>();

        when(loginServiceMock.validateAuth("token")).thenReturn(mockUser);
        when(playlistRepositoryMock.getPlaylist(10, 1)).thenReturn(playlists);

        PlaylistService sut = new PlaylistService(trackServiceMock, playlistRepositoryMock, loginServiceMock);

        PlaylistResponse response = sut.getPlaylist(10, "token");

        assertNotNull(response);
        verify(loginServiceMock).validateAuth("token");
        verify(playlistRepositoryMock).getPlaylist(10, 1);
    }

    @Test
    void addPlaylist_validToken_addsPlaylistAndReturnsResponse() {
        LoginService loginServiceMock = mock(LoginService.class);
        IPlaylistRepository playlistRepositoryMock = mock(IPlaylistRepository.class);
        TrackService trackServiceMock = mock(TrackService.class);

        User user = new User();
        user.setId(1);

        PlaylistRequest request = new PlaylistRequest();
        request.name = "My Playlist";

        List<Playlist> playlists = new ArrayList<>();

        when(loginServiceMock.validateAuth("token")).thenReturn(user);
        when(playlistRepositoryMock.getPlaylists(1)).thenReturn(playlists);

        PlaylistService sut = new PlaylistService(trackServiceMock, playlistRepositoryMock, loginServiceMock);

        PlaylistResponse response = sut.addPlaylist(request, "token");

        assertNotNull(response);
        verify(playlistRepositoryMock).addPlaylist("My Playlist", 1);
        verify(playlistRepositoryMock).getPlaylists(1);
    }

    @Test
    void deletePlaylist_validToken_deletesPlaylist() {
        LoginService loginServiceMock = mock(LoginService.class);
        IPlaylistRepository playlistRepositoryMock = mock(IPlaylistRepository.class);
        TrackService trackServiceMock = mock(TrackService.class);

        User user = new User();
        user.setId(1);

        when(loginServiceMock.validateAuth("token")).thenReturn(user);
        when(playlistRepositoryMock.getPlaylists(1)).thenReturn(new ArrayList<>());

        PlaylistService sut = new PlaylistService(trackServiceMock, playlistRepositoryMock, loginServiceMock);

        PlaylistResponse response = sut.deletePlaylist(5, "token");

        assertNotNull(response);
        verify(playlistRepositoryMock).deletePlaylist(5);
        verify(playlistRepositoryMock).getPlaylists(1);
    }

    @Test
    void updatePlaylistName_validToken_updatesName() {
        LoginService loginServiceMock = mock(LoginService.class);
        IPlaylistRepository playlistRepositoryMock = mock(IPlaylistRepository.class);
        TrackService trackServiceMock = mock(TrackService.class);

        User user = new User();
        user.setId(1);

        PlaylistRequest request = new PlaylistRequest();
        request.name = "Updated Name";

        when(loginServiceMock.validateAuth("token")).thenReturn(user);
        when(playlistRepositoryMock.getPlaylists(1)).thenReturn(new ArrayList<>());

        PlaylistService sut = new PlaylistService(trackServiceMock, playlistRepositoryMock, loginServiceMock);

        PlaylistResponse response = sut.updatePlaylistName(5, request, "token");

        assertNotNull(response);
        verify(playlistRepositoryMock).updatePlaylistName(5, "Updated Name");
    }

    @Test
    void addTrackToPlaylist_validToken_addsTrack() {
        LoginService loginServiceMock = mock(LoginService.class);
        IPlaylistRepository playlistRepositoryMock = mock(IPlaylistRepository.class);
        TrackService trackServiceMock = mock(TrackService.class);

        User user = new User();
        user.setId(1);

        TrackRequest request = new TrackRequest();
        request.setId(99);
        request.setOfflineAvailable(true);

        when(loginServiceMock.validateAuth("token")).thenReturn(user);
        when(playlistRepositoryMock.getPlaylists(1)).thenReturn(new ArrayList<>());

        PlaylistService sut = new PlaylistService(trackServiceMock, playlistRepositoryMock, loginServiceMock);

        PlaylistResponse response = sut.addTrackToPlaylist(10, request, "token");

        assertNotNull(response);
        verify(playlistRepositoryMock).addTrackToPlaylist(10, 99, true);
    }

    @Test
    void deleteTrackFromPlaylist_validToken_deletesTrack() {
        LoginService loginServiceMock = mock(LoginService.class);
        IPlaylistRepository playlistRepositoryMock = mock(IPlaylistRepository.class);
        TrackService trackServiceMock = mock(TrackService.class);

        User user = new User();
        user.setId(1);

        when(loginServiceMock.validateAuth("token")).thenReturn(user);
        when(playlistRepositoryMock.getPlaylists(1)).thenReturn(new ArrayList<>());

        PlaylistService sut = new PlaylistService(trackServiceMock, playlistRepositoryMock, loginServiceMock);

        PlaylistResponse response = sut.deleteTrackFromPlaylist(10, 77, "token");

        assertNotNull(response);
        verify(playlistRepositoryMock).removeTrackFromPlaylist(10, 77);
    }

    @Test
    void buildPlaylistResponse_buildsResponseWithTracksAndDuration() {
        LoginService loginServiceMock = mock(LoginService.class);
        IPlaylistRepository playlistRepositoryMock = mock(IPlaylistRepository.class);
        TrackService trackServiceMock = mock(TrackService.class);

        Playlist playlist = new Playlist();
        playlist.setId(1);

        List<Playlist> playlists = List.of(playlist);

        TrackResponse trackResponse = mock(TrackResponse.class);
        when(trackResponse.getTracks()).thenReturn(new ArrayList<>());
        when(trackResponse.getLength()).thenReturn(120);

        when(trackServiceMock.getTracksInPlaylist(1, null)).thenReturn(trackResponse);

        PlaylistService sut = new PlaylistService(trackServiceMock, playlistRepositoryMock, loginServiceMock);

        PlaylistResponse response = sut.buildPlaylistResponse(playlists);

        assertNotNull(response);
        verify(trackServiceMock).getTracksInPlaylist(1, null);
    }
}

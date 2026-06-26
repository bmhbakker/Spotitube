package com.spotitube.service;

import com.spotitube.api.dto.response.TrackResponse;
import com.spotitube.dal.repository.ITrackRepository;
import com.spotitube.domain.model.Track;
import com.spotitube.domain.model.User;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TrackServiceTest {
    @Test
    void getTracksInPlaylist_validToken_returnsTrackResponse()
    {
        // Arrange
        ITrackRepository trackRepositoryMock = mock(ITrackRepository.class);
        LoginService loginServiceMock = mock(LoginService.class);
        User mockUser = new User();

        int playlistId = 1;
        String token = "validToken";

        Track track1 = new Track();
        track1.setDuration(100);

        Track track2 = new Track();
        track2.setDuration(200);

        List<Track> tracks = List.of(track1, track2);

        when(loginServiceMock.validateAuth(token)).thenReturn(mockUser);
        when(trackRepositoryMock.getAllTracksInPlaylist(playlistId)).thenReturn(tracks);

        TrackService sut = new TrackService(trackRepositoryMock, loginServiceMock);

        // Act
        TrackResponse actualResponse = sut.getTracksInPlaylist(playlistId, token);

        verify(loginServiceMock).validateAuth(token);
        verify(trackRepositoryMock).getAllTracksInPlaylist(playlistId);

        assertNotNull(actualResponse);
        assertEquals(2, actualResponse.getTracks().size());
        assertEquals(300, actualResponse.getLength());
    }

    @Test
    void getTracksInPlaylist_nullToken_doesNotValidateAuth() {
        ITrackRepository trackRepositoryMock = mock(ITrackRepository.class);
        LoginService loginServiceMock = mock(LoginService.class);

        when(trackRepositoryMock.getAllTracksInPlaylist(1)).thenReturn(new ArrayList<>());

        TrackService sut = new TrackService(trackRepositoryMock, loginServiceMock);

        TrackResponse response = sut.getTracksInPlaylist(1, null);

        assertNotNull(response);
        verifyNoInteractions(loginServiceMock);
    }

    @Test
    void getTracksInPlaylist_emptyList_returnsEmptyResponse() {
        ITrackRepository trackRepositoryMock = mock(ITrackRepository.class);
        LoginService loginServiceMock = mock(LoginService.class);

        when(trackRepositoryMock.getAllTracksInPlaylist(1)).thenReturn(new ArrayList<>());

        TrackService sut = new TrackService(trackRepositoryMock, loginServiceMock);

        TrackResponse response = sut.getTracksInPlaylist(1, "token");

        assertNotNull(response);
        assertEquals(0, response.getTracks().size());
        assertEquals(0, response.getLength());
    }

    @Test
    void getOptionalTracks_validToken_returnsTracks() {
        ITrackRepository trackRepositoryMock = mock(ITrackRepository.class);
        LoginService loginServiceMock = mock(LoginService.class);

        Track track = new Track();
        track.setDuration(50);

        when(loginServiceMock.validateAuth("token")).thenReturn(new User());
        when(trackRepositoryMock.getOptionalTracks(1)).thenReturn(List.of(track));

        TrackService sut = new TrackService(trackRepositoryMock, loginServiceMock);

        TrackResponse response = sut.getOptionalTracks(1, "token");

        assertNotNull(response);
        assertEquals(1, response.getTracks().size());

        verify(loginServiceMock).validateAuth("token");
        verify(trackRepositoryMock).getOptionalTracks(1);
    }

    @Test
    void getOptionalTracks_invalidToken_throwsException() {
        ITrackRepository trackRepositoryMock = mock(ITrackRepository.class);
        LoginService loginServiceMock = mock(LoginService.class);

        when(loginServiceMock.validateAuth("bad")).thenThrow(new RuntimeException());

        TrackService sut = new TrackService(trackRepositoryMock, loginServiceMock);

        assertThrows(RuntimeException.class,
                () -> sut.getOptionalTracks(1, "bad"));

        verify(loginServiceMock).validateAuth("bad");
    }
}

package com.spotitube.api.resource;

import com.spotitube.api.dto.request.PlaylistRequest;
import com.spotitube.api.dto.request.TrackRequest;
import com.spotitube.api.dto.response.PlaylistResponse;
import com.spotitube.api.dto.response.TrackResponse;
import com.spotitube.domain.model.Track;
import com.spotitube.service.PlaylistService;
import com.spotitube.service.TrackService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PlaylistResourceTest {

    private PlaylistService playlistServiceMock;
    private PlaylistResponse playlistMockResponse;
    private PlaylistRequest playlistRequestMock;

    private TrackService trackServiceMock;
    private TrackResponse trackMockResponse;
    private TrackRequest trackRequestMock;
    private String token;
    private String invalidToken;


    @BeforeEach
    void init() {
        playlistServiceMock = mock(PlaylistService.class);
        trackServiceMock = mock(TrackService.class);

        playlistMockResponse = new PlaylistResponse();
        playlistMockResponse.setPlaylists(new ArrayList<>());
        playlistMockResponse.setLength(123);

        trackMockResponse = new TrackResponse();
        List<Track> tracks = new ArrayList<>();
        Track track = new Track(1, "Hello", "Someone" , 123, true);
        tracks.add(track);
        trackMockResponse.setTracks(tracks);
        trackMockResponse.setLength(123);

        playlistRequestMock = mock(PlaylistRequest.class);
        trackRequestMock = mock(TrackRequest.class);

        token = "token";
        invalidToken = "ACB123";
    }

    @Test
    void getPlaylists_returnsOkResponse() {
        // Arrange
        when(playlistServiceMock.getPlaylists(token)).thenReturn(playlistMockResponse);

        PlaylistResource sut = new PlaylistResource(playlistServiceMock, trackServiceMock);

        // Act
        Response actualResponse = sut.getPlaylists(token);

        // Assert
        assertNotNull(actualResponse);
        assertEquals(200, actualResponse.getStatus());
        assertEquals(playlistMockResponse, actualResponse.getEntity());
        verify(playlistServiceMock).getPlaylists(token);
    }

    @Test
    void getPlaylists_invalidToken_throwsNotAuthorizedException(){
        //Arrange
        when(playlistServiceMock.getPlaylists(invalidToken)).thenThrow(new NotAuthorizedException("Invalid token"));

        PlaylistResource sut = new PlaylistResource(playlistServiceMock, trackServiceMock);

        //Act & Assert
        assertThrows(NotAuthorizedException.class, () -> sut.getPlaylists(invalidToken));

        //Verify
        verify(playlistServiceMock).getPlaylists(invalidToken);
    }

    @Test
    void getPlaylist_returnsOkResponse() {
        // Arrange
        playlistMockResponse.setLength(1);
        when(playlistServiceMock.getPlaylist(1, token)).thenReturn(playlistMockResponse);
        PlaylistResource sut = new PlaylistResource(playlistServiceMock, trackServiceMock);

        // Act
        Response actualResponse = sut.getPlaylist(token, 1);

        // Assert
        assertNotNull(actualResponse);
        assertEquals(200, actualResponse.getStatus());
        assertEquals(playlistMockResponse, actualResponse.getEntity());
        verify(playlistServiceMock).getPlaylist(1, token);
    }

    @Test
    void getPlaylist_invalidToken_throwsNotAuthorizedException(){
        //Arrange
        when(playlistServiceMock.getPlaylist(1, invalidToken)).thenThrow(new NotAuthorizedException("Invalid token"));
        PlaylistResource sut = new PlaylistResource(playlistServiceMock, trackServiceMock);

        //Act & Assert
        assertThrows(NotAuthorizedException.class, () -> sut.getPlaylist(invalidToken,1 ));

        //Verify
        verify(playlistServiceMock).getPlaylist(1, invalidToken);
    }

    @Test
    void getTracksInPlaylist_returnsOkResponse() {
        // Arrange
        when(trackServiceMock.getTracksInPlaylist(1, token)).thenReturn(trackMockResponse);
        PlaylistResource sut = new PlaylistResource(playlistServiceMock, trackServiceMock);

        // Act
        Response actualResponse = sut.getTracksInPlaylist(token, 1);

        // Assert
        assertNotNull(actualResponse);
        assertEquals(200, actualResponse.getStatus());
        assertEquals(trackMockResponse, actualResponse.getEntity());
        verify(trackServiceMock).getTracksInPlaylist(1, token);
    }

    @Test
    void getTracksInPlaylist_invalidToken_ThrowsNotAuthorizedException() {
        //Arrange
        when(trackServiceMock.getTracksInPlaylist(1, invalidToken)).thenThrow(new NotAuthorizedException("Invalid token"));
        PlaylistResource sut = new PlaylistResource(playlistServiceMock, trackServiceMock);

        //Act & Assert
        assertThrows(NotAuthorizedException.class, () -> sut.getTracksInPlaylist(invalidToken,1 ));

        //Verify
        verify(trackServiceMock).getTracksInPlaylist(1, invalidToken);
    }

    @Test
    void addPlaylist_returnsOkResponse() {
        // Arrange
        when(playlistServiceMock.addPlaylist(playlistRequestMock, token)).thenReturn(playlistMockResponse);
        PlaylistResource sut = new PlaylistResource(playlistServiceMock, trackServiceMock);

        // Act
        Response actualResponse = sut.addPlaylist(token, playlistRequestMock);

        // Assert
        assertNotNull(actualResponse);
        assertEquals(200, actualResponse.getStatus());
        assertEquals(playlistMockResponse, actualResponse.getEntity());
        verify(playlistServiceMock).addPlaylist(playlistRequestMock, token);
    }

    @Test
    void addPlaylist_invalidToken_returnsNotAuthorizedException(){
        //Arrange
        when(playlistServiceMock.addPlaylist(playlistRequestMock, invalidToken)).thenThrow(new NotAuthorizedException("Invalid token"));
        PlaylistResource sut = new PlaylistResource(playlistServiceMock, trackServiceMock);

        //Act & Assert
        assertThrows(NotAuthorizedException.class, () -> sut.addPlaylist(invalidToken, playlistRequestMock));

        //Verify
        verify(playlistServiceMock).addPlaylist(playlistRequestMock, invalidToken);
    }

    @Test
    void updatePlaylistName_ReturnsOkResponse() {
        // Arrange
        when(playlistServiceMock.updatePlaylistName(1, playlistRequestMock, token)).thenReturn(playlistMockResponse);
        PlaylistResource sut = new PlaylistResource(playlistServiceMock, trackServiceMock);

        // Act
        Response actualResponse = sut.updatePlaylistName(token, 1, playlistRequestMock);

        // Assert
        assertNotNull(actualResponse);
        assertEquals(200, actualResponse.getStatus());
        assertEquals(playlistMockResponse, actualResponse.getEntity());
        verify(playlistServiceMock).updatePlaylistName(1, playlistRequestMock, token);
    }

    @Test
    void updatePlaylistName_invalidToken_returnsNotAuthorizedException(){
        //Arrange
        when(playlistServiceMock.updatePlaylistName(1, playlistRequestMock, invalidToken)).thenThrow(new NotAuthorizedException("Invalid token"));
        PlaylistResource sut = new PlaylistResource(playlistServiceMock, trackServiceMock);

        //Act & Assert
        assertThrows(NotAuthorizedException.class, () -> sut.updatePlaylistName(invalidToken, 1, playlistRequestMock));

        //Verify
        verify(playlistServiceMock).updatePlaylistName(1, playlistRequestMock, invalidToken);
    }

    @Test
    void deletePlaylist_returnsOkResponse() {
        // Arrange
        when(playlistServiceMock.deletePlaylist(1, token)).thenReturn(playlistMockResponse);
        PlaylistResource sut = new PlaylistResource(playlistServiceMock, trackServiceMock);

        // Act
        Response actualResponse = sut.deletePlaylist(token, 1);

        // Assert
        assertNotNull(actualResponse);
        assertEquals(200, actualResponse.getStatus());
        assertEquals(playlistMockResponse, actualResponse.getEntity());
        verify(playlistServiceMock).deletePlaylist(1, token);
    }

    @Test
    void deletePlaylist_invalidToken_returnsNotAuthorizedException(){
        //Arrange
        when(playlistServiceMock.deletePlaylist(1, invalidToken)).thenThrow(new NotAuthorizedException("Invalid token"));
        PlaylistResource sut = new PlaylistResource(playlistServiceMock, trackServiceMock);

        //Act & Assert
        assertThrows(NotAuthorizedException.class, () -> sut.deletePlaylist(invalidToken, 1));

        //Verify
        verify(playlistServiceMock).deletePlaylist(1, invalidToken);
    }

    @Test
    void updatePlaylistTracks_returnsOkResponse() {
        // Arrange
        when(playlistServiceMock.addTrackToPlaylist(1, trackRequestMock, token)).thenReturn(playlistMockResponse);
        PlaylistResource sut = new PlaylistResource(playlistServiceMock, trackServiceMock);

        // Act
        Response actualResponse = sut.updatePlaylistTracks(token, 1, trackRequestMock);

        // Assert
        assertNotNull(actualResponse);
        assertEquals(200, actualResponse.getStatus());
        assertEquals(playlistMockResponse, actualResponse.getEntity());
        verify(playlistServiceMock).addTrackToPlaylist(1, trackRequestMock, token);
    }

    @Test
    void updatePlaylistTracks_invalidToken_returnsNotAuthorizedException(){
        //Arrange
        when(playlistServiceMock.addTrackToPlaylist(1, trackRequestMock, invalidToken)).thenThrow(new NotAuthorizedException("Invalid token"));
        PlaylistResource sut = new PlaylistResource(playlistServiceMock, trackServiceMock);

        //Act & Assert
        assertThrows(NotAuthorizedException.class, () -> sut.updatePlaylistTracks(invalidToken, 1, trackRequestMock));

        //Verify
        verify(playlistServiceMock).addTrackToPlaylist(1, trackRequestMock, invalidToken);
    }

    @Test
    void deleteTrackFromPlaylist_returnsOkResponse() {
        // Arrange
        when(playlistServiceMock.deleteTrackFromPlaylist(1, 5, token)).thenReturn(playlistMockResponse);
        PlaylistResource sut = new PlaylistResource(playlistServiceMock, trackServiceMock);

        // Act
        Response actualResponse = sut.deleteTrackFromPlaylist(token, 1, 5);

        // Assert
        assertNotNull(actualResponse);
        assertEquals(200, actualResponse.getStatus());
        assertEquals(playlistMockResponse, actualResponse.getEntity());
        verify(playlistServiceMock).deleteTrackFromPlaylist(1, 5, token);
    }

    @Test
    void deleteTrackFromPlaylist_invalidToken_returnsNotAuthorizedException(){
        //Arrange
        when(playlistServiceMock.deleteTrackFromPlaylist(1, 5, invalidToken)).thenThrow(new NotAuthorizedException("Invalid token"));
        PlaylistResource sut = new PlaylistResource(playlistServiceMock, trackServiceMock);

        //Act & Assert
        assertThrows(NotAuthorizedException.class, () -> sut.deleteTrackFromPlaylist(invalidToken, 1, 5));

        //Verify
        verify(playlistServiceMock).deleteTrackFromPlaylist(1, 5, invalidToken);
    }
}
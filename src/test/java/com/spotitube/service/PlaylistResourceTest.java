package com.spotitube.service;

import com.spotitube.api.dto.request.PlaylistRequest;
import com.spotitube.api.dto.response.PlaylistResponse;
import com.spotitube.api.resource.PlaylistResource;
import jakarta.ws.rs.NotAuthorizedException;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PlaylistResourceTest {

    private PlaylistService playlistServiceMock;
    private TrackService trackServiceMock;
    private PlaylistResponse mockResponse;
    private PlaylistResource resource;
    private PlaylistRequest playlistRequestMock;

    @BeforeEach
    void init() {
        playlistServiceMock = mock(PlaylistService.class);
        trackServiceMock = mock(TrackService.class);
        resource = new PlaylistResource(playlistServiceMock, trackServiceMock);

        mockResponse = new PlaylistResponse();
        mockResponse.setPlaylists(new ArrayList<>());
        mockResponse.setLength(123);

        playlistRequestMock = mock(PlaylistRequest.class);
    }

    @Test
    void getPlaylists_returnsOkResponse() {
        // Arrange
        when(playlistServiceMock.getPlaylists("token")).thenReturn(mockResponse);

        // Act
        Response response = resource.getPlaylists("token");

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatus());
        assertEquals(mockResponse, response.getEntity());
        assertEquals(mockResponse.getLength(), 123);
        verify(playlistServiceMock).getPlaylists("token");
    }

    @Test
    void getPlaylists_invalidToken_throwsNotAuthorizedException(){
        //Arrange
        when(playlistServiceMock.getPlaylists("invalidToken")).thenThrow(new NotAuthorizedException("Invalid token"));

        //Act & Assert
        assertThrows(NotAuthorizedException.class, () -> resource.getPlaylists("invalidToken"));

        //Verify
        verify(playlistServiceMock).getPlaylists("invalidToken");
    }

    @Test
    void addPlaylist_returnsOkResponse() {
        // Arrange
        when(playlistServiceMock.addPlaylist(playlistRequestMock, "token")).thenReturn(mockResponse);

        // Act
        Response response = resource.addPlaylist("token", playlistRequestMock);

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatus());
        assertEquals(mockResponse, response.getEntity());
        assertEquals(mockResponse.getLength(), 123);
        verify(playlistServiceMock).addPlaylist(playlistRequestMock, "token");
    }

    @Test
    void addPlaylist_invalidToken_returnsNotAuthorizedException(){
        //Arrange
        when(playlistServiceMock.addPlaylist(playlistRequestMock, "Invalid token")).thenThrow(new NotAuthorizedException("Invalid token"));

        //Act & Assert
        assertThrows(NotAuthorizedException.class, () -> resource.addPlaylist("Invalid token", playlistRequestMock));

        //Verify
        verify(playlistServiceMock).addPlaylist(playlistRequestMock, "Invalid token");
    }

    @Test
    void updatePlaylistName_ReturnsOkResponse() {
        // Arrange
        when(playlistServiceMock.updatePlaylistName(1, playlistRequestMock, "token")).thenReturn(mockResponse);

        // Act
        Response response = resource.updatePlaylistName("token", 1, playlistRequestMock);

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatus());
        assertEquals(mockResponse, response.getEntity());
        assertEquals(mockResponse.getPlaylists(), new ArrayList<>());
        assertEquals(mockResponse.getLength(), 123);
        verify(playlistServiceMock).updatePlaylistName(1, playlistRequestMock, "token");
    }

    @Test
    void updatePlaylistName_invalidToken_returnsNotAuthorizedException(){
        //Arrange
        when(playlistServiceMock.updatePlaylistName(1, playlistRequestMock, "Invalid token")).thenThrow(new NotAuthorizedException("Invalid token"));

        //Act & Assert
        assertThrows(NotAuthorizedException.class, () -> resource.updatePlaylistName("Invalid token", 1, playlistRequestMock));

        //Verify
        verify(playlistServiceMock).updatePlaylistName(1, playlistRequestMock, "Invalid token");
    }

    @Test
    void deletePlaylist_returnsOkResponse() {
        // Arrange
        when(playlistServiceMock.deletePlaylist(1, "token")).thenReturn(mockResponse);

        // Act
        Response response = resource.deletePlaylist("token", 1);

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatus());
        assertEquals(mockResponse, response.getEntity());
        assertEquals(mockResponse.getPlaylists(), new ArrayList<>());
        assertEquals(mockResponse.getLength(), 123);
        verify(playlistServiceMock).deletePlaylist(1, "token");
    }

    @Test
    void deletePlaylist_invalidToken_returnsNotAuthorizedException(){
        //Arrange
        when(playlistServiceMock.deletePlaylist(1, "Invalid token")).thenThrow(new NotAuthorizedException("Invalid token"));

        //Act & Assert
        assertThrows(NotAuthorizedException.class, () -> resource.deletePlaylist("Invalid token", 1));

        //Verify
        verify(playlistServiceMock).deletePlaylist(1, "Invalid token");
    }
}
package com.spotitube.api.resource;

import com.spotitube.api.dto.response.PlaylistResponse;
import com.spotitube.api.dto.response.TrackResponse;
import com.spotitube.domain.model.Track;
import com.spotitube.service.TrackService;
import jakarta.ws.rs.NotAuthorizedException;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TrackResourceTest {
    private TrackService trackServiceMock;
    private TrackResponse trackMockResponse;

    private String token;
    private int playlistId;

    @BeforeEach
    void init() {
        trackServiceMock = mock(TrackService.class);
        Track track = new Track();

        trackMockResponse = new TrackResponse();
        trackMockResponse.addTrack(track);
        trackMockResponse.setLength(123);

        token = "invalidToken";
        playlistId = 1;
    }

    @Test
    void getTracks_returnsOkResponse() {
        // Arrange
        String token = "validToken";
        int playlistId = 1;
        when(trackServiceMock.getOptionalTracks(playlistId, token)).thenReturn(trackMockResponse);
        TrackResource sut = new TrackResource(trackServiceMock);

        // Act
        Response actualResponse = sut.getTracks(token, playlistId);

        //Assert
        assertNotNull(actualResponse);
        assertEquals(200, actualResponse.getStatus());
        assertEquals(trackMockResponse, actualResponse.getEntity());
        verify(trackServiceMock).getOptionalTracks(playlistId, token);
    }

    @Test
    void getTracks_invalidToken_throwsNotAuthorizedException(){
        //Arrange
        when(trackServiceMock.getOptionalTracks(playlistId, token)).thenThrow(new NotAuthorizedException("Invalid token"));

        TrackResource sut = new TrackResource(trackServiceMock);

        //Act & Assert
        assertThrows(NotAuthorizedException.class, () -> sut.getTracks(token, playlistId));

        //Verify
        verify(trackServiceMock).getOptionalTracks(playlistId, token);
    }
}
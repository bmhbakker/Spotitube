package com.spotitube.service;

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

        PlaylistService service = new PlaylistService(trackServiceMock, playlistRepositoryMock, loginServiceMock);

        User mockUser = new User();
        mockUser.setId(1);

        List<Playlist> playlists = new ArrayList<>();

        when(loginServiceMock.validateAuth("token")).thenReturn(mockUser);
        when(playlistRepositoryMock.getPlaylists(1)).thenReturn(playlists);
        when(trackServiceMock.getTracksInPlaylist(1, "token")).thenReturn(new TrackResponse());

        // Act
        PlaylistResponse response = service.getPlaylists("token");

        // Assert
        assertNotNull(response);
        verify(loginServiceMock).validateAuth("token");
        verify(playlistRepositoryMock).getPlaylists(1);
    }

    @Test
    void getPlaylists_invalidToken_throwsNotAuthorizedException() {
        // Arrange
        LoginService loginServiceMock = mock(LoginService.class);
        IPlaylistRepository playlistRepositoryMock = mock(IPlaylistRepository.class);
        TrackService trackServiceMock = mock(TrackService.class);

        PlaylistService service = new PlaylistService(trackServiceMock, playlistRepositoryMock, loginServiceMock);

        when(loginServiceMock.validateAuth("invalidToken")).thenThrow(new NotAuthorizedException("Invalid token"));

        // Act & Assert
        assertThrows(NotAuthorizedException.class, () -> service.getPlaylists("invalidToken"));

        // Verify
        verify(loginServiceMock).validateAuth("invalidToken");
        verifyNoInteractions(playlistRepositoryMock);
    }
}

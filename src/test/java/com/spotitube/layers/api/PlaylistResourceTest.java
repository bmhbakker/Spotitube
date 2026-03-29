//package com.spotitube.api;
//
//import com.spotitube.api.dto.response.PlaylistResponse;
//import com.spotitube.domain.User;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//import static org.mockito.Mockito.mock;
//import static org.mockito.Mockito.when;
//
//class PlaylistResourceTest {
//    static PlaylistResponse playlistResponseMock;
//    PlaylistResource sut;
//    IPlaylistRepository playlistRepoMock;
//    User userMock;
//
//    @BeforeAll
//    static void initAll() {
//        playlistResponseMock = mock(PlaylistResponse.class);
//    }
//
//    @BeforeEach
//    void setUp() {
//        playlistRepoMock = mock(IPlaylistRepository.class);
//        sut = new PlaylistResource();
//        playlistRepoMock = mock(IPlaylistRepository.class);
//        userMock = mock(User.class);
//    }
//
//
//    @Test
//    void testGetPlaylists_Return_TypeOf_Response() throws Exception {
//        when(playlistRepoMock.getPlaylists("testUser")).thenReturn(playlistResponseMock);
//        when(userMock.getToken()).thenReturn("testToken");
//
//        sut.setPlaylistRepository(playlistRepoMock);
//        sut.setUser(userMock);
//
//        var result = sut.getPlaylists("testToken");
//
//        assertNotNull(result);
//        assertEquals(200, result.getStatus());
//    }
//}
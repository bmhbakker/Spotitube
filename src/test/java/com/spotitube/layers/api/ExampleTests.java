package com.spotitube.api;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ExampleTests {

//    @Test
//    void testGetTracks_Returns_TypeOf_Tracklist() {
//        // Arrange
//        var sut = new TrackResource();
//
//        var trackRepoMock = mock(ITrackRepository.class);
//        when(trackRepoMock.getTracks()).thenReturn(new ArrayList<Song>() {
//            {
//                Song track = new Song();
//                track.setId(1);
//                track.setTitle("testTitle1");
//                track.setPerformer("testPerformer1");
//                track.setUrl("www.google.com");
//                track.setAlbum("TestAlbum1");
//                track.setDuration(200);
//                add(track);
//            }
//        });
//
//        sut.setTrackRepository(trackRepoMock);
//
//        // Act
//        var result = sut.getTracks();
//
//        // Assert
//        assertInstanceOf(ArrayList.class, result.getEntity());
//
//        var trackList = (ArrayList<Song>) result.getEntity();
//        assertInstanceOf(Song.class, trackList.get(0));
//    }
//
//    @Test
//    void testGetTracks_Returns_TrackList() {
//        // Arrange
//        var sut = new TrackResource();
//        int expectedTrackCount = 2;
//
//        var trackRepoMock = mock(ITrackRepository.class);
//        when(trackRepoMock.getTracks()).thenReturn(new ArrayList<Song>() {
//            {
//                Song track = new Song();
//                track.setId(1);
//                track.setTitle("testTitle1");
//                track.setPerformer("testPerformer1");
//                track.setUrl("www.google.com");
//                track.setAlbum("TestAlbum1");
//                track.setDuration(200);
//                add(track);
//
//                Song track2 = new Song();
//                track2.setId(2);
//                track2.setTitle("testTitle2");
//                track2.setPerformer("testPerformer2");
//                track2.setUrl("www.google.com");
//                track2.setAlbum("TestAlbum2");
//                track2.setDuration(200);
//                add(track2);
//            }
//        });
//
//        sut.setTrackRepository(trackRepoMock);
//
//        // Act
//        var result = sut.getTracks();
//
//        // Assert
//        var trackList = (ArrayList<Song>) result.getEntity();
//        assertEquals(expectedTrackCount, trackList.size());
//    }
//
//    @Test
//    void testGetTracks_Returns_Empty_Tracklist() {
//        // Arrange
//        TrackResource sut = new TrackResource();
//        int expectedTrackCount = 0;
//        var trackRepoMock = mock(ITrackRepository.class);
//        when(trackRepoMock.getTracks()).thenReturn(new ArrayList<Song>());
//
//        sut.setTrackRepository(trackRepoMock);
//
//        // Act
//        var result = sut.getTracks();
//
//        // Assert
//        var trackList = (ArrayList<Song>) result.getEntity();
//        assertEquals(expectedTrackCount, trackList.size());
//    }
//
//    @Test
//    void testGetTrack_Returns_TypeOf_Track() {
//        // Arrange
//        var sut = new TrackResource();
//
//        var trackRepoMock = mock(ITrackRepository.class);
//        when(trackRepoMock.getTrack(1)).thenReturn(new Song() {
//            {
//                Song track = new Song();
//                track.setId(1);
//                track.setTitle("testTitle1");
//                track.setPerformer("testPerformer1");
//                track.setUrl("www.google.com");
//                track.setAlbum("TestAlbum1");
//                track.setDuration(200);
//            }
//        });
//
//        sut.setTrackRepository(trackRepoMock);
//
//        // Act
//        var result = sut.getTrack(1);
//
//        // Assert
//
//        var track = (Song) result.getEntity();
//        assertInstanceOf(Song.class, track);
//    }
//
//    @Test
//    void testGetTrack_Returns_Single_Track() {
//        var sut = new TrackResource();
//
//        var trackRepoMock = mock(ITrackRepository.class);
//        Song track = new Song();
//        track.setId(1);
//        track.setTitle("testTitle1");
//        track.setPerformer("testPerformer1");
//        track.setUrl("www.google.com");
//        track.setAlbum("TestAlbum1");
//        track.setDuration(200);
//
//        when(trackRepoMock.getTrack(1)).thenReturn(track);
//
//        sut.setTrackRepository(trackRepoMock);
//
//        // Act
//        var result = sut.getTrack(1);
//
//        // Assert
//        var trackResult = (Song) result.getEntity();
//        assertEquals(1, trackResult.getId());
//        assertEquals("testTitle1", trackResult.getTitle());
//    }
}
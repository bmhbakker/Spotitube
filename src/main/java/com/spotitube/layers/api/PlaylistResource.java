package com.spotitube.layers.api;

import com.spotitube.layers.domain.Response.PlaylistResponse;
import com.spotitube.layers.domain.Response.TrackResponse;
import com.spotitube.layers.domain.User;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

import java.sql.SQLException;
import java.util.Objects;


@Path("/playlists")
public class PlaylistResource {
    private IPlaylistRepository playlistRepository;
    private User user;

    @GET
    @Produces("application/json")
    public Response getPlaylists(@QueryParam("token") String requestToken) {
        PlaylistResponse playlists;
        if (Objects.equals(requestToken, user.getToken())) {
            try {
                playlists = playlistRepository.getPlaylists(user.getUsername());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            return Response.ok(playlists).build();
        }
        return Response.status(Response.Status.FORBIDDEN).build();
    }

    @GET
    @Path("/{id}")
    @Produces("application/json")
    public Response getPlaylist(@PathParam("id") int id) {
        var playlist = playlistRepository.getPlaylist(id);
        return Response.ok(playlist).build();
    }

    @GET
    @Path("/{id}/tracks")
    @Produces("application/json")
    public Response getTracksInPlaylist(@PathParam("id") int id) {
        TrackResponse tracks;
        try {
            tracks = playlistRepository.getAllTracksInPlaylist(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Response.ok(tracks).build();
    }

    @Inject
    public void setPlaylistRepository(IPlaylistRepository playlistRepository) {
        this.playlistRepository = playlistRepository;
    }

    @Inject
    public void setUser(User user) {
        this.user = user;
    }
}



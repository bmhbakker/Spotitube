package com.spotitube.api.resource;

import com.spotitube.api.dto.request.PlaylistRequest;
import com.spotitube.api.dto.request.TrackRequest;
import com.spotitube.api.dto.response.PlaylistResponse;
import com.spotitube.api.dto.response.TrackResponse;
import com.spotitube.service.PlaylistService;
import com.spotitube.service.TrackService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

@Path("/playlists")
public class PlaylistResource {
    private PlaylistService playlistService;
    private TrackService trackService;

    public PlaylistResource() {
    } //ONLY NEEDED FOR PROXYING

    @Inject
    public PlaylistResource(PlaylistService playlistService, TrackService trackService) {

        this.playlistService = playlistService;
        this.trackService = trackService;
    }

    @GET
    @Produces("application/json")
    public Response getPlaylists(@QueryParam("token") String requestToken) {
        PlaylistResponse response = playlistService.getPlaylists(requestToken);
        return Response.ok(response).build();
    }

    @GET
    @Path("/{id}")
    @Produces("application/json")
    public Response getPlaylist(@QueryParam("token") String requestToken, @PathParam("id") int id) {
        PlaylistResponse response = playlistService.getPlaylist(id, requestToken);
        return Response.ok(response).build();
    }

    @GET
    @Path("/{id}/tracks")
    @Produces("application/json")
    public Response getTracksInPlaylist(@QueryParam("token") String requestToken, @PathParam("id") int id) {
        TrackResponse response = trackService.getTracksInPlaylist(id, requestToken);
        return Response.ok(response).build();
    }

    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public Response addPlaylist(@QueryParam("token") String requestToken, PlaylistRequest request) {
        PlaylistResponse response = playlistService.addPlaylist(request, requestToken);
        return Response.ok(response).build();
    }

    @DELETE
    @Path("/{id}")
    @Consumes("application/json")
    @Produces("application/json")
    public Response deletePlaylist(@QueryParam("token") String requestToken, @PathParam("id") int id) {
        PlaylistResponse response = playlistService.deletePlaylist(id, requestToken);
        return Response.ok(response).build();
    }

    //
    @PUT
    @Path("/{id}")
    @Consumes("application/json")
    @Produces("application/json")
    public Response updatePlaylistName(@QueryParam("token") String requestToken, @PathParam("id") int id, PlaylistRequest request) {
        PlaylistResponse response = playlistService.updatePlaylistName(id, request, requestToken);
        return Response.ok(response).build();
    }

    @POST
    @Path("/{playlistId}/tracks")
    @Consumes("application/json")
    @Produces("application/json")
    public Response updatePlaylistTracks(@QueryParam("token") String requestToken, @PathParam("playlistId") int playlistId, TrackRequest request) {
        PlaylistResponse response = playlistService.addTrackToPlaylist(playlistId, request, requestToken);
        return Response.ok(response).build();
    }

    @DELETE
    @Path("/{playlistId}/tracks/{trackId}")
    @Consumes("application/json")
    @Produces("application/json")
    public Response deleteTrackFromPlaylist(@QueryParam("token") String requestToken, @PathParam("playlistId") int playlistId, @PathParam("trackId") int trackId) {
        PlaylistResponse response = playlistService.deleteTrackFromPlaylist(playlistId, trackId, requestToken);
        return Response.ok(response).build();
    }
}



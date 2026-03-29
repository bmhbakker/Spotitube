package com.spotitube.api.resource;

import com.spotitube.api.dto.request.PlaylistRequest;
import com.spotitube.api.dto.request.TrackRequest;
import com.spotitube.dal.repository.IPlaylistRepository;
import com.spotitube.dal.repository.ITrackRepository;
import com.spotitube.dal.repository.impl.LoginRepository;
import com.spotitube.domain.model.User;
import com.spotitube.api.dto.response.PlaylistResponse;
import com.spotitube.api.dto.response.TrackResponse;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

@Path("/playlists")
public class PlaylistResource {
    private IPlaylistRepository playlistRepository;
    private LoginRepository loginRepository;
    private ITrackRepository trackRepository;

    @GET
    @Produces("application/json")
    public Response getPlaylists(@QueryParam("token") String requestToken) {
        try {
            User user = loginRepository.getUserByToken(requestToken);

            PlaylistResponse response = playlistRepository.getPlaylists(user.getId());
            return Response.ok(response).build();
        } catch (Exception e) {
            throw new InternalServerErrorException("Could not fetch playlists");
        }
    }


    @GET
    @Path("/{id}")
    @Produces("application/json")
    public Response getPlaylist(@QueryParam("token") String requestToken, @PathParam("id") int id) {
        try {
            User user = loginRepository.getUserByToken(requestToken);

            PlaylistResponse playlistResponse = playlistRepository.getPlaylist(id, user.getId());
            return Response.ok(playlistResponse).build();
        } catch (Exception e) {
            throw new InternalServerErrorException("Could not fetch playlist on id");
        }
    }

    @GET
    @Path("/{id}/tracks")
    @Produces("application/json")
    public Response getTracksInPlaylist(@QueryParam("token") String requestToken, @PathParam("id") int id) {
        try {
            //token is needed in GET url (?token={token}) but doesn't need to be used?
            loginRepository.getUserByToken(requestToken); // <-- do we need to check the user token here?
            //if we also need the user we can use: User user = loginRepository.getUserByToken(requestToken);

            TrackResponse trackResponse = trackRepository.getAllTracksInPlaylist(id);
            return Response.ok(trackResponse).build();
        } catch (Exception e){
            throw new InternalServerErrorException("Could not fetch all tracks in playlist");
        }
    }

    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public Response addPlaylist(@QueryParam("token") String requestToken, PlaylistRequest request) {
        try {
            User user = loginRepository.getUserByToken(requestToken);

            PlaylistResponse playlistResponse = playlistRepository.addPlaylist(request.name, user.getId());
            return Response.ok(playlistResponse).build();
        } catch (Exception e) {
            throw new InternalServerErrorException("Could not add playlist");
        }
    }

    @DELETE
    @Path("/{id}")
    @Consumes("application/json")
    @Produces("application/json")
    public Response deletePlaylist(@QueryParam("token") String requestToken, @PathParam("id") int id) {
        try {
            User user = loginRepository.getUserByToken(requestToken);

            PlaylistResponse playlistResponse = playlistRepository.deletePlaylist(id, user.getId());
            return Response.ok(playlistResponse).build();
        } catch (Exception e) {
            throw new InternalServerErrorException("Could not delete playlist");
        }
    }

    @PUT
    @Path("/{id}")
    @Consumes("application/json")
    @Produces("application/json")
    public Response updatePlaylistName(@QueryParam("token") String requestToken, @PathParam("id") int id, PlaylistRequest request) {
        try {
            User user = loginRepository.getUserByToken(requestToken);

            PlaylistResponse playlistResponse = playlistRepository.updatePlaylistName(id, request.name, user.getId());
            return Response.ok(playlistResponse).build();
        } catch (Exception e) {
            throw new InternalServerErrorException("Could not change the name of playlist");
        }
    }

    @POST
    @Path("/{playlistId}/tracks")
    @Consumes("application/json")
    @Produces("application/json")
    public Response updatePlaylistTracks(@QueryParam("token") String requestToken, @PathParam("playlistId") int playlistId, TrackRequest request) {
        try {
            User user = loginRepository.getUserByToken(requestToken);

            PlaylistResponse playlistResponse = playlistRepository.addTrackToPlaylist(playlistId, request.getId(), request.isOfflineAvailable(), user.getId());
            return Response.ok(playlistResponse).build();
        } catch (Exception e) {
            throw new InternalServerErrorException("Could not add song to playlist");
        }
    }

    @DELETE
    @Path("/{playlistId}/tracks/{trackId}")
    @Consumes("application/json")
    @Produces("application/json")
    public Response deleteTrackFromPlaylist(@QueryParam("token") String requestToken, @PathParam("playlistId") int playlistId, @PathParam("trackId") int trackId){
        try{
            User user = loginRepository.getUserByToken(requestToken);

            PlaylistResponse playlistResponse = playlistRepository.removeTrackFromPlaylist(playlistId, trackId, user.getId());
            return Response.ok(playlistResponse).build();
        } catch (Exception e){
            throw new InternalServerErrorException("Could not delete track from playlist");
        }
    }

    @Inject
    public void setPlaylistRepository(IPlaylistRepository playlistRepository) { this.playlistRepository = playlistRepository; }

    @Inject
    public void setTrackRepository(ITrackRepository trackRepository){
        this.trackRepository = trackRepository;
    }

    @Inject
    public void setLoginRepository(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }
}



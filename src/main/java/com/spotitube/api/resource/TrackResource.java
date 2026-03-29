package com.spotitube.api.resource;

import com.spotitube.dal.repository.ITrackRepository;
import com.spotitube.dal.repository.impl.LoginRepository;
import com.spotitube.domain.model.User;
import com.spotitube.api.dto.response.TrackResponse;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

@Path("/tracks")
public class TrackResource {
    private LoginRepository loginRepository;
    private ITrackRepository trackRepository;

    @GET
    @Produces("application/json")
    public Response getTracks(@QueryParam("token") String requestToken, @QueryParam("forPlaylist") int playlistId) {
        try {
            User user = loginRepository.getUserByToken(requestToken);

            if (user == null) {
                throw new NotAuthorizedException("Invalid token");
            }

            TrackResponse trackResponse = trackRepository.getOptionalTracks(playlistId);
            return Response.ok(trackResponse).build();
        } catch (Exception e){
            throw new InternalServerErrorException("Could not fetch tracks");
        }
    }


    @Inject
    public void setTrackRepository(ITrackRepository trackRepository) {
        this.trackRepository = trackRepository;
    }

    @Inject
    public void setLoginRepository(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }
}



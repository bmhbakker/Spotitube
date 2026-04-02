package com.spotitube.api.resource;

import com.spotitube.api.dto.response.TrackResponse;
import com.spotitube.service.TrackService;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Response;

@Path("/tracks")
public class TrackResource {
    private TrackService trackService;

    public TrackResource() {
    } //ONLY NEEDED FOR PROXYING

    @Inject
    public TrackResource(TrackService trackService) {
        this.trackService = trackService;
    }

    @GET
    @Produces("application/json")
    public Response getTracks(@QueryParam("token") String requestToken, @QueryParam("forPlaylist") int playlistId) {
        TrackResponse response = trackService.getOptionalTracks(playlistId, requestToken);
        return Response.ok(response).build();
    }
}



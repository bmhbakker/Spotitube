package com.spotitube.api.resource;

import com.spotitube.api.dto.helper.AuthCandidate;
import com.spotitube.api.dto.request.LoginRequest;
import com.spotitube.api.dto.response.LoginResponse;
import com.spotitube.service.LoginService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

@Path("/login")
public class AuthResource {
    private LoginService loginService;

    public AuthResource() { } //ONLY NEEDED FOR PROXYING

    @Inject public AuthResource(LoginService loginService) {
        this.loginService = loginService;
    }

    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public Response login(LoginRequest request) {
        if (request.user == null || request.password == null) {
            throw new BadRequestException("Username and password must be provided");
        }

        AuthCandidate candidate = loginService.authenticate(request.user, request.password);
        LoginResponse response = new LoginResponse(candidate.getUsername(), candidate.getToken());
        return Response.ok(response).build();
    }
}

package com.spotitube.api.resource;

import com.spotitube.api.dto.helper.AuthCandidate;
import com.spotitube.api.dto.request.LoginRequest;
import com.spotitube.dal.repository.impl.LoginRepository;
import com.spotitube.api.dto.response.LoginResponse;
import com.spotitube.domain.model.User;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

import java.util.UUID;

@Path("/login")
public class AuthResource {
    private LoginRepository loginRepository;

    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public Response login(LoginRequest request) {
        AuthCandidate candidate = loginRepository.authenticate(request.user, request.password);

        if (candidate == null) {
            throw new NotAuthorizedException("Invalid username or password");
        }

        //TODO: ENABLE UUID GENERATION INSTEAD OF HARD SETTING A TOKEN (TEMPORARY FOR POSTMAN TESTING)
//        String token = UUID.randomUUID().toString();
        String token = "token";
        User user = loginRepository.saveTokenForUser(candidate.getId(), token);

        LoginResponse response = new LoginResponse(user.getUsername(), token);
        return Response.ok(response).build();
    }

    @Inject
    public void setLoginRepository(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }
}

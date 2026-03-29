package com.spotitube.shared.exceptionMappers;

import jakarta.ws.rs.NotAuthorizedException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class NotAuthorizedExceptionMapper implements ExceptionMapper<NotAuthorizedException> {
    @Override
    public Response toResponse(NotAuthorizedException ex) {
        return Response.status(Response.Status.UNAUTHORIZED).
                entity("Authorization error occurred - NotAuthorizedException").
                type("text/plain").
                build();
    }
}

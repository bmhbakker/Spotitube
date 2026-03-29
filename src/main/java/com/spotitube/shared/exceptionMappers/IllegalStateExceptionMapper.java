package com.spotitube.shared.exceptionMappers;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class IllegalStateExceptionMapper implements ExceptionMapper<IllegalStateException> {
    @Override
    public Response toResponse(IllegalStateException ex) {
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).
                entity("Illegal state - IllegalStateException").
                type("text/plain").
                build();
    }
}

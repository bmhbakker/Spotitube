package com.spotitube.shared.exceptionMappers;

import com.spotitube.shared.exception.DatabaseException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class DatabaseExceptionMapper implements ExceptionMapper<DatabaseException>{
    @Override
    public Response toResponse(DatabaseException ex) {
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(ex.getMessage())
                .type("text/plain")
                .build();
    }
}


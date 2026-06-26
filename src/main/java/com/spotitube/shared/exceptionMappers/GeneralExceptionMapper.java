package com.spotitube.shared.exceptionMappers;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class GeneralExceptionMapper implements ExceptionMapper<Throwable> {
    public Response toResponse(Throwable ex) {
        ex.printStackTrace();

        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).
                entity(ex.getClass().getSimpleName() + ": " + ex.getMessage()).
                build();
    }
}
package com.spotitube.layers.api;

import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerResponseContext;
import jakarta.ws.rs.container.ContainerResponseFilter;
import jakarta.ws.rs.container.PreMatching;
import jakarta.ws.rs.ext.Provider;

@Provider
@PreMatching
public class CorsContainerResponseFilter implements ContainerResponseFilter {
    @Override
    public void filter(final ContainerRequestContext requestContext, final ContainerResponseContext cres) {
        cres.getHeaders().putSingle("Access-Control-Allow-Origin", "https://hanica-dea.github.io");
        cres.getHeaders().putSingle("Access-Control-Allow-Headers", "origin, content-type, accept, authorization");
        cres.getHeaders().putSingle("Access-Control-Allow-Credentials", "true");
        cres.getHeaders().putSingle("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD");

    }
}
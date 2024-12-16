package org.pweb.infrastructure.security.keycloack;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import java.util.Map;

@RegisterRestClient(configKey = "keycloack-service")
public interface KeycloackClient {

    @POST
    @Path("/token")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    Map<String, Object> getToken(
            @FormParam("grant_type") String grantType,
            @FormParam("client_id") String clientId,
            @FormParam("client_secret") String clientSecret,
            @FormParam("username") String username,
            @FormParam("password") String password
    );


    @POST
    @Path("/token/introspect")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    Map<String, Object> introspectToken(
            @FormParam("token") String token,
            @FormParam("client_id") String clientId,
            @FormParam("client_secret") String clientSecret
    );


}

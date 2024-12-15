package org.pweb.rest;

import io.quarkus.security.Authenticated;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.pweb.infrastructure.security.keycloack.KeycloackClient;

@Slf4j
@Path("/users/v1")
@ApplicationScoped
public class AuthenticationResource implements IAuthenticationResource{

    private final KeycloackClient tokenClient;

    @Inject
    public AuthenticationResource(@RestClient KeycloackClient tokenClient) {
        this.tokenClient = tokenClient;
    }


    @Override
    public Response authenticate(String username, String password) {

        var response = tokenClient.getToken(
                "password",
                "quarkus-app",
                "secret",
                username,
                password
        );

        log.debug("access token: {}", response.get("access_token"));

        return Response.ok(response).build();
    }
}

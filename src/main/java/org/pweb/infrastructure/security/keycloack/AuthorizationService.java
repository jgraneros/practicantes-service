package org.pweb.infrastructure.security.keycloack;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.pweb.domain.Auditoria;
import org.pweb.infrastructure.security.keycloack.interfaces.IAuthorizationService;

import java.util.Map;
import java.util.Optional;

@ApplicationScoped
public class AuthorizationService implements IAuthorizationService {

    private Map<String, Object> tokenInfo;

    private final KeycloackClient keycloackClient;

    @Inject
    public AuthorizationService(@RestClient KeycloackClient keycloackClient) {
        this.keycloackClient = keycloackClient;
    }

    @Override
    public void instrospectToken(String token) {

         this.tokenInfo = keycloackClient.introspectToken(
                token,
                "quarkus-app",
                "secret"
        );
    }

    @Override
    public boolean tokenActivo() {
        return (Boolean) tokenInfo.get("active");
    }

    @Override
    public String obtenerUsuarioActual() {

        return (String) tokenInfo.get("upn");
    }

    @Override
    public <T extends Auditoria> void establecerUsuarioCreador(T entidad) {

        if (entidad != null) {
            entidad.setUsuarioCreador(this.obtenerUsuarioActual());
        }

    }
}

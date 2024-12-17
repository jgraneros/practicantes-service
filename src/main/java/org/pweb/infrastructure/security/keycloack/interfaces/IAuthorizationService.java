package org.pweb.infrastructure.security.keycloack.interfaces;

import org.pweb.domain.Auditoria;

import java.util.Map;

public interface IAuthorizationService {

    void instrospectToken(String token);

    boolean tokenActivo();

    String obtenerUsuarioActual();

    <T extends Auditoria> void establecerUsuarioCreador (T entidad);
}

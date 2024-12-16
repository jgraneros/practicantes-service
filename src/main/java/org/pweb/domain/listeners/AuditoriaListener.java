package org.pweb.domain.listeners;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import org.pweb.domain.Auditoria;
import org.pweb.infrastructure.security.keycloack.interfaces.IAuthorizationService;

import java.time.LocalDateTime;

@ApplicationScoped
public class AuditoriaListener {

    private final IAuthorizationService authorizationService;

    @Inject
    public AuditoriaListener(IAuthorizationService authorizationService){
        this.authorizationService = authorizationService;
    }

    @PrePersist
    protected void prePersist(Auditoria auditoria) {
        System.out.println("pre persist");
        auditoria.setUsuarioCreador(authorizationService.obtenerUsuarioActual());
        auditoria.setFechaCreacion(LocalDateTime.now());
        auditoria.setActivo(true);
    }

    @PreUpdate
    protected void preUpdate(Auditoria auditoria) {
        System.out.println("pre update");
        auditoria.setFechaModificacion(LocalDateTime.now());
        auditoria.setUsuarioModificador(authorizationService.obtenerUsuarioActual());
    }


}

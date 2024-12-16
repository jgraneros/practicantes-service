package org.pweb.domain;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@MappedSuperclass
public abstract class Auditoria extends PanacheEntity {

    @Column(updatable = false)
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaModificacion;

    @Column(updatable = false)
    private String usuarioCreador;
    private String usuarioModificador;
    private boolean activo;

/*    @PrePersist
    protected void prePersist() {
        System.out.println("pre persist");
        this.fechaCreacion = LocalDateTime.now();
        //this.usuarioCreador = obtenerUsuarioActual();
        this.activo = true;
    }

    @PreUpdate
    protected void preUpdate() {
        System.out.println("pre update");
        this.fechaModificacion = LocalDateTime.now();
        // this.usuarioModificador = obtenerUsuarioActual();
    }*/




}

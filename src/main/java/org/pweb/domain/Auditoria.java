package org.pweb.domain;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;

import java.time.LocalDateTime;

@Entity
public class Auditoria extends PanacheEntity {

    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaModificacion;
    private String usuarioCreador;
    private String usuarioModificador;


}

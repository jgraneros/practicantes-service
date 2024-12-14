package org.pweb.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.pweb.domain.enums.EstadoExamen;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
public class Examen extends Auditoria {

    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaExamen;

    @OneToMany
    private List<Practicante> practicantes;
    @Enumerated(EnumType.STRING)
    private EstadoExamen estadoExamen;

    public Examen() {
        this.fechaCreacion = LocalDateTime.now();
        this.practicantes = new ArrayList<>();
        var e = EstadoExamen.PENDIENTE;
        this.asignarEstado(e);
    }

    public void asignarEstado(EstadoExamen estado) {
        this.setEstadoExamen(estado);
    }

    public void asignarFechaDeExamen(LocalDateTime fechaHora) {
        this.setFechaExamen(fechaHora);
    }
}

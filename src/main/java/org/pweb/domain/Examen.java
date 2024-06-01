package org.pweb.domain;

import io.quarkus.hibernate.reactive.panache.PanacheEntity;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
public class Examen extends PanacheEntity {

    private LocalDateTime fecha;
    private LocalDateTime fechaExamen;
    private List<Practicante> practicantes;
    private EstadoExamen estadoExamen;

    public Examen() {
        this.fecha = LocalDateTime.now();
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

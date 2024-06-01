package org.pweb.domain;

import io.quarkus.hibernate.reactive.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
public class Inscripcion extends PanacheEntity {

    private LocalDateTime fecha;

    @Enumerated(EnumType.STRING)
    private EstadoInscripcion estado;
    private Practicante practicante;

    public Inscripcion() {
        this.fecha = LocalDateTime.now();
        var e = EstadoInscripcion.NUEVA;
        this.setEstado(e);
    }


    public void agregarPracticante(Practicante practicante) {
        this.setPracticante(practicante);
    }

    public void ingresarPago(Double cantidad) {
        this.practicante.pagarCuota(cantidad);
        var e = EstadoInscripcion.COMPLETADA;
        this.asignarEstado(e);
    }

    private void asignarEstado(EstadoInscripcion estado) {
        this.setEstado(estado);
    }
}

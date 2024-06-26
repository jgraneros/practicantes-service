package org.pweb.domain;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class Inscripcion extends PanacheEntity {

    private LocalDateTime fecha;

    @Enumerated(EnumType.STRING)
    private EstadoInscripcion estado;
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "practicante_id")
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

package org.pweb.domain;

import io.quarkus.hibernate.reactive.panache.PanacheEntity;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
public class Cuota extends PanacheEntity {

    @ManyToOne
    @JoinColumn(name = "practicante_id")
    private Practicante practicante;
    @Enumerated(EnumType.STRING)
    private EstadoCuota estado;
    private LocalDateTime fecha;
    private Pago pago;

    public Cuota(Double cantidad) {
        this.fecha = LocalDateTime.now();
        this.pago = new Pago(cantidad);
        var e = EstadoCuota.PAGADA;
        this.asignarEstado(e);
    }

    private void asignarEstado(EstadoCuota estado) {
        this.setEstado(estado);
    }
}

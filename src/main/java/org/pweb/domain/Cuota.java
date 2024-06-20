package org.pweb.domain;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class Cuota extends PanacheEntity {

    @Enumerated(EnumType.STRING)
    private EstadoCuota estado;
    private LocalDateTime fecha;
    @ManyToOne(cascade = {CascadeType.PERSIST})
    @JoinColumn
    private Pago pago;

    public Cuota(){}

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

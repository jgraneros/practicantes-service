package org.pweb.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
public class PermisoDeExamen {

    @ManyToOne
    @JoinColumn(name = "practicante_id")
    private Practicante practicante;
    @Enumerated(EnumType.STRING)
    private EstadoPermiso estado;
    private LocalDateTime fecha;
    private Pago pago;

    public PermisoDeExamen(Double cantidad) {
        this.fecha = LocalDateTime.now();
        this.pago = new Pago(cantidad);
        var e = EstadoPermiso.PAGADO;
        this.asignarEstado(e);
    }

    private void asignarEstado(EstadoPermiso e) {
        this.setEstado(e);
    }
}

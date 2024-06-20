package org.pweb.domain;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class PermisoDeExamen extends PanacheEntity {

    @Enumerated(EnumType.STRING)
    private EstadoPermiso estado;
    private LocalDateTime fecha;
    @ManyToOne
    @JoinColumn(name = "pago_id")
    private Pago pago;

    public PermisoDeExamen(){
    }

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

package org.pweb.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.pweb.domain.enums.EstadoPermiso;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class PermisoDeExamen extends Auditoria {

    @Enumerated(EnumType.STRING)
    private EstadoPermiso estado;
    private LocalDateTime fecha;

    @ManyToOne(cascade = {CascadeType.PERSIST})
    @JoinColumn
    private Pago pago;

    @ManyToOne(fetch = FetchType.LAZY)
    private Practicante practicante;

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

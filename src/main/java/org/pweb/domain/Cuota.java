package org.pweb.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.pweb.domain.enums.EstadoCuota;
import org.pweb.domain.enums.Mes;
import org.pweb.domain.validations.Validar;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class Cuota extends Auditoria {

    @Enumerated(EnumType.STRING)
    private EstadoCuota estado;
    private LocalDateTime fecha;
    @ManyToOne(cascade = {CascadeType.PERSIST})
    @JoinColumn
    private Pago pago;

    @ManyToOne(fetch = FetchType.LAZY)
    private Practicante practicante;

    @Enumerated(EnumType.STRING)
    private Mes mes;

    public Cuota(){}

    public Cuota(Double cantidad) {

        Validar.cantidad(cantidad);

        this.fecha = LocalDateTime.now();
        this.pago = new Pago(cantidad);
        this.mes = Mes.obtenerMesPorNumero(fecha.getMonthValue());
        var e = EstadoCuota.PAGADA;
        this.asignarEstado(e);
    }

    public Cuota(Double cantidad, Mes mes) {
        this.fecha = LocalDateTime.now();
        this.pago = new Pago(cantidad);
        this.mes = mes;
        var e = EstadoCuota.PAGADA;
        this.asignarEstado(e);
    }

    private void asignarEstado(EstadoCuota estado) {
        this.setEstado(estado);
    }
}

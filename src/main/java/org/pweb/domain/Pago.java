package org.pweb.domain;

import io.quarkus.hibernate.reactive.panache.PanacheEntity;
import jakarta.persistence.Entity;
import lombok.Data;

@Data
@Entity
public class Pago extends PanacheEntity {

    private Double cantidad;

    public Pago(Double cantidad) {
        this.cantidad = cantidad;
    }
}

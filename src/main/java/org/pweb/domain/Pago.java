package org.pweb.domain;


import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.pweb.domain.validations.Validar;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Pago extends PanacheEntity {

    private Double cantidad;

    public Pago(Double cantidad) {

        Validar.cantidad(cantidad);
        this.cantidad = cantidad;
    }

}

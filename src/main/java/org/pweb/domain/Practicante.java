package org.pweb.domain;


import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Slf4j
@NoArgsConstructor
public class Practicante extends PanacheEntity {

    private String nombre;
    private String apellido;
    private String dni;
    private String telefono;

    @OneToMany(fetch = jakarta.persistence.FetchType.EAGER, cascade = CascadeType.PERSIST)
    private List<Cuota> cuotas;

    @OneToMany(fetch = jakarta.persistence.FetchType.EAGER, cascade = CascadeType.PERSIST)
    private List<PermisoDeExamen> permisos;

    public Practicante(String dni, String nombre, String apellido, String telefono) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.telefono = telefono;
        this.cuotas = new ArrayList<>();
    }

    public void pagarCuota(Double cantidad) {
        var cuota = new Cuota(cantidad);
        this.cuotas.add(cuota);
    }

    public void pagarPermisoDeExamen(Double cantidad) {
        var per = new PermisoDeExamen(cantidad);
        this.permisos.add(per);
    }

    public static Practicante buscarPorDni(String dni) {
        log.debug("Buscando practicante");
        return find("dni = ?1", dni).firstResult();
    }


}

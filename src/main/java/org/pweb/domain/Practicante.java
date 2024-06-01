package org.pweb.domain;

import io.quarkus.hibernate.reactive.panache.PanacheEntity;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Practicante extends PanacheEntity {

    private String nombre;
    private String apellido;
    private String dni;
    private String telefono;

    @OneToMany(mappedBy = "practicante")
    private List<Cuota> cuotas;

    @OneToMany(mappedBy = "practicante")
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
}

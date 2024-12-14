package org.pweb.domain;


import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
@Setter
@Entity
@Slf4j
@NoArgsConstructor
public class Practicante extends Auditoria {

    public static final String LOG_BUSCANDO_PRACTICANTE = "Buscando practicante";
    private String nombre;
    private String apellido;
    private String dni;
    private String telefono;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private List<Cuota> cuotas;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
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
        cuota.setPracticante(this);
        this.cuotas.add(cuota);
    }

    public void pagarCuota(Double cantidad, Mes mes) {
        var cuota = new Cuota(cantidad, mes);
        this.cuotas.add(cuota);
    }

    public void pagarPermisoDeExamen(Double cantidad) {
        var per = new PermisoDeExamen(cantidad);
        this.permisos.add(per);
    }

    public static Practicante buscarPorDni(String dni) {
        log.debug(LOG_BUSCANDO_PRACTICANTE);
        return find("dni = ?1", dni).firstResult();
    }

    public static Optional<Practicante> buscarPorDniOpt(String dni) {
        log.debug(LOG_BUSCANDO_PRACTICANTE);
        return Optional.ofNullable(find("dni = ?1", dni).firstResult());
    }


}

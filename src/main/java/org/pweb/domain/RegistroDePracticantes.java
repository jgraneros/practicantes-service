package org.pweb.domain;

import jakarta.enterprise.context.ApplicationScoped;
import lombok.Data;

@Data
@ApplicationScoped
public class RegistroDePracticantes {

    private Inscripcion inscripcion;
    private Practicante practicante;

    public void iniciarNuevaInscripcion() {
        this.inscripcion = new Inscripcion();
    }

    public void ingresarDatosPersonales(String dni, String nombre, String apellido, String telefono) {
        this.practicante = new Practicante(dni, nombre, apellido, telefono);
        this.inscripcion.agregarPracticante(practicante);
    }

    public void ingresarPago(Double cantidad) {
        this.inscripcion.ingresarPago(cantidad);
    }

}

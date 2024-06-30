package org.pweb.domain;


import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.pweb.domain.exceptions.RegistroDePracticantesException;

@Data
@Slf4j
@ApplicationScoped
public class RegistroDePracticantes {

    private Inscripcion inscripcion;
    private Practicante practicante;
    private final RegistroDeExamenes registroDeExamenes;

    @Inject
    public RegistroDePracticantes(RegistroDeExamenes registroDeExamenes) {
        this.registroDeExamenes = registroDeExamenes;
    }

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


    public void registrarPracticanteAlExamen(String dni) throws RegistroDePracticantesException{

        var practicanteEncontrado  = Practicante.buscarPorDni(dni);

        if (practicanteEncontrado != null) {
            this.registroDeExamenes.asignarPracticanteAlExamen(practicanteEncontrado);
        } else {
            throw new RegistroDePracticantesException("No existe el practicante con dni: " + dni);
        }
    }

}

package org.pweb.domain.interfaces;

import org.pweb.domain.Inscripcion;
import org.pweb.domain.Practicante;
import org.pweb.domain.exceptions.RegistroDePracticantesException;

import java.util.Optional;

public interface IRegistroDePracticantes {

    void iniciarNuevaInscripcion();

    void ingresarDatosPersonales(String dni, String nombre, String apellido, String telefono, String upn);

    void ingresarPago(Double cantidad, String upn);

    void registrarPracticanteAlExamen(String dni) throws RegistroDePracticantesException;

    void pagarCuota(String dni, Double cantidad, String fecha) throws RegistroDePracticantesException;

    void pagarPermisoDeExamen(String dni, Double cantidad, String fecha) throws RegistroDePracticantesException;

    Inscripcion guardarInscripcion();

    Practicante actualizarPracticante();

    Practicante obtenerPracticante();

    Optional<Practicante> buscarPorDni(String dni);


}

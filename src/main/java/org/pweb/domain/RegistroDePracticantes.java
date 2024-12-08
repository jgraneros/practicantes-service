package org.pweb.domain;


import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.pweb.domain.exceptions.RegistroDePracticantesException;
import org.pweb.domain.interfaces.IRegistroDePracticantes;
import org.pweb.utils.FechaUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.Optional;

@Data
@Slf4j
@ApplicationScoped
public class RegistroDePracticantes implements IRegistroDePracticantes {

    private Inscripcion inscripcion;
    private Practicante practicante;
    private final RegistroDeExamenes registroDeExamenes;

    @Inject
    public RegistroDePracticantes(RegistroDeExamenes registroDeExamenes) {
        this.registroDeExamenes = registroDeExamenes;
    }

    @Override
    public void iniciarNuevaInscripcion() {
        this.inscripcion = new Inscripcion();
    }

    @Override
    public void ingresarDatosPersonales(String dni, String nombre, String apellido, String telefono) {
        this.practicante = new Practicante(dni, nombre, apellido, telefono);
        this.inscripcion.agregarPracticante(practicante);
    }

    @Override
    public void ingresarPago(Double cantidad) {
        this.inscripcion.ingresarPago(cantidad);
    }

    @Override
    public void registrarPracticanteAlExamen(String dni) throws RegistroDePracticantesException{

        var practicanteEncontrado  = Practicante.buscarPorDni(dni);

        if (practicanteEncontrado != null) {
            this.registroDeExamenes.asignarPracticanteAlExamen(practicanteEncontrado);
        } else {
            throw new RegistroDePracticantesException("No existe el practicante con dni: " + dni);
        }
    }

    @Override
    public void pagarCuota(String dni, Double cantidad, String fecha) throws RegistroDePracticantesException {

        practicante  = Practicante.buscarPorDni(dni);

        LocalDateTime fechaFormateada;

        try {
            fechaFormateada = FechaUtils.formatearFecha(fecha);
        } catch (DateTimeParseException dateTimeParseException) {
            var error = FechaUtils.crearMensajeDeError(dateTimeParseException);
            throw new RegistroDePracticantesException(error.toString());
        }


        if (practicante != null) {

            log.info("fecha formateada");
            var mesEnum = Mes.obtenerMesPorNumero(fechaFormateada.getMonthValue());

            var cuotas = practicante.getCuotas();

            for (var cuota : cuotas) {

                var year = cuota.getFecha().getYear();
                var month = cuota.getFecha().getMonthValue();

                if (year == fechaFormateada.getYear() && month == fechaFormateada.getMonthValue()) {
                    StringBuilder errorMessage = new StringBuilder("Ya existe una cuota para la fecha indicada: ")
                            .append(fechaFormateada);
                    throw new RegistroDePracticantesException(errorMessage.toString());
                }

            }

            practicante.pagarCuota(cantidad, mesEnum);
            practicante.persist();

        } else {
            throw new RegistroDePracticantesException("No existe el practicante con dni: " + dni);
        }
    }

    @Override
    public void pagarPermisoDeExamen(String dni, Double cantidad, String fecha) throws RegistroDePracticantesException {

        practicante  = Practicante.buscarPorDni(dni);

        LocalDateTime fechaFormateada;

        try {
            fechaFormateada = FechaUtils.formatearFecha(fecha);
        } catch (DateTimeParseException dateTimeParseException) {
            var error = FechaUtils.crearMensajeDeError(dateTimeParseException);
            throw new RegistroDePracticantesException(error.toString());
        }


        if (practicante != null) {

            log.info("fecha formateada");

            var permisos = practicante.getPermisos();

            for (var permisoDeExamen : permisos) {

                var year = permisoDeExamen.getFecha().getYear();
                var month = permisoDeExamen.getFecha().getMonthValue();

                if (year == fechaFormateada.getYear() && month == fechaFormateada.getMonthValue()) {
                    StringBuilder errorMessage = new StringBuilder("Ya existe un permiso para la fecha indicada: ")
                            .append(fechaFormateada);
                    throw new RegistroDePracticantesException(errorMessage.toString());
                }

            }

            practicante.pagarPermisoDeExamen(cantidad);
            practicante.persist();

        } else {
            throw new RegistroDePracticantesException("No existe el practicante con dni: " + dni);
        }
    }

    @Override
    public Inscripcion guardarInscripcion() {
        this.inscripcion.persist();
        return getInscripcion();
    }

    @Override
    public Practicante actualizarPracticante() {
        this.practicante.persist();
        return getPracticante();
    }

    @Override
    public Practicante obtenerPracticante() {
        return this.getPracticante();
    }

    @Override
    public Optional<Practicante> buscarPorDni(String dni) {
        return Optional.ofNullable(Practicante.buscarPorDni(dni));
    }
}

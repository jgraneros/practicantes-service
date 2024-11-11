package org.pweb.domain;


import jakarta.enterprise.context.ApplicationScoped;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.pweb.domain.exceptions.RegistroDeExamenException;
import org.pweb.domain.interfaces.IRegistroDeExamenes;
import org.pweb.utils.FechaUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

@Slf4j
@Data
@ApplicationScoped
public class RegistroDeExamenes implements IRegistroDeExamenes {

    private Examen examen;

    @Override
    public void iniciarRegistroDeExamen() {
        this.examen = new Examen();
    }

    @Override
    public void asignarFechaDeExamen(String fechaHora) throws RegistroDeExamenException {

        LocalDateTime fechaFormateada;

        try{
            fechaFormateada = FechaUtils.formatearFecha(fechaHora);
        } catch (DateTimeParseException dateTimeParseException) {
            StringBuilder errorMessage = FechaUtils.crearMensajeDeError(dateTimeParseException);
            throw new RegistroDeExamenException(errorMessage.toString());
        }

        this.examen.asignarFechaDeExamen(fechaFormateada);
    }

    @Override
    public void asignarPracticanteAlExamen(Practicante practicante) {
        log.debug("Asignando practicante al examen: {}", practicante);
        this.examen.getPracticantes().add(practicante);
    }

    @Override
    public Examen guardarExamen() {
        this.examen.persist();
        return getExamen();
    }
}

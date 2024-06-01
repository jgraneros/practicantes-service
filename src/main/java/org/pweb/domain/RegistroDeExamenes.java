package org.pweb.domain;

import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;
import org.pweb.domain.exceptions.RegistroDeExamenException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Slf4j
@ApplicationScoped
public class RegistroDeExamenes {

    public static final String ERROR_AL_INTENTAR_FORMATEAR_LA_FECHA = "Error al intentar formatear la fecha: ";
    private Examen examen;

    public void iniciarRegistroDeExamen() {
        this.examen = new Examen();
    }

    public void asignarFechaDeExamen(String fechaHora) throws RegistroDeExamenException {

        LocalDateTime fechaFormateada;

        try{
            fechaFormateada = this.validarYformatearFecha(fechaHora);
        } catch (DateTimeParseException dateTimeParseException) {
            StringBuilder errorMessage = new StringBuilder();
            errorMessage.append(ERROR_AL_INTENTAR_FORMATEAR_LA_FECHA)
                            .append(dateTimeParseException.getMessage());

            log.error(errorMessage.toString());
            throw new RegistroDeExamenException(errorMessage.toString());
        }

        this.examen.asignarFechaDeExamen(fechaFormateada);
    }

    private LocalDateTime validarYformatearFecha(String fecha) throws DateTimeParseException {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return LocalDateTime.parse(fecha, formatter);
    }
}

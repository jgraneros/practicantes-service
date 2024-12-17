package org.pweb.domain.utils;

import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Slf4j
public class FechaUtils {
    public static final String ERROR_AL_INTENTAR_FORMATEAR_LA_FECHA = "Error al intentar formatear la fecha: ";

    public static LocalDateTime formatearFecha(String fecha) throws DateTimeParseException {

        log.info("FECHA: {}", fecha);
        log.info("FECHA now: {}", LocalDateTime.now());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return LocalDateTime.parse(fecha, formatter);
    }

    public static StringBuilder crearMensajeDeError(DateTimeParseException dateTimeParseException) {
        StringBuilder errorMessage = new StringBuilder();
        errorMessage.append(ERROR_AL_INTENTAR_FORMATEAR_LA_FECHA)
                        .append(dateTimeParseException.getMessage());

        log.error(errorMessage.toString());
        return errorMessage;
    }
}

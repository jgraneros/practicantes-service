package org.pweb.service;

import org.pweb.domain.Inscripcion;
import org.pweb.rest.dto.PagoDTO;
import org.pweb.rest.dto.ExamenDTO;
import org.pweb.rest.dto.PracticanteDTO;

import java.util.Map;

public interface IPracticanteService {

    Inscripcion inscribirPracticante(PracticanteDTO dto);

    Map<String, Object> gestionarExamen(ExamenDTO examenDTO);

    Map<String, Object> gestionarCuota(PagoDTO dto);

    Map<String, Object> buscarPorDni(String dni);

    Map<String, Object> gestionarPermisoDeExamen(PagoDTO dto);
}

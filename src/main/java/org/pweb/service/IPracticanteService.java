package org.pweb.service;

import jakarta.transaction.Transactional;
import org.pweb.domain.Inscripcion;
import org.pweb.dto.PagoDTO;
import org.pweb.dto.ExamenDTO;
import org.pweb.dto.PracticanteDTO;

import java.util.Map;

public interface IPracticanteService {

    @Transactional
    Inscripcion inscribirPracticante(PracticanteDTO dto);

    @Transactional
    Map<String, Object> gestionarExamen(ExamenDTO examenDTO);

    @Transactional
    Map<String, Object> gestionarCuota(PagoDTO dto);

    Map<String, Object> buscarPorDni(String dni);

    @Transactional
    Map<String, Object> gestionarPermisoDeExamen(PagoDTO dto);
}

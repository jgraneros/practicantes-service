package org.pweb.service;

import jakarta.transaction.Transactional;
import org.pweb.domain.Inscripcion;
import org.pweb.dto.CuotaDTO;
import org.pweb.dto.ExamenDTO;
import org.pweb.dto.PracticanteDTO;

import java.util.Map;
import java.util.Optional;

public interface IPracticanteService {

    @Transactional
    Inscripcion inscribirPracticante(PracticanteDTO dto);

    @Transactional
    Map<String, Object> gestionarExamen(ExamenDTO examenDTO);

    @Transactional
    Map<String, Object> gestionarCuota(CuotaDTO cuotaDTO);

    Map<String, Object> buscarPorDni(String dni);
}

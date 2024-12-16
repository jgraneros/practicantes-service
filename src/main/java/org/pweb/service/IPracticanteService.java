package org.pweb.service;

import org.pweb.domain.Inscripcion;

import java.util.List;
import java.util.Map;

public interface IPracticanteService {

    Inscripcion inscribirPracticante(String nombre, String apellido, String telefono, String dni, Double pago, String upn);

    Map<String, Object> gestionarExamen(String fecha, List<String> dniList);

    Map<String, Object> gestionarCuota(String dni, Double cantidad, String mes);

    Map<String, Object> buscarPorDni(String dni);

    Map<String, Object> gestionarPermisoDeExamen(String dni, Double cantidad, String fecha);
}

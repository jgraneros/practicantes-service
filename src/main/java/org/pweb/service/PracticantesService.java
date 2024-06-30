package org.pweb.service;


import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.pweb.domain.*;
import org.pweb.domain.exceptions.RegistroDeExamenException;
import org.pweb.domain.exceptions.RegistroDePracticantesException;
import org.pweb.dto.CuotaDTO;
import org.pweb.dto.ExamenDTO;
import org.pweb.dto.PracticanteDTO;



import java.util.HashMap;
import java.util.Map;


import static org.pweb.domain.exceptions.ExceptionConstants.*;

@Slf4j
@ApplicationScoped
public class PracticantesService {

    public static final String ENTITY = "ENTITY";
    private final RegistroDePracticantes registroDePracticantes;
    private final RegistroDeExamenes registroDeExamenes;

    @Inject
    public PracticantesService(RegistroDePracticantes registro, RegistroDeExamenes registroDeExamenes) {
        this.registroDePracticantes = registro;
        this.registroDeExamenes = registroDeExamenes;
    }


    @Transactional
    public Inscripcion inscribirPracticante(PracticanteDTO dto) {

        var nombre = dto.getNombre();
        var apellido = dto.getApellido();
        var telefono = dto.getTelefono();
        var dni = dto.getDni();
        var pago = dto.getPago();

        registroDePracticantes.iniciarNuevaInscripcion();
        registroDePracticantes.ingresarDatosPersonales(dni, nombre, apellido, telefono);
        registroDePracticantes.ingresarPago(pago);

        var inscripcion = registroDePracticantes.getInscripcion();

        inscripcion.persist();

        return inscripcion;


    }


    @Transactional
    public Map<String, Object> gestionarExamen(ExamenDTO examenDTO) {

        var fechaExamen = examenDTO.getFecha();
        var practicantes = examenDTO.getDniList();
        Map<String, Object> serviceResponse = new HashMap<>();

        registroDeExamenes.iniciarRegistroDeExamen();

        try {

            registroDeExamenes.asignarFechaDeExamen(fechaExamen);

            for (String dni : practicantes) {
                registroDePracticantes.registrarPracticanteAlExamen(dni);
            }


        } catch (RegistroDePracticantesException | RegistroDeExamenException exception) {
            serviceResponse.put(SUCCESS, Boolean.FALSE);
            serviceResponse.put(CAUSA, exception.getMessage());
            return serviceResponse;
        }

        var examen = registroDeExamenes.getExamen();

        examen.persist();

        serviceResponse.put(SUCCESS, Boolean.TRUE);
        serviceResponse.put(ENTITY, examen);

        return serviceResponse;

    }



    @Transactional
    public Map<String, Object> abonarCuota(CuotaDTO cuotaDTO) {

        var dni = cuotaDTO.getDni();
        var cantidadEntregada = cuotaDTO.getCantidadEntregada();
        var mes = cuotaDTO.getMes();

        Map<String, Object> serviceResponse = new HashMap<>();

        try {
            this.registroDePracticantes.pagarCuota(dni, cantidadEntregada, mes);
        } catch (RegistroDePracticantesException e) {
            serviceResponse.put(SUCCESS, Boolean.FALSE);
            serviceResponse.put(CAUSA, e.getMessage());
            return serviceResponse;
        }

        var practicante = registroDePracticantes.getPracticante();
        practicante.persist();

        serviceResponse.put(SUCCESS, Boolean.TRUE);
        serviceResponse.put(ENTITY, practicante);
        return serviceResponse;

    }

}

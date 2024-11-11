package org.pweb.service;


import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.pweb.domain.*;
import org.pweb.domain.exceptions.RegistroDeExamenException;
import org.pweb.domain.exceptions.RegistroDePracticantesException;
import org.pweb.domain.interfaces.IRegistroDeExamenes;
import org.pweb.domain.interfaces.IRegistroDePracticantes;
import org.pweb.dto.CuotaDTO;
import org.pweb.dto.ExamenDTO;
import org.pweb.dto.PracticanteDTO;



import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


import static org.pweb.domain.exceptions.ExceptionConstants.*;

@Slf4j
@ApplicationScoped
public class PracticantesService implements IPracticanteService{

    public static final String ENTITY = "ENTITY";
    private final IRegistroDePracticantes registroDePracticantes;
    private final IRegistroDeExamenes registroDeExamenes;

    @Inject
    public PracticantesService(IRegistroDePracticantes registro, IRegistroDeExamenes registroDeExamenes) {
        this.registroDePracticantes = registro;
        this.registroDeExamenes = registroDeExamenes;
    }


    @Override
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

        return registroDePracticantes.guardarInscripcion();


    }

    @Override
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

        var examen = registroDeExamenes.guardarExamen();

        serviceResponse.put(SUCCESS, Boolean.TRUE);
        serviceResponse.put(ENTITY, examen);

        return serviceResponse;

    }


    @Override
    @Transactional
    public Map<String, Object> gestionarCuota(CuotaDTO cuotaDTO) {

        var dni = cuotaDTO.getDni();
        var cantidadEntregada = cuotaDTO.getCantidadEntregada();
        var mes = cuotaDTO.getFecha();

        Map<String, Object> serviceResponse = new HashMap<>();

        try {
            this.registroDePracticantes.pagarCuota(dni, cantidadEntregada, mes);
        } catch (RegistroDePracticantesException e) {
            serviceResponse.put(SUCCESS, Boolean.FALSE);
            serviceResponse.put(CAUSA, e.getMessage());
            return serviceResponse;
        }

        var practicante = registroDePracticantes.actualizarPracticante();

        serviceResponse.put(SUCCESS, Boolean.TRUE);
        serviceResponse.put(ENTITY, practicante);
        return serviceResponse;

    }

    @Override
    public Map<String, Object> buscarPorDni(String dni) {

        var optional = registroDePracticantes.buscarPorDni(dni);
        Map<String, Object> serviceResponse = new HashMap<>();

        if (optional.isPresent()) {
            var practicante = optional.get();

            var body = new JsonObject();
            var array = new JsonArray();
            var cuotasOptional = Optional.ofNullable(practicante.getCuotas());

            if (cuotasOptional.isPresent()) {

                for (var cuota : practicante.getCuotas()) {
                    array.add(new JsonObject()
                            .put("fecha", cuota.getFecha())
                            .put("estado", cuota.getEstado())
                            .put("mes", cuota.getMes()));
                }
                
                body.put("nombre", practicante.getNombre())
                    .put("apellido", practicante.getApellido());
                body.put("cuotas", array);
            }

            serviceResponse.put(SUCCESS, Boolean.TRUE);
            serviceResponse.put(ENTITY, body);
            return serviceResponse;

        } else {
            serviceResponse.put(SUCCESS, Boolean.FALSE);
            serviceResponse.put(CAUSA, "No hay datos disponibles");
            return serviceResponse;
        }

    }

}

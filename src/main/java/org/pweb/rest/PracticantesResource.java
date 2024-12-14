package org.pweb.rest;

import io.vertx.core.json.JsonObject;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.pweb.domain.Examen;
import org.pweb.rest.dto.PagoDTO;
import org.pweb.rest.dto.ExamenDTO;
import org.pweb.rest.dto.PracticanteDTO;
import org.pweb.service.IPracticanteService;

import static org.pweb.domain.exceptions.ExceptionConstants.*;

@Slf4j
@Path("/practicantes/v1/")
@ApplicationScoped
public class PracticantesResource implements IPracticantesResource{

    private final IPracticanteService service;


    @Inject
    public PracticantesResource(IPracticanteService service) {
        this.service = service;
    }


    @Override
    public Response login(String username, String password) {



        return null;
    }

    @Override
    public Response buscarPorDni(@QueryParam("dni") String dni) {

        var serviceResponse = service.buscarPorDni(dni);
        var success = (Boolean) serviceResponse.get(SUCCESS);

        if (success.booleanValue()) {
            var practicante = (JsonObject) serviceResponse.get("ENTITY");
            return Response.ok(practicante).build();
        } else {

            var causa = (String) serviceResponse.get(CAUSA);
            return Response.status(400).entity(new JsonObject().put(ERROR, causa)).build();
        }


    }


    @Override
    public Response inscribirPracticante(@RequestBody PracticanteDTO dto, @Context UriInfo uriInfo) {

        log.info("inscripcion de practicante: {}", dto);

        var nombre = dto.getNombre();
        var apellido = dto.getApellido();
        var telefono = dto.getTelefono();
        var dni = dto.getDni();
        var pago = dto.getPago();

        var inscripcion = service.inscribirPracticante(nombre, apellido, telefono, dni, pago);
        UriBuilder builder = uriInfo.getAbsolutePathBuilder().path(String.valueOf(inscripcion.id));
        return Response.created(builder.build()).build();

    }

    @Override
    public Response actualizarPracticante(@RequestBody PracticanteDTO practicante, @QueryParam("dni") String dni) {
        return null;
    }


    @Override
    public Response gestionarExamen(@RequestBody ExamenDTO dto, @Context UriInfo uriInfo) {

        log.info("Creacion del examen");

        var fechaExamen = dto.getFecha();
        var dniList = dto.getDniList();

        var serviceResponse = service.gestionarExamen(fechaExamen, dniList);
        var success = (Boolean) serviceResponse.get(SUCCESS);

        if (success.booleanValue()) {
            var entity = (Examen) serviceResponse.get("ENTITY");
            UriBuilder builder = uriInfo.getAbsolutePathBuilder().path(String.valueOf(entity.id));
            return Response.created(builder.build()).build();
        } else {

            var causa = (String) serviceResponse.get(CAUSA);
            return Response.status(400).entity(new JsonObject().put(ERROR, causa)).build();
        }

    }


    @Override
    public Response pagarCuota(@RequestBody PagoDTO dto) {

        log.info("Pago de cuotas");

        var dni = dto.getDni();
        var cantidadEntregada = dto.getCantidadEntregada();
        var mes = dto.getFecha();

        var serviceResponse = service.gestionarCuota(dni, cantidadEntregada, mes);
        var success = (Boolean) serviceResponse.get(SUCCESS);

        if (success.booleanValue()) {
            return Response.accepted().build();
        } else {

            var causa = (String) serviceResponse.get(CAUSA);
            return Response.status(400).entity(new JsonObject().put(ERROR, causa)).build();
        }

    }

    @Override
    public Response pagarPermisoDeExamen(PagoDTO dto) {

        log.info("Pago de cuotas");

        var dni = dto.getDni();
        var cantidadEntregada = dto.getCantidadEntregada();
        var fecha = dto.getFecha();

        var serviceResponse = service.gestionarPermisoDeExamen(dni, cantidadEntregada, fecha);
        var success = (Boolean) serviceResponse.get(SUCCESS);

        if (success.booleanValue()) {
            return Response.accepted().build();
        } else {

            var causa = (String) serviceResponse.get(CAUSA);
            return Response.status(400).entity(new JsonObject().put(ERROR, causa)).build();
        }
    }


}

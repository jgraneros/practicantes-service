package org.pweb.rest;

import io.quarkus.security.Authenticated;
import io.vertx.core.json.JsonObject;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.pweb.domain.Examen;
import org.pweb.infrastructure.security.keycloack.interfaces.IAuthorizationService;
import org.pweb.rest.dto.PagoDTO;
import org.pweb.rest.dto.ExamenDTO;
import org.pweb.rest.dto.PracticanteDTO;
import org.pweb.service.IPracticanteService;

import static org.pweb.domain.exceptions.ExceptionConstants.*;

@Slf4j
@Path("/practicantes/v1/")
@Authenticated
@ApplicationScoped
public class PracticantesResource implements IPracticantesResource{

    private final IPracticanteService service;
    private final IAuthorizationService authorizationService;


    @Inject
    public PracticantesResource(IPracticanteService service, IAuthorizationService authorizationService) {
        this.service = service;
        this.authorizationService = authorizationService;
    }


    //@RolesAllowed("instructor")
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


    @RolesAllowed("instructor")
    @Override
    public Response inscribirPracticante(@RequestBody PracticanteDTO dto, @Context UriInfo uriInfo, @Context HttpHeaders httpHeaders) {

        String authorizationHeader = httpHeaders.getHeaderString("Authorization");

        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity("Token de autorización faltante o inválido.")
                    .build();
        }

        String token = authorizationHeader.substring("Bearer ".length());

        log.debug("Token recibido: " + token);

        authorizationService.instrospectToken(token);

        var active = authorizationService.tokenActivo();

        if (!active) {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity("Token invalido.")
                    .build();
        }

        var upn = authorizationService.obtenerUsuarioActual();

        log.info("usuario upn: {}", upn);

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
    public Response actualizarPracticante(@RequestBody PracticanteDTO dto, @QueryParam("dni") String dni) {

        log.info("actualizacion de practicante");

        var nombre = dto.getNombre();
        var apellido = dto.getApellido();
        var telefono = dto.getTelefono();

        var serviceResponse = service.actualizarPracticante(nombre, apellido, telefono, dni);
        var success = (Boolean) serviceResponse.get(SUCCESS);

        if (success.booleanValue()) {
            var entity = (JsonObject) serviceResponse.get("ENTITY");
            return Response.accepted(entity).build();
        } else {
            var causa = (String) serviceResponse.get(CAUSA);
            return Response.status(400).entity(new JsonObject().put(ERROR, causa)).build();
        }

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

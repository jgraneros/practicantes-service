package org.pweb.rest;

import io.vertx.core.json.JsonObject;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.pweb.domain.Examen;
import org.pweb.domain.Practicante;
import org.pweb.dto.CuotaDTO;
import org.pweb.dto.ExamenDTO;
import org.pweb.dto.PracticanteDTO;
import org.pweb.service.PracticantesService;
import static org.pweb.domain.exceptions.ExceptionConstants.*;

@Slf4j
@Path("/practicantes/v1/")
@ApplicationScoped
public class PracticantesResource {

    private final PracticantesService service;

    @Inject
    public PracticantesResource(PracticantesService service) {
        this.service = service;
    }

    @GET
    public Response getALl() {

        var list = Practicante.listAll();
        return Response.ok(list).build();
    }

    @POST
    @Path("inscripcion")
    @Operation(summary = "Permite inscribir a un nuevo practicante",
    description = "Los datos necesarios para inscribir a un nuevo practicante son: nombre, apellido, telefono, " +
            "dni y ademas se debe abonar el primer pago.")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response inscribirPracticante(@RequestBody PracticanteDTO practicante, @Context UriInfo uriInfo) {

        log.info("inscripcion de practicante: {}", practicante);

        var inscripcion = service.inscribirPracticante(practicante);
        UriBuilder builder = uriInfo.getAbsolutePathBuilder().path(String.valueOf(inscripcion.id));
        return Response.created(builder.build()).build();

    }

    @POST
    @Path("examen")
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(summary = "Permite crear un nuevo examen indicando la fecha y un listado de dni correspondientes a los" +
            " practicantes")
    public Response gestionarExamen(@RequestBody ExamenDTO examen, @Context UriInfo uriInfo) {

        log.info("Creacion del examen");

        var serviceResponse = service.gestionarExamen(examen);
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

    @PUT
    @Path("cuotas")
    @Consumes(MediaType.APPLICATION_JSON)

    public Response pagarCuota(@RequestBody CuotaDTO cuotaDTO) {

        log.info("Pago de cuotas");
        var serviceResponse = service.abonarCuota(cuotaDTO);
        var success = (Boolean) serviceResponse.get(SUCCESS);

        if (success.booleanValue()) {
            var entity = (Practicante) serviceResponse.get("ENTITY");
            return Response.accepted(entity).build();
        } else {

            var causa = (String) serviceResponse.get(CAUSA);
            return Response.status(400).entity(new JsonObject().put(ERROR, causa)).build();
        }

    }


}

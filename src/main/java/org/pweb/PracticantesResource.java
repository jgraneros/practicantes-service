package org.pweb;

import io.vertx.core.json.JsonObject;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.*;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.pweb.domain.Examen;
import org.pweb.domain.Practicante;
import org.pweb.dto.ExamenDTO;
import org.pweb.dto.PracticanteDTO;
import org.pweb.service.PracticantesService;
import static org.pweb.domain.exceptions.ExceptionConstants.*;

@Slf4j
@Path("/practicantes")
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
    public Response crearExamen(@RequestBody ExamenDTO examen, @Context UriInfo uriInfo) {

        log.info("Creacion del examen");

        var serviceResponse = service.crearExamen(examen);
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

}

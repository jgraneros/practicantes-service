package org.pweb.rest;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.pweb.dto.CuotaDTO;
import org.pweb.dto.ExamenDTO;
import org.pweb.dto.PracticanteDTO;

public interface IPracticantesResource {
    @GET
    Response buscarPorDni(@QueryParam("dni") String dni);

    @POST
    @Path("inscripcion")
    @Operation(summary = "Permite inscribir a un nuevo practicante",
    description = "Los datos necesarios para inscribir a un nuevo practicante son: nombre, apellido, telefono, " +
            "dni y ademas se debe abonar el primer pago.")
    @Consumes(MediaType.APPLICATION_JSON)
    Response inscribirPracticante(@RequestBody PracticanteDTO practicante, @Context UriInfo uriInfo);

    @POST
    @Path("examen")
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(summary = "Permite crear un nuevo examen indicando la fecha y un listado de dni correspondientes a los" +
            " practicantes")
    Response gestionarExamen(@RequestBody ExamenDTO examen, @Context UriInfo uriInfo);

    @PUT
    @Path("cuotas")
    @Consumes(MediaType.APPLICATION_JSON)
    Response pagarCuota(@RequestBody CuotaDTO cuotaDTO);
}

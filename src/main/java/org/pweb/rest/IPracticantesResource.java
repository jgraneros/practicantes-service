package org.pweb.rest;

import io.quarkus.security.Authenticated;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.pweb.rest.dto.PagoDTO;
import org.pweb.rest.dto.ExamenDTO;
import org.pweb.rest.dto.PracticanteDTO;

public interface IPracticantesResource {


    @GET
    Response buscarPorDni(@QueryParam("dni") String dni);

    @POST
    @Path("practicante/inscripcion")
    @Operation(summary = "Permite inscribir a un nuevo practicante",
    description = "Los datos necesarios para inscribir a un nuevo practicante son: nombre, apellido, telefono, " +
            "dni y ademas se debe abonar el primer pago.")
    @Consumes(MediaType.APPLICATION_JSON)
    Response inscribirPracticante(@RequestBody PracticanteDTO dto, @Context UriInfo uriInfo);

    @PUT
    @Path("practicante")
    @Operation(summary = "Permite modificar los datos personales del practicante")
    @Consumes(MediaType.APPLICATION_JSON)
    Response actualizarPracticante(@RequestBody PracticanteDTO practicante, @ QueryParam("dni") String dni);

    @POST
    @Path("examen")
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(summary = "Permite crear un nuevo examen indicando la fecha y un listado de dni correspondientes a los" +
            " practicantes")
    Response gestionarExamen(@RequestBody ExamenDTO dto, @Context UriInfo uriInfo);

    @PUT
    @Path("cuotas")
    @Consumes(MediaType.APPLICATION_JSON)
    Response pagarCuota(@RequestBody PagoDTO dto);

    @PUT
    @Path("permiso")
    @Consumes(MediaType.APPLICATION_JSON)
    Response pagarPermisoDeExamen(@RequestBody PagoDTO cuotaDTO);
}

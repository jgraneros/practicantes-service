package org.pweb.service;

import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.pweb.dto.PracticanteDTO;
import org.pweb.domain.RegistroDePracticantes;
import io.vertx.core.json.JsonObject;

@Slf4j
@ApplicationScoped
public class PracticantesService {

    public static final String SUCCESS = "SUCCESS";
    private final RegistroDePracticantes registro;

    @Inject
    public PracticantesService(RegistroDePracticantes registro) {
        this.registro = registro;
    }

    public Uni<JsonObject> inscribirPracticante(PracticanteDTO dto) {

        var nombre = dto.getNombre();
        var apellido = dto.getApellido();
        var telefono = dto.getTelefono();
        var dni = dto.getDni();
        var pago = dto.getPago();

        registro.iniciarNuevaInscripcion();
        registro.ingresarDatosPersonales(dni, nombre, apellido, telefono);
        registro.ingresarPago(pago);

        var inscripcion = registro.getInscripcion();

        return inscripcion.persist()
                .map(result -> new JsonObject().put(SUCCESS, true))
                .onFailure()
                .invoke(f -> log.error("Error: {}", f.getMessage()))
                .onFailure()
                .recoverWithItem(new JsonObject().put(SUCCESS, false));

    }

}

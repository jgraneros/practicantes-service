package org.pweb.domain;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.pweb.domain.exceptions.RegistroDeExamenException;
import org.pweb.domain.interfaces.IRegistroDeExamenes;

import java.time.LocalDateTime;

@QuarkusTest
public class RegistroDeExamenesTest {

    private final IRegistroDeExamenes registroDeExamenes;

    @Inject
    public RegistroDeExamenesTest(IRegistroDeExamenes registroDePracticantes) {
        this.registroDeExamenes = registroDePracticantes;
    }


    @Test
    @Transactional
    public void testRegistroExamenes() throws RegistroDeExamenException {

        Practicante practicante = new Practicante();
        practicante.setNombre("Juan");
        practicante.setApellido("Graneros");
        practicante.setDni("35807442");
        practicante.setTelefono("4811054");

        registroDeExamenes.iniciarRegistroDeExamen();
        registroDeExamenes.asignarFechaDeExamen("2023-11-02 11:00");
        registroDeExamenes.asignarPracticanteAlExamen(practicante);
        var examenGuardado = registroDeExamenes.guardarExamen();

        Assertions.assertNotNull(examenGuardado.id, "El ID del examen no debería ser nulo después de persistir");

    }

}

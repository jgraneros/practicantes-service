package org.pweb.domain.interfaces;

import org.pweb.domain.Examen;
import org.pweb.domain.Practicante;
import org.pweb.domain.exceptions.RegistroDeExamenException;

public interface IRegistroDeExamenes {

    void iniciarRegistroDeExamen();

    void asignarFechaDeExamen(String fechaHora) throws RegistroDeExamenException;

    void asignarPracticanteAlExamen(Practicante practicante);

    Examen guardarExamen();
}

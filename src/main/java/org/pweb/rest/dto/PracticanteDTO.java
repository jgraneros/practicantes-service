package org.pweb.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PracticanteDTO {

    private String nombre;
    private String apellido;
    private String telefono;
    private String dni;
    private Double pago;
}

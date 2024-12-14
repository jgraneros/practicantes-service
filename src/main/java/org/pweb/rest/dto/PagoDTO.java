package org.pweb.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PagoDTO {

    private Double cantidadEntregada;
    private String fecha;
    private String dni;

}

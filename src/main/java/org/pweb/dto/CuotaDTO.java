package org.pweb.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CuotaDTO {

    private Double cantidadEntregada;
    private String fecha;
    private String dni;

}

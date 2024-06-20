package org.pweb.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PracticanteResponseDTO {

    private String nombre;
    private String apellido;
    private String telefono;
    private String dni;

    private List<CuotaDTO> cuotas;

}

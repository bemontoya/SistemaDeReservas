package com.SistemaDeReservas.msclientes.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClienteResponseDTO {

    private Long idCliente;
    private String nombre;
    private String apellido;
    private String email;
    private String telefono;
    private Boolean activo;
}

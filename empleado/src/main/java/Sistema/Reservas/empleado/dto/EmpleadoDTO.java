package Sistema.Reservas.empleado.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class EmpleadoDTO {

    private Long id;

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @NotBlank(message = "El apellido es obligatorio")
    private String apellido;

    @NotBlank(message = "El cargo es obligatorio (Ej: Garzón, Cocinero, Administrador)")
    private String cargo;

    @Email(message = "El formato del email es inválido")
    @NotBlank(message = "El email es obligatorio")
    private String email;
}
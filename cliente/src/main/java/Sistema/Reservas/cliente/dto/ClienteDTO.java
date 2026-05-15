package Sistema.Reservas.cliente.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data // Genera getters y setters automáticamente con Lombok
public class ClienteDTO {

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @NotBlank(message = "El apellido es obligatorio")
    private String apellido;

    @Email(message = "El formato del email es inválido")
    @NotBlank(message = "El email es obligatorio")
    private String email;

    @NotBlank(message = "El teléfono es obligatorio")
    @Pattern(regexp = "^\\+569[0-9]{8}$", message = "El teléfono debe tener el formato chileno: +569XXXXXXXX")
    private String telefono;
}
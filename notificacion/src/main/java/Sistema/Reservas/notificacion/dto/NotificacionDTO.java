package Sistema.Reservas.notificacion.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class NotificacionDTO {

    @NotBlank(message = "El destinatario es obligatorio (Email o identificador)")
    private String destinatario;

    @NotBlank(message = "El mensaje no puede estar vacío")
    @Size(max = 500, message = "El mensaje no puede superar los 500 caracteres")
    private String mensaje;

    @NotBlank(message = "El tipo de notificación es obligatorio (EJ: PEDIDO, PAGO, SISTEMA)")
    private String tipo;
}
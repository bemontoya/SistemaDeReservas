package Sistema.Reservas.pedido.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class PedidoDTO {

    @NotNull(message = "El ID del cliente es obligatorio")
    private Long clienteId;

    @NotNull(message = "El ID de la reserva es obligatorio")
    private Long reservaId;

    @NotBlank(message = "El detalle del pedido es obligatorio (Ej: 1x Pizza, 2x Bebidas)")
    @Size(max = 255, message = "El detalle no puede superar los 255 caracteres")
    private String detalle;
}
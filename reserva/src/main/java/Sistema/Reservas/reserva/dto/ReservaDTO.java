package Sistema.Reservas.reserva.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ReservaDTO {

    @NotNull(message = "El ID del cliente es obligatorio")
    private Long clienteId;

    @NotNull(message = "El ID de la mesa es obligatorio")
    private Long mesaId;

    @NotNull(message = "La fecha y hora de la reserva es obligatoria")
    @Future(message = "La fecha de la reserva debe ser en el futuro")
    private LocalDateTime fechaReserva; // Cuándo asistirá el cliente

    @NotNull(message = "La cantidad de personas es obligatoria")
    @Min(value = 1, message = "La reserva debe ser para al menos 1 persona")
    private Integer cantidadPersonas;
}
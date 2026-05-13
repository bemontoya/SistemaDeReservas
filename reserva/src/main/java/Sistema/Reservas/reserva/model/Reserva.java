package Sistema.Reservas.reserva.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "reservas")
@Data // Genera getters y setters automáticamente con Lombok
public class Reserva {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "El ID del cliente es obligatorio")
    private Long clienteId;

    @NotNull(message = "El ID de la mesa es obligatorio")
    private Long mesaId;

    @Future(message = "La fecha de reserva debe ser en el futuro")
    private LocalDateTime fechaReserva;

    @NotBlank(message = "El estado no puede estar vacío")
    private String estado; // Si está "CONFIRMADA", "PENDIENTE"
}

package Sistema.Reservas.reserva.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "reservas")
@Data
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long clienteId;
    private Long mesaId;
    private LocalDateTime fechaReserva;
    private Integer cantidadPersonas;
    private String estado; // Ejemplo: "CONFIRMADA", "PENDIENTE", "CANCELADA"

    @PrePersist
    protected void onCreate() {
        if (this.estado == null) {
            this.estado = "CONFIRMADA";
        }
    }
}
package Sistema.Reservas.pedido.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "pedidos")
@Data
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "El ID del cliente es obligatorio")
    private Long clienteId;

    // Relación con reserva (Para saber de qué mesa viene el pedido)
    private Long reservaId;

    @NotBlank(message = "El detalle del pedido es obligatorio")
    @Size(max = 255, message = "El detalle no puede superar los 255 caracteres")
    private String detalle;

    // Fecha y hora en la que se tomó el pedido
    private LocalDateTime fechaPedido;

    // Total de la cuenta
    @NotNull(message = "El total es obligatorio")
    @PositiveOrZero(message = "El total debe ser mayor o igual a cero")
    private Double total;

    // Estado del pedido (Ejemplo: "PENDIENTE", "EN_PREPARACION", "ENTREGADO", "PAGADO")
    @NotBlank(message = "El estado no puede estar vacío")
    private String estado;

    // Asigna valores automáticamente antes de guardar en la BD si vienen vacíos
    @PrePersist
    protected void onCreate() {
        this.fechaPedido = LocalDateTime.now();
        if (this.total == null) {
            this.total = 0.0;
        }
        if (this.estado == null) {
            this.estado = "PENDIENTE"; // Sincronizado con el estado inicial del controlador
        }
    }
}
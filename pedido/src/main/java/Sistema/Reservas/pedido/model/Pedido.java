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

    // Relacion con reserva (Para saber que mesa viene el pedido)
    private Long reservaId;

    @NotBlank(message = "El detalle del pedido es obligatorio")
    private String detalle;

    //Fecha y hora en la que se tomó el pedido
    private LocalDateTime fechaPedido;

    // Total de la cuenta
    @PositiveOrZero(message = "El total debe ser mayor o igual a cero")
    private Double total;

    // Estado del pedido

    @NotBlank(message = "El estado no puede estar vacío")
    private String estado; //Ejemplo: "EN_PREPARACION", "ENTREGADO", "PAGADO"

    // Método para asignar la fecha automáticamente antes de persistir
    @PrePersist
    protected void onCreate() {
        this.fechaPedido = LocalDateTime.now();
        if (this.estado == null) {
            this.estado = "EN_PREPARACION";
        }
    }

}
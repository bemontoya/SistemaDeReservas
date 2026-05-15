package Sistema.Reservas.pagos.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "pagos")
@Data
public class Pago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "El ID del pedido es obligatorio")
    private  Long pedidoId;

    @NotNull(message = "El monto es obligatorio")
    @DecimalMin(value = "0.0", inclusive = false, message = "El monto debe ser mayor a 0")
    private BigDecimal monto; //BigDecimal para precision financiera

    @NotBlank(message = "El método de pago es obligatorio (Efectivo, Tarjeta, etc.)")
    private String metodoPago;

    @Column(updatable = false)
    private LocalDateTime fechaPago;

    @PrePersist // Antes de guardar se asigna fecha y hora actual
    protected void onCreate(){
        this.fechaPago = LocalDateTime.now();
    }
}

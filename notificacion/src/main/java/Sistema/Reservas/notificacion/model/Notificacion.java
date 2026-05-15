package Sistema.Reservas.notificacion.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "notificaciones")
@Data
public class Notificacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El destinatario es obligatorio (Email o ID)")
    private String destinatario; //Puede ser el email del cliente

    @NotBlank(message = "El mensaje no puede estar vacío")
    private String mensaje;

    @NotBlank(message = "El tipo de notificación es obligatoio (Pedido, Pago, etc)")
    private String tipo;

    @Column(updatable = false)
    private LocalDateTime fechaEnvio;

    @PrePersist
    protected void onCreate() {
        this.fechaEnvio = LocalDateTime.now();
    }
}

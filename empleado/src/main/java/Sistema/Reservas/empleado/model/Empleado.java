package Sistema.Reservas.empleado.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "empleados")
@Data
public class Empleado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre es obligatorio")
    @Column(nullable = false)
    private String nombre;

    @NotBlank(message = "El apellido es obligatorio")
    @Column(nullable = false)
    private String apellido;

    @NotBlank(message = "El cargo es obligatorio")
    @Column(nullable = false)
    private String cargo;

    @Email(message = "Email inválido")
    @NotBlank(message = "El email es obligatorio")
    @Column(nullable = false, unique = true)
    private String email;

    @Column(updatable = false, nullable = false)
    private LocalDateTime fechaContratacion;

    @PrePersist
    protected void onCreate(){
        this.fechaContratacion = LocalDateTime.now();
    }
}
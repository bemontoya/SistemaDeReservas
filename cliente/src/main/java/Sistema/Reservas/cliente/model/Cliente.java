package Sistema.Reservas.cliente.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name= "clientes")
@Data
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre del cliente es obligatorio")
    @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
    private String nombre;

    @NotBlank(message = "El apellido es obligatorio")
    private String apellido;

    @Email(message = "Debe proporcionar un formato de email válido")
    @NotBlank(message = "El email es obligatorio")
    @Column(unique = true) //Esto evita correos duplicados en la base de datos
    private String email;

    @Pattern(regexp = "\\+56?[0-9]{9}", message = "El teléfono debe empezar con +56 seguido de 9 dígitos (Ej: +56912345678)")
    private String telefono;

    @Column(updatable = false)
    private LocalDateTime fechaRegistro;

    //Para automatizar la fecha de registro
    @PrePersist
    protected void onCreate(){
        this.fechaRegistro = LocalDateTime.now();
    }
}

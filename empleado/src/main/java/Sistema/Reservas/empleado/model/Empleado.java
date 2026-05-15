//Esta clase define la estructura de la tabla en la base de datos y las reglas de validación.

package Sistema.Reservas.empleado.model;


import jakarta.persistence.*; //Jakarta permite conectar el codigo java con bases de datos relaciones.
import jakarta.validation.constraints.*; //Valida datos de los objetos automáticamente.
import lombok.Data; //Genera automáticamente codigo repetitivo.
import java.time.LocalDateTime;

@Entity //Indica que esta clase es una entiodad de base de datos.
@Table(name = "empleados") //Nombre de la tabla en la base de datos
@Data // Genera automáticamente Getters, Setters, toString y Equals gracias a Lombok
public class Empleado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //El id se incrementa solo.
    private Long id;

    @NotBlank(message = "El nombre es obligatorio")//Valida que no sea nulo ni esté vacio.
    private String nombre;

    @NotBlank(message = "El apellido es obligatorio")
    private String apellido;

    @NotBlank(message = "El cargo es obligatorio")
    private String cargo;

    @Email(message = "Email inválido") //Valida que el texto tenga formato de correo (ejemplo@gmail.com)
    @NotBlank(message = "El email es obligatorio")
    private String email;

    @Column(updatable = false) //Una vez creada la fecha no se puede modificar
    private LocalDateTime fechaContratacion;

    @PrePersist //Se ejecuta automáticamente justo antes de guardar enn la base de datos
    protected void onCreate(){
        // Asigna la fecha y la hora actual del sistema al momento del registro
        this.fechaContratacion = LocalDateTime.now();
    }
}

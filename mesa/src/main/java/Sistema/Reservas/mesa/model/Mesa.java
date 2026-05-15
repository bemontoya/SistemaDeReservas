package Sistema.Reservas.mesa.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

@Entity
@Table(name = "mesas")
@Data
public class Mesa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "El número de mesa es obligatorio")
    @Column(unique = true) // No pueden existir dos mesas con el mismo numero
    private Integer numeroMesa;

    @NotNull(message = "La capacidad es obligatoria")
    @Min(value = 1, message = "La capacidad mínima debe ser de al menos 1 persona")
    private Integer capacidad;

    @NotBlank(message = "El estado es obligatorio (LIBRE, OCUPADA, RESERVADA)")
    private String estado = "LIBRE"; // Por defecto, toda mesa nueva empieza LIBRE
}
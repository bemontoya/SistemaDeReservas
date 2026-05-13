package Sistema.Reservas.reportes.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "reportes")
@Data
public class Reportes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank (message = "El nombre del reporte es obligatorio")
    private String nombre;

    @NotBlank (message = "El tipo de reporte no puede estar vacío")
    private String tipo;

    @NotNull (message = "La fecha de generación es obligatoria")
    private LocalDateTime fechaGeneracion;

    @Column (columnDefinition = "TEXT")
    private String contenido; // Aqui se puede guardar un resumen en texto o JSON

    @NotBlank(message = "Debe indicar el autor del reporte")
    private String generadoPor; //Usuario que solicitó el reporte

    @PrePersist
    protected void onCreate() {
        this.fechaGeneracion = LocalDateTime.now();
    }
}

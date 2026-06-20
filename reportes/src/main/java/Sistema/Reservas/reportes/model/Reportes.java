package Sistema.Reservas.reportes.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "reportes")
@Data
public class Reportes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String tipo;

    private LocalDateTime fechaGeneracion;

    @Column(columnDefinition = "TEXT")
    private String contenido; // Aquí se guarda el resumen en texto o JSON

    private String generadoPor; // Usuario que solicitó el reporte

    @PrePersist
    protected void onCreate() {
        this.fechaGeneracion = LocalDateTime.now();
    }
}
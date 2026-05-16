package Sistema.Reservas.reportes.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class ReportesDTO {

    @NotBlank(message = "El nombre del reporte es obligatorio")
    private String nombre;

    @NotBlank(message = "El tipo de reporte no puede estar vacío")
    private String tipo;

    @NotBlank(message = "El contenido o resumen del reporte es obligatorio")
    private String contenido;

    @NotBlank(message = "Debe indicar el autor del reporte")
    private String generadoPor;
}
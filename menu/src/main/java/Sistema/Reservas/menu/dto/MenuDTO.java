package Sistema.Reservas.menu.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class MenuDTO {

    @NotBlank(message = "El nombre del plato/producto es obligatorio")
    private String nombre;

    @NotBlank(message = "La descripción del plato es obligatoria")
    private String descripcion;

    @NotNull(message = "El precio es obligatorio")
    @DecimalMin(value = "0.0", inclusive = false, message = "El precio debe ser mayor a 0")
    private BigDecimal precio;

    @NotBlank(message = "La categoría es obligatoria (Ej: Entrada, Fondo, Postre, Bebestible)")
    private String categoria;

    @NotNull(message = "Debe especificar si el plato está disponible (true/false)")
    private Boolean disponible;
}
package Sistema.Reservas.inventario.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class InventarioDTO {

    @NotBlank(message = "El nombre del ingrediente/insumo es obligatorio")
    private String nombreIngrediente;

    @NotNull(message = "La cantidad inicial es obligatoria")
    @Min(value = 0, message = "La cantidad inicial no puede ser negativa")
    private Integer cantidadActual;

    @NotNull(message = "El stock mínimo es obligatorio")
    @Min(value = 0, message = "El stock mínimo no puede ser negativo")
    private Integer stockMinimo;

    @NotBlank(message = "La unidad de medida es obligatoria (Ej: Kg, Litros, Unidades)")
    private String unidadMedida;
}
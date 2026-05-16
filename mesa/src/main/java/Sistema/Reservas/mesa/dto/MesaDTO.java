package Sistema.Reservas.mesa.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class MesaDTO {

    @NotNull(message = "El número de mesa es obligatorio")
    @Min(value = 1, message = "El número de mesa debe ser mayor a 0")
    private Integer numeroMesa;

    @NotNull(message = "La capacidad es obligatoria")
    @Min(value = 1, message = "La capacidad mínima debe ser de al menos 1 persona")
    private Integer capacity; // Representa la cantidad de asientos
}
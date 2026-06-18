package Sistema.Reservas.mesa.controller;

import Sistema.Reservas.mesa.dto.MesaDTO;
import Sistema.Reservas.mesa.model.Mesa;
import Sistema.Reservas.mesa.service.MesaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mesas")
@Tag(name = "Mesas", description = "Controlador para la gestión física y estado de las mesas del restaurante")
public class MesaController {

    @Autowired
    private MesaService mesaService;

    @GetMapping
    @Operation(summary = "Obtener todas las mesas", description = "Retorna la lista completa de mesas del establecimiento")
    @ApiResponse(responseCode = "200", description = "Lista de mesas obtenida exitosamente")
    public List<Mesa> listarTodas() {
        return mesaService.obtenerTodas();
    }

    @GetMapping("/estado/{estado}")
    @Operation(summary = "Filtrar mesas por estado", description = "Retorna las mesas filtradas por su situación actual (Ej: LIBRE, OCUPADA, RESERVADA)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista filtrada obtenida exitosamente"),
            @ApiResponse(responseCode = "400", description = "El estado proporcionado no es válido")
    })
    public List<Mesa> listarPorEstado(
            @Parameter(description = "Estado de la mesa", example = "LIBRE") @PathVariable String estado) {
        return mesaService.obtenerPorEstado(estado);
    }

    @PostMapping
    @Operation(summary = "Registrar una nueva mesa", description = "Crea una mesa en el sistema. Por defecto su estado inicial será LIBRE")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Mesa creada exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos")
    })
    public ResponseEntity<Mesa> crear(@Valid @RequestBody MesaDTO mesaDTO) {
        Mesa mesaEntidad = new Mesa();
        mesaEntidad.setNumeroMesa(mesaDTO.getNumeroMesa());
        mesaEntidad.setCapacidad(mesaDTO.getCapacity()); // 🛠️ Uso correcto de getCapacity()

        Mesa nuevaMesa = mesaService.crearMesa(mesaEntidad);
        return new ResponseEntity<>(nuevaMesa, HttpStatus.CREATED);
    }

    @PatchMapping("/{id}/estado")
    @Operation(summary = "Cambiar estado de una mesa", description = "Actualiza parcialmente el estado de una mesa específica mediante su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estado de la mesa actualizado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Error en la solicitud o estado inválido"),
            @ApiResponse(responseCode = "404", description = "La mesa solicitada no existe")
    })
    public ResponseEntity<?> actualizarEstado(
            @Parameter(description = "ID de la mesa a modificar", example = "1") @PathVariable Long id,
            @Parameter(description = "Nuevo estado de la mesa", example = "OCUPADA") @RequestParam String nuevoEstado) {
        try {
            return ResponseEntity.ok(mesaService.cambiarEstadoMesa(id, nuevoEstado));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar datos estructurales de una mesa", description = "Modifica el número y la capacidad de asientos de una mesa existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Mesa actualizada exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
            @ApiResponse(responseCode = "404", description = "La mesa no existe")
    })
    public ResponseEntity<?> actualizar(@PathVariable Long id, @Valid @RequestBody MesaDTO mesaDTO) {
        try {
            Mesa mesaModificada = new Mesa();
            mesaModificada.setNumeroMesa(mesaDTO.getNumeroMesa());
            mesaModificada.setCapacidad(mesaDTO.getCapacity());


            return ResponseEntity.ok(mesaService.actualizarMesa(id, mesaModificada));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar una mesa del sistema", description = "Borra permanentemente una mesa utilizando su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Mesa eliminada exitosamente"),
            @ApiResponse(responseCode = "404", description = "La mesa no existe")
    })
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        try {
            mesaService.eliminarMesa(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
package Sistema.Reservas.inventario.controller;

import Sistema.Reservas.inventario.dto.InventarioDTO;
import Sistema.Reservas.inventario.model.Inventario;
import Sistema.Reservas.inventario.service.InventarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventario")
@Tag(name = "Módulo de Inventario", description = "Controlador para la gestión de insumos, stock y alertas críticas del restaurante")
public class InventarioController {

    @Autowired
    private InventarioService inventarioService;

    @GetMapping
    @Operation(summary = "Listar todo el inventario", description = "Retorna una lista con todos los insumos registrados")
    public List<Inventario> listarTodos() { // Estandarizado a listarTodos
        return inventarioService.obtenerTodo();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener insumo por ID", description = "Busca un insumo específico en el inventario por su ID")
    public ResponseEntity<Inventario> obtenerPorId(@PathVariable Long id) {
        try {
            Inventario insumo = inventarioService.obtenerPorId(id);
            return ResponseEntity.ok(insumo);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/criticos")
    @Operation(summary = "Listar insumos críticos", description = "Retorna los insumos cuya cantidad actual está por debajo o igual al stock mínimo")
    public List<Inventario> listarCriticos() {
        return inventarioService.obtenerCriticos();
    }

    @PostMapping
    @Operation(summary = "Registrar nuevo insumo", description = "Crea un nuevo insumo en el inventario")
    public ResponseEntity<Inventario> crear(@Valid @RequestBody InventarioDTO inventarioDTO) {
        Inventario inventarioEntidad = new Inventario();
        inventarioEntidad.setNombreIngrediente(inventarioDTO.getNombreIngrediente());
        inventarioEntidad.setCantidadActual(inventarioDTO.getCantidadActual());
        inventarioEntidad.setStockMinimo(inventarioDTO.getStockMinimo());
        inventarioEntidad.setUnidadMedida(inventarioDTO.getUnidadMedida());

        Inventario nuevoInsumo = inventarioService.crearInsumo(inventarioEntidad);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoInsumo);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar insumo por completo", description = "Modifica todos los campos de un insumo existente")
    public ResponseEntity<Inventario> actualizar(@PathVariable Long id, @Valid @RequestBody InventarioDTO inventarioDTO) {
        try {
            Inventario insumoExistente = inventarioService.obtenerPorId(id);

            insumoExistente.setNombreIngrediente(inventarioDTO.getNombreIngrediente());
            insumoExistente.setCantidadActual(inventarioDTO.getCantidadActual());
            insumoExistente.setStockMinimo(inventarioDTO.getStockMinimo());
            insumoExistente.setUnidadMedida(inventarioDTO.getUnidadMedida());

            Inventario insumoActualizado = inventarioService.actualizarInsumo(insumoExistente);
            return ResponseEntity.ok(insumoActualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PatchMapping("/{id}/descontar")
    @Operation(summary = "Descontar stock de un insumo", description = "Reduce la cantidad actual de un insumo (ej: al vender un plato)")
    public ResponseEntity<?> descontar(@PathVariable Long id, @RequestParam Integer cantidad) {
        try {
            Inventario insumoActualizado = inventarioService.descontarStock(id, cantidad);
            return ResponseEntity.ok(insumoActualizado);
        } catch (RuntimeException e) {
            // Manejo por si no hay stock o no existe el id
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar insumo del inventario", description = "Remueve permanentemente un insumo a partir de su ID")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        try {
            inventarioService.obtenerPorId(id); // Validar existencia
            inventarioService.eliminarPorId(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
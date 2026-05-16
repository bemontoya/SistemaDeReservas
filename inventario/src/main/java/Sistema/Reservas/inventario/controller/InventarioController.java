package Sistema.Reservas.inventario.controller;

import Sistema.Reservas.inventario.dto.InventarioDTO;
import Sistema.Reservas.inventario.model.Inventario;
import Sistema.Reservas.inventario.service.InventarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventario")
public class InventarioController {

    @Autowired
    private InventarioService inventarioService;

    @GetMapping
    public List<Inventario> listar() {
        return inventarioService.obtenerTodo();
    }

    @GetMapping("/criticos")
    public List<Inventario> listarCriticos() {
        return inventarioService.obtenerCriticos();
    }


    @PostMapping
    public ResponseEntity<Inventario> crear(@Valid @RequestBody InventarioDTO inventarioDTO) {

        // Se pasan los datos del DTO a la entidad de la base de datos
        Inventario inventarioEntidad = new Inventario();
        inventarioEntidad.setNombreIngrediente(inventarioDTO.getNombreIngrediente());
        inventarioEntidad.setCantidadActual(inventarioDTO.getCantidadActual());
        inventarioEntidad.setStockMinimo(inventarioDTO.getStockMinimo());
        inventarioEntidad.setUnidadMedida(inventarioDTO.getUnidadMedida());


        Inventario nuevoInsumo = inventarioService.crearInsumo(inventarioEntidad);

        return new ResponseEntity<>(nuevoInsumo, HttpStatus.CREATED);
    }


    @PatchMapping("/{id}/descontar")
    public ResponseEntity<?> descontar(@PathVariable Long id, @RequestParam Integer cantidad) {
        try {
            return ResponseEntity.ok(inventarioService.descontarStock(id, cantidad));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
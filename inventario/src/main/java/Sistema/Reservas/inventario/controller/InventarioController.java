package Sistema.Reservas.inventario.controller;

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
    public ResponseEntity<Inventario> crear(@Valid @RequestBody Inventario inventario) {
        return new ResponseEntity<>(inventarioService.crearInsumo(inventario), HttpStatus.CREATED);
    }


    // Patch /api/inventario/1/descontar?cantidad=5
    @PatchMapping("/{id}/descontar")
    public ResponseEntity<?> descontar(@PathVariable Long id, @RequestParam Integer cantidad) {
        try {
            return ResponseEntity.ok(inventarioService.descontarStock(id, cantidad));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
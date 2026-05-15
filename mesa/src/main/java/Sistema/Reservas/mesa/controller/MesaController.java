package Sistema.Reservas.mesa.controller;

import Sistema.Reservas.mesa.model.Mesa;
import Sistema.Reservas.mesa.service.MesaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mesas")
public class MesaController {

    @Autowired
    private MesaService mesaService;

    // GET /api/mesas (Muestra todas las mesas)
    @GetMapping
    public List<Mesa> listarTodas() {
        return mesaService.obtenerTodas();
    }

    // GET /api/mesas/estado/libre (Muestra solo las mesas libres)
    @GetMapping("/estado/{estado}")
    public List<Mesa> listarPorEstado(@PathVariable String estado) {
        return mesaService.obtenerPorEstado(estado);
    }

    // POST /api/mesas (Crea una mesa)
    @PostMapping
    public ResponseEntity<Mesa> crear(@Valid @RequestBody Mesa mesa) {
        return new ResponseEntity<>(mesaService.crearMesa(mesa), HttpStatus.CREATED);
    }

    // PATCH /api/mesas/1/estado?nuevoEstado=ocupada (Cambia el estado de la mesa)
    @PatchMapping("/{id}/estado")
    public ResponseEntity<?> actualizarEstado(@PathVariable Long id, @RequestParam String nuevoEstado) {
        try {
            return ResponseEntity.ok(mesaService.cambiarEstadoMesa(id, nuevoEstado));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
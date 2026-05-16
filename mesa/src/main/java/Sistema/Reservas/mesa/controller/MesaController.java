package Sistema.Reservas.mesa.controller;

import Sistema.Reservas.mesa.dto.MesaDTO;
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

    @GetMapping
    public List<Mesa> listarTodas() {
        return mesaService.obtenerTodas();
    }

    @GetMapping("/estado/{estado}")
    public List<Mesa> listarPorEstado(@PathVariable String estado) {
        return mesaService.obtenerPorEstado(estado);
    }


    @PostMapping
    public ResponseEntity<Mesa> crear(@Valid @RequestBody MesaDTO mesaDTO) {


        Mesa mesaEntidad = new Mesa();
        mesaEntidad.setNumeroMesa(mesaDTO.getNumeroMesa());
        mesaEntidad.setCapacidad(mesaDTO.getCapacity());
        // El estado 'LIBRE' se asigna por defecto en el modelo o la base de datos


        Mesa nuevaMesa = mesaService.crearMesa(mesaEntidad);

        return new ResponseEntity<>(nuevaMesa, HttpStatus.CREATED);
    }

    @PatchMapping("/{id}/estado")
    public ResponseEntity<?> actualizarEstado(@PathVariable Long id, @RequestParam String nuevoEstado) {
        try {
            return ResponseEntity.ok(mesaService.cambiarEstadoMesa(id, nuevoEstado));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
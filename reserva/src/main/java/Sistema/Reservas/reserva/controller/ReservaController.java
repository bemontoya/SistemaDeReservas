package Sistema.Reservas.reserva.controller;

import Sistema.Reservas.reserva.dto.ReservaDTO;
import Sistema.Reservas.reserva.model.Reserva;
import Sistema.Reservas.reserva.service.ReservaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservas")
public class ReservaController {

    @Autowired
    private ReservaService reservaService;

    @GetMapping
    public List<Reserva> listarTodas() {
        return reservaService.obtenerTodas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reserva> obtenerPorId(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(reservaService.obtenerPorId(id));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }


    @PostMapping
    public ResponseEntity<Reserva> crear(@Valid @RequestBody ReservaDTO reservaDTO) {

        Reserva reservaEntidad = new Reserva();
        reservaEntidad.setClienteId(reservaDTO.getClienteId());
        reservaEntidad.setMesaId(reservaDTO.getMesaId());
        reservaEntidad.setFechaReserva(reservaDTO.getFechaReserva());
        reservaEntidad.setCantidadPersonas(reservaDTO.getCantidadPersonas());

        Reserva nuevaReserva = reservaService.crearReserva(reservaEntidad);

        return new ResponseEntity<>(nuevaReserva, HttpStatus.CREATED);
    }
}
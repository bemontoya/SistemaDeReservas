package Sistema.Reservas.reserva.controller;

import Sistema.Reservas.reserva.dto.ReservaDTO;
import Sistema.Reservas.reserva.model.Reserva;
import Sistema.Reservas.reserva.service.ReservaService;
import io.swagger.v3.oas.annotations.Operation;
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
@RequestMapping("/api/reservas")
@Tag(name = "Reservas", description = "Controlador para la gestión de las reservas de mesas en el restaurante")
public class ReservaController {

    @Autowired
    private ReservaService reservaService;

    @Operation(summary = "Listar todas las reservas", description = "Obtiene una lista con todas las reservas registradas en el sistema")
    @GetMapping
    public ResponseEntity<List<Reserva>> listarTodas() {
        return ResponseEntity.ok(reservaService.obtenerTodas());
    }

    @Operation(summary = "Obtener una reserva por ID", description = "Busca los datos de una reserva mediante su identificador único")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reserva encontrada con éxito"),
            @ApiResponse(responseCode = "404", description = "Reserva no encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Reserva> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(reservaService.obtenerPorId(id));
    }

    @Operation(summary = "Crear una nueva reserva", description = "Registra una reserva asignando un cliente, una mesa, la fecha y la cantidad de comensales")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Reserva creada con éxito"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos")
    })
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

    @Operation(summary = "Actualizar una reserva existente", description = "Modifica los datos de una reserva basándose en su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reserva actualizada de forma exitosa"),
            @ApiResponse(responseCode = "404", description = "Reserva no encontrada")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Reserva> actualizar(@PathVariable Long id, @Valid @RequestBody ReservaDTO reservaDTO) {
        Reserva datosActualizados = new Reserva();
        datosActualizados.setClienteId(reservaDTO.getClienteId());
        datosActualizados.setMesaId(reservaDTO.getMesaId());
        datosActualizados.setFechaReserva(reservaDTO.getFechaReserva());
        datosActualizados.setCantidadPersonas(reservaDTO.getCantidadPersonas());

        Reserva reservaModificada = reservaService.actualizarReserva(id, datosActualizados);
        return ResponseEntity.ok(reservaModificada);
    }

    @Operation(summary = "Eliminar una reserva", description = "Remueve una reserva del sistema de manera permanente usando su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Reserva eliminada correctamente"),
            @ApiResponse(responseCode = "404", description = "Reserva no encontrada")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        reservaService.eliminarPorId(id);
        return ResponseEntity.noContent().build();
    }
}
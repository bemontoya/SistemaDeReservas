package Sistema.Reservas.pagos.controller;

import Sistema.Reservas.pagos.dto.PagoDTO;
import Sistema.Reservas.pagos.model.Pago;
import Sistema.Reservas.pagos.service.PagoService;
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
@RequestMapping("/api/pagos")
@Tag(name = "Pagos", description = "Controlador para el procesamiento, validación y consulta de transacciones de la plataforma")
public class PagoController {

    @Autowired
    private PagoService pagoService;

    @GetMapping
    @Operation(summary = "Listar todos los pagos", description = "Retorna un historial completo de todas las transacciones registradas")
    @ApiResponse(responseCode = "200", description = "Lista de transacciones obtenida exitosamente")
    public List<Pago> listar() {
        return pagoService.obtenerTodos();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar pago por ID", description = "Obtiene los detalles de una transacción específica usando su identificador único")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pago encontrado exitosamente"),
            @ApiResponse(responseCode = "404", description = "No se encontró ningún pago con el ID proporcionado")
    })
    public ResponseEntity<Pago> buscarPorId(
            @Parameter(description = "ID del pago a consultar", example = "1") @PathVariable Long id) {
        try {
            return ResponseEntity.ok(pagoService.obtenerPorId(id));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping
    @Operation(summary = "Procesar un nuevo pago", description = "Valida y registra la transacción de un pedido asegurando que no se dupliquen cobros")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Pago procesado y registrado con éxito"),
            @ApiResponse(responseCode = "400", description = "Error de negocio (Ej: El pedido ya se encuentra pagado o montos inválidos)")
    })
    public ResponseEntity<?> crear(@Valid @RequestBody PagoDTO pagoDTO) {
        try {
            Pago pagoEntidad = new Pago();
            pagoEntidad.setPedidoId(pagoDTO.getPedidoId());
            pagoEntidad.setMonto(pagoDTO.getMonto());
            pagoEntidad.setMetodoPago(pagoDTO.getMetodoPago());

            // Se envia la entidad al servicio para validar negocio y guardar
            Pago nuevoPago = pagoService.procesarPago(pagoEntidad);
            return new ResponseEntity<>(nuevoPago, HttpStatus.CREATED);

        } catch (RuntimeException e) {
            // Si el pedido ya estaba pagado, retorna el mensaje de error de negocio
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
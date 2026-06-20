package Sistema.Reservas.pedido.controller;

import Sistema.Reservas.pedido.dto.PedidoDTO;
import Sistema.Reservas.pedido.model.Pedido;
import Sistema.Reservas.pedido.service.PedidoService;
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
@RequestMapping("/api/pedidos")
@Tag(name = "Pedidos", description = "Controlador para la gestión del ciclo de vida de los pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @Operation(summary = "Listar todos los pedidos", description = "Obtiene una lista con todos los pedidos registrados en el sistema")
    @GetMapping
    public ResponseEntity<List<Pedido>> listarTodos() {
        List<Pedido> pedidos = pedidoService.obtenerTodos();
        return ResponseEntity.ok(pedidos);
    }

    @Operation(summary = "Obtener un pedido por ID", description = "Busca un pedido específico usando su identificador único")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pedido encontrado con éxito"),
            @ApiResponse(responseCode = "404", description = "Pedido no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Pedido> obtenerPorId(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(pedidoService.obtenerPorId(id));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @Operation(summary = "Crear un nuevo pedido", description = "Registra un pedido en estado PENDIENTE con un total inicial de 0.0")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Pedido creado de forma exitosa"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos")
    })
    @PostMapping
    public ResponseEntity<Pedido> crear(@Valid @RequestBody PedidoDTO pedidoDTO) {
        Pedido pedidoEntidad = new Pedido();
        pedidoEntidad.setClienteId(pedidoDTO.getClienteId());
        pedidoEntidad.setReservaId(pedidoDTO.getReservaId());
        pedidoEntidad.setDetalle(pedidoDTO.getDetalle());

        pedidoEntidad.setTotal(0.0);
        pedidoEntidad.setEstado("PENDIENTE");
        pedidoEntidad.setFechaPedido(java.time.LocalDateTime.now());

        Pedido nuevoPedido = pedidoService.crearPedido(pedidoEntidad);
        return new ResponseEntity<>(nuevoPedido, HttpStatus.CREATED);
    }

    @Operation(summary = "Actualizar un pedido existente", description = "Modifica los datos de un pedido basándose en su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pedido actualizado correctamente"),
            @ApiResponse(responseCode = "404", description = "Pedido no encontrado para actualizar")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Pedido> actualizar(@PathVariable Long id, @Valid @RequestBody PedidoDTO pedidoDTO) {
        try {
            Pedido datosActualizados = new Pedido();
            datosActualizados.setClienteId(pedidoDTO.getClienteId());
            datosActualizados.setReservaId(pedidoDTO.getReservaId());
            datosActualizados.setDetalle(pedidoDTO.getDetalle());

            Pedido pedidoModificado = pedidoService.actualizarPedido(id, datosActualizados);
            return ResponseEntity.ok(pedidoModificado);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @Operation(summary = "Eliminar un pedido", description = "Remueve un pedido permanentemente del sistema usando su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Pedido eliminado con éxito (Sin contenido de retorno)"),
            @ApiResponse(responseCode = "404", description = "Pedido no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        try {
            pedidoService.eliminarPorId(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
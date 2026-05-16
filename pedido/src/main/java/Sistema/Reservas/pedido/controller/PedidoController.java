package Sistema.Reservas.pedido.controller;

import Sistema.Reservas.pedido.dto.PedidoDTO;
import Sistema.Reservas.pedido.model.Pedido;
import Sistema.Reservas.pedido.service.PedidoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @GetMapping
    public List<Pedido> listarTodos() {
        return pedidoService.obtenerTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pedido> obtenerPorId(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(pedidoService.obtenerPorId(id));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }


    @PostMapping
    public ResponseEntity<Pedido> crear(@Valid @RequestBody PedidoDTO pedidoDTO) {

        Pedido pedidoEntidad = new Pedido();
        pedidoEntidad.setClienteId(pedidoDTO.getClienteId());
        pedidoEntidad.setReservaId(pedidoDTO.getReservaId());
        pedidoEntidad.setDetalle(pedidoDTO.getDetalle());


        Pedido nuevoPedido = pedidoService.crearPedido(pedidoEntidad);

        return new ResponseEntity<>(nuevoPedido, HttpStatus.CREATED);
    }
}
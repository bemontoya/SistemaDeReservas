package Sistema.Reservas.pagos.controller;

import Sistema.Reservas.pagos.dto.PagoDTO;
import Sistema.Reservas.pagos.model.Pago;
import Sistema.Reservas.pagos.service.PagoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pagos")
public class PagoController {

    @Autowired
    private PagoService pagoService;

    @GetMapping
    public List<Pago> listar() {
        return pagoService.obtenerTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pago> buscarPorId(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(pagoService.obtenerPorId(id));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }


    @PostMapping
    public ResponseEntity<?> crear(@Valid @RequestBody PagoDTO pagoDTO) {
        try {
            Pago pagoEntidad = new Pago();
            pagoEntidad.setPedidoId(pagoDTO.getPedidoId());
            pagoEntidad.setMonto(pagoDTO.getMonto());
            pagoEntidad.setMetodoPago(pagoDTO.getMetodoPago());

            // Enviamos la entidad al servicio para validar negocio y guardar
            Pago nuevoPago = pagoService.procesarPago(pagoEntidad);
            return new ResponseEntity<>(nuevoPago, HttpStatus.CREATED);

        } catch (RuntimeException e) {
            // Si el pedido ya estaba pagado, retorna el mensaje de error de negocio
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
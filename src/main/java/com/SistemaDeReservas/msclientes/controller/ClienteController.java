package com.SistemaDeReservas.msclientes.controller;

import com.SistemaDeReservas.msclientes.dto.ClienteRequestDTO;
import com.SistemaDeReservas.msclientes.dto.ClienteResponseDTO;
import com.SistemaDeReservas.msclientes.service.ClienteService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/clientes")
public class ClienteController {

    private static final Logger logger = LoggerFactory.getLogger(ClienteController.class);

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }


    @GetMapping
    public ResponseEntity<List<ClienteResponseDTO>> obtenerTodos() {
        logger.info("[ms-clientes] GET /api/v1/clientes - Solicitando lista de clientes");
        List<ClienteResponseDTO> clientes = clienteService.obtenerTodos();
        return ResponseEntity.ok(clientes);
    }


    @GetMapping("/{id}")
    public ResponseEntity<ClienteResponseDTO> obtenerPorId(@PathVariable Long id) {
        logger.info("[ms-clientes] GET /api/v1/clientes/{} - Buscando cliente", id);
        ClienteResponseDTO cliente = clienteService.obtenerPorId(id);
        return ResponseEntity.ok(cliente);
    }


    @PostMapping
    public ResponseEntity<ClienteResponseDTO> crearCliente(@Valid @RequestBody ClienteRequestDTO requestDTO) {
        logger.info("[ms-clientes] POST /api/v1/clientes - Creando nuevo cliente con email: {}", requestDTO.getEmail());
        ClienteResponseDTO nuevoCliente = clienteService.crearCliente(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoCliente);
    }


    @PutMapping("/{id}")
    public ResponseEntity<ClienteResponseDTO> actualizarCliente(
            @PathVariable Long id,
            @Valid @RequestBody ClienteRequestDTO requestDTO) {
        logger.info("[ms-clientes] PUT /api/v1/clientes/{} - Actualizando cliente", id);
        ClienteResponseDTO clienteActualizado = clienteService.actualizarCliente(id, requestDTO);
        return ResponseEntity.ok(clienteActualizado);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> desactivarCliente(@PathVariable Long id) {
        logger.info("[ms-clientes] DELETE /api/v1/clientes/{} - Desactivando cliente", id);
        clienteService.desactivarCliente(id);
        return ResponseEntity.noContent().build();
    }
}

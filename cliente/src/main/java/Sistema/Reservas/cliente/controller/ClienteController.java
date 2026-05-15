package Sistema.Reservas.cliente.controller;

import Sistema.Reservas.cliente.dto.ClienteDTO;
import Sistema.Reservas.cliente.model.Cliente;
import Sistema.Reservas.cliente.service.ClienteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    // GET sigue devolviendo la entidad o una lista
    @GetMapping
    public List<Cliente> listarTodos() {
        return clienteService.obtenerTodos();
    }


    @PostMapping
    public ResponseEntity<Cliente> crear(@Valid @RequestBody ClienteDTO clienteDTO) {

        // Se pasan los datos del DTO a una Entidad real antes de guardarla
        Cliente clienteEntidad = new Cliente();
        clienteEntidad.setNombre(clienteDTO.getNombre());
        clienteEntidad.setApellido(clienteDTO.getApellido());
        clienteEntidad.setEmail(clienteDTO.getEmail());
        clienteEntidad.setTelefono(clienteDTO.getTelefono());

        // Guardamos la entidad usando tu servicio de siempre
        Cliente nuevoCliente = clienteService.crearCliente(clienteEntidad);

        return new ResponseEntity<>(nuevoCliente, HttpStatus.CREATED);
    }
}
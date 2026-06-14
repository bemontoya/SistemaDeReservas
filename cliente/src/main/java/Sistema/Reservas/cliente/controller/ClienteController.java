package Sistema.Reservas.cliente.controller;

import Sistema.Reservas.cliente.dto.ClienteDTO;
import Sistema.Reservas.cliente.model.Cliente;
import Sistema.Reservas.cliente.service.ClienteService;
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
@RequestMapping("/api/clientes")
@Tag(name = "Cliente Controller", description = "API para la gestión completa de clientes en el sistema de reservas")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @GetMapping
    @Operation(summary = "Listar todos los clientes", description = "Retorna una lista con todos los clientes registrados en la base de datos")
    public List<Cliente> listarTodos() {
        return clienteService.obtenerTodos();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener un cliente por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente encontrado con éxito"),
            @ApiResponse(responseCode = "404", description = "El cliente con el ID proporcionado no existe")
    })
    public ResponseEntity<Cliente> obtenerPorId(@PathVariable Long id) {
        Cliente cliente = clienteService.obtenerPorId(id);
        return ResponseEntity.ok(cliente);
    }

    @PostMapping
    @Operation(summary = "Crear un nuevo cliente", description = "Registra un cliente validando el formato del teléfono chileno y que el email no esté duplicado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Cliente creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos (Formatos incorrectos o campos vacíos)")
    })
    public ResponseEntity<Cliente> crear(@Valid @RequestBody ClienteDTO clienteDTO) {
        Cliente clienteEntidad = new Cliente();
        clienteEntidad.setNombre(clienteDTO.getNombre());
        clienteEntidad.setApellido(clienteDTO.getApellido());
        clienteEntidad.setEmail(clienteDTO.getEmail());
        clienteEntidad.setTelefono(clienteDTO.getTelefono());

        Cliente nuevoCliente = clienteService.crearCliente(clienteEntidad);
        return new ResponseEntity<>(nuevoCliente, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar datos de un cliente existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente actualizado con éxito"),
            @ApiResponse(responseCode = "400", description = "Datos de actualización inválidos"),
            @ApiResponse(responseCode = "404", description = "Cliente no encontrado")
    })
    public ResponseEntity<Cliente> actualizar(@PathVariable Long id, @Valid @RequestBody ClienteDTO clienteDTO) {
        Cliente clienteExistente = clienteService.obtenerPorId(id);

        clienteExistente.setNombre(clienteDTO.getNombre());
        clienteExistente.setApellido(clienteDTO.getApellido());
        clienteExistente.setEmail(clienteDTO.getEmail());
        clienteExistente.setTelefono(clienteDTO.getTelefono());

        Cliente clienteActualizado = clienteService.crearCliente(clienteExistente);
        return ResponseEntity.ok(clienteActualizado);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un cliente por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Cliente eliminado de forma física exitosamente"),
            @ApiResponse(responseCode = "404", description = "Cliente no encontrado")
    })
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        clienteService.obtenerPorId(id);
        clienteService.eliminarPorId(id);
        return ResponseEntity.noContent().build();
    }
}
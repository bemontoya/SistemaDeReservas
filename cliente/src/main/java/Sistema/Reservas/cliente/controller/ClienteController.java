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

    // GET para listar todos los clientes existentes
    @GetMapping
    public List<Cliente> listarTodos() {
        return clienteService.obtenerTodos();
    }


    // Método GET para conseguir un cliente por id
    @GetMapping("/{id}")
    public ResponseEntity<Cliente> obtenerPorId(@PathVariable Long id) {
        Cliente  cliente = clienteService.obtenerPorId(id);
        return ResponseEntity.ok(cliente);
    }


    // Método POST para crear el cliente
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

    // Método PUT para actualizar cliente
    @PutMapping("/{id}")
    public ResponseEntity<Cliente> actualizar(@PathVariable Long id, @Valid @RequestBody ClienteDTO clienteDTO) {
        try{
            //Se verifica si el cliente ya existe
            Cliente clienteExistente = clienteService.obtenerPorId(id);

            // Se reemplazan los datos con los nuevos
            clienteExistente.setNombre(clienteDTO.getNombre());
            clienteExistente.setApellido(clienteDTO.getApellido());
            clienteExistente.setEmail(clienteDTO.getEmail());
            clienteExistente.setTelefono(clienteDTO.getTelefono());

            //Se guardan los cambios usando el service
            Cliente clienteActualizado = clienteService.crearCliente(clienteExistente);
            return ResponseEntity.ok(clienteActualizado); //Devuelve si está bien


        } catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // Tira un error si no existía
        }
    }

    //Método de eliminar un cliente
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id){
        try{
            //Verifica si existe antes de borrarlo
            clienteService.obtenerPorId(id);

            //Se llama al service para eliminarlo
            clienteService.eliminarPorId(id);

            return ResponseEntity.noContent().build();
        } catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
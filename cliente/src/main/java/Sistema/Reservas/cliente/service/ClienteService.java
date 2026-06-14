package Sistema.Reservas.cliente.service;

import Sistema.Reservas.cliente.model.Cliente;
import Sistema.Reservas.cliente.repository.ClienteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {
    private static final Logger log = LoggerFactory.getLogger(ClienteService.class);

    @Autowired
    private ClienteRepository clienteRepository;

    public List<Cliente> obtenerTodos(){
        log.info("Consultando lista completa de clientes");
        return clienteRepository.findAll();
    }

    public Cliente crearCliente(Cliente cliente){
        log.info("Registrando nuevo cliente con email: {}", cliente.getEmail());

        // Valida si el email ya existe
        Optional<Cliente> clienteExistente = clienteRepository.findByEmail(cliente.getEmail());

        // Si la caja del Optional no está vacía, significa que el correo ya está registrado
        if (clienteExistente.isPresent()) {
            log.error("Error al registrar: El email {} ya se encuentra en uso", cliente.getEmail());
            throw new RuntimeException("El correo electrónico ya está registrado en el sistema");
        }

        return clienteRepository.save(cliente);
    }

    public Cliente obtenerPorId(Long id){
        log.info("Buscando cliente con ID: {}", id);
        return clienteRepository.findById(id)
                .orElseThrow(()-> {
                    log.error("Cliente con ID {} no encontrado", id);
                    return new RuntimeException("Cliente no encontrado");
                });
    }

    // Método eliminar
    public void eliminarPorId(Long id) {
        log.info("Eliminando de forma física al cliente con ID: {}", id);
        clienteRepository.deleteById(id);
    }
}
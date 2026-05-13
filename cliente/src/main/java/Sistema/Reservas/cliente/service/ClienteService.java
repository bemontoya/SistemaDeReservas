package Sistema.Reservas.cliente.service;

import Sistema.Reservas.cliente.model.Cliente;
import Sistema.Reservas.cliente.repository.ClienteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

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
        return clienteRepository.save(cliente);
    }

    public Cliente obtenerPorId(Long id){
        return clienteRepository.findById(id)
                .orElseThrow(()-> {
                    log.error("Cliente con ID {} no encontrado", id);
                    return new RuntimeException("Cliente no encontrado");
                });
    }

}

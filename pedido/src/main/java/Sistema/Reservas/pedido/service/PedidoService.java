package Sistema.Reservas.pedido.service;

import Sistema.Reservas.pedido.model.Pedido;
import Sistema.Reservas.pedido.repository.PedidoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PedidoService {
    private static final Logger log = LoggerFactory.getLogger(PedidoService.class);

    @Autowired
    private PedidoRepository pedidoRepository;

    public List<Pedido> obtenerTodos(){
        log.info("Consultando historial de pedidos");
        return pedidoRepository.findAll();
    }

    public Pedido crearPedido(Pedido pedido){
        log.info("Registrando nuevo pedido para el cliente: {}", pedido.getClienteId());
        return pedidoRepository.save(pedido);
    }

    public Pedido obtenerPorId(Long id){
        return pedidoRepository.findById(id)
                .orElseThrow(()-> {
                    log.error("Pedido con ID {} no encontrado", id);
                    return new RuntimeException("Pedido no encontrado");
                });
    }

}

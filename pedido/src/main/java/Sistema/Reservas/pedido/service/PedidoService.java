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

    public List<Pedido> obtenerTodos() {
        log.info("Consultando historial de todos los pedidos");
        return pedidoRepository.findAll();
    }

    public Pedido crearPedido(Pedido pedido) {
        log.info("Registrando nuevo pedido para el cliente: {}", pedido.getClienteId());
        return pedidoRepository.save(pedido);
    }

    public Pedido obtenerPorId(Long id) {
        return pedidoRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Pedido con ID {} no encontrado", id);
                    return new RuntimeException("Pedido no encontrado");
                });
    }

    public Pedido actualizarPedido(Long id, Pedido datosActualizados) {
        log.info("Intentando actualizar el pedido con ID: {}", id);

        Pedido pedidoExistente = obtenerPorId(id);

        pedidoExistente.setClienteId(datosActualizados.getClienteId());
        pedidoExistente.setReservaId(datosActualizados.getReservaId());
        pedidoExistente.setDetalle(datosActualizados.getDetalle());

        log.info("Pedido con ID {} actualizado exitosamente", id);
        return pedidoRepository.save(pedidoExistente);
    }

    public void eliminarPorId(Long id) {
        log.info("Intentando eliminar el pedido con ID: {}", id);

        Pedido pedido = obtenerPorId(id);
        pedidoRepository.delete(pedido);

        log.info("Pedido con ID {} eliminado correctamente de la base de datos", id);
    }
}
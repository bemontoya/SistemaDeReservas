package Sistema.Reservas.pedido.repository;

import Sistema.Reservas.pedido.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    // Búsqueda por cliente
    List<Pedido> findByClienteId(Long clienteId);

    // Búsqueda por reserva para saber que sse pidió en esa mesa
    List<Pedido> findByReservaId(Long reservaId);
}

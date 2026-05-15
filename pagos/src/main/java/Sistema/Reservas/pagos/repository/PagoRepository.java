package Sistema.Reservas.pagos.repository;

import Sistema.Reservas.pagos.model.Pago;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository // Componenete que maneja operaciiones crud (Crear, Leer, Actualizar, Borrar)
public interface PagoRepository extends JpaRepository<Pago, Long>{
    // Busca si un pedido ya tiene un pago registrado
    boolean existeByPedidoId(Long pedidoId);
}

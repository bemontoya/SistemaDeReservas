package Sistema.Reservas.reserva.repository;

import Sistema.Reservas.reserva.model.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Long> {

    // Métodos de búsqueda personalizados

    // Buscar reservas por cliente para historial
    List<Reserva> findByClienteId(Long clienteId);

    // Buscar reservas por mesa para validar disponibilidad
    List<Reserva> findByMesaId(Long mesaId);

    // Buscar por estado (Ej: PENDIENTE, CONFIRMADA)
    List<Reserva> findByEstado(String estado);
}
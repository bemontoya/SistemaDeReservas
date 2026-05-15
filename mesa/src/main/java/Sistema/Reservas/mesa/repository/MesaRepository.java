package Sistema.Reservas.mesa.repository;

import Sistema.Reservas.mesa.model.Mesa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface MesaRepository extends JpaRepository<Mesa, Long> {

    // Método automático de JPA para filtrar mesas según su estado actual
    List<Mesa> findByEstado(String estado);
}
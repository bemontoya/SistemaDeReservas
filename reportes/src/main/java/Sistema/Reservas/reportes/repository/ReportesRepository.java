package Sistema.Reservas.reportes.repository;

import Sistema.Reservas.reportes.model.Reportes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ReportesRepository extends JpaRepository<Reportes, Long> {
    // Buscar reportes por tipo (Ventas, Reservas)
    List<Reportes> findByTipo(String tipo);

    // Buscar reportes generados por un usuario específico
    List<Reportes> findByGeneradoPor(String generadoPor);
}

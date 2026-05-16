package Sistema.Reservas.reportes.repository;

import Sistema.Reservas.reportes.model.Reportes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportesRepository extends JpaRepository<Reportes, Long> {
}
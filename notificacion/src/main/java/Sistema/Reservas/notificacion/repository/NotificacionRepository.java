package Sistema.Reservas.notificacion.repository;

import Sistema.Reservas.notificacion.model.Notificacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface NotificacionRepository extends JpaRepository<Notificacion, Long> {

}

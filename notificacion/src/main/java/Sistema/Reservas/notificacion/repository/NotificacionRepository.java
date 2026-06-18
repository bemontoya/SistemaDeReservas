package Sistema.Reservas.notificacion.repository;

import Sistema.Reservas.notificacion.model.Notificacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface NotificacionRepository extends JpaRepository<Notificacion, Long> {

    List<Notificacion> findByDestinatario(String destinatario);
}
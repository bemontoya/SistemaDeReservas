package Sistema.Reservas.notificacion.service;

import Sistema.Reservas.notificacion.model.Notificacion;
import Sistema.Reservas.notificacion.repository.NotificacionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class NotificacionService {
    private static final Logger log = LoggerFactory.getLogger(NotificacionService.class);

    @Autowired
    private NotificacionRepository notificacionRepository;

    // Obtener historial de notificaciones enviadas
    public List<Notificacion> obtenerTodas() {
        log.info("Consultando historial de notificaciones");
        return notificacionRepository.findAll();
    }

    // "Enviar" y guardar la notificación
    public Notificacion enviarNotificacion(Notificacion notificacion) {
        // Simulamos la lógica de envío
        log.info("ENVIANDO {} A {}: {}",
                notificacion.getTipo(),
                notificacion.getDestinatario(),
                notificacion.getMensaje());

        // Guardamos el registro en la base de datos
        return notificacionRepository.save(notificacion);
    }
}

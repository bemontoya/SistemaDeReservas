package Sistema.Reservas.notificacion.controller;

import Sistema.Reservas.notificacion.model.Notificacion;
import Sistema.Reservas.notificacion.service.NotificacionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notificaciones")
public class NotificacionController {

    @Autowired
    private NotificacionService notificacionService;

    @GetMapping
    public List<Notificacion> listar() {
        return notificacionService.obtenerTodas();
    }

    @PostMapping
    public ResponseEntity<Notificacion> crear(@Valid @RequestBody Notificacion notificacion) {
        // Al hacer el POST, simulamos que se dispara el envío
        Notificacion nuevaNotificacion = notificacionService.enviarNotificacion(notificacion);
        return new ResponseEntity<>(nuevaNotificacion, HttpStatus.CREATED);
    }
}
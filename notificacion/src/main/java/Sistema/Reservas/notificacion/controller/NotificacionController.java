package Sistema.Reservas.notificacion.controller;

import Sistema.Reservas.notificacion.dto.NotificacionDTO;
import Sistema.Reservas.notificacion.model.Notificacion;
import Sistema.Reservas.notificacion.service.NotificacionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notificaciones")
@Tag(name = "Notificaciones", description = "Controlador para el envío y seguimiento de alertas a los usuarios")
public class NotificacionController {

    @Autowired
    private NotificacionService notificacionService;

    @GetMapping
    @Operation(summary = "Obtener todas las notificaciones", description = "Retorna el historial completo de alertas enviadas en el sistema")
    @ApiResponse(responseCode = "200", description = "Historial obtenido exitosamente")
    public List<Notificacion> listar() {
        return notificacionService.obtenerTodas();
    }

    @GetMapping("/destinatario/{correo}")
    @Operation(summary = "Buscar por destinatario", description = "Filtra las notificaciones enviadas a un correo electrónico específico")
    @ApiResponse(responseCode = "200", description = "Lista de notificaciones filtrada exitosamente")
    public List<Notificacion> listarPorDestinatario(
            @Parameter(description = "Correo del cliente", example = "cliente@correo.com") @PathVariable String correo) {
        return notificacionService.obtenerPorDestinatario(correo);
    }

    @PostMapping
    @Operation(summary = "Enviar una nueva notificación", description = "Registra y despacha una alerta (Email o SMS) a un destinatario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Notificación enviada y registrada exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos de la notificación inválidos")
    })
    public ResponseEntity<Notificacion> crear(@Valid @RequestBody NotificacionDTO notificacionDTO) {
        Notificacion notificacionEntidad = new Notificacion();
        notificacionEntidad.setDestinatario(notificacionDTO.getDestinatario());
        notificacionEntidad.setMensaje(notificacionDTO.getMensaje());
        notificacionEntidad.setTipo(notificacionDTO.getTipo());

        Notificacion nuevaNotificacion = notificacionService.enviarNotificacion(notificacionEntidad);
        return new ResponseEntity<>(nuevaNotificacion, HttpStatus.CREATED);
    }

}
package Sistema.Reservas.reserva.service;

import Sistema.Reservas.reserva.model.Reserva;
import Sistema.Reservas.reserva.repository.ReservaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ReservaService {
    private static final Logger log = LoggerFactory.getLogger(ReservaService.class);

    @Autowired
    private ReservaRepository reservaRepository;

    public List<Reserva> obtenerTodas(){
        log.info("Consultando todas las reservas....");
        return reservaRepository.findAll();
    }

    public Reserva obtenerPorId(Long id){
        return reservaRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Reserva con ID {} no encontrada", id);
                    return new RuntimeException("Reserva no encontrada");
                });
    }

    public Reserva crearReserva(Reserva nuevaReserva){
        log.info("Iniciando proceso de creación para una nueva reserva");

        // Corregido el typo "COFIRMADA" -> "CONFIRMADA"
        if (nuevaReserva.getEstado() == null) {
            nuevaReserva.setEstado("CONFIRMADA");
        }
        Reserva reservaGuardada = reservaRepository.save(nuevaReserva);
        log.info("Reserva creada exitosamente con ID: {}", reservaGuardada.getId());

        return reservaGuardada;
    }

    public Reserva actualizarReserva(Long id, Reserva datosActualizados) {
        log.info("Intentando actualizar la reserva con ID: {}", id);

        Reserva reservaExistente = obtenerPorId(id);

        reservaExistente.setClienteId(datosActualizados.getClienteId());
        reservaExistente.setMesaId(datosActualizados.getMesaId());
        reservaExistente.setFechaReserva(datosActualizados.getFechaReserva());
        reservaExistente.setCantidadPersonas(datosActualizados.getCantidadPersonas());

        if (datosActualizados.getEstado() != null) {
            reservaExistente.setEstado(datosActualizados.getEstado());
        }

        log.info("Reserva con ID {} modificada exitosamente", id);
        return reservaRepository.save(reservaExistente);
    }

    public void eliminarPorId(Long id) {
        log.info("Intentando eliminar la reserva con ID: {}", id);
        Reserva reserva = obtenerPorId(id);
        reservaRepository.delete(reserva);
        log.info("Reserva con ID {} removida correctamente", id);
    }
}
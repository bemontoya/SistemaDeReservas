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

    public List<Reserva> listarTodas(){
        log.info("Consultando todas las reservas....");
        return reservaRepository.findAll();
    }

    public Reserva guardar(Reserva reserva){
        // Por defecto las reservas nacen como PENDIENTE
        if (reserva.getEstado() == null){
            reserva.setEstado("PENDIENTE");
        }
        log.info("Guardando la reserva con el ID: {}", reserva.getClienteId());
        return reservaRepository.save(reserva);
    }

    public Reserva buscarPorId(Long id){
        return reservaRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Reserva con ID {} no encontrada", id);
                    return new RuntimeException("Reserva no encontrada");
                });
    }
}

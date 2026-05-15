package Sistema.Reservas.pagos.service;

import Sistema.Reservas.pagos.model.Pago;
import Sistema.Reservas.pagos.repository.PagoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PagoService {
    private static final Logger log = LoggerFactory.getLogger(PagoService.class);

    @Autowired
    private PagoRepository pagoRepository;

    public List<Pago> obtenerTodos(){
        log.info("Consultando historial de pagos");
        return pagoRepository.findAll();
    }

    public Pago procesarPago(Pago pago){
        if (pagoRepository.existsById(pago.getPedidoId())){
            log.error("Intento de pago duplicado para el Pedido ID: {}", pago.getPedidoId());
            throw new RuntimeException("Este pedido ya ha sido pagado anteriormente");
        }

        log.info("Procesando pago de ${} para el Pedido ID: {}", pago.getMonto(), pago.getPedidoId());
        return pagoRepository.save(pago);
    }

    public Pago obtenerPorId(Long id){
        return pagoRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Pago con ID {} no encontrado", id);
                    return new RuntimeException("Pago no encotrado");
                });
    }
}

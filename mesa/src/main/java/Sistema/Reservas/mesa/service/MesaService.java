package Sistema.Reservas.mesa.service;

import Sistema.Reservas.mesa.model.Mesa;
import Sistema.Reservas.mesa.repository.MesaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class MesaService {
    private static final Logger log = LoggerFactory.getLogger(MesaService.class);

    @Autowired
    private MesaRepository mesaRepository;

    // Listar absolutamente todas las mesas del local
    public List<Mesa> obtenerTodas() {
        log.info("Consultando el plano completo de mesas del restaurante");
        return mesaRepository.findAll();
    }

    // Registrar una nueva mesa en el plano
    public Mesa crearMesa(Mesa mesa) {
        log.info("Registrando la mesa N° {} con capacidad para {} personas", mesa.getNumeroMesa(), mesa.getCapacidad());
        return mesaRepository.save(mesa);
    }

    // Buscar mesas específicas por su estado (ej: traer solo las "LIBRE")
    public List<Mesa> obtenerPorEstado(String estado) {
        log.info("Filtrando mesas en estado: {}", estado.toUpperCase());
        return mesaRepository.findByEstado(estado.toUpperCase());
    }

    // Cambiar el estado de la mesa (Ej: cuando llegan clientes y se sientan)
    public Mesa cambiarEstadoMesa(Long id, String nuevoEstado) {
        Mesa mesa = mesaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Mesa no encontrada con el ID: " + id));

        log.info("Cambiando estado de la mesa N° {}: de {} a {}",
                mesa.getNumeroMesa(), mesa.getEstado(), nuevoEstado.toUpperCase());

        mesa.setEstado(nuevoEstado.toUpperCase());
        return mesaRepository.save(mesa);
    }


    public Mesa actualizarMesa(Long id, Mesa mesaModificada) {
        Mesa mesaExistente = mesaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("La mesa no existe con el ID: " + id));

        log.info("Actualizando datos de la mesa N° {}. Nueva capacidad: {} asientos.",
                mesaModificada.getNumeroMesa(), mesaModificada.getCapacidad());

        mesaExistente.setNumeroMesa(mesaModificada.getNumeroMesa());
        mesaExistente.setCapacidad(mesaModificada.getCapacidad());

        return mesaRepository.save(mesaExistente);
    }

    // Método para eliminar mesa por su ID
    public void eliminarMesa(Long id) {
        Mesa mesa = mesaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("La mesa no existe con el ID: " + id));

        log.warn("Eliminando del sistema la mesa N° {} de forma permanente", mesa.getNumeroMesa());
        mesaRepository.deleteById(id);
    }
}
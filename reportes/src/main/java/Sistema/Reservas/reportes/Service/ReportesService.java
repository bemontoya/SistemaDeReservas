package Sistema.Reservas.reportes.Service;

import Sistema.Reservas.reportes.model.Reportes;
import Sistema.Reservas.reportes.repository.ReportesRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ReportesService {
    private static final Logger log = LoggerFactory.getLogger(ReportesService.class);

    @Autowired
    private ReportesRepository reportesRepository;

    public List<Reportes> obtenerTodos() {
        log.info("Consultando el historial completo de reportes");
        return reportesRepository.findAll();
    }

    public Reportes guardarReporte(Reportes reporte) {
        log.info("Guardando en el historial el reporte: {} [Tipo: {}]", reporte.getNombre(), reporte.getTipo());
        return reportesRepository.save(reporte);
    }

    public Reportes obtenerPorId(Long id) {
        return reportesRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Reporte con ID {} no encontrado en el sistema", id);
                    return new RuntimeException("Reporte no encontrado");
                });
    }

    public Reportes actualizarReporte(Long id, Reportes datosActualizados) {
        log.info("Intentando actualizar el reporte con ID: {}", id);

        Reportes reporteExistente = obtenerPorId(id);

        reporteExistente.setNombre(datosActualizados.getNombre());
        reporteExistente.setTipo(datosActualizados.getTipo());
        reporteExistente.setContenido(datosActualizados.getContenido());
        reporteExistente.setGeneradoPor(datosActualizados.getGeneradoPor());

        log.info("Reporte con ID {} modificado exitosamente", id);
        return reportesRepository.save(reporteExistente);
    }

    public void eliminarPorId(Long id) {
        log.info("Intentando eliminar el reporte con ID: {}", id);

        Reportes reporte = obtenerPorId(id);
        reportesRepository.delete(reporte);

        log.info("Reporte con ID {} removido del sistema permanentemente", id);
    }
}
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
}
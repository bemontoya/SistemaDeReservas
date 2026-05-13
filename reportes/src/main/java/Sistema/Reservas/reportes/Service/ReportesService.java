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

    public List<Reportes> obtenerTodos(){
        log.info("Accediendo al historial de reportes generados");
        return reportesRepository.findAll();
    }

    public List <Reportes> obtenerPorTipo(String tipo){
        log.info("Buscando reportes de tipo: {}", tipo);
        return reportesRepository.findByTipo(tipo);
    }

    public Reportes crearReporte(Reportes reporte){
        log.info("Generando nuevo reporte tipo: {} por el usuario: {}",
                reporte.getTipo(), reporte.getGeneradoPor());
        return reportesRepository.save(reporte);
    }

    public Reportes obtenerPorId(Long id){
        return reportesRepository.findById(id)
                .orElseThrow(() ->  {
                    log.error("No se encontró el reporte con ID: {}", id);
                    return new RuntimeException("Reporte no encontrado");
                });
    }
}

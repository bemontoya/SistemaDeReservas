package Sistema.Reservas.reportes.controller;

import Sistema.Reservas.reportes.dto.ReportesDTO;
import Sistema.Reservas.reportes.model.Reportes;
import Sistema.Reservas.reportes.Service.ReportesService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reportes")
public class ReportesController {

    @Autowired
    private ReportesService reportesService;

    @GetMapping
    public List<Reportes> listarHistorial() {
        return reportesService.obtenerTodos();
    }

    @PostMapping
    public ResponseEntity<Reportes> registrarReporte(@Valid @RequestBody ReportesDTO reportesDTO) {

        Reportes reporteEntidad = new Reportes();
        reporteEntidad.setNombre(reportesDTO.getNombre());
        reporteEntidad.setTipo(reportesDTO.getTipo());
        reporteEntidad.setContenido(reportesDTO.getContenido());
        reporteEntidad.setGeneradoPor(reportesDTO.getGeneradoPor());

        Reportes nuevoReporte = reportesService.guardarReporte(reporteEntidad);

        return new ResponseEntity<>(nuevoReporte, HttpStatus.CREATED);
    }
}
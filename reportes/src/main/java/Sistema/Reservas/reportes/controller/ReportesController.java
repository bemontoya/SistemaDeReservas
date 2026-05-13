package Sistema.Reservas.reportes.controller;

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
    public ResponseEntity<List<Reportes>> listarReportes(){
        return ResponseEntity.ok(reportesService.obtenerTodos());
    }

    @PostMapping
    public ResponseEntity<Reportes> generarReporte(@Valid @RequestBody Reportes reporte){
        Reportes nuevoReporte = reportesService.crearReporte(reporte);
        return new ResponseEntity<>(nuevoReporte, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reportes> buscarPorId(@PathVariable Long id){
        return ResponseEntity.ok(reportesService.obtenerPorId(id));
    }
}

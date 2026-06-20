package Sistema.Reservas.reportes.controller;

import Sistema.Reservas.reportes.dto.ReportesDTO;
import Sistema.Reservas.reportes.model.Reportes;
import Sistema.Reservas.reportes.Service.ReportesService;
import io.swagger.v3.oas.annotations.Operation;
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
@RequestMapping("/api/reportes")
@Tag(name = "Reportes", description = "Controlador para la generación y gestión del historial de reportes del sistema")
public class ReportesController {

    @Autowired
    private ReportesService reportesService;

    @Operation(summary = "Listar historial de reportes", description = "Obtiene una lista con todos los reportes generados en el sistema")
    @GetMapping
    public ResponseEntity<List<Reportes>> listarHistorial() {
        return ResponseEntity.ok(reportesService.obtenerTodos());
    }

    @Operation(summary = "Obtener un reporte por ID", description = "Busca un reporte específico utilizando su identificador único")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reporte encontrado con éxito"),
            @ApiResponse(responseCode = "404", description = "Reporte no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Reportes> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(reportesService.obtenerPorId(id));
    }

    @Operation(summary = "Registrar un nuevo reporte", description = "Crea y guarda el registro de un nuevo reporte generado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Reporte registrado de forma exitosa"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos")
    })
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

    @Operation(summary = "Actualizar un reporte existente", description = "Modifica los datos de un reporte basándose en su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reporte actualizado correctamente"),
            @ApiResponse(responseCode = "404", description = "Reporte no encontrado para actualizar")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Reportes> actualizarReporte(@PathVariable Long id, @Valid @RequestBody ReportesDTO reportesDTO) {
        Reportes datosActualizados = new Reportes();
        datosActualizados.setNombre(reportesDTO.getNombre());
        datosActualizados.setTipo(reportesDTO.getTipo());
        datosActualizados.setContenido(reportesDTO.getContenido());
        datosActualizados.setGeneradoPor(reportesDTO.getGeneradoPor());

        Reportes reporteModificado = reportesService.actualizarReporte(id, datosActualizados);
        return ResponseEntity.ok(reporteModificado);
    }

    @Operation(summary = "Eliminar un reporte", description = "Remueve un reporte permanentemente del historial usando su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Reporte eliminado con éxito"),
            @ApiResponse(responseCode = "404", description = "Reporte no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarReporte(@PathVariable Long id) {
        reportesService.eliminarPorId(id);
        return ResponseEntity.noContent().build();
    }
}
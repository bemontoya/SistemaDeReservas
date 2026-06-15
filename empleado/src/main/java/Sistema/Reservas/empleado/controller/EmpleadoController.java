package Sistema.Reservas.empleado.controller;

import Sistema.Reservas.empleado.dto.EmpleadoDTO;
import Sistema.Reservas.empleado.model.Empleado;
import Sistema.Reservas.empleado.service.EmpleadoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/empleados")
@Tag(name = "Módulo de Empleados", description = "Controlador para la gestión y administración del personal del restaurante")
public class EmpleadoController {

    @Autowired
    private EmpleadoService empleadoService;

    @GetMapping
    @Operation(summary = "Listar todos los empleados", description = "Retorna una lista completa con todos los empleados registrados en el sistema")
    public List<Empleado> listarTodos() {
        return empleadoService.obtenerTodos();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener empleado por ID", description = "Busca y retorna un empleado específico usando su identificador único")
    public ResponseEntity<Empleado> obtenerPorId(@PathVariable Long id) {
        try {
            Empleado empleado = empleadoService.obtenerPorId(id);
            return ResponseEntity.ok(empleado);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping
    @Operation(summary = "Registrar un nuevo empleado", description = "Crea un nuevo empleado en la base de datos validando los datos de entrada")
    public ResponseEntity<Empleado> crear(@Valid @RequestBody EmpleadoDTO empleadoDTO) {
        Empleado nuevoEmpleado = new Empleado();
        nuevoEmpleado.setNombre(empleadoDTO.getNombre());
        nuevoEmpleado.setApellido(empleadoDTO.getApellido());
        nuevoEmpleado.setCargo(empleadoDTO.getCargo());
        nuevoEmpleado.setEmail(empleadoDTO.getEmail());

        Empleado empleadoCreado = empleadoService.crearEmpleado(nuevoEmpleado);
        return ResponseEntity.status(HttpStatus.CREATED).body(empleadoCreado);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un empleado existente", description = "Modifica los datos de un empleado según su ID utilizando un DTO válido")
    public ResponseEntity<Empleado> actualizar(@PathVariable Long id, @Valid @RequestBody EmpleadoDTO empleadoDTO) {
        try {
            Empleado empleadoExistente = empleadoService.obtenerPorId(id);

            empleadoExistente.setNombre(empleadoDTO.getNombre());
            empleadoExistente.setApellido(empleadoDTO.getApellido());
            empleadoExistente.setCargo(empleadoDTO.getCargo());
            empleadoExistente.setEmail(empleadoDTO.getEmail());

            Empleado empleadoActualizado = empleadoService.actualizarEmpleado(empleadoExistente);
            return ResponseEntity.ok(empleadoActualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un empleado", description = "Elimina de forma permanente un empleado del sistema a partir de su ID")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        try {
            // Verificamos si existe antes de proceder a borrar
            empleadoService.obtenerPorId(id);
            empleadoService.eliminarPorId(id);
            return ResponseEntity.noContent().build(); // Retorna un 204 No Content
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
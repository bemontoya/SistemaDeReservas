package Sistema.Reservas.empleado.controller;

import Sistema.Reservas.empleado.dto.EmpleadoDTO;
import Sistema.Reservas.empleado.model.Empleado;
import Sistema.Reservas.empleado.service.EmpleadoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/empleados")
public class EmpleadoController {

    @Autowired
    private EmpleadoService empleadoService;

    // El GET puede seguir retornando la entidad completa para ver el historial
    @GetMapping
    public List<Empleado> listar() {
        return empleadoService.obtenerTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Empleado> obtenerPorId(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(empleadoService.obtenerPorId(id));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }


    @PostMapping
    public ResponseEntity<Empleado> crear(@Valid @RequestBody EmpleadoDTO empleadoDTO) {


        Empleado empleadoEntidad = new Empleado();
        empleadoEntidad.setNombre(empleadoDTO.getNombre());
        empleadoEntidad.setApellido(empleadoDTO.getApellido());
        empleadoEntidad.setCargo(empleadoDTO.getCargo());
        empleadoEntidad.setEmail(empleadoDTO.getEmail());

        // Guardamos la entidad procesada a través del servicio
        Empleado nuevoEmpleado = empleadoService.crearEmpleado(empleadoEntidad);

        return new ResponseEntity<>(nuevoEmpleado, HttpStatus.CREATED);
    }
}
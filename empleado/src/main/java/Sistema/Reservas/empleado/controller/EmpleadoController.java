//Es la cara externa del microservicio. Recibe las peticiones de Postman y entrega respuestas.

package Sistema.Reservas.empleado.controller;

import Sistema.Reservas.empleado.model.Empleado;
import Sistema.Reservas.empleado.service.EmpleadoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // Define que esta clase es un controlador API REST
@RequestMapping("/api/empleados") // Ruta base para todos los endpoints de este servicio
public class EmpleadoController {

    @Autowired // Inyecta el servicio de empleados
    private EmpleadoService empleadoService;

    // GET: http://localhost;8086/api/empleados
    @GetMapping
    public List<Empleado> listar(){
        return empleadoService.obtenerTodos();
    }

    // GET por ID: http://localhost:8086/api/empleados/1
    @GetMapping("/{id}")
    public ResponseEntity<Empleado> obtenerPorId(@PathVariable Long id) {
        try {
            // Intenta buscar el empleado y responde con un 200 OK
            return ResponseEntity.ok(empleadoService.obtenerPorId(id));
        } catch (RuntimeException e) {
            // Si falla, captura la excepción y responde con un 404 Not Found
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // POST: http://localhost:8086/api/empleados
    @PostMapping
    public ResponseEntity<Empleado> crear(@Valid @RequestBody Empleado empleado) {
        // @Valid: Ejecuta las validaciones del modelo (@NotBlank, @Email)
        // @RequestBody: Convierte el JSON de Postman en un objeto Java
        Empleado nuevoEmpleado = empleadoService.crearEmpleado(empleado);
        // Responde con un 201 Created y el objeto creado
        return new ResponseEntity<>(nuevoEmpleado, HttpStatus.CREATED);
    }
}

//Esta clase contiene la logica de negocio

package Sistema.Reservas.empleado.service;

import Sistema.Reservas.empleado.model.Empleado;
import Sistema.Reservas.empleado.repository.EmpleadoRepository;
import org.slf4j.Logger; //Permite escribir mensajes de eventos(errores, etc)
import org.slf4j.LoggerFactory; //Crea el objeto que permite registrar mensajes
import org.springframework.beans.factory.annotation.Autowired; //Importa la anotacion Autowired para la inyeccion de dependencias automaticas
import org.springframework.stereotype.Service; // Marca la clase cmo un componente de servicio
import java.util.List;

@Service
public class EmpleadoService {
    // Se crea el logger para dejar rastro de lo que pasa en la consola de IntelliJ
    private static final Logger log = LoggerFactory.getLogger(EmpleadoService.class);

    @Autowired // Inyecta automaticamente el repositorio
    private EmpleadoRepository empleadoRepository;

    // Metodo para obtener todos los empleados
    public List<Empleado> obtenerTodos(){
        log.info("Consultando la lista de todos los empleados");
        return empleadoRepository.findAll();
    }

    // Metodo para registrar un nuevo empleado
    public Empleado crearEmpleado(Empleado empleado){
        log.info("Contratando nuevo empleado: {} {}", empleado.getNombre(), empleado.getApellido());
        return empleadoRepository.save(empleado); // Guardar el objeto en la base de datos
    }


    // Método para buscar empleado por su ID
    public Empleado obtenerPorId(Long id){
        return empleadoRepository.findById(id)
                .orElseThrow(() -> {
                    // Si no lo encuentra, lanza un error y lo registra en el log
                    log.error("Empleado con ID {} no encontrado", id);
                    return new RuntimeException("Empleado no encontrado");
                        }
                );
    }
}

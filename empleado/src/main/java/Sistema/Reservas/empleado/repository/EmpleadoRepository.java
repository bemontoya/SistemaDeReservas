//Es el encargado de realizar las consultas a la base de datos.

package Sistema.Reservas.empleado.repository;

import Sistema.Reservas.empleado.model.Empleado;
import org.springframework.data.jpa.repository.JpaRepository; //Ofrece funciones como save() findALL(), etc.
import org.springframework.stereotype.Repository; //Permite usar anotacion @Repository

@Repository // Marca la interfaz como un componente de acceso a datos de Spring
public interface EmpleadoRepository extends JpaRepository<Empleado, Long> {
    // Hereda métodos como save(), findById(), delte() y findALL()
    // No se necesita escribir sql gracias al Spring Data JPA
}

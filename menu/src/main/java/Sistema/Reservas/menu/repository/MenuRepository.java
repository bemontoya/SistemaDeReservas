package Sistema.Reservas.menu.repository;

import Sistema.Reservas.menu.model.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface MenuRepository extends JpaRepository<Menu, Long> {
    List<Menu> findByCategoria(String categoria);
    List<Menu> findByDisponibleTrue(); //Para listar solo lo que se puede pedir
}

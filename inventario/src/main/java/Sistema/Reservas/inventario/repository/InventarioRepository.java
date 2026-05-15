package Sistema.Reservas.inventario.repository;

import Sistema.Reservas.inventario.model.Inventario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface InventarioRepository extends JpaRepository<Inventario, Long> {

    // Consulta personalizada para encontrar insumos que están por agotarse
    @Query("SELECT i FROM Inventario i WHERE i.cantidadActual <= i.stockMinimo")
    List<Inventario> findInsumosCriticos();
}
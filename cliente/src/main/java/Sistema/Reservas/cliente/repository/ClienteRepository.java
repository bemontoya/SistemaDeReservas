package Sistema.Reservas.cliente.repository;

import Sistema.Reservas.cliente.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    //Método para buscar por email para validar registro
    Optional<Cliente> findByEmail(String email);
}

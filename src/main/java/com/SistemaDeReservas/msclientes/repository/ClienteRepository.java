package com.SistemaDeReservas.msclientes.repository;

import com.SistemaDeReservas.msclientes.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    Optional<Cliente> findByEmail(String email);

    List<Cliente> findByActivoTrue();

    boolean existsByEmail(String email);
}

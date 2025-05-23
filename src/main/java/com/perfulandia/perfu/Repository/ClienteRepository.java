package com.perfulandia.perfu.Repository;

import com.perfulandia.perfu.Model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
    // Métodos personalizados para buscar clientes
    Optional<Cliente> findByCorreo(String correo);
    List<Cliente> findByNombreContaining(String nombre);
}

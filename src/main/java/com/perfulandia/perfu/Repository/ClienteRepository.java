package com.perfulandia.perfu.Repository;

import com.perfulandia.perfu.Model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer>{
}
// me perturba que este tan vacio
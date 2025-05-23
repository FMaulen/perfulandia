package com.perfulandia.perfu.Repository;

import com.perfulandia.perfu.Model.DetallePedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmpleadoDeVentasRepository extends JpaRepository<DetallePedido, Integer> {
}

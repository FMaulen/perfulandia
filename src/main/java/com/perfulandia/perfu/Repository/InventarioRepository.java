package com.perfulandia.perfu.Repository;

import com.perfulandia.perfu.Model.Inventario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventarioRepository extends JpaRepository<Inventario, Integer> {
}
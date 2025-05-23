package com.perfulandia.perfu.Repository;

import com.perfulandia.perfu.Model.GerenteSucursal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GerenteSucursalRepository extends JpaRepository<GerenteSucursal, Integer> {
}

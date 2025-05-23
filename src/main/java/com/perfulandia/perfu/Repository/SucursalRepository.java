package com.perfulandia.perfu.Repository;

import com.perfulandia.perfu.Model.Cliente;
import com.perfulandia.perfu.Model.Producto;
import com.perfulandia.perfu.Model.Sucursal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SucursalRepository extends JpaRepository<Sucursal, Integer> {
    // Métodos personalizados para buscar sucursales
    List<Sucursal> findByNombre(String nombre);

    // Buscar sucursales por estado/ciudad si los tienes en tu entidad
    List<Sucursal> findByCiudad(String ciudad);

    // Buscar sucursales activas si tienes un campo de estado
    List<Sucursal> findByActivo(boolean activo);

    // Buscar por gerente si tienes esa relación
    Optional<Sucursal> findByGerenteSucursalId(int id_gerente);
}

package com.perfulandia.perfu.Repository;

import com.perfulandia.perfu.Model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Integer> {
    // Métodos personalizados para buscar productos
    List<Producto> findByNombreContaining(String nombre);
    List<Producto> findByCategoria(String categoria);
    List<Producto> findByPrecioBetween(double precioMin, double precioMax);
}

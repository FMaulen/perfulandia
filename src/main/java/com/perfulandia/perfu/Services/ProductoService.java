package com.perfulandia.perfu.Services;

import com.perfulandia.perfu.Model.Producto;
import com.perfulandia.perfu.Repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    public Producto agregarProducto(Producto producto) {
        return productoRepository.save(producto);
    }

    public List<Producto> listarProductos() {
        return productoRepository.findAll();
    }

    public Optional<Producto> buscarProducto(int id) {
        return productoRepository.findById(id);
    }

    public void eliminarProducto(int id) {
        productoRepository.deleteById(id);
    }

    public Optional<Producto> actualizarProducto(int id, Producto productoDetails) {
        return productoRepository.findById(id)
                .map(existente -> {
                    existente.setNombre(productoDetails.getNombre());
                    existente.setDescripcion(productoDetails.getDescripcion());
                    existente.setPrecio(productoDetails.getPrecio());
                    existente.setCategoria(productoDetails.getCategoria());

                    return productoRepository.save(existente);
                });
    }
}

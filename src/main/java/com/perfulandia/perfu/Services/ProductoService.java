package com.perfulandia.perfu.Services;

import com.perfulandia.perfu.Model.Producto;
import com.perfulandia.perfu.Repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    // Registrar producto
    public String agregarProducto(Producto producto) {
        productoRepository.save(producto);
        return "Producto agregado correctamente";
    }

    // Listar todos
    public String listarProductos() {
        StringBuilder output = new StringBuilder();
        for (Producto p : productoRepository.findAll()) {
            output.append("ID: ").append(p.getId_producto()).append("\n")
                    .append("Nombre: ").append(p.getNombre()).append("\n")
                    .append("Precio: $").append(p.getPrecio()).append("\n\n");
        }
        return output.length() > 0 ? output.toString() : "No hay productos";
    }

    // Buscar por ID
    public String buscarProducto(int id) {
        return productoRepository.findById(id)
                .map(p -> "ID: " + p.getId_producto() + "\n" +
                        "Nombre: " + p.getNombre() + "\n" +
                        "Stock: " + p.getStock_total())
                .orElse("Producto no encontrado");
    }

    // Eliminar
    public String eliminarProducto(int id) {
        if (productoRepository.existsById(id)) {
            productoRepository.deleteById(id);
            return "Producto eliminado";
        }
        return "Producto no existe";
    }

    // Actualizar
    public String actualizarProducto(int id, Producto producto) {
        if (productoRepository.existsById(id)) {
            Producto existente = productoRepository.findById(id).get();
            existente.setNombre(producto.getNombre());
            existente.setPrecio(producto.getPrecio());
            existente.setStock_total(producto.getStock_total());
            productoRepository.save(existente);
            return "Producto actualizado";
        }
        return "Producto no existe";
    }
}

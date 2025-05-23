package com.perfulandia.perfu.Controller;

import com.perfulandia.perfu.Model.Producto;
import com.perfulandia.perfu.Services.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @PostMapping
    public String crear(@RequestBody Producto producto) {
        return productoService.agregarProducto(producto);
    }

    @GetMapping
    public String listar() {
        return productoService.listarProductos();
    }

    @GetMapping("/{id}")
    public String buscar(@PathVariable int id) {
        return productoService.buscarProducto(id);
    }

    @DeleteMapping("/{id}")
    public String eliminar(@PathVariable int id) {
        return productoService.eliminarProducto(id);
    }

    @PutMapping("/{id}")
    public String actualizar(@PathVariable int id, @RequestBody Producto producto) {
        return productoService.actualizarProducto(id, producto);
    }
}

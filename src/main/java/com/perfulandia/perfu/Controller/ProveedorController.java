package com.perfulandia.perfu.Controller;

import com.perfulandia.perfu.Model.Proveedor;
import com.perfulandia.perfu.Services.ProveedorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/proveedores")
public class ProveedorController {

    @Autowired
    private ProveedorService proveedorService;

    @PostMapping
    public String crearProveedor(@RequestBody Proveedor proveedor) {
        return proveedorService.registrarProveedor(proveedor);
    }

    @GetMapping
    public String listarProveedores() {
        return proveedorService.listarProveedores();
    }

    @GetMapping("/{id}")
    public String obtenerProveedor(@PathVariable int id) {
        return proveedorService.buscarProveedorPorId(id);
    }

    @DeleteMapping("/{id}")
    public String eliminarProveedor(@PathVariable int id) {
        return proveedorService.eliminarProveedor(id);
    }

    @PutMapping("/{id}")
    public String actualizarProveedor(@PathVariable int id, @RequestBody Proveedor proveedor) {
        return proveedorService.actualizarProveedor(id, proveedor);
    }
}

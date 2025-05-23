package com.perfulandia.perfu.Controller;

import com.perfulandia.perfu.Model.Sucursal;
import com.perfulandia.perfu.Services.SucursalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sucursales")
public class SucursalController {

    @Autowired
    private SucursalService sucursalService;

    @PostMapping
    public String crearSucursal(@RequestBody Sucursal sucursal) {
        return sucursalService.registrarSucursal(sucursal);
    }

    @GetMapping
    public String listarSucursales() {
        return sucursalService.listarSucursales();
    }

    @GetMapping("/{id}")
    public String obtenerSucursal(@PathVariable int id) {
        return sucursalService.buscarSucursalPorId(id);
    }

    @DeleteMapping("/{id}")
    public String eliminarSucursal(@PathVariable int id) {
        return sucursalService.eliminarSucursal(id);
    }

    @PutMapping("/{id}")
    public String actualizarSucursal(@PathVariable int id, @RequestBody Sucursal sucursal) {
        return sucursalService.actualizarSucursal(id, sucursal);
    }
}

package com.perfulandia.perfu.Controller;

import com.perfulandia.perfu.Model.Inventario;
import com.perfulandia.perfu.Services.InventarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/inventario")
public class InventarioController {
    @Autowired
    private InventarioService inventarioService;

    @PostMapping
    public String agregarItem(@RequestBody Inventario item) {
        return inventarioService.registrarItem(item);
    }

    @GetMapping
    public String listarTodo() {
        return inventarioService.listarInventario();
    }

    @GetMapping("/{id}")
    public String buscarItem(@PathVariable int id) {
        return inventarioService.buscarItemPorID(id);
    }

    @DeleteMapping("/{id}")
    public String eliminarItem(@PathVariable int id) {
        return inventarioService.eliminarItemPorID(id);
    }

    @PutMapping("/{id}")
    public String actualizarItem(@PathVariable int id, @RequestBody Inventario item) {
        return inventarioService.actualizarItem(id, item);
    }

    @PatchMapping("/{id}/stock")
    public String ajustarStock(@PathVariable int id, @RequestParam int stock) {
        return inventarioService.actualizarStock(id, stock);
    }
}

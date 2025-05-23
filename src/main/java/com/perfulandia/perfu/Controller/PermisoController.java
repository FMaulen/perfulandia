package com.perfulandia.perfu.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.perfulandia.perfu.Services.PermisoService;

@RestController
@RequestMapping("/permisos")
public class PermisoController {

    @Autowired
    private PermisoService permisoService;

    @PostMapping
    public String crearPermiso(@RequestParam String nombre) {
        return permisoService.crearPermiso(nombre);
    }

    @GetMapping
    public String listarPermisos() {
        return permisoService.listarTodos();
    }

    @DeleteMapping("/{id}")
    public String eliminarPermiso(@PathVariable int id) {
        return permisoService.eliminarPermiso(id);
    }
}

package com.perfulandia.perfu.Controller;

import com.perfulandia.perfu.Model.AdministradorSistema;
import com.perfulandia.perfu.Services.AdministradorSistemaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin-sistema")
public class AdministradorSistemaController {

    @Autowired
    private AdministradorSistemaService adminService;

    @PostMapping
    public String crearAdmin(@RequestBody AdministradorSistema admin) {
        return adminService.registrarAdmin(admin);
    }

    @GetMapping
    public String listarAdmins() {
        return adminService.listarAdmins();
    }

    @GetMapping("/{id}")
    public String obtenerAdmin(@PathVariable int id) {
        return adminService.buscarAdminPorId(id);
    }

    @PatchMapping("/{id}/estado")
    public String cambiarEstado(@PathVariable int id, @RequestParam boolean activo) {
        return adminService.cambiarEstadoAdmin(id, activo);
    }

    @PostMapping("/{id}/permisos")
    public String asignarPermisos(@PathVariable int id, @RequestBody List<Integer> permisosIds) {
        return adminService.asignarPermisos(id, permisosIds);
    }

    @DeleteMapping("/{adminId}/permisos/{permisoId}")
    public String quitarPermiso(@PathVariable int adminId, @PathVariable int permisoId) {
        return adminService.quitarPermiso(adminId, permisoId);
    }
}

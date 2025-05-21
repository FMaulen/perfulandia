package com.perfulandia.perfu.Controller;

import com.perfulandia.perfu.Model.*;
import com.perfulandia.perfu.Services.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;


    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam String email,
                                   @RequestParam String password) {
        Usuario usuario = usuarioService.login(email, password);
        if (usuario != null && usuario.isActivo()) {
            return ResponseEntity.ok(usuario);
        }
        return ResponseEntity.status(401).body("Credenciales incorrectas o usuario inactivo");
    }

    @PostMapping("/administradores")
    public ResponseEntity<AdministradorSistema> crearAdministrador(@RequestBody AdministradorSistema admin) {
        return ResponseEntity.ok(usuarioService.crearAdministrador(admin));
    }

    @PostMapping("/gerentes")
    public ResponseEntity<GerenteSucursal> crearGerente(@RequestBody GerenteSucursal gerente,
                                                        @RequestParam Long sucursalId) {
        return ResponseEntity.ok(usuarioService.crearGerente(gerente, sucursalId));
    }

    @PostMapping("/empleados-ventas")
    public ResponseEntity<EmpleadoDeVentas> crearEmpleadoVentas(@RequestBody EmpleadoDeVentas empleado,
                                                                @RequestParam Long sucursalId) {
        return ResponseEntity.ok(usuarioService.crearEmpleadoVentas(empleado, sucursalId));
    }

    @PostMapping("/logistica")
    public ResponseEntity<Logistica> crearLogistica(@RequestBody Logistica logistica) {
        return ResponseEntity.ok(usuarioService.crearLogistica(logistica));
    }

    @PutMapping("/{id}/desactivar")
    public ResponseEntity<String> desactivarUsuario(@PathVariable Long id) {
        usuarioService.desactivarUsuario(id);
        return ResponseEntity.ok("Usuario desactivado");
    }
}
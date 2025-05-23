package com.perfulandia.perfu.Controller;

import com.perfulandia.perfu.Model.Envio;
import com.perfulandia.perfu.Services.EnvioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/envios")
@CrossOrigin(origins = "*")
public class EnvioController {
    @Autowired
    private EnvioService envioService;

    // Crear nuevo envío
    @PostMapping
    public ResponseEntity<?> crearEnvio(@RequestBody Envio envio) {
        try {
            Envio nuevoEnvio = envioService.crearEnvio(envio);
            return ResponseEntity.ok(nuevoEnvio);
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body("Error al crear el envío: " + e.getMessage());
        }
    }

    // Obtener envío por ID
    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerEnvio(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(envioService.obtenerEnvioPorId(id));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Buscar envío por número de seguimiento
    @GetMapping("/seguimiento/{numero}")
    public ResponseEntity<?> buscarPorSeguimiento(@PathVariable String numero) {
        try {
            return ResponseEntity.ok(envioService.obtenerEnvioPorSeguimiento(numero));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Actualizar estado del envío
    @PutMapping("/{id}/estado")
    public ResponseEntity<?> actualizarEstado(
            @PathVariable Long id,
            @RequestBody String nuevoEstado) {
        try {
            Envio envioActualizado = envioService.actualizarEstadoEnvio(id, nuevoEstado);
            return ResponseEntity.ok(envioActualizado);
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body("Error al actualizar el estado: " + e.getMessage());
        }
    }

    // Listar envíos por sucursal origen
    @GetMapping("/origen/{idSucursal}")
    public ResponseEntity<?> listarPorSucursalOrigen(@PathVariable Long idSucursal) {
        return ResponseEntity.ok(envioService.listarEnviosPorSucursalOrigen(idSucursal));
    }

    // Listar envíos por sucursal destino
    @GetMapping("/destino/{idSucursal}")
    public ResponseEntity<?> listarPorSucursalDestino(@PathVariable Long idSucursal) {
        return ResponseEntity.ok(envioService.listarEnviosPorSucursalDestino(idSucursal));
    }

    // Listar envíos por estado
    @GetMapping("/estado/{estado}")
    public ResponseEntity<?> listarPorEstado(@PathVariable String estado) {
        return ResponseEntity.ok(envioService.listarEnviosPorEstado(estado));
    }
}
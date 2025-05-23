package com.perfulandia.perfu.Controller;

import com.perfulandia.perfu.Model.Envio;
import com.perfulandia.perfu.Model.EnvioDTO;
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
    public ResponseEntity<?> crearEnvio(@RequestBody EnvioDTO envioDTO) {
        try {
            Envio nuevoEnvio = envioService.crearEnvio(envioDTO);
            return ResponseEntity.ok(nuevoEnvio);
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body("Error al crear el envío: " + e.getMessage());
        }
    }

    // ... resto de endpoints ...
}
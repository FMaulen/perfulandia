package com.perfulandia.perfu.Controller;

import com.perfulandia.perfu.Assembler.EnvioModelAssembler;
import com.perfulandia.perfu.Model.Envio;
import com.perfulandia.perfu.Services.EnvioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/envios")
@Tag(name = "Controlador de Envíos", description = "API para la gestión de envíos de pedidos")
public class EnvioController {

    @Autowired
    private EnvioService envioService;

    @Autowired
    private EnvioModelAssembler assembler;

    @GetMapping
    @Operation(summary = "Listar todos los envíos", description = "Devuelve una lista de todos los envíos registrados.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de envíos encontrada"),
            @ApiResponse(responseCode = "404", description = "No se encontraron envíos")
    })
    public ResponseEntity<CollectionModel<EntityModel<Envio>>> listarEnvios() {
        List<Envio> envios = envioService.listarEnvios();
        if (envios.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(assembler.toCollectionModel(envios), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener un envío por ID", description = "Busca y devuelve un envío por su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Envío encontrado"),
            @ApiResponse(responseCode = "404", description = "Envío no encontrado")
    })
    public ResponseEntity<EntityModel<Envio>> obtenerEnvio(
            @Parameter(description = "ID del envío a buscar", required = true, example = "1")
            @PathVariable int id
    ) {
        return envioService.buscarEnvioPorId(id)
                .map(assembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    @Operation(summary = "Crear un nuevo envío", description = "Crea un nuevo registro de envío para un pedido.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Envío creado exitosamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Envio.class)))
    })
    public ResponseEntity<EntityModel<Envio>> crearEnvio(@RequestBody Envio envio) {
        Envio nuevoEnvio = envioService.agregarEnvio(envio);
        return new ResponseEntity<>(assembler.toModel(nuevoEnvio), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un envío", description = "Actualiza los datos de un envío existente.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Envío actualizado"),
            @ApiResponse(responseCode = "404", description = "Envío no encontrado")
    })
    public ResponseEntity<EntityModel<Envio>> actualizarEnvio(
            @Parameter(description = "ID del envío a actualizar", required = true, example = "1")
            @PathVariable int id,
            @RequestBody Envio envio
    ) {
        return envioService.actualizarEnvio(id, envio)
                .map(assembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un envío", description = "Elimina un registro de envío por su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Envío eliminado"),
            @ApiResponse(responseCode = "404", description = "Envío no encontrado")
    })
    public ResponseEntity<?> eliminarEnvio(
            @Parameter(description = "ID del envío a eliminar", required = true, example = "1")
            @PathVariable int id
    ) {
        if (envioService.buscarEnvioPorId(id).isPresent()) {
            envioService.eliminarEnvio(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}

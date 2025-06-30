package com.perfulandia.perfu.Controller;

import com.perfulandia.perfu.Assembler.LogisticaModelAssembler;
import com.perfulandia.perfu.Model.Logistica;
import com.perfulandia.perfu.Services.LogisticaService;
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
@RequestMapping("/logisticas")
@Tag(name = "Controlador de Logística", description = "API para la gestión de empresas de logística")
public class LogisticaController {

    @Autowired
    private LogisticaService logisticaService;

    @Autowired
    private LogisticaModelAssembler assembler;

    @GetMapping
    @Operation(summary = "Listar todas las empresas de logística", description = "Devuelve una lista de todas las empresas de logística.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de empresas encontrada"),
            @ApiResponse(responseCode = "404", description = "No se encontraron empresas")
    })
    public ResponseEntity<CollectionModel<EntityModel<Logistica>>> listarLogisticas() {
        List<Logistica> logisticas = logisticaService.listarLogisticas();
        if (logisticas.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(assembler.toCollectionModel(logisticas), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener una empresa de logística por ID", description = "Busca y devuelve una empresa por su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Empresa encontrada"),
            @ApiResponse(responseCode = "404", description = "Empresa no encontrada")
    })
    @Parameter(description = "ID de la empresa de logística a buscar", required = true, example = "1")
    public ResponseEntity<EntityModel<Logistica>> obtenerLogistica(@PathVariable int id) {
        return logisticaService.buscarLogisticaPorId(id)
                .map(assembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    @Operation(summary = "Crear una nueva empresa de logística", description = "Crea una nueva empresa en el sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Empresa creada exitosamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Logistica.class)))
    })
    public ResponseEntity<EntityModel<Logistica>> crearLogistica(@RequestBody Logistica logistica) {
        Logistica nuevaLogistica = logisticaService.agregarLogistica(logistica);
        return new ResponseEntity<>(assembler.toModel(nuevaLogistica), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar una empresa de logística", description = "Actualiza los datos de una empresa existente.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Empresa actualizada"),
            @ApiResponse(responseCode = "404", description = "Empresa no encontrada")
    })
    @Parameter(description = "ID de la empresa a actualizar", required = true, example = "1")
    public ResponseEntity<EntityModel<Logistica>> actualizarLogistica(@PathVariable int id, @RequestBody Logistica logistica) {
        return logisticaService.actualizarLogistica(id, logistica)
                .map(assembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/{logisticaId}/proveedores/{proveedorId}")
    @Operation(summary = "Asignar un proveedor a una empresa de logística", description = "Crea una relación entre una empresa de logística y un proveedor.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Proveedor asignado"),
            @ApiResponse(responseCode = "404", description = "Logística o proveedor no encontrado")
    })
    @Parameter(description = "ID de la empresa de logística", required = true, example = "1")
    @Parameter(description = "ID del proveedor a asignar", required = true, example = "1")
    public ResponseEntity<EntityModel<Logistica>> asignarProveedor(@PathVariable int logisticaId, @PathVariable int proveedorId) {
        return logisticaService.asignarProveedor(logisticaId, proveedorId)
                .map(assembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar una empresa de logística", description = "Elimina una empresa por su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Empresa eliminada"),
            @ApiResponse(responseCode = "404", description = "Empresa no encontrada")
    })
    @Parameter(description = "ID de la empresa a eliminar", required = true, example = "1")
    public ResponseEntity<?> eliminarLogistica(@PathVariable int id) {
        if (logisticaService.buscarLogisticaPorId(id).isPresent()) {
            logisticaService.eliminarLogistica(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}

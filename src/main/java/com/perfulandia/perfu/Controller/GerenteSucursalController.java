package com.perfulandia.perfu.Controller;

import com.perfulandia.perfu.Assembler.GerenteSucursalModelAssembler;
import com.perfulandia.perfu.Model.GerenteSucursal;
import com.perfulandia.perfu.Services.GerenteSucursalService;
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
@RequestMapping("/gerentes-sucursal")
@Tag(name = "Controlador Gerentes de Sucursal", description = "API para la gestión de gerentes de sucursal")
public class GerenteSucursalController {

    @Autowired
    private GerenteSucursalService gerenteService;

    @Autowired
    private GerenteSucursalModelAssembler assembler;

    @GetMapping
    @Operation(summary = "Listar todos los gerentes de sucursal", description = "Devuelve una lista de todos los gerentes.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de gerentes encontrada"),
            @ApiResponse(responseCode = "404", description = "No se encontraron gerentes")
    })
    public ResponseEntity<CollectionModel<EntityModel<GerenteSucursal>>> getGerentes() {
        List<GerenteSucursal> gerentes = gerenteService.listarGerentes();
        if (gerentes.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(assembler.toCollectionModel(gerentes), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener un gerente por ID", description = "Busca y devuelve un gerente por su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Gerente encontrado"),
            @ApiResponse(responseCode = "404", description = "Gerente no encontrado con ese ID")
    })
    @Parameter(description = "ID del gerente a buscar", required = true, example = "1")
    public ResponseEntity<EntityModel<GerenteSucursal>> getGerentePorId(@PathVariable Long id) {
        return gerenteService.obtenerGerentePorId(id)
                .map(assembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    @Operation(summary = "Agregar un nuevo gerente", description = "Crea un nuevo gerente en el sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Gerente creado exitosamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = GerenteSucursal.class)))
    })
    public ResponseEntity<EntityModel<GerenteSucursal>> addGerente(@RequestBody GerenteSucursal gerente) {
        GerenteSucursal nuevoGerente = gerenteService.agregarGerente(gerente);
        return new ResponseEntity<>(assembler.toModel(nuevoGerente), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un gerente", description = "Actualiza los datos de un gerente por su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Gerente actualizado"),
            @ApiResponse(responseCode = "404", description = "Gerente no encontrado")
    })
    @Parameter(description = "ID del gerente a actualizar", required = true, example = "1")
    public ResponseEntity<EntityModel<GerenteSucursal>> updateGerente(@PathVariable Long id, @RequestBody GerenteSucursal gerente) {
        return gerenteService.actualizarGerente(id, gerente)
                .map(assembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un gerente por ID", description = "Elimina un gerente del sistema por su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Gerente eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Gerente no encontrado")
    })
    @Parameter(description = "ID del gerente a eliminar", required = true, example = "1")
    public ResponseEntity<?> deleteGerente(@PathVariable Long id) {
        if (gerenteService.obtenerGerentePorId(id).isPresent()) {
            gerenteService.eliminarGerentePorId(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}

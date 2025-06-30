package com.perfulandia.perfu.Controller;

import com.perfulandia.perfu.Assembler.SucursalModelAssembler;
import com.perfulandia.perfu.Model.Sucursal;
import com.perfulandia.perfu.Services.SucursalService;
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
@RequestMapping("/sucursales")
@Tag(name = "Controlador de Sucursales", description = "API para la gestión de sucursales")
public class SucursalController {

    @Autowired
    private SucursalService sucursalService;

    @Autowired
    private SucursalModelAssembler assembler;

    @GetMapping
    @Operation(summary = "Listar todas las sucursales", description = "Devuelve una lista de todas las sucursales registradas.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de sucursales encontrada"),
            @ApiResponse(responseCode = "404", description = "No se encontraron sucursales")
    })
    public ResponseEntity<CollectionModel<EntityModel<Sucursal>>> listarSucursales() {
        List<Sucursal> sucursales = sucursalService.listarSucursales();
        if (sucursales.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(assembler.toCollectionModel(sucursales), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener una sucursal por ID", description = "Busca y devuelve una sucursal por su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sucursal encontrada"),
            @ApiResponse(responseCode = "404", description = "Sucursal no encontrada")
    })
    @Parameter(description = "ID de la sucursal a buscar", required = true, example = "1")
    public ResponseEntity<EntityModel<Sucursal>> obtenerSucursal(@PathVariable int id) {
        return sucursalService.buscarSucursalPorId(id)
                .map(assembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    @Operation(summary = "Registrar una nueva sucursal", description = "Crea una nueva sucursal en el sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Sucursal creada exitosamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Sucursal.class)))
    })
    public ResponseEntity<EntityModel<Sucursal>> crearSucursal(@RequestBody Sucursal sucursal) {
        Sucursal nuevaSucursal = sucursalService.registrarSucursal(sucursal);
        return new ResponseEntity<>(assembler.toModel(nuevaSucursal), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar una sucursal", description = "Actualiza los datos de una sucursal existente.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sucursal actualizada"),
            @ApiResponse(responseCode = "404", description = "Sucursal no encontrada")
    })
    @Parameter(description = "ID de la sucursal a actualizar", required = true, example = "1")
    public ResponseEntity<EntityModel<Sucursal>> actualizarSucursal(@PathVariable int id, @RequestBody Sucursal sucursal) {
        return sucursalService.actualizarSucursal(id, sucursal)
                .map(assembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar una sucursal", description = "Elimina una sucursal del sistema por su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Sucursal eliminada"),
            @ApiResponse(responseCode = "404", description = "Sucursal no encontrada")
    })
    @Parameter(description = "ID de la sucursal a eliminar", required = true, example = "1")
    public ResponseEntity<?> eliminarSucursal(@PathVariable int id) {
        if (sucursalService.buscarSucursalPorId(id).isPresent()) {
            sucursalService.eliminarSucursal(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}

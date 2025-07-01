package com.perfulandia.perfu.Controller;

import com.perfulandia.perfu.Assembler.InventarioModelAssembler;
import com.perfulandia.perfu.Model.Inventario;
import com.perfulandia.perfu.Services.InventarioService;
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
@RequestMapping("/inventario")
@Tag(name = "Controlador de Inventario", description = "API para la gestión del stock de productos en sucursales")
public class InventarioController {

    @Autowired
    private InventarioService inventarioService;

    @Autowired
    private InventarioModelAssembler assembler;

    @GetMapping
    @Operation(summary = "Listar todos los items del inventario", description = "Devuelve una lista completa del inventario de todas las sucursales.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Inventario encontrado"),
            @ApiResponse(responseCode = "404", description = "No hay items en el inventario")
    })
    public ResponseEntity<CollectionModel<EntityModel<Inventario>>> listarTodo() {
        List<Inventario> inventario = inventarioService.listarInventario();
        if (inventario.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(assembler.toCollectionModel(inventario), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar un item de inventario por ID", description = "Busca un item de inventario específico por su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Item encontrado"),
            @ApiResponse(responseCode = "404", description = "Item no encontrado")
    })
    public ResponseEntity<EntityModel<Inventario>> buscarItem(
            @Parameter(description = "ID del item de inventario", required = true, example = "1")
            @PathVariable int id
    ) {
        return inventarioService.buscarItemPorID(id)
                .map(assembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    @Operation(summary = "Agregar un item al inventario", description = "Registra un nuevo producto con su stock en una sucursal.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Item creado exitosamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Inventario.class)))
    })
    public ResponseEntity<EntityModel<Inventario>> agregarItem(@RequestBody Inventario item) {
        Inventario nuevoItem = inventarioService.registrarItem(item);
        return new ResponseEntity<>(assembler.toModel(nuevoItem), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un item de inventario", description = "Elimina un registro de inventario por su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Item eliminado"),
            @ApiResponse(responseCode = "404", description = "Item no encontrado")
    })
    public ResponseEntity<?> eliminarItem(
            @Parameter(description = "ID del item a eliminar", required = true, example = "1")
            @PathVariable int id
    ) {
        if (inventarioService.buscarItemPorID(id).isPresent()) {
            inventarioService.eliminarItemPorID(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un item de inventario", description = "Actualiza todos los datos de un item de inventario.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Item actualizado"),
            @ApiResponse(responseCode = "404", description = "Item no encontrado")
    })
    public ResponseEntity<EntityModel<Inventario>> actualizarItem(
            @Parameter(description = "ID del item a actualizar", required = true, example = "1")
            @PathVariable int id,
            @RequestBody Inventario item
    ) {
        return inventarioService.actualizarItem(id, item)
                .map(assembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PatchMapping("/{id}/stock")
    @Operation(summary = "Ajustar el stock de un item", description = "Actualiza únicamente el stock de un item de inventario.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Stock actualizado"),
            @ApiResponse(responseCode = "404", description = "Item no encontrado")
    })
    public ResponseEntity<EntityModel<Inventario>> ajustarStock(
            @Parameter(description = "ID del item", required = true, example = "1")
            @PathVariable int id,
            @Parameter(description = "La nueva cantidad en stock", required = true, example = "50")
            @RequestParam int stock
    ) {
        return inventarioService.actualizarStock(id, stock)
                .map(assembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}

package com.perfulandia.perfu.Controller;

import com.perfulandia.perfu.Assembler.ResenaModelAssembler;
import com.perfulandia.perfu.Model.Resena;
import com.perfulandia.perfu.Services.ResenaService;
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
@RequestMapping("/resenas")
@Tag(name = "Controlador de Reseñas", description = "API para la gestión de reseñas de productos")
public class ResenaController {

    @Autowired
    private ResenaService resenaService;

    @Autowired
    private ResenaModelAssembler assembler;

    @GetMapping
    @Operation(summary = "Listar todas las reseñas", description = "Devuelve una lista de todas las reseñas de productos.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de reseñas encontrada"),
            @ApiResponse(responseCode = "404", description = "No se encontraron reseñas")
    })
    public ResponseEntity<CollectionModel<EntityModel<Resena>>> listarResenas() {
        List<Resena> resenas = resenaService.listarResenas();
        if (resenas.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(assembler.toCollectionModel(resenas), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener una reseña por ID", description = "Busca y devuelve una reseña por su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reseña encontrada"),
            @ApiResponse(responseCode = "404", description = "Reseña no encontrada")
    })
    @Parameter(description = "ID de la reseña a buscar", required = true, example = "1")
    public ResponseEntity<EntityModel<Resena>> obtenerResena(@PathVariable int id) {
        return resenaService.buscarResenaPorId(id)
                .map(assembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    @Operation(summary = "Crear una nueva reseña", description = "Crea una nueva reseña para un producto por parte de un cliente.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Reseña creada exitosamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Resena.class)))
    })
    public ResponseEntity<EntityModel<Resena>> crearResena(@RequestBody Resena resena) {
        Resena nuevaResena = resenaService.agregarResena(resena);
        return new ResponseEntity<>(assembler.toModel(nuevaResena), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar una reseña", description = "Actualiza el contenido de una reseña existente.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reseña actualizada"),
            @ApiResponse(responseCode = "404", description = "Reseña no encontrada")
    })
    @Parameter(description = "ID de la reseña a actualizar", required = true, example = "1")
    public ResponseEntity<EntityModel<Resena>> actualizarResena(@PathVariable int id, @RequestBody Resena resena) {
        return resenaService.actualizarResena(id, resena)
                .map(assembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar una reseña", description = "Elimina una reseña por su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Reseña eliminada"),
            @ApiResponse(responseCode = "404", description = "Reseña no encontrada")
    })
    @Parameter(description = "ID de la reseña a eliminar", required = true, example = "1")
    public ResponseEntity<?> eliminarResena(@PathVariable int id) {
        if (resenaService.buscarResenaPorId(id).isPresent()) {
            resenaService.eliminarResena(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}

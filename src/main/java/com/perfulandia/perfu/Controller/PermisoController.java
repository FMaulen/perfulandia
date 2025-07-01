package com.perfulandia.perfu.Controller;

import com.perfulandia.perfu.Assembler.PermisoModelAssembler;
import com.perfulandia.perfu.Model.Permiso;
import com.perfulandia.perfu.Services.PermisoService;
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
@RequestMapping("/permisos")
@Tag(name = "Controlador de Permisos", description = "API para la gestión de permisos de usuario")
public class PermisoController {

    @Autowired
    private PermisoService permisoService;

    @Autowired
    private PermisoModelAssembler assembler;

    @GetMapping
    @Operation(summary = "Listar todos los permisos", description = "Devuelve una lista de todos los permisos disponibles en el sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Permisos listados correctamente"),
            @ApiResponse(responseCode = "404", description = "No se encontraron permisos")
    })
    public ResponseEntity<CollectionModel<EntityModel<Permiso>>> listarPermisos() {
        List<Permiso> permisos = permisoService.listarTodos();
        if (permisos.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(assembler.toCollectionModel(permisos), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener un permiso por ID", description = "Busca y devuelve un permiso por su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Permiso encontrado"),
            @ApiResponse(responseCode = "404", description = "Permiso no encontrado")
    })
    public ResponseEntity<EntityModel<Permiso>> getPermisoById(
            @Parameter(description = "ID del permiso a buscar", required = true, example = "1")
            @PathVariable int id
    ) {
        return permisoService.buscarPorId(id)
                .map(assembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    @Operation(summary = "Crear un nuevo permiso", description = "Crea un nuevo permiso en el sistema a partir de su nombre.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Permiso creado exitosamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Permiso.class)))
    })
    public ResponseEntity<EntityModel<Permiso>> crearPermiso(@RequestBody Permiso permiso) {
        Permiso nuevoPermiso = permisoService.crearPermiso(permiso);
        return new ResponseEntity<>(assembler.toModel(nuevoPermiso), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un permiso", description = "Elimina un permiso del sistema por su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Permiso eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Permiso no encontrado")
    })
    public ResponseEntity<?> eliminarPermiso(
            @Parameter(description = "ID del permiso a eliminar", required = true, example = "1")
            @PathVariable int id
    ) {
        if (permisoService.buscarPorId(id).isPresent()) {
            permisoService.eliminarPermiso(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}

package com.perfulandia.perfu.Controller;

import com.perfulandia.perfu.Assembler.ProveedorModelAssembler;
import com.perfulandia.perfu.Model.Proveedor;
import com.perfulandia.perfu.Services.ProveedorService;
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
@RequestMapping("/proveedores")
@Tag(name = "Controlador de Proveedores", description = "API para la gestión de proveedores")
public class ProveedorController {

    @Autowired
    private ProveedorService proveedorService;

    @Autowired
    private ProveedorModelAssembler assembler;

    @GetMapping
    @Operation(summary = "Listar todos los proveedores", description = "Devuelve una lista de todos los proveedores registrados.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de proveedores encontrada"),
            @ApiResponse(responseCode = "404", description = "No se encontraron proveedores")
    })
    public ResponseEntity<CollectionModel<EntityModel<Proveedor>>> listarProveedores() {
        List<Proveedor> proveedores = proveedorService.listarProveedores();
        if (proveedores.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(assembler.toCollectionModel(proveedores), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener un proveedor por ID", description = "Busca y devuelve un proveedor por su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Proveedor encontrado"),
            @ApiResponse(responseCode = "404", description = "Proveedor no encontrado")
    })
    public ResponseEntity<EntityModel<Proveedor>> obtenerProveedor(
            @Parameter(description = "ID del proveedor a buscar", required = true, example = "1")
            @PathVariable int id
    ) {
        return proveedorService.buscarProveedorPorId(id)
                .map(assembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    @Operation(summary = "Registrar un nuevo proveedor", description = "Crea un nuevo proveedor en el sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Proveedor creado exitosamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Proveedor.class)))
    })
    public ResponseEntity<EntityModel<Proveedor>> crearProveedor(@RequestBody Proveedor proveedor) {
        Proveedor nuevoProveedor = proveedorService.registrarProveedor(proveedor);
        return new ResponseEntity<>(assembler.toModel(nuevoProveedor), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un proveedor", description = "Actualiza los datos de un proveedor existente.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Proveedor actualizado"),
            @ApiResponse(responseCode = "404", description = "Proveedor no encontrado")
    })
    public ResponseEntity<EntityModel<Proveedor>> actualizarProveedor(
            @Parameter(description = "ID del proveedor a actualizar", required = true, example = "1")
            @PathVariable int id,
            @RequestBody Proveedor proveedor
    ) {
        return proveedorService.actualizarProveedor(id, proveedor)
                .map(assembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un proveedor", description = "Elimina un proveedor del sistema por su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Proveedor eliminado"),
            @ApiResponse(responseCode = "404", description = "Proveedor no encontrado")
    })
    public ResponseEntity<?> eliminarProveedor(
            @Parameter(description = "ID del proveedor a eliminar", required = true, example = "1")
            @PathVariable int id
    ) {
        if (proveedorService.buscarProveedorPorId(id).isPresent()) {
            proveedorService.eliminarProveedor(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}

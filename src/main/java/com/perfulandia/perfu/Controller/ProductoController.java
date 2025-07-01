package com.perfulandia.perfu.Controller;

import com.perfulandia.perfu.Assembler.ProductoModelAssembler;
import com.perfulandia.perfu.Model.Producto;
import com.perfulandia.perfu.Services.ProductoService;
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
@RequestMapping("/productos")
@Tag(name = "Controlador de Productos", description = "API para la gestión del catálogo de productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @Autowired
    private ProductoModelAssembler assembler;

    @GetMapping
    @Operation(summary = "Listar todos los productos", description = "Devuelve una lista de todos los productos del catálogo.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de productos encontrada"),
            @ApiResponse(responseCode = "404", description = "No se encontraron productos")
    })
    public ResponseEntity<CollectionModel<EntityModel<Producto>>> listar() {
        List<Producto> productos = productoService.listarProductos();
        if (productos.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(assembler.toCollectionModel(productos), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar un producto por ID", description = "Busca y devuelve un producto específico por su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Producto encontrado"),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    })
    public ResponseEntity<EntityModel<Producto>> buscar(
            @Parameter(description = "ID del producto a buscar", required = true, example = "1")
            @PathVariable int id
    ) {
        return productoService.buscarProducto(id)
                .map(assembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    @Operation(summary = "Crear un nuevo producto", description = "Agrega un nuevo producto al catálogo.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Producto creado exitosamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Producto.class)))
    })
    public ResponseEntity<EntityModel<Producto>> crear(@RequestBody Producto producto) {
        Producto nuevoProducto = productoService.agregarProducto(producto);
        return new ResponseEntity<>(assembler.toModel(nuevoProducto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un producto", description = "Actualiza los datos de un producto existente por su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Producto actualizado"),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    })
    public ResponseEntity<EntityModel<Producto>> actualizar(
            @Parameter(description = "ID del producto a actualizar", required = true, example = "1")
            @PathVariable int id,
            @RequestBody Producto producto
    ) {
        return productoService.actualizarProducto(id, producto)
                .map(assembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un producto", description = "Elimina un producto del catálogo por su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Producto eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    })
    public ResponseEntity<?> eliminar(
            @Parameter(description = "ID del producto a eliminar", required = true, example = "1")
            @PathVariable int id
    ) {
        if (productoService.buscarProducto(id).isPresent()) {
            productoService.eliminarProducto(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}

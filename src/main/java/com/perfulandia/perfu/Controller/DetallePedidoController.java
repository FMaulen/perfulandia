package com.perfulandia.perfu.Controller;

import com.perfulandia.perfu.Assembler.DetallePedidoModelAssembler;
import com.perfulandia.perfu.Model.DetallePedido;
import com.perfulandia.perfu.Services.DetallePedidoService;
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
@RequestMapping("/detalles-pedido")
@Tag(name = "Controlador de Detalles de Pedido", description = "API para la gestión de los items dentro de un pedido")
public class DetallePedidoController {

    @Autowired
    private DetallePedidoService detalleService;

    @Autowired
    private DetallePedidoModelAssembler assembler;

    @GetMapping
    @Operation(summary = "Listar todos los detalles de pedidos", description = "Devuelve una lista de todos los items de todos los pedidos.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de detalles encontrada"),
            @ApiResponse(responseCode = "404", description = "No se encontraron detalles de pedidos")
    })
    public ResponseEntity<CollectionModel<EntityModel<DetallePedido>>> listarDetalles() {
        List<DetallePedido> detalles = detalleService.listarDetalles();
        if (detalles.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(assembler.toCollectionModel(detalles), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener un detalle de pedido por ID", description = "Busca y devuelve un item de pedido específico por su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Detalle encontrado"),
            @ApiResponse(responseCode = "404", description = "Detalle no encontrado")
    })
    public ResponseEntity<EntityModel<DetallePedido>> obtenerDetalle(
            @Parameter(description = "ID del detalle de pedido a buscar", required = true, example = "1")
            @PathVariable int id
    ) {
        return detalleService.buscarDetallePorId(id)
                .map(assembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    @Operation(summary = "Agregar un nuevo detalle a un pedido", description = "Crea un nuevo item dentro de un pedido.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Detalle creado y agregado al pedido",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = DetallePedido.class)))
    })
    public ResponseEntity<EntityModel<DetallePedido>> crearDetalle(@RequestBody DetallePedido detalle) {
        DetallePedido nuevoDetalle = detalleService.agregarDetalle(detalle);
        return new ResponseEntity<>(assembler.toModel(nuevoDetalle), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un detalle de pedido", description = "Actualiza los datos de un item de pedido existente.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Detalle actualizado"),
            @ApiResponse(responseCode = "404", description = "Detalle no encontrado")
    })
    public ResponseEntity<EntityModel<DetallePedido>> actualizarDetalle(
            @Parameter(description = "ID del detalle a actualizar", required = true, example = "1")
            @PathVariable int id,
            @RequestBody DetallePedido detalle
    ) {
        return detalleService.actualizarDetalle(id, detalle)
                .map(assembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un detalle de pedido", description = "Elimina un item de un pedido por su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Detalle eliminado"),
            @ApiResponse(responseCode = "404", description = "Detalle no encontrado")
    })
    public ResponseEntity<?> eliminarDetalle(
            @Parameter(description = "ID del detalle a eliminar", required = true, example = "1")
            @PathVariable int id
    ) {
        if (detalleService.buscarDetallePorId(id).isPresent()) {
            detalleService.eliminarDetalle(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}

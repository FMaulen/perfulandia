package com.perfulandia.perfu.Controller;

import com.perfulandia.perfu.Assembler.ClienteModelAssembler;
import com.perfulandia.perfu.Model.Cliente;
import com.perfulandia.perfu.Services.ClienteServices;
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
@RequestMapping("/clientes")
@Tag(name = "Controlador Clientes", description = "API para la gestión de clientes")
public class ClienteController {

    @Autowired
    private ClienteServices clienteServices;

    @Autowired
    private ClienteModelAssembler assembler;

    @GetMapping
    @Operation(summary = "Listar todos los clientes", description = "Devuelve una lista de todos los clientes.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de clientes encontrada"),
            @ApiResponse(responseCode = "404", description = "No se encontraron clientes")
    })
    public ResponseEntity<CollectionModel<EntityModel<Cliente>>> getClientes(){
        List<Cliente> clientes = clienteServices.listarClientes();
        if (clientes.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(assembler.toCollectionModel(clientes), HttpStatus.OK);
    }

    @PostMapping
    @Operation(summary = "Agregar un nuevo cliente", description = "Crea un nuevo cliente en el sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Cliente creado exitosamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Cliente.class)))
    })
    public ResponseEntity<EntityModel<Cliente>> addCliente(@RequestBody Cliente cliente){
        Cliente nuevoCliente = clienteServices.agregarCliente(cliente);
        return new ResponseEntity<>(assembler.toModel(nuevoCliente), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener un cliente por ID", description = "Busca y devuelve un cliente por su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente encontrado"),
            @ApiResponse(responseCode = "404", description = "Cliente no encontrado con ese ID")
    })
    @Parameter(description = "ID del cliente a buscar", required = true, example = "1")
    public ResponseEntity<EntityModel<Cliente>> getClientePorID(@PathVariable int id){
        return clienteServices.obtenerClientePorID(id)
                .map(assembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un cliente por ID", description = "Elimina un cliente del sistema por su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Cliente eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Cliente no encontrado")
    })
    @Parameter(description = "ID del cliente a eliminar", required = true, example = "1")
    public ResponseEntity<?> deleteClientePorID(@PathVariable int id){
        if (clienteServices.obtenerClientePorID(id).isPresent()) {
            clienteServices.eliminarClientePorID(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un cliente completo", description = "Actualiza todos los datos de un cliente por su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente actualizado"),
            @ApiResponse(responseCode = "404", description = "Cliente no encontrado")
    })
    @Parameter(description = "ID del cliente a actualizar", required = true, example = "1")
    public ResponseEntity<EntityModel<Cliente>> updateClientePorID(@PathVariable int id, @RequestBody Cliente cliente){
        return clienteServices.actualizarCliente(id, cliente)
                .map(assembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PatchMapping("/{id}/estado")
    @Operation(summary = "Cambiar estado de un cliente", description = "Cambia el estado (habilitado/deshabilitado) de un cliente.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estado del cliente actualizado"),
            @ApiResponse(responseCode = "404", description = "Cliente no encontrado")
    })
    @Parameter(description = "ID del cliente", required = true, example = "1")
    @Parameter(description = "Nuevo estado (true para habilitado, false para deshabilitado)", required = true, example = "false")
    public ResponseEntity<EntityModel<Cliente>> cambiarEstadoCliente(@PathVariable int id, @RequestParam boolean habilitado){
        return clienteServices.cambiarEstadoCliente(id, habilitado)
                .map(assembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}

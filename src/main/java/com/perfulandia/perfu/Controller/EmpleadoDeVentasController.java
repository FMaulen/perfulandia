package com.perfulandia.perfu.Controller;

import com.perfulandia.perfu.Assembler.EmpleadoDeVentasModelAssembler;
import com.perfulandia.perfu.Model.EmpleadoDeVentas;
import com.perfulandia.perfu.Services.EmpleadoDeVentasService;
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
@RequestMapping("/empleados-ventas")
@Tag(name = "Controlador de Empleados de Ventas", description = "API para la gestión de empleados de ventas")
public class EmpleadoDeVentasController {

    @Autowired
    private EmpleadoDeVentasService empleadoService;

    @Autowired
    private EmpleadoDeVentasModelAssembler assembler;

    @GetMapping
    @Operation(summary = "Listar todos los empleados de ventas", description = "Devuelve una lista de todos los empleados de ventas.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de empleados encontrada"),
            @ApiResponse(responseCode = "404", description = "No se encontraron empleados")
    })
    public ResponseEntity<CollectionModel<EntityModel<EmpleadoDeVentas>>> listarEmpleados() {
        List<EmpleadoDeVentas> empleados = empleadoService.listarEmpleados();
        if (empleados.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(assembler.toCollectionModel(empleados), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener un empleado por ID", description = "Busca y devuelve un empleado por su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Empleado encontrado"),
            @ApiResponse(responseCode = "404", description = "Empleado no encontrado")
    })
    @Parameter(description = "ID del empleado a buscar", required = true, example = "1")
    public ResponseEntity<EntityModel<EmpleadoDeVentas>> obtenerEmpleado(@PathVariable int id) {
        return empleadoService.buscarEmpleadoPorId(id)
                .map(assembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    @Operation(summary = "Contratar un nuevo empleado de ventas", description = "Crea un nuevo empleado en el sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Empleado creado exitosamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = EmpleadoDeVentas.class)))
    })
    public ResponseEntity<EntityModel<EmpleadoDeVentas>> crearEmpleado(@RequestBody EmpleadoDeVentas empleado) {
        EmpleadoDeVentas nuevoEmpleado = empleadoService.agregarEmpleado(empleado);
        return new ResponseEntity<>(assembler.toModel(nuevoEmpleado), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un empleado", description = "Actualiza los datos de un empleado existente.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Empleado actualizado"),
            @ApiResponse(responseCode = "404", description = "Empleado no encontrado")
    })
    @Parameter(description = "ID del empleado a actualizar", required = true, example = "1")
    public ResponseEntity<EntityModel<EmpleadoDeVentas>> actualizarEmpleado(@PathVariable int id, @RequestBody EmpleadoDeVentas empleado) {
        return empleadoService.actualizarEmpleado(id, empleado)
                .map(assembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un empleado", description = "Elimina un empleado del sistema por su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Empleado eliminado"),
            @ApiResponse(responseCode = "404", description = "Empleado no encontrado")
    })
    @Parameter(description = "ID del empleado a eliminar", required = true, example = "1")
    public ResponseEntity<?> eliminarEmpleado(@PathVariable int id) {
        if (empleadoService.buscarEmpleadoPorId(id).isPresent()) {
            empleadoService.eliminarEmpleado(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}

package com.perfulandia.perfu.Controller;

import com.perfulandia.perfu.Assembler.AdministradorSistemaModelAssembler;
import com.perfulandia.perfu.Model.AdministradorSistema;
import com.perfulandia.perfu.Services.AdministradorSistemaService;
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
@RequestMapping("/admin-sistema")
@Tag(name = "Controlador Administradores de Sistema", description = "API para la gestión de administradores del sistema")
public class AdministradorSistemaController {

    @Autowired
    private AdministradorSistemaService adminService;

    @Autowired
    private AdministradorSistemaModelAssembler assembler;

    @PostMapping
    @Operation(summary = "Crear un nuevo administrador", description = "Registra un nuevo administrador en la base de datos.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Administrador creado exitosamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AdministradorSistema.class))),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos")
    })
    public ResponseEntity<EntityModel<AdministradorSistema>> crearAdmin(@RequestBody AdministradorSistema admin) {
        AdministradorSistema nuevoAdmin = adminService.registrarAdmin(admin);
        return new ResponseEntity<>(assembler.toModel(nuevoAdmin), HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Listar todos los administradores", description = "Devuelve una lista de todos los administradores registrados.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de administradores obtenida"),
            @ApiResponse(responseCode = "404", description = "No se encontraron administradores")
    })
    public ResponseEntity<CollectionModel<EntityModel<AdministradorSistema>>> listarAdmins() {
        List<AdministradorSistema> admins = adminService.listarAdmins();
        if (admins.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(assembler.toCollectionModel(admins), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener administrador por ID", description = "Busca un administrador específico por su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Administrador encontrado"),
            @ApiResponse(responseCode = "404", description = "Administrador no encontrado")
    })
    public ResponseEntity<EntityModel<AdministradorSistema>> obtenerAdmin(
            @Parameter(description = "ID del administrador a buscar", required = true, example = "1")
            @PathVariable int id
    ) {
        return adminService.buscarAdminPorId(id)
                .map(assembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PatchMapping("/{id}/estado")
    @Operation(summary = "Cambiar estado de un administrador", description = "Activa o desactiva un administrador por su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estado del administrador actualizado"),
            @ApiResponse(responseCode = "404", description = "Administrador no encontrado")
    })
    public ResponseEntity<EntityModel<AdministradorSistema>> cambiarEstado(
            @Parameter(description = "ID del administrador a modificar", required = true, example = "1")
            @PathVariable int id,
            @Parameter(description = "Nuevo estado (true para activo, false para inactivo)", required = true, example = "false")
            @RequestParam boolean activo
    ) {
        return adminService.cambiarEstadoAdmin(id, activo)
                .map(assembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/{id}/permisos")
    @Operation(summary = "Asignar permisos a un administrador", description = "Asigna una lista de permisos a un administrador por su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Permisos asignados correctamente"),
            @ApiResponse(responseCode = "404", description = "Administrador no encontrado")
    })
    public ResponseEntity<EntityModel<AdministradorSistema>> asignarPermisos(
            @Parameter(description = "ID del administrador", required = true, example = "1")
            @PathVariable int id,
            @RequestBody List<Integer> permisosIds
    ) {
        return adminService.asignarPermisos(id, permisosIds)
                .map(assembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{adminId}/permisos/{permisoId}")
    @Operation(summary = "Quitar un permiso de un administrador", description = "Elimina un permiso específico de un administrador.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Permiso quitado correctamente"),
            @ApiResponse(responseCode = "404", description = "Administrador o permiso no encontrado para esa combinación")
    })
    public ResponseEntity<EntityModel<AdministradorSistema>> quitarPermiso(
            @Parameter(description = "ID del administrador", required = true, example = "1")
            @PathVariable int adminId,
            @Parameter(description = "ID del permiso a quitar", required = true, example = "3")
            @PathVariable int permisoId
    ) {
        return adminService.quitarPermiso(adminId, permisoId)
                .map(assembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}

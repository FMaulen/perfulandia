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
    @Parameter(description = "ID del administrador a buscar", required = true, example = "1")
    public ResponseEntity<EntityModel<AdministradorSistema>> obtenerAdmin(@PathVariable int id) {
        if (adminService.buscarAdminPorId(id).isPresent()) {
            AdministradorSistema admin = adminService.buscarAdminPorId(id).get();
            return new ResponseEntity<>(assembler.toModel(admin), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping("/{id}/estado")
    @Operation(summary = "Cambiar estado de un administrador", description = "Activa o desactiva un administrador por su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estado del administrador actualizado"),
            @ApiResponse(responseCode = "404", description = "Administrador no encontrado")
    })
    @Parameter(description = "ID del administrador a modificar", required = true, example = "1")
    @Parameter(description = "Nuevo estado (true para activo, false para inactivo)", required = true, example = "false")
    public ResponseEntity<EntityModel<AdministradorSistema>> cambiarEstado(@PathVariable int id, @RequestParam boolean activo) {
        if (adminService.buscarAdminPorId(id).isPresent()) {
            AdministradorSistema adminActualizado = adminService.cambiarEstadoAdmin(id, activo).get();
            return new ResponseEntity<>(assembler.toModel(adminActualizado), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/{id}/permisos")
    @Operation(summary = "Asignar permisos a un administrador", description = "Asigna una lista de permisos a un administrador por su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Permisos asignados correctamente"),
            @ApiResponse(responseCode = "404", description = "Administrador no encontrado")
    })
    @Parameter(description = "ID del administrador", required = true, example = "1")
    public ResponseEntity<EntityModel<AdministradorSistema>> asignarPermisos(@PathVariable int id, @RequestBody List<Integer> permisosIds) {
        if (adminService.buscarAdminPorId(id).isPresent()) {
            AdministradorSistema adminConPermisos = adminService.asignarPermisos(id, permisosIds).get();
            return new ResponseEntity<>(assembler.toModel(adminConPermisos), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{adminId}/permisos/{permisoId}")
    @Operation(summary = "Quitar un permiso de un administrador", description = "Elimina un permiso específico de un administrador.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Permiso quitado correctamente"),
            @ApiResponse(responseCode = "404", description = "Administrador o permiso no encontrado para esa combinación")
    })
    @Parameter(description = "ID del administrador", required = true, example = "1")
    @Parameter(description = "ID del permiso a quitar", required = true, example = "3")
    public ResponseEntity<EntityModel<AdministradorSistema>> quitarPermiso(@PathVariable int adminId, @PathVariable int permisoId) {
        if (adminService.buscarAdminPorId(adminId).isPresent()) {
            AdministradorSistema adminActualizado = adminService.quitarPermiso(adminId, permisoId).get();
            return new ResponseEntity<>(assembler.toModel(adminActualizado), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}

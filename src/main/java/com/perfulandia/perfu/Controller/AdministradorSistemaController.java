package com.perfulandia.perfu.Controller;

import com.perfulandia.perfu.Assembler.AdministradorSistemaAssembler;
import com.perfulandia.perfu.Model.AdministradorSistema;
import com.perfulandia.perfu.Services.AdministradorSistemaService;
import io.swagger.v3.oas.annotations.*;
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
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/admin-sistema")
@Tag(name = "Controlador Administradores", description = "Servicio de gestión de administradores del sistema")
public class AdministradorSistemaController {

    @Autowired
    private AdministradorSistemaService adminService;

    @Autowired
    private AdministradorSistemaAssembler assembler;

    @GetMapping
    @Operation(summary = "Obtener Administradores", description = "Obtiene la lista completa de administradores")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna lista completa"),
            @ApiResponse(responseCode = "404", description = "No se encuentran datos")
    })
    public ResponseEntity<CollectionModel<EntityModel<AdministradorSistema>>> getAllAdmins() {
        List<AdministradorSistema> admins = adminService.listarAdminsObjects();
        if (admins.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(assembler.toCollectionModel(admins));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar Administrador por ID", description = "Obtiene un administrador según su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna administrador"),
            @ApiResponse(responseCode = "404", description = "No se encuentra el administrador")
    })
    public ResponseEntity<EntityModel<AdministradorSistema>> getAdminById(@PathVariable int id) {
        return adminService.buscarAdminPorIdObject(id)
                .map(assembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    @Operation(summary = "Agregar Administrador", description = "Registra un nuevo administrador en el sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Administrador creado"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    public ResponseEntity<EntityModel<AdministradorSistema>> addAdmin(@RequestBody AdministradorSistema admin) {
        AdministradorSistema nuevoAdmin = adminService.registrarAdminObject(admin);
        return new ResponseEntity<>(assembler.toModel(nuevoAdmin), HttpStatus.CREATED);
    }

    @PatchMapping("/{id}/estado")
    @Operation(summary = "Cambiar estado", description = "Activa/desactiva un administrador")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estado actualizado"),
            @ApiResponse(responseCode = "404", description = "Administrador no encontrado")
    })
    public ResponseEntity<EntityModel<AdministradorSistema>> changeStatus(
            @PathVariable int id,
            @RequestParam boolean activo) {
        return adminService.cambiarEstadoAdminObject(id, activo)
                .map(assembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}

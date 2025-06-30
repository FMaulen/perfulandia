package com.perfulandia.perfu.Assembler;

import com.perfulandia.perfu.Controller.PermisoController;
import com.perfulandia.perfu.Model.Permiso;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class PermisoModelAssembler implements RepresentationModelAssembler<Permiso, EntityModel<Permiso>> {

    @Override
    public EntityModel<Permiso> toModel(Permiso permiso) {
        return EntityModel.of(permiso,
                linkTo(methodOn(PermisoController.class).getPermisoById(permiso.getId_permiso())).withSelfRel(),
                linkTo(methodOn(PermisoController.class).listarPermisos()).withRel("permisos")
        );
    }
}
// Permiso
// Adelante
// Gracias
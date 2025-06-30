package com.perfulandia.perfu.Assembler;

import com.perfulandia.perfu.Controller.AdministradorSistemaController;
import com.perfulandia.perfu.Model.AdministradorSistema;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class AdministradorSistemaModelAssembler implements RepresentationModelAssembler<AdministradorSistema, EntityModel<AdministradorSistema>> {

    @Override
    public EntityModel<AdministradorSistema> toModel(AdministradorSistema admin) {
        return EntityModel.of(admin,
                linkTo(methodOn(AdministradorSistemaController.class).obtenerAdmin(admin.getId_administrador_sistema())).withSelfRel(),
                linkTo(methodOn(AdministradorSistemaController.class).listarAdmins()).withRel("administradores")
        );
    }
}

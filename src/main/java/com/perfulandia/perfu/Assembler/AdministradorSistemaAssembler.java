package com.perfulandia.perfu.Assembler;

import com.perfulandia.perfu.Controller.AdministradorSistemaController;
import com.perfulandia.perfu.Model.AdministradorSistema;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class AdministradorSistemaAssembler implements
        RepresentationModelAssembler<AdministradorSistema, EntityModel<AdministradorSistema>> {

    @Override
    public EntityModel<AdministradorSistema> toModel(AdministradorSistema admin) {
        return EntityModel.of(admin,
                linkTo(methodOn(AdministradorSistemaController.class).getAdminById(admin.getId_administrador_sistema())).withSelfRel(),
                linkTo(methodOn(AdministradorSistemaController.class).getAllAdmins()).withRel("administradores")
        );
    }

    public CollectionModel<EntityModel<AdministradorSistema>> toCollectionModel(List<AdministradorSistema> admins) {
        return CollectionModel.of(
                admins.stream().map(this::toModel).collect(Collectors.toList()),
                linkTo(methodOn(AdministradorSistemaController.class).getAllAdmins()).withSelfRel()
        );
    }
}

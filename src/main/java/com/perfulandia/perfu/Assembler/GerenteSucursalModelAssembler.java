package com.perfulandia.perfu.Assembler;

import com.perfulandia.perfu.Controller.GerenteSucursalController;
import com.perfulandia.perfu.Model.GerenteSucursal;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class GerenteSucursalModelAssembler implements RepresentationModelAssembler<GerenteSucursal, EntityModel<GerenteSucursal>> {

    @Override
    public EntityModel<GerenteSucursal> toModel(GerenteSucursal gerente) {
        return EntityModel.of(gerente,
                linkTo(methodOn(GerenteSucursalController.class).getGerentePorId(gerente.getId_gerente_sucursal())).withSelfRel(),
                linkTo(methodOn(GerenteSucursalController.class).getGerentes()).withRel("gerentes")
        );
    }
}

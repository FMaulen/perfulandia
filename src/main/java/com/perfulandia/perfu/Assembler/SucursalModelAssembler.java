package com.perfulandia.perfu.Assembler;

import com.perfulandia.perfu.Controller.SucursalController;
import com.perfulandia.perfu.Model.Sucursal;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class SucursalModelAssembler implements RepresentationModelAssembler<Sucursal, EntityModel<Sucursal>> {

    @Override
    public EntityModel<Sucursal> toModel(Sucursal sucursal) {
        return EntityModel.of(sucursal,
                linkTo(methodOn(SucursalController.class).obtenerSucursal(sucursal.getId_sucursal())).withSelfRel(),
                linkTo(methodOn(SucursalController.class).listarSucursales()).withRel("sucursales")
        );
    }
}

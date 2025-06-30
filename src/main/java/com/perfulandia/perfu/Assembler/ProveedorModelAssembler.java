package com.perfulandia.perfu.Assembler;

import com.perfulandia.perfu.Controller.ProveedorController;
import com.perfulandia.perfu.Model.Proveedor;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ProveedorModelAssembler implements RepresentationModelAssembler<Proveedor, EntityModel<Proveedor>> {

    @Override
    public EntityModel<Proveedor> toModel(Proveedor proveedor) {
        return EntityModel.of(proveedor,
                linkTo(methodOn(ProveedorController.class).obtenerProveedor(proveedor.getId_proveedor())).withSelfRel(),
                linkTo(methodOn(ProveedorController.class).listarProveedores()).withRel("proveedores")
        );
    }
}

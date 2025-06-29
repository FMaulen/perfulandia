package com.perfulandia.perfu.Assembler;

import com.perfulandia.perfu.Controller.InventarioController;
import com.perfulandia.perfu.Model.Inventario;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class InventarioModelAssembler implements RepresentationModelAssembler<Inventario, EntityModel<Inventario>> {

    @Override
    public EntityModel<Inventario> toModel(Inventario inventario) {
        return EntityModel.of(inventario,
                linkTo(methodOn(InventarioController.class).buscarItem(inventario.getId())).withSelfRel(),
                linkTo(methodOn(InventarioController.class).listarTodo()).withRel("inventario")
        );
    }
}

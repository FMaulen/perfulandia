package com.perfulandia.perfu.Assembler;

import com.perfulandia.perfu.Controller.ResenaController;
import com.perfulandia.perfu.Model.Resena;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ResenaModelAssembler implements RepresentationModelAssembler<Resena, EntityModel<Resena>> {

    @Override
    public EntityModel<Resena> toModel(Resena resena) {
        return EntityModel.of(resena,
                linkTo(methodOn(ResenaController.class).obtenerResena(resena.getId_resena())).withSelfRel(),
                linkTo(methodOn(ResenaController.class).listarResenas()).withRel("resenas")
        );
    }
}

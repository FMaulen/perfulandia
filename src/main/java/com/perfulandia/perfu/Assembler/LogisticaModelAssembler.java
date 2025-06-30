package com.perfulandia.perfu.Assembler;

import com.perfulandia.perfu.Controller.LogisticaController;
import com.perfulandia.perfu.Model.Logistica;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class LogisticaModelAssembler implements RepresentationModelAssembler<Logistica, EntityModel<Logistica>> {

    @Override
    public EntityModel<Logistica> toModel(Logistica logistica) {
        return EntityModel.of(logistica,
                linkTo(methodOn(LogisticaController.class).obtenerLogistica(logistica.getId_logistica())).withSelfRel(),
                linkTo(methodOn(LogisticaController.class).listarLogisticas()).withRel("logisticas")
        );
    }
}

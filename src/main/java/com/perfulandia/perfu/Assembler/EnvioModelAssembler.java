package com.perfulandia.perfu.Assembler;

import com.perfulandia.perfu.Controller.EnvioController;
import com.perfulandia.perfu.Model.Envio;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class EnvioModelAssembler implements RepresentationModelAssembler<Envio, EntityModel<Envio>> {

    @Override
    public EntityModel<Envio> toModel(Envio envio) {
        return EntityModel.of(envio,
                linkTo(methodOn(EnvioController.class).obtenerEnvio(envio.getId_envio())).withSelfRel(),
                linkTo(methodOn(EnvioController.class).listarEnvios()).withRel("envios")
        );
    }
}

package com.perfulandia.perfu.Assembler;

import com.perfulandia.perfu.Controller.DetallePedidoController;
import com.perfulandia.perfu.Model.DetallePedido;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class DetallePedidoModelAssembler implements RepresentationModelAssembler<DetallePedido, EntityModel<DetallePedido>> {

    @Override
    public EntityModel<DetallePedido> toModel(DetallePedido detalle) {
        return EntityModel.of(detalle,
                linkTo(methodOn(DetallePedidoController.class).obtenerDetalle(detalle.getId())).withSelfRel(),
                linkTo(methodOn(DetallePedidoController.class).listarDetalles()).withRel("detallesPedido")
        );
    }
}

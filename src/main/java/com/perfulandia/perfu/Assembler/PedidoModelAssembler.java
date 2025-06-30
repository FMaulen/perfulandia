package com.perfulandia.perfu.Assembler;

import com.perfulandia.perfu.Controller.PedidoController;
import com.perfulandia.perfu.Model.Pedido;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class PedidoModelAssembler implements RepresentationModelAssembler<Pedido, EntityModel<Pedido>> {

    @Override
    public EntityModel<Pedido> toModel(Pedido pedido) {
        return EntityModel.of(pedido,
                linkTo(methodOn(PedidoController.class).obtenerPedido(pedido.getId_pedido())).withSelfRel(),
                linkTo(methodOn(PedidoController.class).listarPedidos()).withRel("pedidos")
        );
    }
}

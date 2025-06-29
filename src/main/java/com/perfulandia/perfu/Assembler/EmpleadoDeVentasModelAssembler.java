package com.perfulandia.perfu.Assembler;

import com.perfulandia.perfu.Controller.EmpleadoDeVentasController;
import com.perfulandia.perfu.Model.EmpleadoDeVentas;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class EmpleadoDeVentasModelAssembler implements RepresentationModelAssembler<EmpleadoDeVentas, EntityModel<EmpleadoDeVentas>> {

    @Override
    public EntityModel<EmpleadoDeVentas> toModel(EmpleadoDeVentas empleado) {
        return EntityModel.of(empleado,
                linkTo(methodOn(EmpleadoDeVentasController.class).obtenerEmpleado(empleado.getId_empleado_ventas())).withSelfRel(),
                linkTo(methodOn(EmpleadoDeVentasController.class).listarEmpleados()).withRel("empleados")
        );
    }
}

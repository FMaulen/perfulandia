package com.perfulandia.perfu.Model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@DiscriminatorValue("VENTAS")
public class EmpleadoDeVentas extends Usuario {
    @ManyToOne
    @JoinColumn(name = "sucursal_id")
    private Sucursal sucursal;

    private Date fechaContratacion;
    private double comisionVentas;
}
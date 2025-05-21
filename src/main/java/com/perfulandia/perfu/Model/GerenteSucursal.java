package com.perfulandia.perfu.Model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@DiscriminatorValue("GERENTE")
public class GerenteSucursal extends Usuario {
    @ManyToOne
    @JoinColumn(name = "sucursal_id")
    private Sucursal sucursalAsignada;

    private Date fechaContratacion;
}

package com.perfulandia.perfu.Model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@DiscriminatorValue("LOGISTICA")
public class Logistica extends Usuario {
    private String especialidad;

    @ManyToMany
    @JoinTable(
            name = "logistica_proveedor",
            joinColumns = @JoinColumn(name = "logistica_id"),
            inverseJoinColumns = @JoinColumn(name = "proveedor_id")
    )
    private Set<Proveedor> proveedores;
}

// como vai a dejar el proyecto abierto
// tralalero tralala
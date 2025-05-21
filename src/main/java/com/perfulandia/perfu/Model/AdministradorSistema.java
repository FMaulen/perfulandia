package com.perfulandia.perfu.Model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@DiscriminatorValue("ADMIN")
public class AdministradorSistema extends Usuario {
    private String nivelAcceso;
    private boolean puedeCrearUsuarios = true;
    private boolean puedeConfigurarPermisos = true;
}
package com.perfulandia.perfu.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor

@Entity
public class Permiso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_permiso;
    private String nombre_permiso;

    @ManyToMany(mappedBy = "permisos")
    private Set<AdministradorSistema> admins = new HashSet<>();
}
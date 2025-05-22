package com.perfulandia.perfu.Model;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
public class Permiso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_permiso;
    private String nombre_permiso;

    @ManyToMany(mappedBy = "permisos")
    private Set<AdministradorSistema> admins = new HashSet<>();
}
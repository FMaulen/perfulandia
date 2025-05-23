package com.perfulandia.perfu.Model;


import jakarta.persistence.*;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;


@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
public class AdministradorSistema {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_administrador_sistema;
    private String nombre;
    private String correo;
    private Date fecha_registro;
    private Boolean activo;

    // Relacion OneToMany con Sucursal
    @OneToMany(mappedBy = "administradorSistema")
    private Set<Sucursal> sucursales = new HashSet<>();

    // Relacion ManyToMany con Permiso
    // AdministradorSistema es el lado propietario
    @ManyToMany
    @JoinTable(
            name = "administrador_sistema_permiso",
            joinColumns = @JoinColumn(name = "id_administrador_sistema"),
            inverseJoinColumns = @JoinColumn(name = "id_permiso")
    )
    private Set<Permiso> permisos = new HashSet<>();
}
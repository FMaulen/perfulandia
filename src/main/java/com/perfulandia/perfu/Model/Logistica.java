package com.perfulandia.perfu.Model;

import jakarta.persistence.Id;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.JoinColumn;

import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
public class Logistica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_logistica;

    private String nombre;
    private String email;
    private String especialidad;

    // Relacion N-N con proveedor
    @ManyToMany
    @JoinTable(
            name = "logistica_sucursal", // Tabla intermedia
            joinColumns = @JoinColumn(name = "id_logistica"), // Columna FK en la tabla intermedia que apunta a logistica
            inverseJoinColumns = @JoinColumn(name = "id_proveedor") // Columna FK en la tabla intermedia que apunta a proveedor
    )
    private Set<Proveedor> proveedores = new HashSet<>();
}

// como vai a dejar el proyecto abierto
// tralalero tralala
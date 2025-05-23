package com.perfulandia.perfu.Model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

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
    private String correo;
    private String especialidad;

    // Relacion N-N con proveedor
    @ManyToMany
    @JoinTable(
            name = "logistica_proveedor", // Tabla intermedia
            joinColumns = @JoinColumn(name = "id_logistica"), // Columna FK en la tabla intermedia que apunta a logistica
            inverseJoinColumns = @JoinColumn(name = "id_proveedor") // Columna FK en la tabla intermedia que apunta a proveedor
    )
    private Set<Proveedor> proveedores = new HashSet<>();

    // Relacion @OneToMany con Envio
    // Logistica es el lado inverso
    @OneToMany(mappedBy = "logistica", cascade = CascadeType.ALL)
    private Set<Envio> envios = new HashSet<>();
}

// como vai a dejar el proyecto abierto
// tralalero tralala
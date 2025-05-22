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

public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private int id_producto;
    private String nombre;
    private String descripcion;
    private String precio;
    private String categoria;
    private int stock_total;

    // Relacion ManyToMany con Proveedor
    /*
    @ManyToMany
    @JoinTable(
            name = "producto_proveedor",
            joinColumns = @JoinColumn(name = "id_producto"),
            inverseJoinColumns = @JoinColumn(name = "id_proveedor")
    )
    private Set<Proveedor> proveedorHashSetveedores = new HashSet<>();
    */

    // Relacion ManyToOne con proveedor
    // Producto es el lado propietario
    @ManyToOne
    @JoinColumn(name = "id_proveedor")
    private Proveedor proveedor;

    // Relacion ManyToOne con Inventario
    // Producto es el lado inverso
    @OneToMany(mappedBy = "producto")
    private Set<Inventario> inventarios = new HashSet<>();

    // Relacion OneToMany con Resena
    // Producto es el lado inverso
    @OneToMany(mappedBy = "producto")
    private Set<Resena> resenas = new HashSet<>();

    // Relacion OneToMany con DetallePedido
    // Producto es el lado inverso
    @OneToMany(mappedBy = "producto")
    private Set<DetallePedido> detallePedidos = new HashSet<>();
}
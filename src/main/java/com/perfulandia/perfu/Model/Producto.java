package com.perfulandia.perfu.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
    private Double precio;
    private String categoria;
    private int stock_total;

    @ManyToOne
    @JoinColumn(name = "id_proveedor")
    @JsonBackReference("proveedor-productos")
    private Proveedor proveedor;

    @OneToMany(mappedBy = "producto")
    @JsonManagedReference("producto-inventario")
    private Set<Inventario> inventarios = new HashSet<>();

    @OneToMany(mappedBy = "producto")
    @JsonManagedReference("producto-resena")
    private Set<Resena> resenas = new HashSet<>();

    @OneToMany(mappedBy = "producto")
    @JsonManagedReference("producto-detalle")
    private Set<DetallePedido> detallePedidos = new HashSet<>();
}

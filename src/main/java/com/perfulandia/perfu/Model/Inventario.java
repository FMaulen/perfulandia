package com.perfulandia.perfu.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Inventario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int stock;

    @ManyToOne
    @JoinColumn(name = "id_sucursal")
    @JsonBackReference("sucursal-inventario")
    private Sucursal sucursal;

    @ManyToOne
    @JoinColumn(name = "id_producto")
    @JsonBackReference("producto-inventario")
    private Producto producto;
}

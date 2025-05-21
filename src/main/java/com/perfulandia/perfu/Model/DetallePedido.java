package com.perfulandia.perfu.Model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity

public class DetallePedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    //id_pedido
    //id_producto
    private int cantidad;
    private int precio_unitario;
    private int descuento;

    // Relacion OneToOne con Pedido
    // DetallePedido es el lado propietario
    @OneToOne
    @JoinColumn(name = "id_pedido")
    private Pedido pedido;

    // Relacion ManyToOne con Producto
    // DetallePedido es el lado propietario
    @ManyToOne
    @JoinColumn(name = "id_producto")
    private Producto producto;
}

package com.perfulandia.perfu.Model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.Date;
import java.util.HashSet;


@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity


public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    // id_cliente
    private Date fecha_pedido;
    private String estado_pedido;
    private int total_pedido;
    private String metodo_pago;

    // Relacion @ManyToOne con Sucursal
    // Pedido es el lado propietario
    @ManyToOne
    @JoinColumn(name = "id_sucursal")
    private Sucursal sucursal;

    // Relacion @OneToOne con Pedido
    // Pedido es el lado inverso
    @OneToOne(mappedBy = "pedido")
    private Envio envio;

    // Relacion OneToOne con DetallePedido
    // Pedido es el lado inverso
    @OneToOne(mappedBy = "pedido")
    private DetallePedido detallePedido;

    // Relacion ManyToOne con Cliente
    // Pedido es el lado propietario
    @ManyToOne
    @JoinColumn(name = "id_cliente")
    private Cliente cliente;
}
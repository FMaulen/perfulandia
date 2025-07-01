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
public class DetallePedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int cantidad;
    private int precio_unitario;
    private int descuento;

    @OneToOne
    @JoinColumn(name = "id_pedido")
    @JsonBackReference("pedido-detalle")
    private Pedido pedido;

    @ManyToOne
    @JoinColumn(name = "id_producto")
    @JsonBackReference("producto-detalle")
    private Producto producto;
}

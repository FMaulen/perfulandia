package com.perfulandia.perfu.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_pedido;
    private Date fecha_pedido;
    private String estado_pedido;
    private int total_pedido;
    private String metodo_pago;

    @ManyToOne
    @JoinColumn(name = "id_sucursal")
    @JsonBackReference("sucursal-pedidos")
    private Sucursal sucursal;

    @OneToOne(mappedBy = "pedido")
    @JsonManagedReference("pedido-envio")
    private Envio envio;

    @OneToOne(mappedBy = "pedido")
    @JsonManagedReference("pedido-detalle")
    private DetallePedido detallePedido;

    @ManyToOne
    @JoinColumn(name = "id_cliente")
    @JsonBackReference("cliente-pedidos")
    private Cliente cliente;
}

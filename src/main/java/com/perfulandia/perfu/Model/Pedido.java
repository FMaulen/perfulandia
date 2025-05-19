package com.perfulandia.perfu.Model;

import jakarta.persistence.Id;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
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
    private int id;
    // id_cliente
    // id_sucursal
    private Date fecha_pedido;
    private String estado;
    private int total;
    private String metodo_pago;

}

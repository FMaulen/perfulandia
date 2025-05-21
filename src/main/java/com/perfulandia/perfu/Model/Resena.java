package com.perfulandia.perfu.Model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity


public class Resena {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_resena;
    private String calificacion;
    private String comentario;
    private Date fecha_resena;

    // Relacion ManyToOne con cliente
    // Resena es el lado propietario
    @ManyToOne
    @JoinColumn(name = "id_cliente")
    private Cliente cliente;

    // Relacion ManyToOne con producto
    // Resena es el lado propietario
    @ManyToOne
    @JoinColumn(name = "id_producto")
    private Producto producto;
}

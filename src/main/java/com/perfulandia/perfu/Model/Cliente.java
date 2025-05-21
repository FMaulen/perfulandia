package com.perfulandia.perfu.Model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_cliente;
    private String nombre;
    private String correo;
    private String direccion;
    private String telefono;
    private Date fecha_registro;

    // Relacion OneToMany con Pedido
    // Cliente es el lado inverso
    @OneToMany(mappedBy = "cliente")
    private Set<Pedido> pedidos = new HashSet<>();

    // Relacion OneToMany con Resena
    // Cliente es el lado inverso
    @OneToMany(mappedBy = "cliente")
    private Set<Resena> resenas = new HashSet<>();
}
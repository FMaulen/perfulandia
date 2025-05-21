package com.perfulandia.perfu.Model;

import jakarta.persistence.Id;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;

import java.util.HashSet;
import java.util.Set;


@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
public class Proveedor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_proveedor;
    private String nombre;
    private String contacto;
    private String telefono;
    private String email;
    private String direccion;

    // @ManyToMany a Logistica
    // Este lado es el inverso, por lo que va a usar mappedBy
    // mappedBy apunta al nombre del campo de la relacion en la entidad Logistica
    @ManyToMany(mappedBy = "proveedores") // "proveedores" es el nombre del Set en la clase Logistica
    private Set<Logistica> logisticas = new HashSet<>();

    // @ManyToMany a Producto
    @ManyToMany(mappedBy = "proveedores")
    private Set<Producto> productos = new HashSet<>();
}
package com.perfulandia.perfu.Model;

import jakarta.persistence.*;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
public class Sucursal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nombre;
    private String direccion;
    private String telefono;
    private String email;

    @OneToOne(mappedBy = "sucursal", cascade = CascadeType.ALL)
    private GerenteSucursal gerente_sucursal;
}
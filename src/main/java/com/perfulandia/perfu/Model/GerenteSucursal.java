package com.perfulandia.perfu.Model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.Date;

import jakarta.persistence.Id;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
public class GerenteSucursal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_gerente_sucursal;

    private String nombre;
    private String correo;
    private Date fecha_contratacion;


    // Relacion OneToOne con Sucursal
    @OneToOne
    @JoinColumn(name = "id_sucursal", unique = true)
    private Sucursal sucursal;
}
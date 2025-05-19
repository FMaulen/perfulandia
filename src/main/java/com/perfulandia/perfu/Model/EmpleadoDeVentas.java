package com.perfulandia.perfu.Model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import jakarta.persistence.Id;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
public class EmpleadoDeVentas {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nombre;
    private String email;
    // ID sucursal FK
    private Date fecha_contratacion;
}
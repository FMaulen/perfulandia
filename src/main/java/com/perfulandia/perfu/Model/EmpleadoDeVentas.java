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
public class EmpleadoDeVentas {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_empleado_ventas;
    private String nombre;
    private String correo;
    private Date fecha_contratacion;

   // Relacion ManyToOne con Sucursal
   // EmpleadoDeVentas es el lado propietario
    @ManyToOne
    @JoinColumn(name = "id_sucursal")
    private Sucursal sucursal;

    @ManyToOne
    @JoinColumn(name = "id_sucursal_contrato")
    private Sucursal sucursalContrato;
}
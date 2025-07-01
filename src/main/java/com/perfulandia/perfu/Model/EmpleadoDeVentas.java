package com.perfulandia.perfu.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
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

    @ManyToOne
    @JoinColumn(name = "id_sucursal")
    @JsonBackReference("sucursal-empleados")
    private Sucursal sucursal;

    @ManyToOne
    @JoinColumn(name = "id_sucursal_contrato")
    @JsonBackReference("sucursal-contratos")
    private Sucursal sucursalContrato;
}

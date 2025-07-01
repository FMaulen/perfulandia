package com.perfulandia.perfu.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.Date;
import jakarta.persistence.*;

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

    @OneToOne
    @JoinColumn(name = "id_sucursal", unique = true)
    @JsonBackReference("sucursal-gerente")
    private Sucursal sucursal;
}

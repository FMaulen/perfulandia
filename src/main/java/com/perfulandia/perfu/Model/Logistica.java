package com.perfulandia.perfu.Model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Logistica {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_logistica;
    private String nombre;
    private String correo;
    private String especialidad;

    @ManyToMany(mappedBy = "logisticas")
    private Set<Proveedor> proveedores = new HashSet<>();

    @OneToMany(mappedBy = "logistica", cascade = CascadeType.ALL)
    @JsonManagedReference("logistica-envios")
    private Set<Envio> envios = new HashSet<>();
}

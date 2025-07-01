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
public class Proveedor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_proveedor;
    private String nombre;
    private String contacto;
    private String telefono;
    private String email;
    private String direccion;

    @ManyToMany
    @JoinTable(
            name = "logistica_proveedor",
            joinColumns = @JoinColumn(name = "id_proveedor"),
            inverseJoinColumns = @JoinColumn(name = "id_logistica")
    )
    private Set<Logistica> logisticas = new HashSet<>();

    @OneToMany(mappedBy = "proveedor")
    @JsonManagedReference("proveedor-productos")
    private Set<Producto> productosProveedor = new HashSet<>();
}

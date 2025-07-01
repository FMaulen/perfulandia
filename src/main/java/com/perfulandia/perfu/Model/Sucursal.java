package com.perfulandia.perfu.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
public class Sucursal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_sucursal;
    private String nombre;
    private String direccion;
    private String telefono;
    private String correo;

    @OneToOne(mappedBy = "sucursal", cascade = CascadeType.ALL)
    @JsonManagedReference("sucursal-gerente")
    private GerenteSucursal gerenteSucursal;

    @ManyToOne
    @JoinColumn(name = "id_administrador_sistema", unique = true)
    @JsonBackReference("admin-sucursales")
    private AdministradorSistema administradorSistema;

    @OneToMany(mappedBy = "sucursal")
    @JsonManagedReference("sucursal-empleados")
    private Set<EmpleadoDeVentas> empleadosDeVentas = new HashSet<>();

    @OneToMany(mappedBy = "sucursalContrato")
    @JsonManagedReference("sucursal-contratos")
    private Set<EmpleadoDeVentas> contratadosVenta= new HashSet<>();

    @OneToMany(mappedBy = "sucursal")
    @JsonManagedReference("sucursal-pedidos")
    private Set<Pedido> pedidos = new HashSet<>();

    @OneToMany(mappedBy = "sucursal")
    @JsonManagedReference("sucursal-inventario")
    private Set<Inventario> inventarios = new HashSet<>();
}

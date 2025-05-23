package com.perfulandia.perfu.Model;

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

    // Relacion OneToOne con GerenteSucursal
    @OneToOne(mappedBy = "sucursal", cascade = CascadeType.ALL)
    private GerenteSucursal gerenteSucursal;

    // Relacion ManyToOne con AdministradorSistema
    @ManyToOne
    @JoinColumn(name = "id_administrador_sistema", unique = true)
    private AdministradorSistema administradorSistema;

    // Relacion OneToMany con EmpleadoDeVentas
    // Sucursal es el lado inverso
    @OneToMany(mappedBy = "sucursal")
    private Set<EmpleadoDeVentas> empleadosDeVentas = new HashSet<>(); // Sucursal Trabajo (Los que trabajan aqui)

    @OneToMany(mappedBy = "sucursalContrato")
    private Set<EmpleadoDeVentas> contratadosVenta= new HashSet<>(); // Contratados Aqui

    // Relacion OneToMany con Pedido
    // Sucursal es el lado inverso
    @OneToMany(mappedBy = "sucursal")
    private Set<Pedido> pedidos = new HashSet<>(); // La sucursal puede manejar varios pedidos

    // Relacion OneToMany con Inventario
    // Sucursal es el lado inverso
    @OneToMany(mappedBy = "sucursal")
    private Set<Inventario> inventarios = new HashSet<>();



    /* To Do
    *   - Gestionar Usuarios
    *   - Configurar Permisos
    *   - Monitorizar sistemas
    *   - Respaldar / Restaurar Datos
    * */
}
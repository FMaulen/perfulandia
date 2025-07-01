package com.perfulandia.perfu.Model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class AdministradorSistema {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_administrador_sistema;
    private String nombre;
    private String correo;
    private Date fecha_registro;
    private Boolean activo;

    @OneToMany(mappedBy = "administradorSistema")
    @JsonManagedReference("admin-sucursales")
    private Set<Sucursal> sucursales = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "administrador_sistema_permiso",
            joinColumns = @JoinColumn(name = "id_administrador_sistema"),
            inverseJoinColumns = @JoinColumn(name = "id_permiso")
    )
    private Set<Permiso> permisos = new HashSet<>();
}

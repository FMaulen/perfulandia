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
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_cliente;
    private String nombre;
    private String correo;
    private String direccion;
    private String telefono;
    private Date fecha_registro;
    private Boolean habilitado;

    @OneToMany(mappedBy = "cliente")
    @JsonManagedReference("cliente-pedidos")
    private Set<Pedido> pedidos = new HashSet<>();

    @OneToMany(mappedBy = "cliente")
    @JsonManagedReference("cliente-resenas")
    private Set<Resena> resenas = new HashSet<>();
}

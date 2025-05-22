package com.perfulandia.perfu.Model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.Date;

@Data
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo_usuario")
public abstract class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // Cambiado a Long para consistencia

    private String nombre;
    private String email;
    private String password;
    private Date fechaRegistro;
    private boolean activo = true;
}
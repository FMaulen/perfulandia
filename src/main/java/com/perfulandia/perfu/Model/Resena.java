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
public class Resena {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_resena;
    private String calificacion;
    private String comentario;
    private Date fecha_resena;

    @ManyToOne
    @JoinColumn(name = "id_cliente")
    @JsonBackReference("cliente-resenas")
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "id_producto")
    @JsonBackReference("producto-resena")
    private Producto producto;
}

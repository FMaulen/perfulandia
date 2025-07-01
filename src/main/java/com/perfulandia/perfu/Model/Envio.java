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
public class Envio {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private int id_envio;
   private String direccion;
   private Date fecha_envio;
   private Date fecha_estimada;
   private String estado;
   private String transportista;

   @ManyToOne
   @JoinColumn(name = "id_logistica")
   @JsonBackReference("logistica-envios")
   private Logistica logistica;

   @OneToOne
   @JoinColumn(name = "id_pedido")
   @JsonBackReference("pedido-envio")
   private Pedido pedido;
}

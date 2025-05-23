package com.perfulandia.perfu.Model;

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
   // Pedido FK
   private String direccion;
   private Date fecha_envio;
   private Date fecha_estimada;
   private String estado;
   private String transportista;

   // Relacion ManyToOne con Logistica
   // Envio es del lado propietario
   @ManyToOne
   @JoinColumn(name = "id_logistica")
   private Logistica logistica;

   // Relacion OneToOne con Pedido
   // Envio es el lado propietario
   @OneToOne
   @JoinColumn(name = "id_pedido")
   private Pedido pedido;
}

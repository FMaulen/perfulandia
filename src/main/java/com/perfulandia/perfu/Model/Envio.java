package com.perfulandia.perfu.Model;

import jakarta.persistence.Id;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

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
   private int id;
   // Pedido FK
   private String direccion;
   private Date fecha_envio;
   private Date fecha_estimada;
   private String estado;
   private String transportista;
}

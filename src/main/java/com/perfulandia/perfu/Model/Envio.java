package com.perfulandia.perfu.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Envio {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private int id_envio;

   private String numero_seguimiento;

   @Temporal(TemporalType.TIMESTAMP)
   private Date fecha_envio;

   private String estado; // PENDIENTE, EN_PROCESO, EN_TRANSITO, ENTREGADO
   private String direccion_entrega;
   private String notas;

   @ManyToOne(fetch = FetchType.LAZY)
   @JoinColumn(name = "id_sucursal_origen")
   @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
   private Sucursal sucursalOrigen;

   @ManyToOne(fetch = FetchType.LAZY)
   @JoinColumn(name = "id_sucursal_destino")
   @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
   private Sucursal sucursalDestino;

   @ManyToOne(fetch = FetchType.LAZY)
   @JoinColumn(name = "id_cliente")
   @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
   private Cliente cliente;

   @ManyToMany(fetch = FetchType.LAZY)
   @JoinTable(
           name = "envio_producto",
           joinColumns = @JoinColumn(name = "id_envio"),
           inverseJoinColumns = @JoinColumn(name = "id_producto")
   )
   @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
   private List<Producto> productos;

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

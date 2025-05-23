package com.perfulandia.perfu.Model;

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
   private Long id_envio;

   private String numero_seguimiento;
   private Date fecha_envio;
   private String estado; // PENDIENTE, EN_PROCESO, EN_TRANSITO, ENTREGADO
   private String direccion_entrega;
   private String notas;

   @ManyToOne
   @JoinColumn(name = "id_sucursal_origen")
   private Sucursal sucursalOrigen;

   @ManyToOne
   @JoinColumn(name = "id_sucursal_destino")
   private Sucursal sucursalDestino;

   @ManyToOne
   @JoinColumn(name = "id_cliente")
   private Cliente cliente;

   @OneToMany
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

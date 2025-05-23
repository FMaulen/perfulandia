package com.perfulandia.perfu.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnvioDTO {
    private String direccion_entrega;
    private String notas;
    private int id_sucursal_origen;
    private int id_sucursal_destino;
    private int id_cliente;
    private List<Integer> id_productos;
    private String estado;  // Para actualizaciones

    // Constructor para creación
    public EnvioDTO(String direccion_entrega, String notas,
                    int id_sucursal_origen, int id_sucursal_destino,
                    int id_cliente, List<Integer> id_productos) {
        this.direccion_entrega = direccion_entrega;
        this.notas = notas;
        this.id_sucursal_origen = id_sucursal_origen;
        this.id_sucursal_destino = id_sucursal_destino;
        this.id_cliente = id_cliente;
        this.id_productos = id_productos;
    }
}
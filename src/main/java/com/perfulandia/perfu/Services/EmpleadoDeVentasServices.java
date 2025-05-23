package com.perfulandia.perfu.Services;

import com.perfulandia.perfu.Model.DetallePedido;
import com.perfulandia.perfu.Repository.EmpleadoDeVentasRepository;
import org.springframework.stereotype.Service;

@Service
public class EmpleadoDeVentasServices {

    private final EmpleadoDeVentasRepository empleadoDeVentasRepository;

    public EmpleadoDeVentasServices(EmpleadoDeVentasRepository empleadoDeVentasRepository) {
        this.empleadoDeVentasRepository = empleadoDeVentasRepository;
    }

    public String registrarVenta(DetallePedido detallePedido) {
        // Calcular subtotal sin descuento
        int subtotal = detallePedido.getCantidad() * detallePedido.getPrecio_unitario();

        // Calcular descuento si aplica
        int descuentoAplicado = 0;
        if (detallePedido.getDescuento() > 0) {
            descuentoAplicado = (subtotal * detallePedido.getDescuento()) / 100;
        }

        // Calcular total
        int total = subtotal - descuentoAplicado;

        // Guardar el detalle del pedido
        empleadoDeVentasRepository.save(detallePedido);

        // Generar mensaje de confirmación
        String mensaje = "Venta registrada exitosamente:\n" +
                "Producto ID: " + detallePedido.getProducto().getId() + "\n" +
                "Cantidad: " + detallePedido.getCantidad() + "\n" +
                "Precio unitario: $" + detallePedido.getPrecio_unitario() + "\n" +
                "Subtotal: $" + subtotal + "\n";

        if (descuentoAplicado > 0) {
            mensaje += "Descuento (" + detallePedido.getDescuento() + "%): -$" + descuentoAplicado + "\n";
        }

        mensaje += "Total a pagar: $" + total;

        return mensaje;
    }

    // Método adicional para obtener todas las ventas
    public String listarVentas() {
        StringBuilder output = new StringBuilder();
        for (DetallePedido venta : empleadoDeVentasRepository.findAll()) {
            output.append("ID Venta: ").append(venta.getId()).append("\n")
                    .append("ID Pedido: ").append(venta.getPedido().getId()).append("\n")
                    .append("Producto: ").append(venta.getProducto().getNombre()).append("\n")
                    .append("Cantidad: ").append(venta.getCantidad()).append("\n")
                    .append("Precio Unitario: $").append(venta.getPrecio_unitario()).append("\n")
                    .append("Descuento: ").append(venta.getDescuento()).append("%\n\n");
        }

        if (output.isEmpty()) {
            return "No hay ventas registradas";
        } else {
            return output.toString();
        }
    }

    // Método para obtener una venta por ID
    public String obtenerVentaPorID(int id) {
        return empleadoDeVentasRepository.findById(id)
                .map(venta -> {
                    String output = "ID Venta: " + venta.getId() + "\n";
                    output += "ID Pedido: " + venta.getPedido().getId() + "\n";
                    output += "Producto: " + venta.getProducto().getNombre() + "\n";
                    output += "Cantidad: " + venta.getCantidad() + "\n";
                    output += "Precio Unitario: $" + venta.getPrecio_unitario() + "\n";
                    output += "Descuento: " + venta.getDescuento() + "%\n";

                    // Calcular total
                    int subtotal = venta.getCantidad() * venta.getPrecio_unitario();
                    int descuento = (subtotal * venta.getDescuento()) / 100;
                    int total = subtotal - descuento;

                    output += "Total: $" + total;

                    return output;
                })
                .orElse("No existe una venta con ese ID");
    }
}
package com.perfulandia.perfu.Services;

import com.perfulandia.perfu.Model.DetallePedido;
import com.perfulandia.perfu.Repository.DetallePedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DetallePedidoService {

    @Autowired
    private DetallePedidoRepository detallePedidoRepository;

    public List<DetallePedido> listarDetalles() {
        return detallePedidoRepository.findAll();
    }

    public Optional<DetallePedido> buscarDetallePorId(int id) {
        return detallePedidoRepository.findById(id);
    }

    public DetallePedido agregarDetalle(DetallePedido detalle) {

        return detallePedidoRepository.save(detalle);
    }

    public Optional<DetallePedido> actualizarDetalle(int id, DetallePedido detalleActualizado) {
        return detallePedidoRepository.findById(id)
                .map(detalleExistente -> {
                    detalleExistente.setCantidad(detalleActualizado.getCantidad());
                    detalleExistente.setPrecio_unitario(detalleActualizado.getPrecio_unitario());
                    detalleExistente.setDescuento(detalleActualizado.getDescuento());
                    detalleExistente.setPedido(detalleActualizado.getPedido());
                    detalleExistente.setProducto(detalleActualizado.getProducto());
                    return detallePedidoRepository.save(detalleExistente);
                });
    }

    public void eliminarDetalle(int id) {
        detallePedidoRepository.deleteById(id);
    }
}

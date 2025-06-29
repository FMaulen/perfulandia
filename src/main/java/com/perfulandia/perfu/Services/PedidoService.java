package com.perfulandia.perfu.Services;

import com.perfulandia.perfu.Model.Pedido;
import com.perfulandia.perfu.Repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    public List<Pedido> listarPedidos() {
        return pedidoRepository.findAll();
    }

    public Optional<Pedido> buscarPedidoPorId(int id) {
        return pedidoRepository.findById(id);
    }

    public Pedido agregarPedido(Pedido pedido) {

        pedido.setFecha_pedido(new Date());
        if (pedido.getEstado_pedido() == null || pedido.getEstado_pedido().isEmpty()) {
            pedido.setEstado_pedido("PENDIENTE");
        }

        return pedidoRepository.save(pedido);
    }

    public Optional<Pedido> actualizarPedido(int id, Pedido pedidoActualizado) {
        return pedidoRepository.findById(id)
                .map(pedidoExistente -> {
                    pedidoExistente.setEstado_pedido(pedidoActualizado.getEstado_pedido());
                    pedidoExistente.setTotal_pedido(pedidoActualizado.getTotal_pedido());
                    pedidoExistente.setMetodo_pago(pedidoActualizado.getMetodo_pago());
                    pedidoExistente.setSucursal(pedidoActualizado.getSucursal());
                    pedidoExistente.setCliente(pedidoActualizado.getCliente());

                    return pedidoRepository.save(pedidoExistente);
                });
    }

    public void eliminarPedido(int id) {
        pedidoRepository.deleteById(id);
    }
}

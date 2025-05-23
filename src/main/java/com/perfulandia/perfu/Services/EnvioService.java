package com.perfulandia.perfu.Services;

import com.perfulandia.perfu.Model.*;
import com.perfulandia.perfu.Repository.ClienteRepository;
import com.perfulandia.perfu.Repository.EnvioRepository;
import com.perfulandia.perfu.Repository.ProductoRepository;
import com.perfulandia.perfu.Repository.SucursalRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class EnvioService {
    @Autowired
    private EnvioRepository envioRepository;
    @Autowired
    private SucursalRepository sucursalRepository;
    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private ProductoRepository productoRepository;

    // Crear nuevo envío usando DTO
    public Envio crearEnvio(EnvioDTO envioDTO) {
        Envio envio = new Envio();
        return guardarEnvioDesdeDTO(envio, envioDTO);
    }

    // Método auxiliar para guardar envío desde DTO
    private Envio guardarEnvioDesdeDTO(Envio envio, EnvioDTO envioDTO) {
        envio.setDireccion_entrega(envioDTO.getDireccion_entrega());
        envio.setNotas(envioDTO.getNotas());

        if (envio.getFecha_envio() == null) {
            envio.setFecha_envio(new Date());
            envio.setEstado("PENDIENTE");
            envio.setNumero_seguimiento("ENV-" + System.currentTimeMillis());
        }

        // Establecer relaciones
        Sucursal sucursalOrigen = sucursalRepository.findById(envioDTO.getId_sucursal_origen())
                .orElseThrow(() -> new RuntimeException("Sucursal origen no encontrada"));
        envio.setSucursalOrigen(sucursalOrigen);

        Sucursal sucursalDestino = sucursalRepository.findById(envioDTO.getId_sucursal_destino())
                .orElseThrow(() -> new RuntimeException("Sucursal destino no encontrada"));
        envio.setSucursalDestino(sucursalDestino);

        Cliente cliente = clienteRepository.findById(envioDTO.getId_cliente())
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
        envio.setCliente(cliente);

        List<Producto> productos = envioDTO.getId_productos().stream()
                .map(id -> productoRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("Producto no encontrado: " + id)))
                .collect(Collectors.toList());
        envio.setProductos(productos);

        return envioRepository.save(envio);
    }

    // Obtener envío por ID
    public Envio obtenerEnvioPorId(int id) {
        return envioRepository.findById((int) id)
                .orElseThrow(() -> new RuntimeException("Envío no encontrado"));
    }

    // Obtener envío por número de seguimiento
    public Envio obtenerEnvioPorSeguimiento(String numeroSeguimiento) {
        return envioRepository.findByNumeroSeguimiento(numeroSeguimiento)
                .orElseThrow(() -> new RuntimeException("Envío no encontrado"));
    }

    // Actualizar estado del envío
    public Envio actualizarEstadoEnvio(int id, String nuevoEstado) {
        Envio envio = obtenerEnvioPorId(id);
        envio.setEstado(nuevoEstado);
        return envioRepository.save(envio);
    }

    // Listar todos los envíos
    public List<Envio> listarTodos() {
        return envioRepository.findAll();
    }

    // Listar envíos por sucursal origen
    public List<Envio> listarEnviosPorSucursalOrigen(int idSucursal) {
        sucursalRepository.findById(idSucursal)
                .orElseThrow(() -> new RuntimeException("Sucursal no encontrada"));
        return envioRepository.findBySucursalOrigen_IdSucursal(idSucursal);
    }

    // Listar envíos por sucursal destino
    public List<Envio> listarEnviosPorSucursalDestino(int idSucursal) {
        sucursalRepository.findById(idSucursal)
                .orElseThrow(() -> new RuntimeException("Sucursal no encontrada"));
        return envioRepository.findBySucursalDestino_IdSucursal(idSucursal);
    }

    // Listar envíos por estado
    public List<Envio> listarEnviosPorEstado(String estado) {
        return envioRepository.findByEstado(estado);
    }

    // Actualizar envío
    public Envio actualizarEnvio(int id, EnvioDTO envioDTO) {
        Envio envioExistente = obtenerEnvioPorId(id);
        return guardarEnvioDesdeDTO(envioExistente, envioDTO);
    }

    // Eliminar envío
    public void eliminarEnvio(int id) {
        if (!envioRepository.existsById((int) id)) {
            throw new RuntimeException("Envío no encontrado");
        }
        envioRepository.deleteById((int) id);
    }
}
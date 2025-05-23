package com.perfulandia.perfu.Services;

import com.perfulandia.perfu.Model.Envio;
import com.perfulandia.perfu.Repository.EnvioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class EnvioService {
    @Autowired
    private EnvioRepository envioRepository;

    // Crear nuevo envío
    public Envio crearEnvio(Envio envio) {
        envio.setFecha_envio(new Date());
        envio.setEstado("PENDIENTE");
        // Generar número de seguimiento único
        envio.setNumero_seguimiento("ENV-" + System.currentTimeMillis());
        return envioRepository.save(envio);
    }

    // Obtener envío por ID
    public Envio obtenerEnvioPorId(Long id) {
        return envioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Envío no encontrado"));
    }

    // Obtener envío por número de seguimiento
    public Envio obtenerEnvioPorSeguimiento(String numeroSeguimiento) {
        return envioRepository.findByNumeroSeguimiento(numeroSeguimiento)
                .orElseThrow(() -> new RuntimeException("Envío no encontrado"));
    }

    // Actualizar estado del envío
    public Envio actualizarEstadoEnvio(Long id, String nuevoEstado) {
        Envio envio = obtenerEnvioPorId(id);
        envio.setEstado(nuevoEstado);
        return envioRepository.save(envio);
    }

    // Listar envíos por sucursal origen
    public List<Envio> listarEnviosPorSucursalOrigen(Long idSucursal) {
        return envioRepository.findBySucursalOrigenId(idSucursal);
    }

    // Listar envíos por sucursal destino
    public List<Envio> listarEnviosPorSucursalDestino(Long idSucursal) {
        return envioRepository.findBySucursalDestinoId(idSucursal);
    }

    // Listar envíos por estado
    public List<Envio> listarEnviosPorEstado(String estado) {
        return envioRepository.findByEstado(estado);
    }
}
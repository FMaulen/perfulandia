package com.perfulandia.perfu.Services;

import com.perfulandia.perfu.Model.Envio;
import com.perfulandia.perfu.Repository.EnvioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class EnvioService {

    @Autowired
    private EnvioRepository envioRepository;

    public List<Envio> listarEnvios() {
        return envioRepository.findAll();
    }

    public Optional<Envio> buscarEnvioPorId(int id) {
        return envioRepository.findById(id);
    }

    public Envio agregarEnvio(Envio envio) {

        envio.setFecha_envio(new Date());
        if (envio.getEstado() == null) {
            envio.setEstado("PROCESANDO");
        }
        return envioRepository.save(envio);
    }

    public Optional<Envio> actualizarEnvio(int id, Envio envioActualizado) {
        return envioRepository.findById(id)
                .map(envioExistente -> {
                    envioExistente.setDireccion(envioActualizado.getDireccion());
                    envioExistente.setFecha_estimada(envioActualizado.getFecha_estimada());
                    envioExistente.setEstado(envioActualizado.getEstado());
                    envioExistente.setTransportista(envioActualizado.getTransportista());
                    envioExistente.setLogistica(envioActualizado.getLogistica());
                    envioExistente.setPedido(envioActualizado.getPedido());
                    return envioRepository.save(envioExistente);
                });
    }

    public void eliminarEnvio(int id) {
        envioRepository.deleteById(id);
    }
}

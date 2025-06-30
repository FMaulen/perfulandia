package com.perfulandia.perfu.Services;

import com.perfulandia.perfu.Model.Logistica;
import com.perfulandia.perfu.Model.Proveedor;
import com.perfulandia.perfu.Repository.LogisticaRepository;
import com.perfulandia.perfu.Repository.ProveedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LogisticaService {

    @Autowired
    private LogisticaRepository logisticaRepository;

    @Autowired
    private ProveedorRepository proveedorRepository;

    public List<Logistica> listarLogisticas() {
        return logisticaRepository.findAll();
    }

    public Optional<Logistica> buscarLogisticaPorId(int id) {
        return logisticaRepository.findById(id);
    }

    public Logistica agregarLogistica(Logistica logistica) {
        return logisticaRepository.save(logistica);
    }

    public Optional<Logistica> actualizarLogistica(int id, Logistica logisticaActualizada) {
        return logisticaRepository.findById(id)
                .map(logisticaExistente -> {
                    logisticaExistente.setNombre(logisticaActualizada.getNombre());
                    logisticaExistente.setCorreo(logisticaActualizada.getCorreo());
                    logisticaExistente.setEspecialidad(logisticaActualizada.getEspecialidad());
                    return logisticaRepository.save(logisticaExistente);
                });
    }

    public void eliminarLogistica(int id) {
        logisticaRepository.deleteById(id);
    }


    public Optional<Logistica> asignarProveedor(int logisticaId, int proveedorId) {
        Optional<Logistica> logisticaOpt = logisticaRepository.findById(logisticaId);
        Optional<Proveedor> proveedorOpt = proveedorRepository.findById(proveedorId);

        if (logisticaOpt.isPresent() && proveedorOpt.isPresent()) {
            Logistica logistica = logisticaOpt.get();
            Proveedor proveedor = proveedorOpt.get();
            logistica.getProveedores().add(proveedor);
            return Optional.of(logisticaRepository.save(logistica));
        }
        return Optional.empty();
    }
}

package com.perfulandia.perfu.Services;

import com.perfulandia.perfu.Model.GerenteSucursal;
import com.perfulandia.perfu.Repository.GerenteSucursalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class GerenteSucursalService {

    @Autowired
    private GerenteSucursalRepository gerenteRepository;

    public List<GerenteSucursal> listarGerentes() {
        return gerenteRepository.findAll();
    }

    public Optional<GerenteSucursal> obtenerGerentePorId(Long id) {
        return gerenteRepository.findById(id);
    }

    public GerenteSucursal agregarGerente(GerenteSucursal gerente) {
        gerente.setFecha_contratacion(new Date());
        return gerenteRepository.save(gerente);
    }

    public Optional<GerenteSucursal> actualizarGerente(Long id, GerenteSucursal gerenteDetails) {
        return gerenteRepository.findById(id)
                .map(gerenteExistente -> {
                    gerenteExistente.setNombre(gerenteDetails.getNombre());
                    gerenteExistente.setCorreo(gerenteDetails.getCorreo());

                    if (gerenteDetails.getSucursal() != null) {
                        gerenteExistente.setSucursal(gerenteDetails.getSucursal());
                    }
                    return gerenteRepository.save(gerenteExistente);
                });
    }

    public void eliminarGerentePorId(Long id) {
        gerenteRepository.deleteById(id);
    }
}

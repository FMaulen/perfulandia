package com.perfulandia.perfu.Services;

import com.perfulandia.perfu.Model.Sucursal;
import com.perfulandia.perfu.Repository.SucursalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SucursalService {

    @Autowired
    private SucursalRepository sucursalRepository;

    public Sucursal registrarSucursal(Sucursal sucursal) {
        return sucursalRepository.save(sucursal);
    }

    public List<Sucursal> listarSucursales() {
        return sucursalRepository.findAll();
    }

    public Optional<Sucursal> buscarSucursalPorId(int id) {
        return sucursalRepository.findById(id);
    }

    public void eliminarSucursal(int id) {
        sucursalRepository.deleteById(id);
    }

    public Optional<Sucursal> actualizarSucursal(int id, Sucursal sucursalActualizada) {
        return sucursalRepository.findById(id)
                .map(s -> {
                    s.setNombre(sucursalActualizada.getNombre());
                    s.setDireccion(sucursalActualizada.getDireccion());
                    s.setTelefono(sucursalActualizada.getTelefono());
                    s.setCorreo(sucursalActualizada.getCorreo());
                    if (sucursalActualizada.getAdministradorSistema() != null) {
                        s.setAdministradorSistema(sucursalActualizada.getAdministradorSistema());
                    }
                    return sucursalRepository.save(s);
                });
    }
}

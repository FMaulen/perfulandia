package com.perfulandia.perfu.Services;

import com.perfulandia.perfu.Model.Proveedor;
import com.perfulandia.perfu.Repository.ProveedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProveedorService {

    @Autowired
    private ProveedorRepository proveedorRepository;

    public Proveedor registrarProveedor(Proveedor proveedor) {
        return proveedorRepository.save(proveedor);
    }

    public List<Proveedor> listarProveedores() {
        return proveedorRepository.findAll();
    }

    public Optional<Proveedor> buscarProveedorPorId(int id) {
        return proveedorRepository.findById(id);
    }

    public void eliminarProveedor(int id) {
        proveedorRepository.deleteById(id);
    }

    public Optional<Proveedor> actualizarProveedor(int id, Proveedor proveedorActualizado) {
        return proveedorRepository.findById(id)
                .map(p -> {
                    p.setNombre(proveedorActualizado.getNombre());
                    p.setContacto(proveedorActualizado.getContacto());
                    p.setTelefono(proveedorActualizado.getTelefono());
                    p.setEmail(proveedorActualizado.getEmail());
                    p.setDireccion(proveedorActualizado.getDireccion());
                    return proveedorRepository.save(p);
                });
    }
}

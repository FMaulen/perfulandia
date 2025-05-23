package com.perfulandia.perfu.Services;

import com.perfulandia.perfu.Model.Proveedor;
import com.perfulandia.perfu.Repository.ProveedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProveedorService {

    @Autowired
    private ProveedorRepository proveedorRepository;

    public String registrarProveedor(Proveedor proveedor) {
        proveedorRepository.save(proveedor);
        return "Proveedor registrado correctamente";
    }

    public String listarProveedores() {
        StringBuilder output = new StringBuilder();
        for (Proveedor p : proveedorRepository.findAll()) {
            output.append("ID: ").append(p.getId_proveedor()).append("\n")
                    .append("Nombre: ").append(p.getNombre()).append("\n")
                    .append("Contacto: ").append(p.getContacto()).append("\n")
                    .append("Teléfono: ").append(p.getTelefono()).append("\n\n");
        }
        return output.length() > 0 ? output.toString() : "No hay proveedores registrados";
    }

    public String buscarProveedorPorId(int id) {
        return proveedorRepository.findById(id)
                .map(p -> "ID: " + p.getId_proveedor() + "\n" +
                        "Nombre: " + p.getNombre() + "\n" +
                        "Email: " + p.getEmail() + "\n" +
                        "Dirección: " + p.getDireccion())
                .orElse("No existe proveedor con ese ID");
    }

    public String eliminarProveedor(int id) {
        if (proveedorRepository.existsById(id)) {
            proveedorRepository.deleteById(id);
            return "Proveedor eliminado correctamente";
        }
        return "No existe proveedor con ese ID";
    }

    public String actualizarProveedor(int id, Proveedor proveedorActualizado) {
        return proveedorRepository.findById(id)
                .map(p -> {
                    p.setNombre(proveedorActualizado.getNombre());
                    p.setContacto(proveedorActualizado.getContacto());
                    p.setTelefono(proveedorActualizado.getTelefono());
                    p.setEmail(proveedorActualizado.getEmail());
                    p.setDireccion(proveedorActualizado.getDireccion());
                    proveedorRepository.save(p);
                    return "Proveedor actualizado correctamente";
                })
                .orElse("No existe proveedor con ese ID");
    }
}

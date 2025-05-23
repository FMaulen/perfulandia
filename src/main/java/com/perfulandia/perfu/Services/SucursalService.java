package com.perfulandia.perfu.Services;

import com.perfulandia.perfu.Model.Sucursal;
import com.perfulandia.perfu.Repository.SucursalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SucursalService {

    @Autowired
    private SucursalRepository sucursalRepository;

    public String registrarSucursal(Sucursal sucursal) {
        sucursalRepository.save(sucursal);
        return "Sucursal registrada correctamente";
    }

    public String listarSucursales() {
        StringBuilder output = new StringBuilder();
        for (Sucursal s : sucursalRepository.findAll()) {
            output.append("ID: ").append(s.getId_sucursal()).append("\n")
                    .append("Nombre: ").append(s.getNombre()).append("\n")
                    .append("Dirección: ").append(s.getDireccion()).append("\n")
                    .append("Teléfono: ").append(s.getTelefono()).append("\n\n");
        }
        return output.length() > 0 ? output.toString() : "No hay sucursales registradas";
    }

    public String buscarSucursalPorId(int id) {
        return sucursalRepository.findById(id)
                .map(s -> "ID: " + s.getId_sucursal() + "\n" +
                        "Nombre: " + s.getNombre() + "\n" +
                        "Email: " + s.getCorreo() + "\n" +
                        "Gerente: " + (s.getGerenteSucursal() != null ? s.getGerenteSucursal().getNombre() : "No asignado"))
                .orElse("No existe sucursal con ese ID");
    }

    public String eliminarSucursal(int id) {
        if (sucursalRepository.existsById(id)) {
            sucursalRepository.deleteById(id);
            return "Sucursal eliminada correctamente";
        }
        return "No existe sucursal con ese ID";
    }

    public String actualizarSucursal(int id, Sucursal sucursalActualizada) {
        return sucursalRepository.findById(id)
                .map(s -> {
                    s.setNombre(sucursalActualizada.getNombre());
                    s.setDireccion(sucursalActualizada.getDireccion());
                    s.setTelefono(sucursalActualizada.getTelefono());
                    s.setCorreo(sucursalActualizada.getCorreo());
                    sucursalRepository.save(s);
                    return "Sucursal actualizada correctamente";
                })
                .orElse("No existe sucursal con ese ID");
    }
}

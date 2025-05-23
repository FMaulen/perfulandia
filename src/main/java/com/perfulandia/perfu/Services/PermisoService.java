package com.perfulandia.perfu.Services;

import com.perfulandia.perfu.Model.Permiso;
import com.perfulandia.perfu.Repository.PermisoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PermisoService {

    @Autowired
    private PermisoRepository permisoRepository;

    public String crearPermiso(String nombrePermiso) {
        Permiso permiso = new Permiso();
        permiso.setNombre_permiso(nombrePermiso);
        permisoRepository.save(permiso);
        return "Permiso creado: " + nombrePermiso;
    }

    public String listarTodos() {
        StringBuilder sb = new StringBuilder();
        for (Permiso p : permisoRepository.findAll()) {
            sb.append("ID: ").append(p.getId_permiso())
                    .append(" | Nombre: ").append(p.getNombre_permiso())
                    .append("\n");
        }
        return sb.length() > 0 ? sb.toString() : "No hay permisos registrados";
    }

    public String eliminarPermiso(int id) {
        if (permisoRepository.existsById(id)) {
            permisoRepository.deleteById(id);
            return "Permiso eliminado";
        }
        return "Permiso no encontrado";
    }
}

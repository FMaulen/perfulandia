package com.perfulandia.perfu.Services;

import com.perfulandia.perfu.Model.AdministradorSistema;
import com.perfulandia.perfu.Model.Permiso;
import com.perfulandia.perfu.Repository.AdministradorSistemaRepository;
import com.perfulandia.perfu.Repository.PermisoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AdministradorSistemaService {

    @Autowired
    private AdministradorSistemaRepository adminRepository;

    @Autowired
    private PermisoRepository permisoRepository;

    public String registrarAdmin(AdministradorSistema admin) {
        admin.setFecha_registro(new Date());
        admin.setActivo(true);
        adminRepository.save(admin);
        return "Administrador registrado correctamente";
    }

    public String listarAdmins() {
        StringBuilder output = new StringBuilder();
        adminRepository.findAll().forEach(admin -> {
            output.append("ID: ").append(admin.getId_administrador_sistema()).append("\n")
                    .append("Nombre: ").append(admin.getNombre()).append("\n")
                    .append("Correo: ").append(admin.getCorreo()).append("\n")
                    .append("Estado: ").append(admin.getActivo() ? "Activo" : "Inactivo").append("\n\n");
        });
        return output.length() > 0 ? output.toString() : "No hay administradores registrados";
    }

    public String buscarAdminPorId(int id) {
        return adminRepository.findById(id)
                .map(admin -> {
                    String permisos = admin.getPermisos().stream()
                            .map(Permiso::getNombre_permiso)
                            .collect(Collectors.joining(", "));

                    return "ID: " + admin.getId_administrador_sistema() + "\n" +
                            "Nombre: " + admin.getNombre() + "\n" +
                            "Correo: " + admin.getCorreo() + "\n" +
                            "Permisos: " + (permisos.isEmpty() ? "Ninguno" : permisos);
                })
                .orElse("Administrador no encontrado");
    }

    public String cambiarEstadoAdmin(int id, boolean activo) {
        return adminRepository.findById(id)
                .map(admin -> {
                    admin.setActivo(activo);
                    adminRepository.save(admin);
                    return "Estado actualizado a: " + (activo ? "Activo" : "Inactivo");
                })
                .orElse("Administrador no encontrado");
    }

    public String asignarPermisos(int adminId, List<Integer> permisosIds) {
        return adminRepository.findById(adminId)
                .map(admin -> {
                    Set<Permiso> permisos = permisoRepository.findAllById(permisosIds).stream()
                            .collect(Collectors.toSet());
                    admin.setPermisos(permisos);
                    adminRepository.save(admin);
                    return permisosIds.size() + " permisos asignados correctamente";
                })
                .orElse("Administrador no encontrado");
    }

    public String quitarPermiso(int adminId, int permisoId) {
        return adminRepository.findById(adminId)
                .map(admin -> {
                    admin.getPermisos().removeIf(p -> p.getId_permiso() == permisoId);
                    adminRepository.save(admin);
                    return "Permiso removido correctamente";
                })
                .orElse("Administrador no encontrado");
    }
}

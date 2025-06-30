package com.perfulandia.perfu.Services;

import com.perfulandia.perfu.Model.AdministradorSistema;
import com.perfulandia.perfu.Model.Permiso;
import com.perfulandia.perfu.Repository.AdministradorSistemaRepository;
import com.perfulandia.perfu.Repository.PermisoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AdministradorSistemaService {

    @Autowired
    private AdministradorSistemaRepository adminRepository;

    @Autowired
    private PermisoRepository permisoRepository;

    public AdministradorSistema registrarAdmin(AdministradorSistema admin) {
        admin.setFecha_registro(new Date());
        admin.setActivo(true);
        return adminRepository.save(admin);
    }

    public List<AdministradorSistema> listarAdmins() {
        return adminRepository.findAll();
    }

    public Optional<AdministradorSistema> buscarAdminPorId(int id) {
        return adminRepository.findById(id);
    }

    public Optional<AdministradorSistema> cambiarEstadoAdmin(int id, boolean activo) {
        return adminRepository.findById(id)
                .map(admin -> {
                    admin.setActivo(activo);
                    return adminRepository.save(admin);
                });
    }

    public Optional<AdministradorSistema> asignarPermisos(int adminId, List<Integer> permisosIds) {
        return adminRepository.findById(adminId)
                .map(admin -> {
                    Set<Permiso> permisos = permisoRepository.findAllById(permisosIds).stream()
                            .collect(Collectors.toSet());
                    admin.setPermisos(permisos);
                    return adminRepository.save(admin);
                });
    }


    public Optional<AdministradorSistema> quitarPermiso(int adminId, int permisoId) {
        Optional<AdministradorSistema> adminOpt = adminRepository.findById(adminId);
        if (adminOpt.isPresent()) {
            AdministradorSistema admin = adminOpt.get();
            boolean removed = admin.getPermisos().removeIf(p -> p.getId_permiso() == permisoId);
            if (removed) {
                return Optional.of(adminRepository.save(admin));
            }
        }
        return adminOpt;
    }
}

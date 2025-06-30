package com.perfulandia.perfu.Services;

import com.perfulandia.perfu.Model.Permiso;
import com.perfulandia.perfu.Repository.PermisoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PermisoService {

    @Autowired
    private PermisoRepository permisoRepository;

    public Permiso crearPermiso(Permiso permiso) {
        return permisoRepository.save(permiso);
    }

    public List<Permiso> listarTodos() {
        return permisoRepository.findAll();
    }

    public Optional<Permiso> buscarPorId(int id) {
        return permisoRepository.findById(id);
    }

    public void eliminarPermiso(int id) {
        permisoRepository.deleteById(id);
    }
}

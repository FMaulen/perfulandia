package com.perfulandia.perfu.Services;

import com.perfulandia.perfu.Model.*;
import com.perfulandia.perfu.Repository.SucursalRepository;
import com.perfulandia.perfu.Repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.Date;
@RequiredArgsConstructor
@Service
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final SucursalRepository sucursalRepository;

    public AdministradorSistema crearAdministrador(AdministradorSistema admin) {
        admin.setFechaRegistro(new Date());
        return usuarioRepository.save(admin);
    }

    public GerenteSucursal crearGerente(GerenteSucursal gerente, Long sucursalId) {
        Sucursal sucursal = sucursalRepository.findById(sucursalId)
                .orElseThrow(() -> new RuntimeException("Sucursal no encontrada"));

        gerente.setSucursalAsignada(sucursal);
        gerente.setFechaRegistro(new Date());
        return usuarioRepository.save(gerente);
    }

    public EmpleadoDeVentas crearEmpleadoVentas(EmpleadoDeVentas empleado, Long sucursalId) {
        Sucursal sucursal = sucursalRepository.findById(sucursalId)
                .orElseThrow(() -> new RuntimeException("Sucursal no encontrada"));

        empleado.setSucursal(sucursal);
        empleado.setFechaRegistro(new Date());
        return usuarioRepository.save(empleado);
    }

    public Logistica crearLogistica(Logistica logistica) {
        logistica.setFechaRegistro(new Date());
        return usuarioRepository.save(logistica);
    }

    public Usuario login(String email, String password) {
        return usuarioRepository.findByEmailAndPassword(email, password);
    }

    public void desactivarUsuario(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        usuario.setActivo(false);
        usuarioRepository.save(usuario);
    }
}
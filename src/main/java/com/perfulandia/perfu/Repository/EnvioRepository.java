package com.perfulandia.perfu.Repository;

import com.perfulandia.perfu.Model.Envio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EnvioRepository extends JpaRepository<Envio, Integer> {
    // Cambiamos findBySucursalOrigenId por findBySucursalOrigenIdSucursal
    List<Envio> findBySucursalOrigen_IdSucursal(int idSucursal);

    // Cambiamos findBySucursalDestinoId por findBySucursalDestinoIdSucursal
    List<Envio> findBySucursalDestino_IdSucursal(int idSucursal);

    Optional<Envio> findByNumeroSeguimiento(String numeroSeguimiento);

    List<Envio> findByEstado(String estado);
}
package com.perfulandia.perfu.Repository;

import com.perfulandia.perfu.Model.Envio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EnvioRepository extends JpaRepository<Envio, Long> {
    List<Envio> findBySucursalOrigenId(Long id_sucursal);
    List<Envio> findBySucursalDestinoId(Long id_sucursal);
    List<Envio> findByClienteId(Long id_cliente);
    List<Envio> findByEstado(String estado);
    Optional<Envio> findByNumeroSeguimiento(String numeroSeguimiento);
}
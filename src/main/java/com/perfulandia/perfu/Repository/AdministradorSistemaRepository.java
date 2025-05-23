package com.perfulandia.perfu.Repository;

import com.perfulandia.perfu.Model.AdministradorSistema;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdministradorSistemaRepository extends JpaRepository<AdministradorSistema, Integer> {
}

package com.perfulandia.perfu.Repository;

import com.perfulandia.perfu.Model.Logistica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogisticaRepository extends JpaRepository<Logistica, Integer> {
}

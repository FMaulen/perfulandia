package com.perfulandia.perfu.Repository;

import com.perfulandia.perfu.Model.Resena;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResenaRepository extends JpaRepository<Resena, Integer> {
}

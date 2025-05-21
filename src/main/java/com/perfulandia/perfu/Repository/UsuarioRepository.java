package com.perfulandia.perfu.Repository;

import com.perfulandia.perfu.Model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Usuario findByEmailAndPassword(String email, String password);
}

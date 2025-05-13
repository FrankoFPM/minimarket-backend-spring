package org.minimarket.minimarketbackendspring.repositories;

import org.minimarket.minimarketbackendspring.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UsuarioRepository extends JpaRepository<Usuario, String> {
    List<Usuario> findByEmail(String email);

    Usuario findByGoogleId(String googleId);

    Usuario findByFacebookId(String facebookId);
}

package org.minimarket.minimarketbackendspring.repositories;

import org.minimarket.minimarketbackendspring.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Repositorio para manejar operaciones CRUD de la entidad Usuario.
 */
public interface UsuarioRepository extends JpaRepository<Usuario, String> {



    /**
     * Encuentra un usuario por su dirección de email.
     *
     * @param email dirección de email del usuario
     * @return
     */
    Optional<Usuario> findByEmail(String email);

    /**
     * Encuentra todos los usuarios asociados a un distrito específico.
     *
     * @param idDistrito el identificador del distrito
     * @return
     */
    List<Usuario> findByIdDistrito_Id(Long idDistrito);

    /**
     * Encuentra un usuario por su ID de Google.
     *
     * @param googleId el ID de Google del usuario
     * @return 
     */
    Optional<Usuario> findByGoogleId(String googleId);

    /**
     * Encuentra un usuario por su ID de Facebook.
     *
     * @param facebookId el ID de Facebook del usuario
     * @return
     */
    Optional<Usuario> findByFacebookId(String facebookId);

    /**
     * Encuentra usuarios por su rol.
     *
     * @param rol rol del usuario (cliente o admin)
     * @return 
     */
    List<Usuario> findByRol(String rol);
}

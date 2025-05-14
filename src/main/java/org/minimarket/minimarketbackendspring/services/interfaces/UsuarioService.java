package org.minimarket.minimarketbackendspring.services.interfaces;

import org.minimarket.minimarketbackendspring.dtos.UsuarioDTO;

import java.util.List;
import java.util.Optional;

/**
 * Interfaz para definir las operaciones relacionadas con la entidad Usuario.
 */
public interface UsuarioService {

    /**
     * Obtiene una lista de todos los usuarios.
     *
     * @return 
     */
    List<UsuarioDTO> findAll();

    /**
     * Busca un usuario por su ID.
     *
     * @param id el identificador del usuario
     * @return
     */
    UsuarioDTO findById(String id);

    /**
     * Busca un usuario por su email.
     *
     * @param email el email del usuario
     * @return 
     */
    Optional<UsuarioDTO> findByEmail(String email);

    /**
     * Obtiene una lista de usuarios asociados a un distrito específico.
     *
     * @param idDistrito el identificador del distrito
     * @return
     */
    List<UsuarioDTO> findByDistritoId(Long idDistrito);

    /**
     * Busca un usuario por su ID de Google.
     *
     * @param googleId el ID de Google del usuario
     * @return 
     */
    Optional<UsuarioDTO> findByGoogleId(String googleId);

    /**
     * Busca un usuario por su ID de Facebook.
     *
     * @param facebookId el ID de Facebook del usuario
     * @return
     */
    Optional<UsuarioDTO> findByFacebookId(String facebookId);

    /**
     * Encuentra usuarios por su rol.
     *
     * @param rol rol del usuario
     * @return
     */
    List<UsuarioDTO> findByRol(String rol);

    /**
     * Guarda un nuevo usuario.
     *
     * @param usuario
     */
    void save(UsuarioDTO usuario);

    /**
     * Actualiza un usuario existente.
     *
     * @param usuario 
     */
    void update(UsuarioDTO usuario);

    /**
     * Elimina un usuario por su ID.
     *
     * @param id 
     */
    void delete(String id);
}
package org.minimarket.minimarketbackendspring.services.impl;

import jakarta.persistence.EntityNotFoundException;
import org.minimarket.minimarketbackendspring.dtos.UsuarioDTO;
import org.minimarket.minimarketbackendspring.entities.Distrito;
import org.minimarket.minimarketbackendspring.entities.Usuario;
import org.minimarket.minimarketbackendspring.repositories.DistritoRepository;
import org.minimarket.minimarketbackendspring.repositories.UsuarioRepository;
import org.minimarket.minimarketbackendspring.services.interfaces.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Implementación del servicio de Usuario.
 */
@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private DistritoRepository distritoRepository;

    /**
     * Convierte una entidad Usuario a UsuarioDTO.
     *
     * @param u la entidad Usuario
     * @return 
     */
    private UsuarioDTO convertToDTO(Usuario u) {
        return new UsuarioDTO(
                u.getIdUsuario(),
                u.getNombre(),
                u.getApellido(),
                u.getEmail(),
                u.getClave(),
                u.getTelefono(),
                u.getIdDistrito() != null ? u.getIdDistrito().getId() : null,
                u.getDireccion(),
                u.getGoogleId(),
                u.getFacebookId(),
                u.getRol(),
                u.getEstado(),
                u.getCreatedAt(),
                u.getUpdatedAt()
        );
    }

    /**
     * Obtiene una lista de todos los usuarios.
     *
     * @return
     */
    @Override
    public List<UsuarioDTO> findAll() {
        List<Usuario> usuarios = usuarioRepository.findAll();

        return usuarios.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Busca un usuario por su ID.
     *
     * @param id el identificador del usuario
     * @return 
     */
    @Override
    public UsuarioDTO findById(String id) {
        return usuarioRepository.findById(id)
                .map(this::convertToDTO)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado con ID: " + id));
    }

    /**
     * Busca un usuario por su email.
     *
     * @param email el email del usuario
     * @return
     */
    @Override
    public Optional<UsuarioDTO> findByEmail(String email) {
        return usuarioRepository.findByEmail(email)
                .map(this::convertToDTO);
    }

    /**
     * Obtiene una lista de usuarios asociados a un distrito específico.
     *
     * @param idDistrito el identificador del distrito
     * @return
     */
    @Override
    public List<UsuarioDTO> findByDistritoId(Long idDistrito) {
        return usuarioRepository.findByIdDistrito_Id(idDistrito).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Busca un usuario por su ID de Google.
     *
     * @param googleId el ID de Google del usuario
     * @return 
     */
    @Override
    public Optional<UsuarioDTO> findByGoogleId(String googleId) {
        return usuarioRepository.findByGoogleId(googleId)
                .map(this::convertToDTO);
    }

    /**
     * Busca un usuario por su ID de Facebook.
     *
     * @param facebookId el ID de Facebook del usuario
     * @return
     */
    @Override
    public Optional<UsuarioDTO> findByFacebookId(String facebookId) {
        return usuarioRepository.findByFacebookId(facebookId)
                .map(this::convertToDTO);
    }

    /**
     * Encuentra usuarios por su rol.
     *
     * @param rol el rol del usuario (cliente, admin, etc.)
     * @return
     */
    @Override
    public List<UsuarioDTO> findByRol(String rol) {
        return usuarioRepository.findByRol(rol).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Guarda un nuevo usuario.
     *
     * @param usuario el objeto UsuarioDTO a guardar
     */
    @Override
    public void save(UsuarioDTO usuario) {
        Usuario u = new Usuario();
        
        // Aqui se genera un ID único para nuevos usuarios :D
        if (usuario.getIdUsuario() == null || usuario.getIdUsuario().isEmpty()) {
            u.setIdUsuario(UUID.randomUUID().toString());
        } else {
            u.setIdUsuario(usuario.getIdUsuario());
        }
        
        u.setNombre(usuario.getNombre());
        u.setApellido(usuario.getApellido());
        u.setEmail(usuario.getEmail());
        u.setClave(usuario.getClave());
        u.setTelefono(usuario.getTelefono());
        u.setDireccion(usuario.getDireccion());
        u.setGoogleId(usuario.getGoogleId());
        u.setFacebookId(usuario.getFacebookId());
        u.setRol(usuario.getRol());
        u.setEstado(usuario.getEstado());

        // Asignar distrito proporcionando ID
        if (usuario.getIdDistrito() != null) {
            Distrito distrito = distritoRepository.findById(usuario.getIdDistrito())
                    .orElseThrow(() -> new EntityNotFoundException("Distrito no encontrado con ID: " + usuario.getIdDistrito()));
            u.setIdDistrito(distrito);
        }

        usuarioRepository.save(u);
    }

    /**
     * Actualiza un usuario existente.
     *
     * @param usuario
     */
    @Override
    public void update(UsuarioDTO usuario) {
        Usuario u = usuarioRepository.findById(usuario.getIdUsuario())
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado con ID: " + usuario.getIdUsuario()));

        u.setNombre(usuario.getNombre());
        u.setApellido(usuario.getApellido());
        u.setEmail(usuario.getEmail());
        
        // Aqui actualiza la clave si se proporciona una nueva
        if (usuario.getClave() != null && !usuario.getClave().isEmpty()) {
            u.setClave(usuario.getClave());
        }
        
        u.setTelefono(usuario.getTelefono());
        u.setDireccion(usuario.getDireccion());
        u.setGoogleId(usuario.getGoogleId());
        u.setFacebookId(usuario.getFacebookId());
        u.setRol(usuario.getRol());
        u.setEstado(usuario.getEstado());

        // Actualizar distrito si se proporciona ID
        if (usuario.getIdDistrito() != null) {
            Distrito distrito = distritoRepository.findById(usuario.getIdDistrito())
                    .orElseThrow(() -> new EntityNotFoundException("Distrito no encontrado con ID: " + usuario.getIdDistrito()));
            u.setIdDistrito(distrito);
        } else {
            u.setIdDistrito(null);
        }

        usuarioRepository.save(u);
    }

    /**
     * Elimina un usuario por su ID.
     *
     * @param id
     */
    @Override
    public void delete(String id) {
        if (!usuarioRepository.existsById(id)) {
            throw new EntityNotFoundException("Usuario no encontrado con ID: " + id);
        }
        usuarioRepository.deleteById(id);
    }
}
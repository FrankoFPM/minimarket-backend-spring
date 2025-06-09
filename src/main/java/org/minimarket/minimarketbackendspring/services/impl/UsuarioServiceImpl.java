package org.minimarket.minimarketbackendspring.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.minimarket.minimarketbackendspring.dtos.UsuarioDTO;
import org.minimarket.minimarketbackendspring.entities.Distrito;
import org.minimarket.minimarketbackendspring.entities.Usuario;
import org.minimarket.minimarketbackendspring.repositories.DistritoRepository;
import org.minimarket.minimarketbackendspring.repositories.UsuarioRepository;
import org.minimarket.minimarketbackendspring.services.interfaces.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;

/**
 * Implementación del servicio de Usuario.
 */
@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private DistritoRepository distritoRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Convierte una entidad Usuario a UsuarioDTO.
     *
     * @param u la entidad Usuario
     * @return 
     */
    public UsuarioDTO convertToDTO(Usuario u) {
        return new UsuarioDTO(
                u.getIdUsuario(),
                u.getNombre(),
                u.getApellido(),
                u.getEmail(),
                u.getTelefono(),
                u.getClave(),
                u.getIdDistrito() != null ? u.getIdDistrito().getId() : null,
                u.getIdDistrito() != null ? u.getIdDistrito().getNombre() : null,
                u.getDireccion(),
                u.getGoogleId(),
                u.getFacebookId(),
                u.getRol(),
                u.getEstado(),
                null, // createdBy (ajusta si tienes este dato en la entidad)
                null  // updatedBy (ajusta si tienes este dato en la entidad)
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

    /*
    insert into usuarios (nombre, apellido, email, clave, telefono, id_distrito, direccion, rol)
    values ('Franco', 'PM','example@example.com','123456789','987654321',1,'Calle Falsa 123','admin');
    * */

    /**
     * Guarda un nuevo usuario.
     *
     * @param usuario el objeto UsuarioDTO a guardar
     */
    @Override
    public void save(UsuarioDTO usuario) {
        Usuario u = new Usuario();
        
        // Aqui se genera un ID único para nuevos usuarios :D
        if (usuario.getId() == null || usuario.getId().isEmpty()) {
            u.setIdUsuario(UUID.randomUUID().toString());
        } else {
            u.setIdUsuario(usuario.getId());
        }
        
        u.setNombre(usuario.getNombre());
        u.setApellido(usuario.getApellido());
        u.setEmail(usuario.getEmail());
        u.setClave(passwordEncoder.encode(usuario.getPassword()));
        u.setTelefono(usuario.getTelefono());

        if (usuario.getDistritoId() != null) {
            Distrito distrito = distritoRepository.findById(usuario.getDistritoId())
                    .orElseThrow(() -> new EntityNotFoundException("Distrito no encontrado con ID: " + usuario.getDistritoId()));
            u.setIdDistrito(distrito);
        } else {
            u.setIdDistrito(null);
        }

        u.setDireccion(usuario.getDireccion());
        u.setRol(usuario.getRol() != null ? usuario.getRol() : "cliente");
        u.setGoogleId(usuario.getGoogleId());
        u.setFacebookId(usuario.getFacebookId());
        u.setEstado("activo"); 

        // Asignar distrito proporcionando ID
        if (usuario.getDistritoId() != null) {
            Distrito distrito = distritoRepository.findById(usuario.getDistritoId())
                    .orElseThrow(() -> new EntityNotFoundException("Distrito no encontrado con ID: " + usuario.getDistritoId()));
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
        Usuario u = usuarioRepository.findById(usuario.getId())
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado con ID: " + usuario.getId()));

        u.setNombre(usuario.getNombre());
        u.setApellido(usuario.getApellido());
        u.setEmail(usuario.getEmail());
        
        // Aqui actualiza la clave si se proporciona una nueva
        if (usuario.getPassword() != null && !usuario.getPassword().isEmpty()) {
            u.setClave(passwordEncoder.encode(usuario.getPassword()));
        }
        
        u.setTelefono(usuario.getTelefono());
        u.setDireccion(usuario.getDireccion());
        u.setGoogleId(usuario.getGoogleId());
        u.setFacebookId(usuario.getFacebookId());
        u.setRol(usuario.getRol());
        u.setEstado(usuario.getEstado());

        // Actualizar distrito si se proporciona ID
        if (usuario.getDistritoId() != null) {
            Distrito distrito = distritoRepository.findById(usuario.getDistritoId())
                    .orElseThrow(() -> new EntityNotFoundException("Distrito no encontrado con ID: " + usuario.getDistritoId()));
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
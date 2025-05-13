package org.minimarket.minimarketbackendspring.services.impl;

import org.minimarket.minimarketbackendspring.dtos.UsuarioDTO;
import org.minimarket.minimarketbackendspring.entities.Usuario;
import org.minimarket.minimarketbackendspring.repositories.UsuarioRepository;
import org.minimarket.minimarketbackendspring.services.interfaces.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    /**
     * @return
     */
    @Override
    public List<UsuarioDTO> findAll() {
        List<Usuario> usuarios = usuarioRepository.findAll();

        return usuarios.stream()
                .map(u -> new UsuarioDTO(
                        u.getIdUsuario(),
                        u.getNombre(),
                        u.getApellido(),
                        u.getEmail(),
                        u.getTelefono(),
                        u.getClave(),
                        u.getIdDistrito().getNombre(),
                        u.getGoogleId(),
                        u.getFacebookId(),
                        u.getRol(),
                        u.getEstado(),
                        (u.getCreatedBy() != null && u.getCreatedBy().getNombre() != null) ? u.getCreatedBy().getNombre() : "Usuario_no_asignado", // Manejo de null
                        (u.getUpdateBy() != null && u.getUpdateBy().getNombre() != null) ? u.getUpdateBy().getNombre() : "Usuario_no_asignado"  // Manejo de null
                ))
                .toList();
    }

    /**
     * @param id
     * @return
     */
    @Override
    public UsuarioDTO findById(String id) {
        return usuarioRepository.findById(id)
                .map(u -> new UsuarioDTO(
                        u.getIdUsuario(),
                        u.getNombre(),
                        u.getApellido(),
                        u.getEmail(),
                        u.getTelefono(),
                        u.getClave(),
                        u.getIdDistrito().getNombre(),
                        u.getGoogleId(),
                        u.getFacebookId(),
                        u.getRol(),
                        u.getEstado(),
                        (u.getCreatedBy() != null && u.getCreatedBy().getNombre() != null) ? u.getCreatedBy().getNombre() : "Usuario_no_asignado", // Manejo de null
                        (u.getUpdateBy() != null && u.getUpdateBy().getNombre() != null) ? u.getUpdateBy().getNombre() : "Usuario_no_asignado"  // Manejo de null
                )).orElse(null);
    }

    /**
     * @param email
     * @return
     */
    @Override
    public UsuarioDTO findByEmail(String email) {
        return usuarioRepository.findByEmail(email).stream()
                .map(u -> new UsuarioDTO(
                        u.getIdUsuario(),
                        u.getNombre(),
                        u.getApellido(),
                        u.getEmail(),
                        u.getTelefono(),
                        u.getClave(),
                        u.getIdDistrito().getNombre(),
                        u.getGoogleId(),
                        u.getFacebookId(),
                        u.getRol(),
                        u.getEstado(),
                        (u.getCreatedBy() != null && u.getCreatedBy().getNombre() != null) ? u.getCreatedBy().getNombre() : "Usuario_no_asignado", // Manejo de null
                        (u.getUpdateBy() != null && u.getUpdateBy().getNombre() != null) ? u.getUpdateBy().getNombre() : "Usuario_no_asignado"  // Manejo de null
                )).findFirst().orElse(null);
    }

    /**
     * @param usuario
     */
    @Override
    public void save(UsuarioDTO usuario) {

    }

    /**
     * @param usuario
     */
    @Override
    public void update(UsuarioDTO usuario) {

    }

    /**
     * @param id
     */
    @Override
    public void delete(String id) {

    }

    /**
     * @param email
     * @param password
     * @return true si la autenticación es exitosa, false en caso contrario
     */
    @Override
    public boolean authenticate(String email, String password) {
        return usuarioRepository.findByEmail(email)
                .stream()
                .anyMatch(u -> u.getEmail().equals(email) && u.getClave().equals(password));
    }
}

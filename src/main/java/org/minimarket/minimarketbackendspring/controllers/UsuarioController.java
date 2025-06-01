package org.minimarket.minimarketbackendspring.controllers;

import java.util.List;

import org.minimarket.minimarketbackendspring.dtos.UsuarioDTO;
import org.minimarket.minimarketbackendspring.dtos.requests.LoginRequestDTO;
import org.minimarket.minimarketbackendspring.entities.Usuario;
import org.minimarket.minimarketbackendspring.services.interfaces.AuthService;
import org.minimarket.minimarketbackendspring.services.interfaces.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controlador REST para manejar operaciones sobre Usuario.
 * TODO: aplicar @Valid y manejo de errores
 */
@RestController
@RequestMapping("/api/usuario")
@CrossOrigin(origins = "*")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private AuthService authService;

    /**
     * Obtiene una lista de todos los usuarios.
     *
     * @return
     */
    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> getAllUsuarios() {
        return ResponseEntity.ok(usuarioService.findAll());
    }

    /**
     * Obtiene un usuario por su ID.
     *
     * @param id el identificador del usuario
     * @return
     */
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> getUsuarioById(@PathVariable String id) {
        return ResponseEntity.ok(usuarioService.findById(id));
    }

    /**
     * Busca un usuario por su email.
     *
     * @param email el email del usuario
     * @return
     */
    @GetMapping("/email/{email}")
    public ResponseEntity<UsuarioDTO> getUsuarioByEmail(@PathVariable String email) {
        return usuarioService.findByEmail(email)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Obtiene una lista de usuarios asociados a un distrito específico.
     *
     * @param idDistrito el identificador del distrito
     * @return
     */
    @GetMapping("/distrito/{idDistrito}")
    public ResponseEntity<List<UsuarioDTO>> getUsuariosByDistrito(@PathVariable Long idDistrito) {
        return ResponseEntity.ok(usuarioService.findByDistritoId(idDistrito));
    }

    /**
     * Busca un usuario por su ID de Google.
     *
     * @param googleId el ID de Google del usuario
     * @return
     */
    @GetMapping("/google/{googleId}")
    public ResponseEntity<UsuarioDTO> getUsuarioByGoogleId(@PathVariable String googleId) {
        return usuarioService.findByGoogleId(googleId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Busca un usuario por su ID de Facebook.
     *
     * @param facebookId el ID de Facebook del usuario
     * @return
     */
    @GetMapping("/facebook/{facebookId}")
    public ResponseEntity<UsuarioDTO> getUsuarioByFacebookId(@PathVariable String facebookId) {
        return usuarioService.findByFacebookId(facebookId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Encuentra usuarios por su rol.
     *
     * @param rol el rol del usuario (cliente, admin, etc.)
     * @return
     */
    @GetMapping("/rol/{rol}")
    public ResponseEntity<List<UsuarioDTO>> getUsuariosByRol(@PathVariable String rol) {
        return ResponseEntity.ok(usuarioService.findByRol(rol));
    }


    /**
     * Crea un nuevo usuario.
     *
     * @param usuario el objeto UsuarioDTO a crear
     * @return una respuesta HTTP 201 si se crea correctamente
     */
    @PostMapping
    public ResponseEntity<Void> createUsuario(@RequestBody UsuarioDTO usuario) {
        usuarioService.save(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * Actualiza un usuario existente.
     *
     * @param id el identificador del usuario a actualizar
     * @param usuario el objeto UsuarioDTO con los datos actualizados
     * @return
     */
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateUsuario(@PathVariable String id, @RequestBody UsuarioDTO usuario) {
        usuario.setId(id);
        usuarioService.update(usuario);
        return ResponseEntity.ok().build();
    }

    /**
     * Elimina un usuario por su ID.
     *
     * @param id el identificador del usuario a eliminar
     * @return
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUsuario(@PathVariable String id) {
        usuarioService.delete(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/auth")
    public ResponseEntity<Boolean> authenticate(@RequestBody LoginRequestDTO loginRequestDTO) {
        Usuario usuario = authService.authenticateTraditional(loginRequestDTO.getEmail(), loginRequestDTO.getPassword());
        if (usuario != null) {
            return ResponseEntity.ok(true); // OK
        } else {
            return ResponseEntity.status(401).body(false); // Unauthorized
        }
    }
}
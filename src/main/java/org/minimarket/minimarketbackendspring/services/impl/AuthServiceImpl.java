package org.minimarket.minimarketbackendspring.services.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.minimarket.minimarketbackendspring.dtos.UsuarioDTO;
import org.minimarket.minimarketbackendspring.entities.Usuario;
import org.minimarket.minimarketbackendspring.repositories.UsuarioRepository;
import org.minimarket.minimarketbackendspring.services.interfaces.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import com.google.firebase.auth.UserRecord;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private FirebaseAuth firebaseAuth;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioServiceImpl usuarioService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UsuarioDTO authenticateFirebaseToken(String idToken) throws FirebaseAuthException {
        // Verificar el token de Firebase
        FirebaseToken decodedToken = firebaseAuth.verifyIdToken(idToken);
        String uid = decodedToken.getUid();
        String email = decodedToken.getEmail();
        String name = decodedToken.getName();

        Map<String, Object> claims = decodedToken.getClaims();
        for (Map.Entry<String, Object> entry : claims.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }

        String providerId = null;

        // Obtener información adicional del proveedor
        Object signInProvider = decodedToken.getClaims().get("firebase");
        if (signInProvider != null && signInProvider instanceof Map) {
            Map<?, ?> firebaseMap = (Map<?, ?>) signInProvider;
            Object provider = firebaseMap.get("sign_in_provider");
            if (provider != null) {
                providerId = provider.toString();
            }
        }

        // Buscar usuario existente o crear uno nuevo
        Optional<Usuario> existingUser = usuarioRepository.findByEmail(email);
        
        if (existingUser.isPresent()) {
            Usuario usuario = existingUser.get();
            UsuarioDTO usuDTO = usuarioService.convertToDTO(usuario);
            // Actualizar IDs de proveedores si es necesario
            updateSocialProviderIds(usuDTO, uid, providerId);
            usuarioService.update(usuDTO);
            return usuDTO;
        } else {
            // Crear nuevo usuario
            UsuarioDTO newUser = createNewSocialUser(email, name, uid, providerId);
            usuarioService.save(newUser);
            return newUser;
        }
    }

    @Override
    public UserRecord getUserByEmail(String email) throws FirebaseAuthException {
        return firebaseAuth.getUserByEmail(email);
    }

    @Override
    public Usuario authenticateTraditional(String email, String password) {
        Optional<Usuario> userOptional = usuarioRepository.findByEmail(email);
        
        if (userOptional.isPresent()) {
            Usuario usuario = userOptional.get();
            // Verificar si el usuario tiene contraseña (no es usuario social)
            if (usuario.getClave() != null && !usuario.getClave().isEmpty()) {
                if (passwordEncoder.matches(password, usuario.getClave())) {
                    return usuario;
                }
            }
        }
        return null;
    }

    /**
     * Actualiza los IDs de proveedores sociales en un usuario existente
     */
    private void updateSocialProviderIds(UsuarioDTO usuario, String uid, String providerId) {
        if ("google.com".equals(providerId) && (usuario.getGoogleId() == null || usuario.getGoogleId().isEmpty())) {
            usuario.setGoogleId(uid);
        } else if ("facebook.com".equals(providerId) && (usuario.getFacebookId() == null || usuario.getFacebookId().isEmpty())) {
            usuario.setFacebookId(uid);
        }
    }

    /**
     * Crea un nuevo usuario para autenticación social
     */
    private UsuarioDTO createNewSocialUser(String email, String name, String uid, String providerId) {
        UsuarioDTO newUser = new UsuarioDTO();
        newUser.setId(UUID.randomUUID().toString());
        newUser.setEmail(email);

        // Dividir el nombre completo en fragmentos
        String[] nameParts = name != null ? name.split(" ") : new String[0];
        newUser.setNombre(name != null ? name : "Usuario");

        // Asignar el nombre y apellido según los fragmentos disponibles
        newUser.setNombre(nameParts.length > 0 ? nameParts[0] : "Usuario");
        newUser.setApellido(nameParts.length > 1 ?
                String.join(" ", nameParts.length > 2 ?
                        new String[]{nameParts[nameParts.length - 2], nameParts[nameParts.length - 1]} :
                        new String[]{nameParts[1]}) : "");
        
        // Establecer valores por defecto
        newUser.setTelefono("");
        newUser.setPassword("");
        newUser.setDistritoId(  null);
        newUser.setDireccion("");
        newUser.setGoogleId("");
        newUser.setFacebookId("");
        newUser.setRol("cliente");
        newUser.setEstado("activo");
        newUser.setCreatedBy(null);

        // Establecer el ID del proveedor según el tipo de autenticación
        if ("google.com".equals(providerId)) {
            newUser.setGoogleId(uid);
        } else if ("facebook.com".equals(providerId)) {
            newUser.setFacebookId(uid);
        }
        try {
            syncFirebaseClaims(uid, newUser.getRol());
        } catch (FirebaseAuthException e) {
            // Manejo del error, por ejemplo, loguear la excepción
            e.printStackTrace();
        }

        return newUser;
    }

    @Override
    public void syncFirebaseClaims(String uid, String rol) throws FirebaseAuthException {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", rol);
        firebaseAuth.setCustomUserClaims(uid, claims);
    }

}
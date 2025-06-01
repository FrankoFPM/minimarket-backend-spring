package org.minimarket.minimarketbackendspring.services.impl;

import java.util.Map;
import java.util.Optional;

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
    private PasswordEncoder passwordEncoder;

    @Override
    public Usuario authenticateFirebaseToken(String idToken) throws FirebaseAuthException {
        // Verificar el token de Firebase
        FirebaseToken decodedToken = firebaseAuth.verifyIdToken(idToken);
        String uid = decodedToken.getUid();
        String email = decodedToken.getEmail();
        String name = decodedToken.getName();

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
            // Actualizar IDs de proveedores si es necesario
            updateSocialProviderIds(usuario, uid, providerId);
            return usuarioRepository.save(usuario);
        } else {
            // Crear nuevo usuario
            Usuario newUser = createNewSocialUser(email, name, uid, providerId);
            return usuarioRepository.save(newUser);
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
    private void updateSocialProviderIds(Usuario usuario, String uid, String providerId) {
        if ("google.com".equals(providerId) && (usuario.getGoogleId() == null || usuario.getGoogleId().isEmpty())) {
            usuario.setGoogleId(uid);
        } else if ("facebook.com".equals(providerId) && (usuario.getFacebookId() == null || usuario.getFacebookId().isEmpty())) {
            usuario.setFacebookId(uid);
        }
    }

    /**
     * Crea un nuevo usuario para autenticación social
     */
    private Usuario createNewSocialUser(String email, String name, String uid, String providerId) {
        Usuario newUser = new Usuario();
        newUser.setEmail(email);
        newUser.setNombre(name != null ? name : "Usuario");
        
        // Establecer valores por defecto
        newUser.setApellido("");
        newUser.setTelefono("");
        newUser.setClave("");
        newUser.setIdDistrito(  null); 
        newUser.setDireccion("");
        newUser.setGoogleId("");
        newUser.setFacebookId("");
        newUser.setRol("CLIENTE");
        newUser.setEstado("ACTIVO");
        newUser.setCreatedBy(newUser);
        newUser.setUpdateBy(newUser);

        // Establecer el ID del proveedor según el tipo de autenticación
        if ("google.com".equals(providerId)) {
            newUser.setGoogleId(uid);
        } else if ("facebook.com".equals(providerId)) {
            newUser.setFacebookId(uid);
        }

        return newUser;
    }
}
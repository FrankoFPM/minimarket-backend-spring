package org.minimarket.minimarketbackendspring.services.interfaces;

import org.minimarket.minimarketbackendspring.entities.Usuario;

import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;

public interface AuthService {
    
    /**
     * Autentica un token de Firebase y retorna el usuario correspondiente
     * @param idToken Token de Firebase a verificar
     * @return Usuario autenticado
     * @throws FirebaseAuthException Si hay error en la verificación del token
     */
    Usuario authenticateFirebaseToken(String idToken) throws FirebaseAuthException;
    
    /**
     * Obtiene un usuario de Firebase por su email
     * @param email Email del usuario
     * @return UserRecord de Firebase
     * @throws FirebaseAuthException Si hay error al obtener el usuario
     */
    UserRecord getUserByEmail(String email) throws FirebaseAuthException;
    
    /**
     * Autentica un usuario con email y contraseña (método tradicional)
     * @param email Email del usuario
     * @param password Contraseña del usuario
     * @return      Usuario autenticado
     * @throws FirebaseAuthException Si hay error en la autenticación
     */
    Usuario authenticateTraditional(String email, String password);
}
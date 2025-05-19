package org.minimarket.minimarketbackendspring.services.impl;

import org.minimarket.minimarketbackendspring.dtos.requests.LoginRequestDTO;
import org.minimarket.minimarketbackendspring.repositories.UsuarioRepository;
import org.minimarket.minimarketbackendspring.services.interfaces.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Autentica a un usuario verificando su email y contraseña.
     *
     * @param email    el email del usuario
     * @param password la contraseña del usuario
     * @return true si la autenticación es exitosa, false en caso contrario
     */
    @Override
    public boolean authenticate(String email, String password) {
        return usuarioRepository.findByEmail(email)
                .map(usuario -> passwordEncoder.matches(password, usuario.getClave()))
                .orElse(false);
    }
}

package org.minimarket.minimarketbackendspring.controllers;

import java.util.HashMap;
import java.util.Map;

import org.minimarket.minimarketbackendspring.dtos.UsuarioDTO;
import org.minimarket.minimarketbackendspring.dtos.requests.FirebaseTokenDTO;
import org.minimarket.minimarketbackendspring.dtos.requests.LoginRequestDTO;
import org.minimarket.minimarketbackendspring.entities.Usuario;
import org.minimarket.minimarketbackendspring.services.interfaces.AuthService;
import org.minimarket.minimarketbackendspring.services.interfaces.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.firebase.auth.FirebaseAuthException;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/firebase/google")
    public ResponseEntity<?> authenticateWithGoogle(@RequestBody FirebaseTokenDTO tokenDTO) {
        try {
            UsuarioDTO usuario = authService.authenticateFirebaseToken(tokenDTO.getIdToken());
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Autenticación exitosa con Google");
            response.put("user", usuario);
            
            return ResponseEntity.ok(response);
        } catch (FirebaseAuthException e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "Error en la autenticación: " + e.getMessage());
            
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "Error interno del servidor: " + e.getMessage());
            
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @PostMapping("/firebase/facebook")
    public ResponseEntity<?> authenticateWithFacebook(@RequestBody FirebaseTokenDTO tokenDTO) {
        try {
            UsuarioDTO usuario = authService.authenticateFirebaseToken(tokenDTO.getIdToken());
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Autenticación exitosa con Facebook");
            response.put("user", usuario);
            
            return ResponseEntity.ok(response);
        } catch (FirebaseAuthException e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "Error en la autenticación: " + e.getMessage());
            
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "Error interno del servidor: " + e.getMessage());
            
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginTraditional(@RequestBody LoginRequestDTO loginDTO) {
        try {
            Usuario usuario = authService.authenticateTraditional(loginDTO.getEmail(), loginDTO.getPassword());
            
            if (usuario != null) {
                Map<String, Object> response = new HashMap<>();
                response.put("success", true);
                response.put("message", "Login exitoso");
                response.put("user", usuario);
                
                return ResponseEntity.ok(response);
            } else {
                Map<String, Object> errorResponse = new HashMap<>();
                errorResponse.put("success", false);
                errorResponse.put("message", "Credenciales inválidas");
                
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
            }
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "Error interno del servidor: " + e.getMessage());
            
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
}
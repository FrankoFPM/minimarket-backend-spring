package org.minimarket.minimarketbackendspring.controllers;

import org.minimarket.minimarketbackendspring.dtos.UsuarioDTO;
import org.minimarket.minimarketbackendspring.dtos.requests.LoginRequestDTO;
import org.minimarket.minimarketbackendspring.services.interfaces.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuario")
@CrossOrigin(origins = "*")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> getAllUsuarios() {
        return ResponseEntity.ok(usuarioService.findAll());
    }

    @PostMapping("/auth")
    public ResponseEntity<Boolean> authenticate(@RequestBody LoginRequestDTO loginRequestDTO) {
        boolean isAuthenticated = usuarioService.authenticate(loginRequestDTO.getEmail(), loginRequestDTO.getPassword());
        if (isAuthenticated) {
            return ResponseEntity.ok(true); // OK
        } else {
            return ResponseEntity.status(401).body(false); // Unauthorized
        }
    }
}

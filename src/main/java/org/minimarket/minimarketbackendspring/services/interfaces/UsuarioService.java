package org.minimarket.minimarketbackendspring.services.interfaces;

import org.minimarket.minimarketbackendspring.dtos.UsuarioDTO;
import org.springframework.stereotype.Service;

import java.util.List;

public interface UsuarioService {

    List<UsuarioDTO> findAll();

    UsuarioDTO findById(String id);

    UsuarioDTO findByEmail(String email);

    void save(UsuarioDTO usuario);

    void update(UsuarioDTO usuario);

    void delete(String id);

    boolean authenticate(String email, String password);
}

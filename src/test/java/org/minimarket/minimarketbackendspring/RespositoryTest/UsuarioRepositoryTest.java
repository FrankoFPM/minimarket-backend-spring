package org.minimarket.minimarketbackendspring.RespositoryTest;

import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.minimarket.minimarketbackendspring.entities.Usuario;
import org.minimarket.minimarketbackendspring.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;


/*
    Test para Verificar el Comportamiento de UsurioRepository
    (Inyeccciones SQL)
 */
@ActiveProfiles("test") // Le Indica a Spring que use application-test.properties
@DataJpaTest
@EntityScan(basePackageClasses = Usuario.class)
public class UsuarioRepositoryTest {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Test
    void guradarYRecuperarUsuarioporEmail() {
        // Crear Usuario para pruebas
        Usuario user = new Usuario();
        user.setIdUsuario("OEZX7S1NZTZvG6pToEPDEdtl1dz1"); // Firebase UID simulado
        
        user.setEmail("miuser@correo.com");
        user.setRol("admin");
        usuarioRepository.save(user);

        Optional<Usuario> encontrado = usuarioRepository.findByEmail("miuser@correo.com");

        assertTrue(encontrado.isPresent());
        assertEquals("OEZX7SINZTgV6pToEPdEtld1dz1", encontrado.get().getIdUsuario());
    }

    @Test
    void noDebeEncontrarConSqlInjectionSimulada() {
        Usuario usuario = new Usuario();
        usuario.setIdUsuario("OEZX7SINZTgV6pToEPdEtld1dab");
        usuario.setEmail("seguro@example.com");
        usuario.setRol("cliente");
        usuarioRepository.save(usuario);

        // Intento malicioso con una condición falsa
        Optional<Usuario> resultado = usuarioRepository.findByEmail("seguro@example.com' OR '1' = '1'");

        // JPA usa parámetros, no concatena SQL, por eso no encuentra nada
        assertFalse(resultado.isPresent(), "Debe devolver vacío. JPA evita la inyección SQL.");
    }

}

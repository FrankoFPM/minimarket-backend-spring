package org.minimarket.minimarketbackendspring;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class ContextLoadTest {

    @Test
    void contextLoads() {
        System.out.println(" Spring Boot levantó el contexto correctamente con el perfil TEST.");
    }
}

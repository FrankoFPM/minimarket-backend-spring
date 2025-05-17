package org.minimarket.minimarketbackendspring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // La protección CSRF se desactiva porque esta API está diseñada para ser stateless
                // y usará autenticación basada en tokens (por ejemplo, JWT). Las APIs stateless no
                // dependen de cookies o sesiones, por lo que la protección CSRF no es necesaria.
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/**", "/api/products/**").permitAll()
                        .anyRequest().authenticated()
                )
                //TODO: Implement JWT authentication
                .httpBasic(httpBasic -> {
                });//Plan: Implementar autenticación JWT en el futuro
        return http.build();
    }
}

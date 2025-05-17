package org.minimarket.minimarketbackendspring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    /*
    Si quieres realizar una configuración más avanzada, puedes usar la documentación oficial de Spring Security:
    https://docs.spring.io/spring-security/reference/servlet/configuration/java.html#_securityfilterchain_endpoints
    no le tengas miedo a experimentar con la configuración de seguridad, pero asegúrate de entender
    lo que estás haciendo. La seguridad es un aspecto crítico de cualquier aplicación, y una mala
    configuración puede llevar a vulnerabilidades graves.
    att: Franco
    * */


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // La protección CSRF se desactiva porque esta API está diseñada para ser stateless
                // y usará autenticación basada en tokens (por ejemplo, JWT). Las APIs stateless no
                // dependen de cookies o sesiones, por lo que la protección CSRF no es necesaria.
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/**", "/api/products/**", "/api/usuario", "/api/usuario/**", "/api/usuario/register").permitAll()
                        .anyRequest().authenticated()
                )
                //TODO: Implement JWT authentication
                .httpBasic(httpBasic -> {
                });//Plan: Implementar autenticación JWT en el futuro
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Usar BCrypt para codificar contraseñas
    }


}

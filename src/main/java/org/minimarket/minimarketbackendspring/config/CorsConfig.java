package org.minimarket.minimarketbackendspring.config;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
public class CorsConfig {

    /**
     * Configuración global de CORS para permitir peticiones desde el frontend.
     * 
     * - allowedOrigins: Solo permite solicitudes desde http://localhost:3000 (React u otro frontend local).
     * - allowedMethods: Métodos HTTP permitidos.
     * - allowedHeaders: Permite cualquier header.
     * - allowCredentials: Permite el envío de cookies o credenciales.
     * 
     * Esta configuración es necesaria para que el frontend pueda comunicarse con el backend
     * sin problemas de CORS durante el desarrollo.
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000")); // Origen permitido (frontend local)
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS")); // Métodos permitidos
        configuration.setAllowedHeaders(Arrays.asList("*")); // Permitir cualquier header
        configuration.setAllowCredentials(true); // Permitir credenciales (cookies, auth)

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration); // Aplicar configuración a todos los endpoints
        return source;
    }
}
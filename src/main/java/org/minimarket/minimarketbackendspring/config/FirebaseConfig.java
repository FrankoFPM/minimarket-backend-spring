package org.minimarket.minimarketbackendspring.config;

import java.io.IOException;

import javax.annotation.PostConstruct;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;

@Configuration
public class FirebaseConfig {

    /**
     * Inicializa la app de Firebase al arrancar la aplicación Spring.
     * Solo se inicializa si no hay instancias previas.
     * Usa el archivo de credenciales firebase-service-account.json ubicado en resources.
     */
    @PostConstruct
    public void initialize() {
        try {
            // Solo inicializa Firebase si aún no hay ninguna instancia activa
            if (FirebaseApp.getApps().isEmpty()) {
                FirebaseOptions options = FirebaseOptions.builder()
                        // Carga las credenciales del archivo JSON de la cuenta de servicio de Firebase
                        .setCredentials(GoogleCredentials.fromStream(
                                new ClassPathResource("firebase-service-account.json").getInputStream()))
                        .build();
                // Inicializa la app de Firebase con las opciones configuradas
                FirebaseApp.initializeApp(options);
            }
        } catch (IOException e) {
            // Lanza una excepción si ocurre un error al cargar las credenciales de Firebase
            throw new RuntimeException("Error initializing Firebase", e);
        }
    }

    /**
     * Expone un bean de FirebaseAuth para inyectar en otros componentes.
     * Permite acceder a los métodos de autenticación de Firebase.
     */
    @Bean
    public FirebaseAuth firebaseAuth() {
        return FirebaseAuth.getInstance();
    }
}
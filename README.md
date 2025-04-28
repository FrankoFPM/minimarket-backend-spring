# Minimarket Backend - Spring

## Cómo comenzar

Sigue los pasos a continuación para configurar y ejecutar el proyecto:

1. **Clonar el repositorio**  
   ```bash
   git clone https://github.com/FrankoFPM/minimarket-backend.git
   cd minimarket-backend-spring
   ```

2. **Configurar el entorno**  
   Asegúrate de tener instalado:
   - [Java 22](https://www.oracle.com/java/technologies/javase/jdk22-archive-downloads.html)
   - [IDE IntelliJ](https://www.jetbrains.com/es-es/idea/download/?section=windows)
   - [Oracle](https://www.oracle.com/database/technologies/appdev/xe.html)

   Una alternativa al IDE IntelliJ es [Eclipse](https://www.eclipse.org/downloads/packages/) o [Visual Studio Code](https://code.visualstudio.com/).
   Recuerda que si usas  Visual Studio Code, debes instalar el plugin de Java, además de los plugins de Spring Boot.
 
3. **Configurar la base de datos**  
    - Crea tu usuario para la base de datos en Oracle.
    - Crea una conexion base de datos en Oracle.
    - Configura el archivo `application.properties` en `src/main/resources` con tus credenciales de Oracle:
      ```properties
      spring.datasource.url=jdbc:oracle:thin:@localhost:1521:xe
      spring.datasource.username=tu_usuario
      spring.datasource.password=tu_contraseña
      ```
       
    - Si usas un puerto diferente al 1521, asegúrate de cambiarlo en la URL de conexión.
    - Como recomendación, usa tu ip local en lugar de `localhost` para evitar problemas de conexión.

4. **Construir el proyecto**  
   Usa los wizards de Maven o tu entorno de trabajo para construir el proyecto.

5. **Ejecutar la aplicación**  
   Inicia la aplicación con tu IDE o desde la línea de comandos:
   ```bash
    mvn spring-boot:run
    ```

6. **Probar la API**  
   Una vez que la aplicación esté en ejecución, accede a `http://localhost:8080` para interactuar con la API.

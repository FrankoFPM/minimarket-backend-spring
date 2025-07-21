# Minimarket Backend - Spring

## **PROYECTO CON TDD IMPLEMENTADO**

**Test-Driven Development aplicado exitosamente**  
**9 tests unitarios funcionando al 100%**  
**Metodología RED-GREEN-REFACTOR validada**

**Ver documentación completa:** [`TDD_DOCUMENTATION.md`](TDD_DOCUMENTATION.md)

---

## Cómo comenzar

Sigue los pasos a continuación para configurar y ejecutar el proyecto:

1. **Clonar el repositorio**

   ```bash
   git clone https://github.com/FrankoFPM/minimarket-backend-spring.git
   cd minimarket-backend-spring
   ```

2. **Configurar el entorno**  
   Asegúrate de tener instalado:

   - [Java 22](https://www.oracle.com/java/technologies/javase/jdk22-archive-downloads.html)
   - [IDE IntelliJ](https://www.jetbrains.com/es-es/idea/download/?section=windows)
   - [Oracle](https://www.oracle.com/database/technologies/appdev/xe.html)

   Una alternativa al IDE IntelliJ es [Eclipse](https://www.eclipse.org/downloads/packages/) o [Visual Studio Code](https://code.visualstudio.com/).
   Recuerda que si usas Visual Studio Code, debes instalar el plugin de Java, además de los plugins de Spring Boot.

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

## Sistema de Mantenimiento y Backup

### **Cron Jobs Automatizados**

El sistema incluye tareas programadas para mantenimiento automático:

- **Limpieza de carritos**: Cada 24 horas a medianoche
- **Cancelación de pedidos**: Cada día a las 6:00 AM (pedidos pendientes por 3+ días)
- **Reportes del sistema**: Diario a las 6:00 AM
- **Verificación de BD**: Cada hora
- **Limpieza de logs**: Cada domingo a las 3:00 AM

**Ver documentación completa:** [`PLAN_MANTENIMIENTO_INTEGRAL.md`](PLAN_MANTENIMIENTO_INTEGRAL.md)

### **Scripts de Backup Oracle**

#### **Backup Automático**

```batch
@echo off
set ORACLE_USER=USUARIO_FRANCO
set ORACLE_PASS=Admin123
set ORACLE_HOST=192.168.1.47
set ORACLE_PORT=1521
set ORACLE_SID=XE
set ORACLE_SCHEMA=USUARIO_FRANCO
set ORACLE_DIR=DIR_BACKUP
set FECHA=%DATE:~6,4%%DATE:~3,2%%DATE:~0,2%

expdp %ORACLE_USER%/%ORACLE_PASS%@%ORACLE_HOST%:%ORACLE_PORT%/%ORACLE_SID% schemas=%ORACLE_SCHEMA% directory=%ORACLE_DIR% dumpfile=backup_%FECHA%.dmp logfile=backup_%FECHA%.log
```

#### **Usar los scripts**

1. **⚠️ CONFIGURACIÓN INICIAL (OBLIGATORIA)**: Ejecuta `setup_oracle_directory.bat`
2. **Backup manual**: Ejecuta `backup_oracle.bat`
3. **Restaurar**: Ejecuta `restore_oracle.bat`
4. **Programar backup**: Usa el Programador de Tareas de Windows

#### **Configurar directorio Oracle**

**⚠️ CONFIGURACIÓN OBLIGATORIA ANTES DE USAR LOS SCRIPTS:**

1. **Crear directorio físico:**

   ```cmd
   mkdir C:\backups\oracle
   ```

2. **Crear directorio en Oracle (como DBA):**

   ```sql
   -- Conectarse como DBA
   sqlplus sys/admin@192.168.1.47:1521/XE as sysdba

   -- Crear directorio Oracle
   CREATE OR REPLACE DIRECTORY DIR_BACKUP AS 'C:\backups\oracle';
   GRANT READ, WRITE ON DIRECTORY DIR_BACKUP TO USUARIO_FRANCO;
   ```

3. **Verificar configuración:**
   ```sql
   SELECT * FROM DBA_DIRECTORIES WHERE DIRECTORY_NAME = 'DIR_BACKUP';
   ```

**❌ Sin esta configuración, los scripts fallarán con error "ORA-39001: invalid argument value"**

4. **Construir el proyecto**  
   Usa los wizards de Maven o tu entorno de trabajo para construir el proyecto.

5. **Demostrar TDD (RECOMENDADO)**

   ```bash
   mvn test -Dtest="CarritoTemporalServiceTest"
   ```

   **Resultado esperado:** `BUILD SUCCESS` con 9 tests exitosos

6. **Ejecutar la aplicación**  
   Inicia la aplicación con tu IDE o desde la línea de comandos:

   ```bash
    mvn spring-boot:run
   ```

7. **Probar la API**  
   Una vez que la aplicación esté en ejecución, accede a `http://localhost:8080` para interactuar con la API.

## Estructura del proyecto

Para cumplir con los requisitos de MVC, DAO, TDD y SOLID, el proyecto está organizado de la siguiente manera:

```
src/
├── main/java/org/minimarket/minimarketbackendspring/
│   ├── controllers       # Controladores (Capa de Presentación - MVC)
│   ├── services          # Servicios (Lógica de Negocio - SOLID)
│   ├── daos              # DAO (Acceso a Datos - DAO)
│   ├── repositories      # Repositorios (Spring Data JPA)
│   ├── entities          # Entidades (Modelos - MVC)
│   ├── dtos              # DTOs (Transferencia de datos)
│   └── utils             # Utilidades (Clases de soporte)
└── test/java/org/minimarket/minimarketbackendspring/
    └── unit/             # Tests TDD (9 tests funcionando)
        └── CarritoTemporalServiceTest.java
```

### **📋 Documentación del Proyecto:**

- **[`TDD_DOCUMENTATION.md`](TDD_DOCUMENTATION.md)** - **Documentación completa del TDD implementado**
- **[`PLAN_MANTENIMIENTO_INTEGRAL.md`](PLAN_MANTENIMIENTO_INTEGRAL.md)** - **Plan de mantenimiento y cron jobs**
- `src/test/java/.../unit/CarritoTemporalServiceTest.java` - Tests TDD funcionando
- `setup_oracle_directory.bat` - **Script de configuración inicial (EJECUTAR PRIMERO)**
- `backup_oracle.bat` - Script de backup automático de Oracle
- `restore_oracle.bat` - Script de restauración de Oracle

---

### Definición de arquitecturas

- **MVC (Modelo-Vista-Controlador)**: Patrón de diseño que separa la lógica de negocio, la interfaz de usuario y el control de flujo.
- **DAO (Data Access Object)**: Patrón de diseño que proporciona una interfaz abstracta para acceder a los datos.
- **TDD (Test Driven Development)**: **IMPLEMENTADO** - Desarrollo guiado por tests con metodología RED-GREEN-REFACTOR.
- **SOLID**: Conjunto de principios de diseño orientado a objetos que promueven la mantenibilidad y escalabilidad del software.
- **Spring Data JPA**: Framework que simplifica el acceso a datos en aplicaciones Java, proporcionando una capa de abstracción sobre JPA (Java Persistence API).

## Completados

- [x] Crear un proyecto Spring Boot.
- [x] Crear un controlador REST.
- [x] Crear un servicio.
- [x] Implementar JPA.
- [x] **Implementar TDD (9 tests funcionando) - Ver [`TDD_DOCUMENTATION.md`](TDD_DOCUMENTATION.md)**
- [x] **Implementar sistema de mantenimiento con cron jobs (@Scheduled)**
- [x] **Crear scripts de backup automático para Oracle**
- [x] **Configurar gestión de stock con validaciones temporales**

## Pendientes

- [ ] Aplicar la libreria Google Guava para la paginación.
- [ ] Aplicar la libreria Apache POI para la exportación a Excel.
- [ ] Aplicar la libreria Apache Commons para la exportación a PDF.
- [ ] Aplicar la libreria LogBack para el registro de logs.
- [ ] Integrar sistema de monitoreo con Prometheus y Grafana

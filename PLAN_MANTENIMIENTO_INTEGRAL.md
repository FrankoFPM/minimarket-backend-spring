# Plan de Mantenimiento Integral - Sistema Minimarket

## 1. Cron Jobs (Tareas Programadas con @Scheduled)

### Justificación de Implementación

En el contexto de un sistema de e-commerce como el minimarket, los cron jobs son esenciales para:

1. **Mantener la integridad de datos** - Evitar acumulación de datos obsoletos
2. **Optimizar el rendimiento** - Limpiar tablas con datos innecesarios
3. **Mejorar la experiencia del usuario** - Cancelar pedidos que no se completan
4. **Garantizar la disponibilidad** - Monitorear el estado del sistema
5. **Facilitar el análisis** - Generar reportes automáticos

### Tareas Implementadas

#### **1. Limpieza de Carritos Abandonados**

- **Frecuencia**: Cada 24 horas a medianoche (`0 0 0 * * *`)
- **Justificación**: Los carritos abandonados consumen espacio en BD y pueden contener productos que ya no están disponibles
- **Acción**: Elimina carritos completos donde el item más reciente tiene más de 24 horas de antigüedad
- **Lógica**: Agrupa items por usuario y usa la fecha más reciente para determinar si el carrito debe eliminarse
- **Beneficio**: Mejora el rendimiento y libera espacio en la base de datos, respetando el tiempo mínimo de 24 horas

#### **2. Cancelación de Pedidos Pendientes**

- **Frecuencia**: Cada día a las 6:00 AM (`0 0 6 * * *`)
- **Justificación**: Los pedidos "pendiente_pago" que no se completan deben cancelarse para liberar stock
- **Acción**: Cancela pedidos pendientes de pago con más de 3 días de antigüedad
- **Beneficio**: Libera stock para otros usuarios y mantiene consistencia, dando tiempo suficiente para el pago

#### **3. Reporte de Estado del Sistema**

- **Frecuencia**: Diario a las 6:00 AM (`0 0 6 * * *`)
- **Justificación**: Monitorear el estado del sistema para detectar anomalías
- **Acción**: Genera reporte con estadísticas de carritos, pedidos y productos
- **Beneficio**: Facilita el monitoreo y la toma de decisiones

#### **4. Verificación de Base de Datos**

- **Frecuencia**: Cada hora (`0 0 * * * *`)
- **Justificación**: Detectar problemas de conectividad o inconsistencias tempranamente
- **Acción**: Realiza verificaciones básicas de conectividad
- **Beneficio**: Permite respuesta rápida ante problemas de BD

#### **5. Limpieza de Logs Antiguos**

- **Frecuencia**: Cada domingo a las 3:00 AM (`0 0 3 * * SUN`)
- **Justificación**: Evitar que los logs crezcan indefinidamente
- **Acción**: Mantiene solo logs de los últimos 30 días
- **Beneficio**: Optimiza el espacio en disco y mejora el rendimiento

### Configuración Técnica

```java
@Service
public class MantenimientoScheduledService {

    @Scheduled(cron = "0 0 0 * * *") // Cada 24 horas a medianoche
    public void limpiarCarritosAbandonados() {
        // Agrupa carritos por usuario y usa la fecha más reciente
        // para determinar si el carrito debe eliminarse después de 24 horas
    }

    @Scheduled(cron = "0 0 6 * * *") // Cada día a las 6:00 AM
    public void cancelarPedidosPendientes() {
        // Cancela pedidos pendientes de pago con más de 3 días
    }

    @Scheduled(cron = "0 0 6 * * *") // Diario a las 6:00 AM
    public void generarReporteEstadoSistema() {
        // Lógica de reporte
    }
}
```

## 2. Backups Automáticos

### Script de Backup (.bat)

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

echo Iniciando backup de la base de datos Oracle...
expdp %ORACLE_USER%/%ORACLE_PASS%@%ORACLE_HOST%:%ORACLE_PORT%/%ORACLE_SID% schemas=%ORACLE_SCHEMA% directory=%ORACLE_DIR% dumpfile=backup_%FECHA%.dmp logfile=backup_%FECHA%.log

if %errorlevel% == 0 (
    echo Backup completado exitosamente: backup_%FECHA%.dmp
) else (
    echo Error durante el backup. Revisar log: backup_%FECHA%.log
)
```

### Configuración del Directorio Oracle

**⚠️ IMPORTANTE: Configuración previa requerida**

Antes de ejecutar los scripts de backup, es **OBLIGATORIO** crear el directorio Oracle y otorgar permisos:

#### **Paso 1: Crear directorio físico**

```cmd
mkdir C:\backups\oracle
```

#### **Paso 2: Crear directorio en Oracle (como DBA)**

```sql
-- Conectarse como DBA (system o sys)
sqlplus sys/admin@192.168.1.47:1521/XE as sysdba

-- Crear el directorio Oracle
CREATE OR REPLACE DIRECTORY DIR_BACKUP AS 'C:\backups\oracle';

-- Otorgar permisos al usuario
GRANT READ, WRITE ON DIRECTORY DIR_BACKUP TO USUARIO_FRANCO;

-- Verificar que el directorio fue creado
SELECT * FROM DBA_DIRECTORIES WHERE DIRECTORY_NAME = 'DIR_BACKUP';
```

#### **Paso 3: Verificar permisos**

```sql
-- Conectarse como USUARIO_FRANCO
sqlplus USUARIO_FRANCO/Admin123@192.168.1.47:1521/XE

-- Verificar acceso al directorio
SELECT * FROM USER_TAB_PRIVS WHERE GRANTEE = 'USUARIO_FRANCO' AND TABLE_NAME = 'DIR_BACKUP';
```

**Sin esta configuración, los scripts de backup fallarán con error "ORA-39001: invalid argument value"**

### Programación en Windows

- **Herramienta**: Programador de tareas de Windows
- **Frecuencia**: Diario a las 2:00 AM
- **Ubicación**: `C:\backups\minimarket\`
- **Retención**: 30 días de backups

## 3. Scripts de Mantenimiento

### Script de Restauración

```batch
@echo off
set /p BACKUP_FILE="Ingrese el nombre del archivo de backup: "
set ORACLE_USER=USUARIO_FRANCO
set ORACLE_PASS=Admin123
set ORACLE_HOST=192.168.1.47
set ORACLE_PORT=1521
set ORACLE_SID=XE
set ORACLE_SCHEMA=USUARIO_FRANCO
set ORACLE_DIR=DIR_BACKUP

echo Iniciando restauración de la base de datos...
impdp %ORACLE_USER%/%ORACLE_PASS%@%ORACLE_HOST%:%ORACLE_PORT%/%ORACLE_SID% ^
  schemas=%ORACLE_SCHEMA% ^
  directory=%ORACLE_DIR% ^
  dumpfile=%BACKUP_FILE% ^
  table_exists_action=replace
```

### Script de Limpieza Manual

```sql
-- Limpiar carritos abandonados manualmente (más de 24 horas desde el último item)
DELETE FROM carrito_temporal
WHERE id_usuario IN (
    SELECT id_usuario
    FROM (
        SELECT id_usuario, MAX(fecha_agregado) as ultima_fecha
        FROM carrito_temporal
        GROUP BY id_usuario
        HAVING MAX(fecha_agregado) < SYSDATE - 1
    )
);

-- Cancelar pedidos pendientes antiguos (más de 3 días)
UPDATE pedido
SET estado = 'cancelado',
    updated_at = SYSDATE
WHERE estado = 'pendiente_pago'
  AND fecha_pedido < SYSDATE - 3;
```

## 4. Monitoreo y Logs

### Configuración de Logging

```properties
# Configuración en application.properties
logging.level.org.minimarket.minimarketbackendspring.services.impl.MantenimientoScheduledService=INFO
logging.file.name=logs/minimarket-maintenance.log
logging.file.max-size=10MB
logging.file.max-history=30
```

### Métricas de Rendimiento

Los cron jobs generan métricas útiles:

- **Carritos eliminados por ejecución**
- **Pedidos cancelados automáticamente**
- **Tiempo de ejecución de cada tarea**
- **Errores durante la ejecución**

## 5. Beneficios del Sistema de Mantenimiento

### Para el Negocio:

- **Optimización de recursos**: Libera espacio en BD y mejora rendimiento
- **Consistencia de datos**: Mantiene la integridad del sistema
- **Disponibilidad**: Detecta y previene problemas antes que afecten usuarios
- **Escalabilidad**: Permite que el sistema maneje más usuarios sin degradación

### Para el Usuario:

- **Mejor experiencia**: Carritos siempre actualizados y consistentes
- **Tiempo de respuesta**: Sistema más rápido por optimización automática
- **Confiabilidad**: Menos errores por inconsistencias en datos

### Para Desarrollo:

- **Mantenimiento automatizado**: Reduce tareas manuales repetitivas
- **Monitoreo proactivo**: Detecta problemas antes que se vuelvan críticos
- **Análisis de uso**: Reportes automáticos para toma de decisiones

## 6. Consideraciones de Producción

### Configuración Flexible

```properties
# Intervalos configurables
maintenance.carritos.cleanup.hours=24
maintenance.pedidos.cancelacion.days=3
maintenance.reportes.hora=6
maintenance.verificacion.intervalo=1
```

### Manejo de Errores

- **Logging detallado** de cada operación
- **Reintentos automáticos** para operaciones críticas
- **Notificaciones** en caso de errores persistentes

### Escalabilidad

- **Ejecución condicional** basada en carga del sistema
- **Paralelización** de tareas independientes
- **Monitoreo de recursos** durante la ejecución

Este plan de mantenimiento integral asegura que el sistema funcione de manera óptima, mantenga la consistencia de datos y proporcione una experiencia de usuario confiable a lo largo del tiempo.

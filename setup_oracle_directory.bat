@echo off
REM =============================================================================
REM Script de Configuración de Directorio Oracle para Backups
REM Sistema: Minimarket Backend Spring
REM Autor: Franco
REM Fecha: 2025-07-16
REM =============================================================================

echo.
echo ===== CONFIGURACIÓN DIRECTORIO ORACLE - MINIMARKET SYSTEM =====
echo.

REM Configuración de conexión Oracle
set ORACLE_HOST=192.168.1.47
set ORACLE_PORT=1521
set ORACLE_SID=XE
set ORACLE_USER=USUARIO_FRANCO
set BACKUP_DIR=C:\backups\oracle

echo Este script configurará el directorio Oracle para backups.
echo.
echo Configuración:
echo - Host: %ORACLE_HOST%:%ORACLE_PORT%
echo - SID: %ORACLE_SID%
echo - Usuario: %ORACLE_USER%
echo - Directorio: %BACKUP_DIR%
echo.

REM Paso 1: Crear directorio físico
echo ===== PASO 1: Crear directorio físico =====
if not exist "%BACKUP_DIR%" (
    echo Creando directorio: %BACKUP_DIR%
    mkdir "%BACKUP_DIR%"
    if %errorlevel% == 0 (
        echo ✓ Directorio creado exitosamente
    ) else (
        echo ✗ Error al crear directorio
        goto :end
    )
) else (
    echo ✓ Directorio ya existe
)
echo.

REM Paso 2: Configurar directorio en Oracle
echo ===== PASO 2: Configurar directorio en Oracle =====
echo.
echo Para configurar el directorio en Oracle, ejecute los siguientes comandos:
echo.
echo 1. Conectarse como DBA:
echo    sqlplus sys/admin@%ORACLE_HOST%:%ORACLE_PORT%/%ORACLE_SID% as sysdba
echo.
echo 2. Crear directorio Oracle:
echo    CREATE OR REPLACE DIRECTORY DIR_BACKUP AS '%BACKUP_DIR%';
echo.
echo 3. Otorgar permisos:
echo    GRANT READ, WRITE ON DIRECTORY DIR_BACKUP TO %ORACLE_USER%;
echo.
echo 4. Verificar configuración:
echo    SELECT * FROM DBA_DIRECTORIES WHERE DIRECTORY_NAME = 'DIR_BACKUP';
echo.

set /p AUTO_CONFIG="¿Desea intentar la configuración automática? (S/N): "
if /i "%AUTO_CONFIG%"=="S" (
    echo.
    echo Intentando configuración automática...
    echo.
    
    REM Solicitar credenciales DBA
    set /p DBA_USER="Usuario DBA (sys): "
    if "%DBA_USER%"=="" set DBA_USER=sys
    
    set /p DBA_PASS="Password DBA: "
    if "%DBA_PASS%"=="" (
        echo Error: Debe proporcionar la contraseña DBA
        goto :manual
    )
    
    REM Crear archivo temporal con comandos SQL
    echo CREATE OR REPLACE DIRECTORY DIR_BACKUP AS '%BACKUP_DIR%'; > temp_config.sql
    echo GRANT READ, WRITE ON DIRECTORY DIR_BACKUP TO %ORACLE_USER%; >> temp_config.sql
    echo SELECT 'Directorio configurado correctamente' AS STATUS FROM DUAL; >> temp_config.sql
    echo exit; >> temp_config.sql
    
    REM Ejecutar configuración
    sqlplus %DBA_USER%/%DBA_PASS%@%ORACLE_HOST%:%ORACLE_PORT%/%ORACLE_SID% as sysdba @temp_config.sql
    
    REM Limpiar archivo temporal
    del temp_config.sql
    
    if %errorlevel% == 0 (
        echo.
        echo ✓ Configuración automática completada
    ) else (
        echo.
        echo ✗ Error en configuración automática
        echo Utilice la configuración manual mostrada anteriormente
    )
) else (
    :manual
    echo.
    echo Utilice los comandos mostrados anteriormente para configurar manualmente.
)

echo.
echo ===== PASO 3: Verificar configuración =====
echo.
echo Para verificar que todo funciona correctamente:
echo 1. Ejecute backup_oracle.bat
echo 2. Verifique que se crea el archivo backup_YYYYMMDD.dmp
echo.

:end
echo.
echo ===== CONFIGURACIÓN COMPLETADA =====
echo.
echo Presiona cualquier tecla para continuar...
pause >nul

@echo off
REM =============================================================================
REM Script de Restauración para Oracle Database
REM Sistema: Minimarket Backend Spring
REM Autor: Franco
REM Fecha: 2025-07-16
REM =============================================================================

echo.
echo ===== RESTAURACIÓN ORACLE DATABASE - MINIMARKET SYSTEM =====
echo.

REM Verificar que el directorio físico existe
if not exist "C:\backups\oracle" (
    echo ERROR: El directorio C:\backups\oracle no existe.
    echo.
    echo Para crear el directorio, ejecute:
    echo mkdir C:\backups\oracle
    echo.
    echo Luego configure el directorio Oracle como DBA:
    echo sqlplus sys/admin@192.168.1.47:1521/XE as sysdba
    echo CREATE OR REPLACE DIRECTORY DIR_BACKUP AS 'C:\backups\oracle';
    echo GRANT READ, WRITE ON DIRECTORY DIR_BACKUP TO USUARIO_FRANCO;
    echo.
    goto :end
)

REM Configuración de conexión Oracle
set ORACLE_USER=USUARIO_FRANCO
set ORACLE_PASS=Admin123
set ORACLE_HOST=192.168.1.47
set ORACLE_PORT=1521
set ORACLE_SID=XE
set ORACLE_SCHEMA=USUARIO_FRANCO
set ORACLE_DIR=DIR_BACKUP

echo Configuración:
echo - Usuario: %ORACLE_USER%
echo - Host: %ORACLE_HOST%:%ORACLE_PORT%
echo - SID: %ORACLE_SID%
echo - Esquema: %ORACLE_SCHEMA%
echo - Directorio: %ORACLE_DIR%
echo.

REM Listar archivos de backup disponibles
echo Archivos de backup disponibles:
echo.
dir C:\backups\oracle\backup_*.dmp /B 2>nul
if %errorlevel% neq 0 (
    echo No se encontraron archivos de backup en C:\backups\oracle\
    echo.
)

echo.
set /p BACKUP_FILE="Ingrese el nombre del archivo de backup (ejemplo: backup_20250716.dmp): "

if "%BACKUP_FILE%"=="" (
    echo Error: Debe especificar un archivo de backup.
    goto :end
)

echo.
echo ADVERTENCIA: Esta operación reemplazará todos los datos existentes.
set /p CONFIRM="¿Está seguro de continuar? (S/N): "
if /i not "%CONFIRM%"=="S" (
    echo Operación cancelada.
    goto :end
)

echo.
echo Iniciando restauración de la base de datos...
echo Archivo: %BACKUP_FILE%
echo Fecha/Hora: %DATE% %TIME%
echo.

REM Ejecutar la restauración con impdp
impdp %ORACLE_USER%/%ORACLE_PASS%@%ORACLE_HOST%:%ORACLE_PORT%/%ORACLE_SID% schemas=%ORACLE_SCHEMA% directory=%ORACLE_DIR% dumpfile=%BACKUP_FILE% table_exists_action=replace

REM Verificar el resultado
if %errorlevel% == 0 (
    echo.
    echo ===== RESTAURACIÓN COMPLETADA EXITOSAMENTE =====
    echo Archivo restaurado: %BACKUP_FILE%
    echo Fecha/Hora fin: %DATE% %TIME%
    echo.
) else (
    echo.
    echo ===== ERROR DURANTE LA RESTAURACIÓN =====
    echo Código de error: %errorlevel%
    echo Revisar mensajes de error anteriores.
    echo.
    echo POSIBLES CAUSAS:
    echo - El directorio DIR_BACKUP no existe en Oracle
    echo - El usuario no tiene permisos sobre el directorio
    echo - El archivo de backup no existe o está corrupto
    echo - Conexión a la base de datos fallida
    echo.
    echo Para solucionar, ejecute como DBA:
    echo CREATE OR REPLACE DIRECTORY DIR_BACKUP AS 'C:\backups\oracle';
    echo GRANT READ, WRITE ON DIRECTORY DIR_BACKUP TO USUARIO_FRANCO;
    echo.
)

:end
echo Presiona cualquier tecla para continuar...
pause >nul

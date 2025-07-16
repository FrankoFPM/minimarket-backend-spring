@echo off
REM =============================================================================
REM Script de Backup Automático para Oracle Database
REM Sistema: Minimarket Backend Spring
REM Autor: Franco
REM Fecha: 2025-07-16
REM =============================================================================

echo.
echo ===== BACKUP ORACLE DATABASE - MINIMARKET SYSTEM =====
echo.

REM Verificar que el directorio físico existe
if not exist "C:\backups\oracle" (
    echo ERROR: El directorio C:\backups\oracle no existe.
    echo.
    echo Para crear el directorio, ejecute:
    echo mkdir C:\backups\oracle
    echo.
    echo Luego configure el directorio Oracle como DBA:
    echo sqlplus sys/admin@127.0.0.1:1521/XE as sysdba
    echo CREATE OR REPLACE DIRECTORY DIR_BACKUP AS 'C:\backups\oracle';
    echo GRANT READ, WRITE ON DIRECTORY DIR_BACKUP TO USUARIO_FRANCO;
    echo.
    goto :end
)

REM Configuración de conexión Oracle
set ORACLE_USER=USUARIO_FRANCO
set ORACLE_PASS=Admin123
set ORACLE_HOST=127.0.0.1
set ORACLE_PORT=1521
set ORACLE_SID=XE
set ORACLE_SCHEMA=USUARIO_FRANCO
set ORACLE_DIR=DIR_BACKUP

REM Generar fecha y hora en formato seguro para nombres de archivo
for /f "tokens=2 delims==" %%I in ('wmic os get localdatetime /value') do set DATETIME=%%I
set FECHA=%DATETIME:~0,8%
set HORA=%DATETIME:~8,6%
set TIMESTAMP=%FECHA%_%HORA%

REM Usar timestamp para los nombres de archivo, evitando espacios y caracteres especiales
set DMP_FILE=backup_%TIMESTAMP%.dmp
set LOG_FILE=backup_%TIMESTAMP%.log

echo Configuración:
echo - Usuario: %ORACLE_USER%
echo - Host: %ORACLE_HOST%:%ORACLE_PORT%
echo - SID: %ORACLE_SID%
echo - Esquema: %ORACLE_SCHEMA%
echo - Directorio: %ORACLE_DIR%
echo - Archivo: %DMP_FILE%
echo.

echo Iniciando backup de la base de datos Oracle...
echo Fecha/Hora: %DATE% %TIME%
echo.

REM Ejecutar el backup con expdp usando los nombres corregidos
expdp %ORACLE_USER%/%ORACLE_PASS%@%ORACLE_HOST%:%ORACLE_PORT%/%ORACLE_SID% schemas=%ORACLE_SCHEMA% directory=%ORACLE_DIR% dumpfile=%DMP_FILE% logfile=%LOG_FILE%

REM Verificar el resultado
if %errorlevel% == 0 (
    echo.
    echo ===== BACKUP COMPLETADO EXITOSAMENTE =====
    echo Archivo generado: %DMP_FILE%
    echo Log generado: %LOG_FILE%
    echo Fecha/Hora fin: %DATE% %TIME%
    echo.
) else (
    echo.
    echo ===== ERROR DURANTE EL BACKUP =====
    echo Código de error: %errorlevel%
    echo Revisar archivo de log: %LOG_FILE%
    echo.
    echo POSIBLES CAUSAS:
    echo - El directorio DIR_BACKUP no existe en Oracle
    echo - El usuario no tiene permisos sobre el directorio
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

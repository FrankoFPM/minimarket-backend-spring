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

REM Generar timestamp para el nombre del archivo
set FECHA=%DATE:~6,4%%DATE:~3,2%%DATE:~0,2%
set HORA=%TIME:~0,2%%TIME:~3,2%%TIME:~6,2%
set TIMESTAMP=%FECHA%_%HORA%

REM Limpiar espacios en la hora
set HORA=%HORA: =0%
set TIMESTAMP=%FECHA%_%HORA%

echo Configuración:
echo - Usuario: %ORACLE_USER%
echo - Host: %ORACLE_HOST%:%ORACLE_PORT%
echo - SID: %ORACLE_SID%
echo - Esquema: %ORACLE_SCHEMA%
echo - Directorio: %ORACLE_DIR%
echo - Archivo: backup_%FECHA%.dmp
echo.

echo Iniciando backup de la base de datos Oracle...
echo Fecha/Hora: %DATE% %TIME%
echo.

REM Ejecutar el backup con expdp
expdp %ORACLE_USER%/%ORACLE_PASS%@%ORACLE_HOST%:%ORACLE_PORT%/%ORACLE_SID% schemas=%ORACLE_SCHEMA% directory=%ORACLE_DIR% dumpfile=backup_%FECHA%.dmp logfile=backup_%FECHA%.log

REM Verificar el resultado
if %errorlevel% == 0 (
    echo.
    echo ===== BACKUP COMPLETADO EXITOSAMENTE =====
    echo Archivo generado: backup_%FECHA%.dmp
    echo Log generado: backup_%FECHA%.log
    echo Fecha/Hora fin: %DATE% %TIME%
    echo.
) else (
    echo.
    echo ===== ERROR DURANTE EL BACKUP =====
    echo Código de error: %errorlevel%
    echo Revisar archivo de log: backup_%FECHA%.log
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

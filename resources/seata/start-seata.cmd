@ECHO OFF
SETLOCAL ENABLEDELAYEDEXPANSION
PUSHD "%CD%"
SET a=130
SET b=30
mode con: cols=%a% lines=%b%
REM -----------------------------------------
set "WORKPLACE=seata-server-1.4.2"
md %WORKPLACE%\logs
start %WORKPLACE%\bin\seata-server.bat
exit

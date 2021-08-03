@ECHO OFF
SETLOCAL ENABLEDELAYEDEXPANSION
PUSHD "%CD%"
SET a=130
SET b=30
mode con: cols=%a% lines=%b%
REM -----------------------------------------
bin\startup.cmd -m standalone
pause

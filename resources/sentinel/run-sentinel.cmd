@ECHO OFF
SETLOCAL ENABLEDELAYEDEXPANSION
PUSHD "%CD%"
SET a=130
SET b=30
mode con: cols=%a% lines=%b%
REM -----------------------------------------
SET "SENTINEL=sentinel-dashboard-1.8.2.jar"
SET "HOST=localhost"
SET "PORT=8087"
java -Dserver.port=%PORT% -Dcsp.sentinel.dashboard.server=%HOST%:%PORT% -Dproject.name=sentinel-dashboard ^
-Dsentinel.dashboard.auth.username=admin ^
-Dsentinel.dashboard.auth.password=123456 ^
-jar %SENTINEL%
pause

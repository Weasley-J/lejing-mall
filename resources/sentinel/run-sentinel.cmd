@ECHO OFF
SETLOCAL ENABLEDELAYEDEXPANSION
PUSHD "%CD%"
SET a=130
SET b=30
mode con: cols=%a% lines=%b%
SET "SENTINEL=sentinel-dashboard-1.8.2.jar"
SET "HOST=localhost"
SET "PORT=8087"
title 应用:%SENTINEL%                 日期: %date%    时间: %time%
java -Dserver.port=%PORT% ^
-Dcsp.sentinel.dashboard.server=%HOST%:%PORT% ^
-Dproject.name=sentinel-dashboard ^
-Dsentinel.dashboard.auth.username=admin ^
-Dsentinel.dashboard.auth.password=123456 ^
-jar %SENTINEL%
exit
@echo off
setlocal enabledelayedexpansion
set "WORKPLACE=E:\IdeaProjects\lejing-mall\resources\seata\seata-server-1.4.2"
md %WORKPLACE%\logs
start %WORKPLACE%\bin\seata-server.bat
exit

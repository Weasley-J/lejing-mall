@echo off & color 0A
setlocal enabledelayedexpansion
title 乐璟商城-门户网站
cls
REM npm下载live-server启动门户网站, 指定门户网站的端口8080
npm install -g live-server --registry=https://registry.npm.taobao.org && live-server --port=8002
pause && exit

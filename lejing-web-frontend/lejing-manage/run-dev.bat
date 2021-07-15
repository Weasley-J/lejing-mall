@echo off & color 0A
setlocal enabledelayedexpansion
title 乐璟商城-后台管理
cls
npm install --registry=https://registry.npm.taobao.org && npm run dev
pause && exit

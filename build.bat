@echo off & color 0A
setlocal enabledelayedexpansion
set name=打包springboot应用为可执行jar文件
title %name%                 日期: %date%    时间: %time%
cls && mvn clean && mvn package && mvn clean && exit
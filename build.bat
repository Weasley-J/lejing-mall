@echo off & color 0A
setlocal enabledelayedexpansion
set name=���springbootӦ��Ϊ��ִ��jar�ļ�
title %name%                 ����: %date%    ʱ��: %time%
cls && mvn clean && mvn package && exit

@ECHO OFF
SET a=180
SET b=45
SET zipkin=D:\DevTools\zipkin-server\zipkin-server-2.23.2-exec.jar
mode con: cols=%a% lines=%b%
title 微服务:%zipkin%                 日期: %date%    时间: %time%
java -Dlogging.level.zipkin2=DEBUG -jar %zipkin% --STORAGE_TYPE=mysql --MYSQL_DB=zipkin --MYSQL_HOST=127.0.0.1 --MYSQL_PORT=3306 --MYSQL_USER=root --MYSQL_PASS=123456
PAUSE
exit

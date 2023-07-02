#!/bin/bash

BASE_DIR_POSTGRES="/usr/local/postgres"
BASE_DIR_SONARQUBE="/usr/local/sonarqube"

#主机ip
POSTGRESQL_HOST="192.168.31.140"

clear && rm -rfv ${BASE_DIR_POSTGRES} && rm -rfv ${BASE_DIR_SONARQUBE}
mkdir -pv ${BASE_DIR_POSTGRES} && mkdir -pv ${BASE_DIR_SONARQUBE}/{data,extensions,logs}
chown -vR 0999 ${BASE_DIR_SONARQUBE}/

docker stop postgres && docker rm -f postgres
docker run --name postgres \
  -p 5432:5432 \
  -v ${BASE_DIR_POSTGRES}:/var/lib/postgresql/data \
  -v /etc/timezone:/etc/timezone \
  -v /etc/localtime:/etc/localtime \
  -e POSTGRES_USER=root \
  -e POSTGRES_DB=postgres \
  -e POSTGRES_PASSWORD=123456 \
  -e PGDATA=/var/lib/postgresql/data/pgdata \
  -d postgres

# 开启postgresql远程访问参考连接: https://www.cnblogs.com/chendongbky/p/14874794.html, 看5 -> 5.(1)

docker exec -it postgres psql -h ${POSTGRESQL_HOST} -U root

# 界面运行下面的SQL, 创建sonarqube的数据库
CREATE DATABASE "sonarqube" WITH OWNER = "root" TEMPLATE = "postgres" ENCODING = 'UTF8' TABLESPACE = "pg_default"
COMMENT ON DATABASE "sonarqube" IS 'SonarQube数据库'

#退出容器

# 社区版本
docker stop sonarqube && docker rm -f sonarqube
docker run --name sonarqube \
  -p 9001:9000 \
  -p 9092:9092 \
  -e SONAR_JDBC_USERNAME=root \
  -e SONAR_JDBC_PASSWORD=123456 \
  -e SONAR_JDBC_URL="jdbc:postgresql://${POSTGRESQL_HOST}:5432/sonarqube" \
  -v ${BASE_DIR_SONARQUBE}/data:/opt/sonarqube/data \
  -v ${BASE_DIR_SONARQUBE}/extensions:/opt/sonarqube/extensions \
  -v ${BASE_DIR_SONARQUBE}/logs:/opt/sonarqube/logs \
  -d sonarqube

# 企业版本
#docker run -d -p 9001:9000  -p 9092:9092 --name sonarqube \
#-v ${BASE_DIR_SONARQUBE}/sonarqube_data:/opt/sonarqube/data \
#-v ${BASE_DIR_SONARQUBE}/sonarqube_extensions:/opt/sonarqube/extensions \
#-v ${BASE_DIR_SONARQUBE}/sonarqube_logs:/opt/sonarqube/logs \
#-e SONAR_JDBC_USERNAME=sonarqube \
#-e SONAR_JDBC_PASSWORD=sonarqube \
#-e SONAR_JDBC_URL="jdbc:postgresql://${POSTGRESQL_HOST}:5432/sonarqube" \
#sonarqube:enterprise

#编辑这个文件, 再末尾追加(已经有就不用追加): vm.max_map_count=655360
vim /etc/sysctl.conf
sysctl -p

# echo后边用单引号包围要添加的内容
echo 'vm.max_map_count=655360' >>/etc/sysctl.conf
sysctl -p

# 浏览器访问sonarqube: http://192.168.40.132:9001, 默认用户名密码为：admin/admin , 进入界面后: Administration --> marketplace --> 搜索chinese, 安装中文语言

# token - > 99bfe61d5bba8bb2164d6679ee345c03ad30d5d7

#!/bin/bash

BASE_DIR="/usr/local/zipkin"
# 浏览器打开链接[https://search.maven.org/remote_content?g=io.zipkin&a=zipkin-server&v=LATEST&c=exec]下载个最新版, 提取版本号；
VERSION="2.23.16"
APP=zipkin-server-${VERSION}-exec
DOCKER_FILE="${BASE_DIR}/Dockerfile"
mkdir -pv ${BASE_DIR}

cd ${BASE_DIR} || exit

rm -rfv ${BASE_DIR}/${APP}.jar
wget https://repo1.maven.org/maven2/io/zipkin/zipkin-server/${VERSION}/zipkin-server-${VERSION}-exec.jar

rm -rfv ${DOCKER_FILE}
{
  echo FROM openjdk:11
  echo RUN mkdir -pv ${BASE_DIR}/
  echo ADD ${APP}.jar ${BASE_DIR}/${APP}.jar
  echo EXPOSE 9411
  echo WORKDIR ${BASE_DIR}/
  echo ENTRYPOINT [\"java\",\"-jar\", \"${BASE_DIR}/${APP}.jar\"]
} >>${DOCKER_FILE} &&
  clear && cat ${DOCKER_FILE}

rm -rfv ${APP}.jar

docker stop zipkin && docker rm -f zipkin
docker rmi -f zipkin:latest
docker build -f ${DOCKER_FILE} -t zipkin:latest .

docker stop zipkin && docker rm -f zipkin
#以下mysql的参数需要修改
docker run --name zipkin --restart=always \
  -p 9411:9411 \
  -e JAVA_OPTS="-Xms512m -Xmx512m" \
  -e STORAGE_TYPE="mysql" \
  -e MYSQL_HOST="192.168.31.23" \
  -e MYSQL_TCP_PORT="3306" \
  -e MYSQL_DB="zipkin" \
  -e MYSQL_USER="root" \
  -e MYSQL_PASS="123456" \
  -v /etc/timezone:/etc/timezone \
  -v /etc/localtime:/etc/localtime \
  -d zipkin:latest

clear && docker logs -f zipkin

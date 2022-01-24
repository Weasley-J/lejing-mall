#!/bin/bash

CONTAINER_NAME="nacos"
IMAGE_NAME="nacos/nacos-server"
BASE_DIR="/usr/local"
WORK_DIR="${BASE_DIR}/${CONTAINER_NAME}"

rm -rfv ${WORK_DIR} && mkdir -pv ${WORK_DIR} && ll ${WORK_DIR}

#准备挂载文件
docker stop nacos && docker rm -f nacos
docker run --name nacos -p 8848:8848 \
  --net mynet \
  -e MODE="standalone" \
  -e JVM_XMS="512m" \
  -e JVM_XMX="512m" \
  -d ${IMAGE_NAME}

docker cp ${CONTAINER_NAME}:/home/nacos/conf ${WORK_DIR}/
docker cp ${CONTAINER_NAME}:/home/nacos/logs ${WORK_DIR}/
ll ${WORK_DIR}/conf

docker network rm mynet
docker network create --driver bridge --subnet 172.18.0.0/16 --gateway 172.18.0.1 mynet

# 以下创建容器时指定的mysql参数根据自己的实际情况修改
docker stop nacos && docker rm -f nacos
docker run --name nacos -p 8848:8848 --restart=always \
  --net mynet \
  -e MODE="standalone" \
  -e JVM_XMS="512m" \
  -e JVM_XMX="512m" \
  -e SPRING_DATASOURCE_PLATFORM="mysql" \
  -e MYSQL_SERVICE_HOST="192.168.31.105" \
  -e MYSQL_SERVICE_PORT="3306" \
  -e MYSQL_SERVICE_DB_NAME="nacos" \
  -e MYSQL_SERVICE_USER="root" \
  -e MYSQL_SERVICE_PASSWORD="123456" \
  -v /etc/timezone:/etc/timezone \
  -v /etc/localtime:/etc/localtime \
  -v ${WORK_DIR}/conf:/home/nacos/conf \
  -v ${WORK_DIR}/logs:/home/nacos/logs \
  -d ${IMAGE_NAME}

#日志
clear && docker logs -f nacos

#!/bin/bash

CONTAINER_NAME="zipkin"
IMAGE_NAME="openzipkin/zipkin"
BASE_DIR="/usr/local/zipkin"

mkdir -pv ${BASE_DIR}/

docker stop ${CONTAINER_NAME} && docker rm -f ${CONTAINER_NAME}
docker rmi ${IMAGE_NAME} && docker pull ${IMAGE_NAME}

#内存版 - 需要开机自启动就加上参数[--restart=always]
docker stop ${CONTAINER_NAME} && docker rm -f ${CONTAINER_NAME}
docker run --name ${CONTAINER_NAME} \
  -p 9411:9411 \
  -e JAVA_OPTS="-Xms256m -Xmx256m -Dlogging.level.zipkin2=DEBUG" \
  -v /etc/timezone:/etc/timezone \
  -v /etc/localtime:/etc/localtime \
  -d ${IMAGE_NAME}

#zipkin日志
clear && docker logs -f zipkin

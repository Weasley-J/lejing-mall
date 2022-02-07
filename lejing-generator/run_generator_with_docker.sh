#!/bin/bash

WORK_DIR="/usr/local/web-app"
DOCKER_FILE="${WORK_DIR}/Dockerfile"
APP="lejing-generator.jar"
CONTAINER="code-generator"
APP_PORT="7070"
mv -fv "${APP}" "${WORK_DIR}/${CONTAINER}.jar"
cd ${WORK_DIR} || exit

ll

rm -rfv ${DOCKER_FILE}
{
  echo FROM openjdk:11
  echo RUN mkdir -pv ${WORK_DIR}/
  echo ADD ${CONTAINER}.jar ${WORK_DIR}/${CONTAINER}.jar
  echo EXPOSE ${APP_PORT}
  echo WORKDIR ${WORK_DIR}/
  echo ENTRYPOINT [\"java\",\"-jar\", \"${WORK_DIR}/${CONTAINER}.jar\"]
} >>${DOCKER_FILE} &&
  clear && cat ${DOCKER_FILE}

docker stop ${CONTAINER} && docker rm -f ${CONTAINER}
docker rmi -f ${CONTAINER}:latest
docker build -f ${DOCKER_FILE} -t ${CONTAINER}:latest .

docker stop ${CONTAINER} && docker rm -f ${CONTAINER}
docker run --name ${CONTAINER} --restart=always \
  --net mynet \
  -p ${APP_PORT}:${APP_PORT} \
  -e JAVA_OPTS="-Xms256m -Xmx256m" \
  -e server.port="${APP_PORT}" \
  -e Spring.application.name="${CONTAINER}" \
  -e spring.cloud.nacos.config.server-addr="10.0.12.12:8848" \
  -e spring.cloud.nacos.server-addr="10.0.12.12:8848" \
  -v /etc/timezone:/etc/timezone \
  -v /etc/localtime:/etc/localtime \
  -v ${WORK_DIR}/:${WORK_DIR}/ \
  -d ${CONTAINER}:latest

#clear && docker logs -f ${CONTAINER}

cd ${WORK_DIR} || exit
rm -rfv ${APP}.jar

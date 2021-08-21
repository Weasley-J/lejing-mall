# zipkin安装地址

## 常规安装

[官网](https://zipkin.io/)

[github连接](https://github.com/openzipkin/zipkin/)

```shell
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
```

## 使用docker-compose.yml

```shell
#!/bin/bash
BASE_DIR="/usr/local/zipkin"
mkdir -pv ${BASE_DIR}/
cd ${BASE_DIR}/
sudo tee ${BASE_DIR}/docker-compose.yml <<-'EOF'
version: '2'

services:
  zipkin:
    image: openzipkin/zipkin
    container_name: zipkin
    environment:
      - STORAGE_TYPE=mysql
      - MYSQL_HOST=192.168.40.132
      - MYSQL_DB=zipkin
      - MYSQL_PORT=3306
      - MYSQL_USER=root
      - MYSQL_PASS=123456
      - JAVA_OPTS=-Dlogging.level.zipkin2=DEBUG
    ports:
      - "9411:9411"
    network_mode: mynet
EOF
# 终端运行
docker-compose -f docker-compose.yml up
```

终端运行：

```shell
docker-compose -f docker-compose.yml up
```


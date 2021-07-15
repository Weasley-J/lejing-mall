#!/bin/bash

CONTAINER_NAME="torna"
IMAGE_NAME="tanghc2020/torna"
BASE_DIR="/usr/local/torna"
CONFIG_FILE="application.properties"

docker stop ${CONTAINER_NAME} && docker rm -f ${CONTAINER_NAME}
docker rmi ${IMAGE_NAME}:latest && docker pull ${IMAGE_NAME}:latest

mkdir -pv ${BASE_DIR}/{config,data}

#运行个临时容器,把application.properties配置文件copy到宿主机
docker run --name torna -p 7700:7700 -d ${IMAGE_NAME}:latest
docker cp ${CONTAINER_NAME}:/torna/config ${BASE_DIR}/config
ls ${BASE_DIR}/config

#或者使用wget命令直接下载配置文件然后把配置问价改成你自己的(推荐)
#wget -v https://gitee.com/durcframework/torna/raw/master/server/boot/src/main/resources/application.properties -O application.properties

# 存在application.properties就用vim编辑下就好了, 没有application.properties配置文件, 你手动就创建创建配置文件
rm -rfv ${BASE_DIR}/config/${CONFIG_FILE}
sudo tee ${BASE_DIR}/config/${CONFIG_FILE} <<-'EOF'
# Server port
server.port=7700

# MySQL host, 这里改成你的MySQL的主机ip:端口
mysql.host=192.168.40.132:3306
# Schema name, 数据库名
mysql.schema=torna

# Insure the account can run CREATE/ALTER sql.
mysql.username=root
mysql.password=123456
EOF

#修改权限
chmod -vR 777 ${BASE_DIR}/

#查看配置文件
cat ${BASE_DIR}/config/application.properties

#删除容器
docker stop ${CONTAINER_NAME} && docker rm -f ${CONTAINER_NAME}

#正式安装torna, jvm参数根据自己的机器性能调整
docker run --name torna --restart=always \
  -p 7700:7700 \
  -e JAVA_OPTS="-Xms256m -Xmx256m" \
  -v ${BASE_DIR}/config:/torna/config \
  -v /etc/timezone:/etc/timezone \
  -v /etc/localtime:/etc/localtime \
  -d ${IMAGE_NAME}:latest

#torna日志
clear && docker logs -f torna

#!/bin/bash

#打开链接[https://github.com/seata/seata/releases]查看现在的版本号
CURRENT_VERSION="1.4.2"
OLD_VERSION="1.4.2"
DOWNLOAD_LINK="https://github.com/seata/seata/releases/download/v${CURRENT_VERSION}/seata-server-${CURRENT_VERSION}.tar.gz"

BASE_DIR="/usr/local/seata"
CONTAINER_NAME="seata-server"
IMAGE_NAME="seataio/seata-server"
WORK_DIR="${BASE_DIR}/${CONTAINER_NAME}"

mkdir -pv "${BASE_DIR}/"
mkdir -pv "${WORK_DIR}/"

cd ${BASE_DIR}/ || exit
rm -rfv seata-server-${CURRENT_VERSION}.tar.gz
wget ${DOWNLOAD_LINK}
rm -rfv "${BASE_DIR}/seata"
tar -C "${BASE_DIR}/" -xzvf "${BASE_DIR}/seata-server-${CURRENT_VERSION}.tar.gz"
mv -fv "${BASE_DIR}/seata/seata-server-${CURRENT_VERSION}" "${BASE_DIR}/seata-server-${CURRENT_VERSION}"
rm -rfv "${BASE_DIR}/seata"
ll ${BASE_DIR}/

#拉镜像
docker stop ${CONTAINER_NAME} && docker rm -f ${CONTAINER_NAME}
docker rmi -f ${IMAGE_NAME}:${OLD_VERSION}
docker pull ${IMAGE_NAME}:${CURRENT_VERSION}
clear && printf '\r\t\t\t\t%s\n\r\r' "Docker镜像列表" && docker images

docker network rm mynet
docker network create --driver bridge --subnet 172.18.0.0/16 --gateway 172.18.0.1 mynet

#复制文件
docker run --name ${CONTAINER_NAME} -p 8091:8091 -d ${IMAGE_NAME}:${CURRENT_VERSION}
docker cp ${CONTAINER_NAME}:/seata-server ${BASE_DIR}/
ll ${WORK_DIR}

docker stop ${CONTAINER_NAME} && docker rm -f ${CONTAINER_NAME}

#创建数据库[seata], 初始化sql，链接: https://github.com/seata/seata/tree/develop/script/server/db
#这里自行创建即可，不做演示。

#登录nacos，创建seata的nacos命名空间，改配置文件会用到，如：
#命名空间名称:命名空间ID, seata-server:f1c51b7c-562f-48c8-9b15-3a0ebd25dc2d, Group: SEATA_GROUP

#修改file.conf文件, 将mode修改为db, 然后把数据库配置[database store property]换成上面自己新建的seata数据库的配置
cd ${WORK_DIR}/resources/ || exit
cat file.conf
#这一步你可以使用vim修改file.conf，完成后保存并退出vim
vim file.conf

#修改registry.conf文件，将type修改为nacos, 将注册中心和配置中心改为自己上面创建的命名空间和对应的nacos IP地址
#主要修改两个代码块：registry {}, config {}; [dataId = "seataServer.properties"]可先加#号注释掉
vim registry.conf

#下载源码
cd ${BASE_DIR} || exit
wet https://github.com/seata/seata/archive/refs/tags/v${CURRENT_VERSION}.tar.gz
tar -xzvf "seata-${CURRENT_VERSION}.tar.gz"
cd seata-${CURRENT_VERSION}/script/config-center || exit

# vim修改config.txt，将[store.mode=file]改为[store.mode=db],将数据库[store.db.*]改为自己数据库的配置
# [service.vgroupMapping.my_test_tx_group=default]改为[service.vgroupMapping.lejing_tx_group=default],
# 这个[lejing_tx_group]代码中会用到, 对应自己的事务的名, 根据自己的情况修改
vim config.txt

CONFIG_TEXT="${BASE_DIR}/seata-${CURRENT_VERSION}/script/config-center/config.txt"
# cat检查[config.txt]参数对不对
cat ${CONFIG_TEXT}

#接下来导入[config.txt]元数据到[nacos]中
NACOS_CONFIG="${BASE_DIR}/seata-${CURRENT_VERSION}/script/config-center/nacos/nacos-config.sh"
NACOS_HOST="192.168.31.105"
GROUP="SEATA_GROUP"
NAME_SPACE="f1c51b7c-562f-48c8-9b15-3a0ebd25dc2d"
chmod 777 -v ${NACOS_CONFIG}
cd "${BASE_DIR}/seata-${CURRENT_VERSION}/script/config-center/nacos/" || exit
#USAGE OPTION: $0 [-h host] [-p port] [-g group] [-t tenant] [-u username] [-w password]
${NACOS_CONFIG} -h ${NACOS_HOST} -p 8848 -g ${GROUP} -t ${NAME_SPACE}

#重启容器, 更多支持的环境变量参考: https://registry.hub.docker.com/r/seataio/seata-server
docker stop ${CONTAINER_NAME} && docker rm -f ${CONTAINER_NAME}
docker run --name ${CONTAINER_NAME} --restart=always \
  --net mynet \
  -p 8091:8091 \
  -v ${WORK_DIR}:/seata-server \
  -v /etc/timezone:/etc/timezone \
  -v /etc/localtime:/etc/localtime \
  -e SEATA_IP="192.168.31.105" \
  -e SEATA_PORT="8091" \
  -d ${IMAGE_NAME}:${CURRENT_VERSION}

clear && docker logs -f ${CONTAINER_NAME}

rm -rfv ${BASE_DIR}/seata-${CURRENT_VERSION}.tar.gz
rm -rfv ${BASE_DIR}/seata-server-${CURRENT_VERSION}.tar.gz
rm -rfv ${BASE_DIR}/seata-server-${CURRENT_VERSION}

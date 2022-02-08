#!/bin/bash

#
# 内网穿透安装，这是个内网穿透的安装脚本，提供本机对外公网访问的能力
# 去官网[https://www.fgnwct.com/home.html]找几个版本提取文件名和下载链接， 不知道怎么用看官方文档
#

DIR="/usr/local/feige"
TAR_NAME="linux_amd64_client"
LOG_NAME="${DIR}/npc"
EXEC_APP="${DIR}/${TAR_NAME}/npc"
LINK="https://www.fgnwct.com/client/${TAR_NAME}.tar.gz"
mkdir -pv "${DIR}"
cd ${DIR} || exit
rm -rfv ./*.gz
wget ${LINK}

ll

rm -rfv "./${TAR_NAME}"
tar -xzvf "${TAR_NAME}.tar.gz"

cd ${DIR}/${TAR_NAME} || exit

#以下4个参数需要在飞鸽后台[https://www.fgnwct.com/home.html]根据自己的情况修改，访问域名: http://前置域名.${SERVER}
PRE_DOMAIN="weasley"
SERVER="free.svipss.top"
SERVER_PORT="8024"
V_KEY="48eb2e7a48"

rm -rfv ~/feige_client.sh
{
  echo "nohup ${EXEC_APP} -server=${SERVER}:${SERVER_PORT} -vkey=${V_KEY} > ${LOG_NAME}.log 2>&1 &"
} >>~/feige_client.sh &&
  clear && cat ~/feige_client.sh

chmod -v 777 ~/*.sh

rm rfv "${TAR_NAME}.tar.gz"

clear && printf '\r\n
\t\t\t启动客户端反向代理: ~/feige_client.sh
\t\t\t查看客户端运行日志: clear && tail -f /usr/local/feige/npc.log
\t\t\t访问反向代理的域名: http://%s.%s\r\n' "${PRE_DOMAIN}" "${SERVER}"

# 查看日志
#clear && tail -f "/usr/local/feige/npc.log"

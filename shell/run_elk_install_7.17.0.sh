#!/usr/bin/env bash

#
#                  ******  Ubuntu环境下基于Docker安装ELK全家桶自动化脚本    ******
#
#
#                  用最新版才能学到东西，我希望你也和我一样
#
#                                              Weasley J - 2021年6月26日
#

#
# tips:
# 需要合理修改elasticsearch的ip:port, 本教程使用的服务器的ip为: 192.168.31.106, 搜索替换为自己的ip即可
#

######################################################################
#                        安装elasticsearch
######################################################################
#
#  定义全局安装变量
#
# 当前软件的版本
CURRENT_VERSION="7.17.0"
# 上一个版本
OLD_VERSION="7.16.3"
# 容器名称
CONTAINER_NAME="elasticsearch"
# 配置文件名称
CONFIG_FILE="elasticsearch.yml"
# ELK的基础安装目录
ELK_BASE_DIR="/usr/local/elk"
# 当前软件的安装目录
BASE_DIR="${ELK_BASE_DIR}/${CONTAINER_NAME}"

# 销毁旧容器
clear && docker stop ${CONTAINER_NAME} && docker rm -f ${CONTAINER_NAME}

docker rmi ${CONTAINER_NAME}:${OLD_VERSION} && docker pull ${CONTAINER_NAME}:${CURRENT_VERSION}
clear && printf '\r\t\t\t\t%s\n\r\r' "Docker镜像列表" && docker images

# 创建基础目录
mkdir -pv ${BASE_DIR}/{config,data,logs,plugins}

#如果有需要，创建用户定义的网络（用于连接到连接到同一网络的其他服务（例如：Kibana））
docker network rm mynet
docker network create --driver bridge --subnet 172.18.0.0/16 --gateway 172.18.0.1 mynet

# 创建临时容器复制点配置文件出来，后期修改方便
clear && docker stop ${CONTAINER_NAME} && docker rm -f ${CONTAINER_NAME}
docker run --name ${CONTAINER_NAME} \
  -p 9200:9200 \
  -p 9300:9300 \
  -e ES_JAVA_OPTS="-Xms512m -Xmx512m" \
  -d ${CONTAINER_NAME}:${CURRENT_VERSION}

# 容器:"/usr/share/elasticsearch/{config,plugins}" -> 宿主机:"/usr/local/elk/elasticsearch/{config,plugins}"
docker cp ${CONTAINER_NAME}:/usr/share/elasticsearch/config ${BASE_DIR}/
docker cp ${CONTAINER_NAME}:/usr/share/elasticsearch/plugins ${BASE_DIR}/

clear && printf '\r\t\t\t\t%s\n\r\r' "目录列表"
clear && printf '\r\t\t\t\t%s %s\n\r\r' "${BASE_DIR}/config" "${BASE_DIR}/plugins"
ll ${BASE_DIR}/config && ll ${BASE_DIR}/plugins

# 创建配置文件
rm -rfv ${BASE_DIR}/config/${CONFIG_FILE}
sudo tee ${BASE_DIR}/config/${CONFIG_FILE} <<-'EOF'
# ======================== Elasticsearch Configuration =========================
#
# NOTE: Elasticsearch comes with reasonable defaults for most settings.
#       Before you set out to tweak and tune the configuration, make sure you
#       understand what are you trying to accomplish and the consequences.
#
# The primary way of configuring a node is via this file. This template lists
# the most important settings you may want to configure for a production cluster.
#
# Please consult the documentation for further information on configuration options:
# https://www.elastic.co/guide/en/elasticsearch/reference/index.html
#
# ---------------------------------- Cluster -----------------------------------
#
# Use a descriptive name for your cluster:
#
#cluster.name: my-application
#
# ------------------------------------ Node ------------------------------------
#
# Use a descriptive name for the node:
#
#node.name: node-1
#
# Add custom attributes to the node:
#
#node.attr.rack: r1
#
# ----------------------------------- Paths ------------------------------------
#
# Path to directory where to store the data (separate multiple locations by comma):
#
#path.data: /path/to/data
#
# Path to log files:
#
#path.logs: /path/to/logs
#
# ----------------------------------- Memory -----------------------------------
#
# Lock the memory on startup:
#
#bootstrap.memory_lock: true
#
# Make sure that the heap size is set to about half the memory available
# on the system and that the owner of the process is allowed to use this
# limit.
#
# Elasticsearch performs poorly when the system is swapping the memory.
#
# ---------------------------------- Network -----------------------------------
#
# Set the bind address to a specific IP (IPv4 or IPv6):
#
#network.host: 192.168.0.1
#
# Set a custom port for HTTP:
#
#http.port: 9200
#
# For more information, consult the network module documentation.
#
# --------------------------------- Discovery ----------------------------------
#
# Pass an initial list of hosts to perform discovery when this node is started:
# The default list of hosts is ["127.0.0.1", "[::1]"]
#
#discovery.seed_hosts: ["host1", "host2"]
#
# Bootstrap the cluster using an initial set of master-eligible nodes:
#
#cluster.initial_master_nodes: ["node-1", "node-2"]
#
# For more information, consult the discovery and cluster formation module documentation.
#
# ---------------------------------- Gateway -----------------------------------
#
# Block initial recovery after a full cluster restart until N nodes are started:
#
#gateway.recover_after_nodes: 3
#
# For more information, consult the gateway module documentation.
#
# ---------------------------------- Various -----------------------------------
#
# Require explicit names when deleting indices:
#
#action.destructive_requires_name: true

#
# added by user
#

node.name: docker-node-1
network.host: 0.0.0.0
http.cors.enabled: true
http.cors.allow-origin: "*"
EOF

#
# 修改目录权限
#
chmod -vR 777 ${BASE_DIR}/

# 删除旧版本的ik分词器如果存在
rm -rfv ${BASE_DIR}/plugins/analysis-ik
rm -rfv ${BASE_DIR}/logs/*.*

# 1. 创建单节点容器-开发环境推荐
clear && docker stop ${CONTAINER_NAME} && docker rm -f ${CONTAINER_NAME}
docker run --name ${CONTAINER_NAME} --restart=always \
  --net mynet \
  -p 9200:9200 \
  -p 9300:9300 \
  -e ES_JAVA_OPTS="-Xms512m -Xmx512m" \
  -e "discovery.type=single-node" \
  -v /etc/timezone:/etc/timezone \
  -v /etc/localtime:/etc/localtime \
  -v ${BASE_DIR}/config/:/usr/share/elasticsearch/config \
  -v ${BASE_DIR}/data:/usr/share/elasticsearch/data \
  -v ${BASE_DIR}/logs:/usr/share/elasticsearch/logs \
  -v ${BASE_DIR}/plugins:/usr/share/elasticsearch/plugins \
  -d ${CONTAINER_NAME}:${CURRENT_VERSION}

# 2. 创建集群容器-生产环境推荐
#clear && docker stop ${CONTAINER_NAME} && docker rm -f ${CONTAINER_NAME}
#docker run --name ${CONTAINER_NAME} --restart=always \
#  --net mynet \
#  -p 9200:9200 \
#  -p 9300:9300 \
#  -e ES_JAVA_OPTS="-Xms1g -Xmx1g" \
#  -v /etc/timezone:/etc/timezone \
#  -v /etc/localtime:/etc/localtime \
#  -v ${BASE_DIR}/config/:/usr/share/elasticsearch/config \
#  -v ${BASE_DIR}/data:/usr/share/elasticsearch/data \
#  -v ${BASE_DIR}/logs:/usr/share/elasticsearch/logs \
#  -v ${BASE_DIR}/plugins:/usr/share/elasticsearch/plugins \
#  -d ${CONTAINER_NAME}:${CURRENT_VERSION}

#1. 进入到容器里
docker exec -it elasticsearch bash

CURRENT_VERSION="7.17.0"
#2. 执行安装命令，注意版本和你的ES版本对应
elasticsearch-plugin install https://github.com/medcl/elasticsearch-analysis-ik/releases/download/v${CURRENT_VERSION}/elasticsearch-analysis-ik-${CURRENT_VERSION}.zip && exit
#重启es容器
docker restart elasticsearch && docker logs -f elasticsearch

curl http://127.0.0.1:9200/_cat/plugins/

# 安装es的机器上使用curl命令在服务器终端上验证下，有json返回表示安装成功了
curl http://127.0.0.1:9200

######################################################################
#                        安装Kibana
######################################################################
#
#  *** 定义全局安装变量 ***
#

# 容器名称
CONTAINER_NAME="kibana"
# 配置文件名称
CONFIG_FILE="kibana.yml"
# ELK的基础安装目录
ELK_BASE_DIR="/usr/local/elk"
# 当前软件的安装目录
BASE_DIR="${ELK_BASE_DIR}/${CONTAINER_NAME}"

# 销毁旧容器
docker stop ${CONTAINER_NAME} && docker rm -f ${CONTAINER_NAME}

docker rmi ${CONTAINER_NAME}:${OLD_VERSION}
docker pull ${CONTAINER_NAME}:${CURRENT_VERSION}
clear && printf '\r\t\t\t\t%s\n\r\r' "Docker镜像列表" && docker images

# 创建基础目录
mkdir -pv ${BASE_DIR}/{config,data,logs,plugins}

# 创建临时容器复制点配置文件出来，后期修改方便
docker run --name ${CONTAINER_NAME} \
  -p 5601:5601 \
  -v /etc/timezone:/etc/timezone \
  -v /etc/localtime:/etc/localtime \
  -d ${CONTAINER_NAME}:${CURRENT_VERSION}

# 把容器中的"/usr/share/kibana/{config,plugins}" -> 宿主机"/usr/local/elk/kibana/{config,plugins}"
docker cp ${CONTAINER_NAME}:/usr/share/${CONTAINER_NAME}/config ${BASE_DIR}/
docker cp ${CONTAINER_NAME}:/usr/share/${CONTAINER_NAME}/plugins ${BASE_DIR}/

clear && printf '\r\t\t\t\t%s\n\r\r' "目录列表"
clear && printf '\r\t\t\t\t%s %s\n\r\r' "${BASE_DIR}/config" "${BASE_DIR}/plugins"
ll ${BASE_DIR}/config && ll ${BASE_DIR}/plugins

#
# 创建配置文件kibana.yml，配置文件中连接elasticsearch的ip要根据自己的实际情况修改
#

rm -rfv ${BASE_DIR}/config/${CONFIG_FILE}
sudo tee ${BASE_DIR}/config/${CONFIG_FILE} <<-'EOF'
#
# ** THIS IS AN AUTO-GENERATED FILE **
#

# Default Kibana configuration for docker target

#
# added by user
# 更多设置参考官网: https://www.elastic.co/guide/en/kibana/current/settings.html
#

# 主机名
server.name: "kibana"

# 主机地址，可以是ip
server.host: "0.0.0.0"
server.shutdownTimeout: "5s"

# kibana访问es服务器的URL，就可以有多个，以英文逗号","隔开
elasticsearch.hosts: [ "http://elasticsearch:9200" ]
monitoring.ui.container.elasticsearch.enabled: true

# 修改kibana可视化界面语言为中文，默认是英文界面
i18n.locale: "zh-CN"

# 在代理后面运行，则可以指定安装Kibana的路径
# 使用server.rewriteBasePath设置告诉Kibana是否应删除basePath
# 接收到的请求，并在启动时防止过时警告，此设置不能以斜杠结尾
# server.basePath: "/kibana"
#
# ** THIS IS AN AUTO-GENERATED FILE **
#
EOF

clear && cat ${BASE_DIR}/config/${CONFIG_FILE}

# 销毁旧容器
docker stop ${CONTAINER_NAME} && docker rm -f ${CONTAINER_NAME}
docker run --name ${CONTAINER_NAME} --restart=always \
  --net mynet \
  -e JAVA_OPTS="-Xm512m -Xmx512m" \
  -p 5601:5601 \
  -v ${BASE_DIR}/config:/usr/share/kibana/config \
  -v /etc/timezone:/etc/timezone \
  -v /etc/localtime:/etc/localtime \
  -d ${CONTAINER_NAME}:${CURRENT_VERSION}

# 查看日志
clear && docker logs -f kibana

######################################################################
#                        安装Logstash
######################################################################
#
#  *** 定义全局安装变量 ***
#

# 容器名称
CONTAINER_NAME="logstash"
# 配置文件名称
CONFIG_FILE="${CONTAINER_NAME}.yml"
# ELK的基础安装目录
ELK_BASE_DIR="/usr/local/elk"
# 当前软件的安装目录
BASE_DIR="${ELK_BASE_DIR}/${CONTAINER_NAME}"

# 销毁旧容器
docker stop ${CONTAINER_NAME} && docker rm -f ${CONTAINER_NAME}

docker rmi ${CONTAINER_NAME}:${OLD_VERSION}
docker pull ${CONTAINER_NAME}:${CURRENT_VERSION}
clear && printf '\r\t\t\t\t%s\n\r\r' "Docker镜像列表" && docker images

# 创建基础目录
mkdir -pv ${BASE_DIR}/{config/conf.d,.gem,pipeline}

# 创建临时容器复制点配置文件出来，后期修改方便
docker run --name ${CONTAINER_NAME} \
  -p 5044:5044 \
  -p 9600:9600 \
  -d ${CONTAINER_NAME}:${CURRENT_VERSION}

# 把容器中的"/usr/share/logstash/{config,plugins}" -> 宿主机"/usr/local/elk/logstash/{config,plugins}"
docker cp ${CONTAINER_NAME}:/usr/share/${CONTAINER_NAME}/config ${BASE_DIR}/
docker cp ${CONTAINER_NAME}:/usr/share/${CONTAINER_NAME}/.gem ${BASE_DIR}/
docker cp ${CONTAINER_NAME}:/usr/share/${CONTAINER_NAME}/pipeline ${BASE_DIR}/

clear && printf '\r\t\t\t\t%s\n\r\r' "目录列表"
clear && printf '\r\t\t\t\t%s %s\n\r\r' "${BASE_DIR}/pipeline" "${BASE_DIR}/.gem"
ll ${BASE_DIR}/config && ll ${BASE_DIR}/pipeline && ll ${BASE_DIR}/.gem

#
# 创建配置文件logstash.yml，配置文件中连接elasticsearch的ip要根据自己的实际情况修改
#
CONFIG_FILE_NAME="${BASE_DIR}/config/${CONFIG_FILE}"
rm -rfv ${CONFIG_FILE_NAME}
sudo tee ${CONFIG_FILE_NAME} <<-'EOF'
#
#    added by user
#

# 0.0.0.0：允许任何IP访问
http.host: "0.0.0.0"

# 配置elasticsearch集群地址
xpack.monitoring.elasticsearch.hosts: [ "http://elasticsearch:9200" ]

# 允许监控
xpack.monitoring.enabled: true

# 启动时读取配置文件指定
path.config: /usr/share/logstash/conf.d/*.conf
path.logs: /usr/share/logstash/logs
EOF

#
# 新建文件 logstash.conf，用来收集/var/log/messages
#
CONFIG_FILE_NAME="${BASE_DIR}/config/conf.d/logstash.conf"
rm -rfv ${CONFIG_FILE_NAME}
sudo tee ${CONFIG_FILE_NAME} <<-'EOF'
# Sample Logstash configuration for creating a simple
# Beats -> Logstash -> Elasticsearch pipeline.

input {

  file {
    #标签
    type => "logstash-dev"
    #采集点
    path => "/var/log/messages"
    #开始收集点
    start_position => "beginning"
    #扫描间隔时间，默认是1s，建议5s
    stat_interval => "5"
  }

  tcp {
    mode => "server"
    host => "0.0.0.0"
    port => "5044"
    codec => "json_lines"
  }
}

#请根据实际情况修改elasticsearch地址
output {

  elasticsearch {
    hosts => ["http://elasticsearch:9200"]
    #此处index可以从logback-spring.xml中customFields标签中获取app_name和run_env的值做为索引的名称
    index => "%{[app_name]}-%{[run_env]}-%{+YYYY.MM.dd}"
    #index => index => "logstash-dev-%{+YYYY.MM.dd}"
    #user => "elastic"
    #password => "changeme"
  }

  #若不需要在控制台中输出，此行可以删除
  stdout {
    codec => rubydebug
  }
}
EOF

# 设置日志文件读取权限
chmod -vR 777 ${BASE_DIR}/
chmod -v 0644 /var/log/messages

# 销毁旧容器
docker stop ${CONTAINER_NAME} && docker rm -f ${CONTAINER_NAME}
#运行容器
docker run --name ${CONTAINER_NAME} --restart=always \
  --net mynet \
  -p 5044:5044 \
  -p 9600:9600 \
  -e JAVA_OPTS="-Xm512m -Xmx512m" \
  --privileged=true \
  -v ${BASE_DIR}/config/conf.d/:/usr/share/logstash/conf.d/ \
  -v ${BASE_DIR}/config:/usr/share/${CONTAINER_NAME}/config \
  -v ${BASE_DIR}/pipeline:/usr/share/${CONTAINER_NAME}/pipeline \
  -v ${BASE_DIR}/.gem:/usr/share/${CONTAINER_NAME}/.gem \
  -v /etc/timezone:/etc/timezone \
  -v /etc/localtime:/etc/localtime \
  -d ${CONTAINER_NAME}:${CURRENT_VERSION}

#安装插件
docker exec -it logstash bash

clear && logstash-plugin install logstash-codec-json_lines
clear && logstash-plugin install logstash-codec-json && exit

docker restart logstash && clear && docker logs -f logstash

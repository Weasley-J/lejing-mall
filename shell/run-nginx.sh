#!/bin/bash

set -e -x

#nginx="/usr/local/nginx/sbin/nginx"

if [ -f /var/run/nginx/nginx.pid ]; then
  pid=$(cat /var/run/nginx/nginx.pid)
fi

if [ -f /usr/local/openresty/nginx/logs/nginx.pid ]; then
  pid=$(cat /usr/local/openresty/nginx/logs/nginx.pid)
fi

if [ -n "$pid" ]; then
  kill -QUIT "$pid"
  sleep 0.5
fi

mkdir -pv /var/run/nginx &&
  rm -rfv /var/run/nginx/nginx.pid &&
  touch /var/run/nginx/nginx.pid

nginx

echo "nginx启动成功！"

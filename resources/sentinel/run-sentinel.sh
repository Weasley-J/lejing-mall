#!/bin/bash
SENTINEL="sentinel-dashboard-1.8.2.jar"
HOST="localhost"
PORT="8088"
java -Dserver.port=${PORT} \
  -Dcsp.sentinel.dashboard.server=${HOST}:${PORT} \
  -Dproject.name=sentinel-dashboard \
  -Dsentinel.dashboard.auth.username=admin \
  -Dsentinel.dashboard.auth.password=123456 \
  -jar ${SENTINEL}

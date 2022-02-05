#!/bin/bash
WORKPLACE="seata-server-1.4.2"
mkdir "${WORKPLACE}/logs"
chmod 777 ${WORKPLACE}/bin/seata-server.sh
${WORKPLACE}/bin/seata-server.sh

#!/bin/bash

# 只打包某个service
# mvn clean package -pl "lejing-auth/lejing-auth-service" -am

# 安装基础依赖到本地仓库, 方便后续service打包

cache="${MAVEN_HOME}/repository/cn/alphahub"

rm -rfv "${cache}"

mvn clean &&
  mvn clean install -pl :lejing-common-util -am &&
  mvn clean install -pl :lejing-common-base-domain -am &&
  mvn clean install -pl :lejing-common-base-public -am &&
  mvn clean install -pl :lejing-common-dependencies-db -am &&
  mvn clean install -pl :lejing-common-dependencies-web -am &&
  mvn clean install -pl :lejing-common-email-support -am &&
  mvn clean install -pl :lejing-common-sms-support -am &&
  mvn clean install -pl :lejing-auth-common -am &&
  mvn clean install -pl :lejing-auth-interface -am &&
  mvn clean install -pl :lejing-cart-interface -am &&
  mvn clean install -pl :lejing-coupon-interface -am &&
  mvn clean install -pl :lejing-member-interface -am &&
  mvn clean install -pl :lejing-order-interface -am &&
  mvn clean install -pl :lejing-order-share -am &&
  mvn clean install -pl :lejing-product-interface -am &&
  mvn clean install -pl :lejing-search-interface -am &&
  mvn clean install -pl :lejing-seckill-interface -am &&
  mvn clean install -pl :lejing-ware-interface -am &&
  mvn clean && clear

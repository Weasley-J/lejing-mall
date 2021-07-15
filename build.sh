#!/bin/bash
# 只打包某个service
# mvn clean package -pl "lejing-auth/lejing-auth-service" -am

# 安装基础依赖到本地仓库, 方便后续service打包
mvn clean &&
  mvn install -pl "lejing-common/lejing-common-util" -am &&
  mvn install -pl "lejing-common/lejing-common-base-domain" -am &&
  mvn install -pl "lejing-common/lejing-common-base-public" -am &&
  mvn install -pl "lejing-common/lejing-common-dependencies-db" -am &&
  mvn install -pl "lejing-common/lejing-common-dependencies-web" -am &&
  mvn install -pl "lejing-auth/lejing-auth-common" -am &&
  mvn install -pl "lejing-auth/lejing-auth-interface" -am &&
  mvn install -pl "lejing-cart/lejing-cart-interface" -am &&
  mvn install -pl "lejing-coupon/lejing-coupon-interface" -am &&
  mvn install -pl "lejing-member/lejing-member-interface" -am &&
  mvn install -pl "lejing-order/lejing-order-interface" -am &&
  mvn install -pl "lejing-order/lejing-order-share" -am &&
  mvn install -pl "lejing-product/lejing-product-interface" -am &&
  mvn install -pl "lejing-search/lejing-search-interface" -am &&
  mvn install -pl "lejing-seckill/lejing-seckill-interface" -am &&
  mvn install -pl "lejing-ware/lejing-ware-interface" -am &&
  mvn clean && clear

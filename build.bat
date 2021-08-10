@echo off & color 0A

REM 只打包某个service
REM mvn clean package -pl "lejing-auth/lejing-auth-service" -am ^

REM 安装基础依赖到本地仓库, 方便后续service打包

mvn clean ^
&& mvn clean install -pl "lejing-common/lejing-common-util" -am ^
&& mvn clean install -pl "lejing-common/lejing-common-base-domain" -am ^
&& mvn clean install -pl "lejing-common/lejing-common-base-public" -am ^
&& mvn clean install -pl "lejing-common/lejing-common-dependencies-db" -am ^
&& mvn clean install -pl "lejing-common/lejing-common-dependencies-web" -am ^

&& mvn clean install -pl "lejing-auth/lejing-auth-common" -am ^
&& mvn clean install -pl "lejing-auth/lejing-auth-interface" -am ^

&& mvn clean install -pl "lejing-cart/lejing-cart-interface" -am ^

&& mvn clean install -pl "lejing-coupon/lejing-coupon-interface" -am ^

&& mvn clean install -pl "lejing-member/lejing-member-interface" -am ^

&& mvn clean install -pl "lejing-order/lejing-order-interface" -am ^
&& mvn clean install -pl "lejing-order/lejing-order-share" -am ^

&& mvn clean install -pl "lejing-product/lejing-product-interface" -am ^

&& mvn clean install -pl "lejing-search/lejing-search-interface" -am ^

&& mvn clean install -pl "lejing-seckill/lejing-seckill-interface" -am ^

&& mvn clean install -pl "lejing-ware/lejing-ware-interface" -am ^

&& cls && mvn clean ^
&& exit

@echo off & color 0A
mvn clean compile sonar:sonar ^
&& mvn clean ^
&& exit

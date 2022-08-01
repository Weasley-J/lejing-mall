#!/bin/bash

SETTINGS="/Users/weasley/Development/program/apache-maven/conf/settings-sonarqube.xml"

clear &&
  mvn clean compile sonar:sonar --settings $SETTINGS &&
  mvn clean

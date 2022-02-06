#!/bin/bash
mvn clean compile sonar:sonar &&
  mvn clean

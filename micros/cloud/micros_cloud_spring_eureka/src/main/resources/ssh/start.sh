#!/bin/bash

_JAR_NAME=micros_spring_boot.jar

nohup java -jar $_JAR_NAME & 
echo -ne "\033[32m Starting \033[0m" 
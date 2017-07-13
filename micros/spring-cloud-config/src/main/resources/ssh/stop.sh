#!/bin/bash

_JAR_NAME=micros_spring_boot.jar

PID=$(ps -ef | grep ${_JAR_NAME} | grep -v grep | awk '{ print $2 }')
if [ -z "$PID" ]
then
    echo 服务已关闭
else
    echo 关闭服务中 $PID
    kill $PID
fi
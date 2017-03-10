#!/bin/bash
usage() {
	echo "Usage:"
	echo "	stop.sh"
	echo "Description:"
	exit -1
}

P_MAIN_NAME="com.yueny.demo.start.agent.LogDubboStart"

# current dir
RUNTIME_DIR=`pwd`

LIB_DIR="$RUNTIME_DIR/lib"
CONF_DIR=$RUNTIME_DIR/config

CLASS_PATH=$CONF_DIR
lib_str=`ls -1 ${LIB_DIR}/*.jar`
for lib in $lib_str
do
        CLASS_PATH="${CLASS_PATH}":"${lib}"
done

$JAVA_HOME/bin/java -classpath $CLASS_PATH ${P_MAIN_NAME} shutdown

P_PID=`ps -ef | grep ${P_MAIN_NAME} | grep -v grep | awk '{print $2}'`
# -z  空串 [zero]
if [ ! -z $P_PID ]; then
	kill $P_PID
	sleep 2s
fi

P_PID=`ps -ef | grep ${P_MAIN_NAME} | grep -v grep | awk '{print $2}'`
if [ ! -z $P_PID ]; then
	kill -9 $P_PID
	sleep 2s
fi

P_PID=`ps -ef | grep ${P_MAIN_NAME} | grep -v grep | awk '{print $2}'`
if [ -z $P_PID ]; then
	# 绿色32， 红色31， 洋红35， 青色36， 白色37
	echo -e "\033[32m STOP SUCCESSFUL!!! \033[0m"
else
	echo -e "\033[31m STOP FAIL!!! \033[0m"
fi

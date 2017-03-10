#!/bin/bash
usage() {
	echo "Usage:"
	echo "	start.sh"
	echo "Description:"
	exit -1
}

P_MAIN_NAME="com.yueny.demo.start.agent.LogDubboStart"

P_PID=`ps -ef | grep ${P_MAIN_NAME} | grep -v grep | awk '{print $2}'`
if [ ! -z $P_PID ]; then
	echo -e "\033[31m LogDubboStart ia aleary start。。。  \033[0m"
	exit -1
fi

# current dir
# chmod 755 config
RUNTIME_DIR=`pwd`

LIB_DIR="$RUNTIME_DIR/lib"
CONF_DIR=$RUNTIME_DIR/config

CLASS_PATH=$CONF_DIR
lib_str=`ls -1 ${LIB_DIR}/*.jar`
for lib in $lib_str
do
        CLASS_PATH="${CLASS_PATH}":"${lib}"
done

LOG_DIR="${RUNTIME_DIR}/logs"
# 判断文件夹是否存在
if [ ! -d $LOG_DIR ]; then
	mkdir $LOG_DIR
fi 
touch "${LOG_DIR}/log.out"

# JVM OPTIONS
JAVA_OPTIONS="-Xmx56m -Xms56m -Xmn28m"

# nohup $JAVA_HOME/bin/java $JAVA_OPTIONS -classpath $CLASS_PATH com.yueny.demo.start.agent.LogDubboStart start >> logs/log.out 2>&1 &
nohup $JAVA_HOME/bin/java $JAVA_OPTIONS -Xdebug -Xnoagent -classpath $CLASS_PATH ${P_MAIN_NAME} start >> "${LOG_DIR}/log.out" 2>&1 &

tail -f "${LOG_DIR}/log.out"
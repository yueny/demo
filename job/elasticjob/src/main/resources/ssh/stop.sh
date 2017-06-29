#!/bin/bash

usage() {
    echo "Usage: $0 [OPTIONS]"
    echo "  -n <name>          Server name"
    exit 1
}

pushd `dirname $0`/.. > /dev/null
BASE=`pwd`
popd > /dev/null
cd $BASE

# 默认值设置
name=""

while getopts ":n:" arg 
do
    case "$arg" in
        n)
                echo "name is :$OPTARG"
                name="$OPTARG";;
	    [?])
	            #当有不认识的选项的时候arg为 ?  
	        echo "unkonw argument"
	            usage;;
    esac
done

if [ ! -n "$name" ]; then
	echo "argument -n  must not be empty"
	usage
fi

PID=$(ps -ef | grep ${name} | grep -v grep | awk '{ print $2 }')
if [ -z "$PID" ]
then
    echo 服务已关闭
else
    echo 关闭服务中 $PID ${name}
    kill $PID
    # curl -X POST http://123.57.163.175:9091/shutdown
fi
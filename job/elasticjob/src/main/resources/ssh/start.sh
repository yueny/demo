#!/bin/bash

usage() {
    echo "Usage: $0 [OPTIONS]"
    echo "  -n <name>          Server name"
    exit 1
}

# 回到`当前脚本`的`上一级目录`
pushd `dirname $0`/.. > /dev/null
BASE=`pwd`
popd > /dev/null
cd $BASE
TIME_STAMP=`date +%Y%m%d%H%M` 

# 默认值设置
name=""
_sys_file="system.properties"

while getopts ":n:" arg 
do
        case "$arg" in
            n)
                    name="$OPTARG";;
	        [?])
	                #当有不认识的选项的时候arg为 ?  
	            echo "unkonw argument"
	                usage;;
        esac
done

if [ ! -n "$name" ]; then
	echo "argument -n  must not be empty, actual is ${name}."
	usage
fi

# 是否已被启动
pid=`ps -ef | grep ${name}.jar |grep -v "grep" |awk '{print $2}'`
if [ "x${pid}x" != "xx" ]; then
	echo "项目已经启动中 running, pid is ${pid}"  
	exit 1
fi

if [ "x${CONF_PATH}x" == "xx" ];then
	CONF_PATH="$BASE"
fi
sys_file=$CONF_PATH/$_sys_file
echo "conf file is :$sys_file"

# 统计配置文件行数
# count=`awk 'END{print NR}' $file`
# 读取端口配置

# pt=$(grep -i "^server.port\s*=" $sys_file | cut -d= -f 2| sed 's/^\s*//;s/\s*$//')
port=`sed '/^server.port=/!d;s/.*=//' $sys_file`
if [ "x${port}x" == "xx" ]; then
	port = ""
fi
echo "name is :${name}"
echo "port is :x${port}x"

if [ "x${port}x" == "xx" ]; then
	nohup java -jar ${name}.jar > nohup.out 2>&1 & 
else
	nohup java -jar -Dserver.port=$port ${name}.jar > nohup.out 2>&1 & 
fi

echo -ne "\033[32m Starting \033[0m"
touch nohup.out
tail -f nohup.out

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

# 默认值设置
name=""
port="8080"

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

if [ "x${CONF_PATH}x" == "xx" ];then
	CONF_PATH="$BASE"
fi
file=$CONF_PATH/application.properties
echo "conf file is :$file"

# 统计配置文件行数
# count=`awk 'END{print NR}' $file`
# 读取端口配置
# p=$(grep -i "^server.port\s*=" $file | cut -d= -f 2| sed 's/^\s*//;s/\s*$//')
p=`sed '/^server.port=/!d;s/.*=//' $file`  
if [ "x${p}x" != "xx" ]; then
	port = $p
fi
echo "name is :${name}"
echo "port is :${port}"

nohup java -jar -Dserver.port=$port ${name}.jar > nohup.out 2>&1 & 
echo -ne "\033[32m Starting \033[0m"
tail -f nohup.out

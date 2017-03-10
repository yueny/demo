#!/bin/bash
usage() {
	echo "Usage:"
	echo "	go.sh"
	echo "Description:"
	exit -1
}

# eg> /root or /home/xiaobai
BASE_DIR="${HOME}"

# 停止服务
echo "service stop..."
sh stop.sh

# 启动服务
echo "service start..."
sh start.sh

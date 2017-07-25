## 消费消息 rocketmq-consumer
rocketmq-consumer
消费数据。ok

# 不涉及数据库，缓存等配置
* mvn clean package -Dmaven.test.skip=true -U

# 命令启动应用程序
#  不指定加载，使用默认的配置文件路径
* java -jar consumer-0.0.1.jar
# 指定服务对外端口号
* java -jar -Dserver.port=8090 consumer-0.0.1.jar

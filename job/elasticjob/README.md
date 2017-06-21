## 简单的boot配置
# 涉及spring注入, jetty(tomcat端口配置)
# 不涉及数据库，缓存等配置
* mvn clean package -Dmaven.test.skip=true -U

# 命令启动应用程序
#  不指定加载，使用默认的配置文件路径
* java -jar target/micros_job_spring-0.0.1-SNAPSHOT.jar

# 指定服务对外端口号
* java -jar -Dserver.port=8090 target/micros_job_spring-0.0.1-SNAPSHOT.jar

## Elastic-Job
# 作业暂停(Pause)与作业失效(Disable)的区别
作业暂停和失效都会停止当前节点作业的运行。但作业暂停和恢复不会触发重分片，而作业失效和生效将触发重分片。
# Elastic-Job 2.0.0版本API
作业的Spring命名空间属性regCenter变更为registry-center-ref

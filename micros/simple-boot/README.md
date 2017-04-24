## 简单的boot配置
# 涉及spring注入, jetty(tomcat端口配置)
# 不涉及数据库，缓存等配置
* mvn clean install

# 命令启动应用程序
* java -jar -Dserver.port=8090 target/micros_simple_boot-1.5.3.RELEASE.jar
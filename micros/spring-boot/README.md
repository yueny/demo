## 简单的boot配置
# 涉及spring注入, jetty(tomcat端口配置)
# 不涉及数据库，缓存等配置
* mvn clean install

# 命令启动应用程序
#  不指定加载，使用默认的配置文件路径
* java -jar target/micros_spring_boot-1.5.3.RELEASE.jar

#  指定加载的配置文件路径 -Dspring.config.location
* java -jar -Dspring.config.location=classpath:/properties/*.yml target/micros_spring_boot-1.5.3.RELEASE.jar
# 指定服务对外端口号
* java -jar -Dserver.port=8090 target/micros_spring_boot-1.5.3.RELEASE.jar
# 
* java -jar -Dserver.port=8090 -Dspring.config.location=classpath:/application.yml,classpath:/properties/default.yml target/micros_spring_boot-1.5.3.RELEASE.jar
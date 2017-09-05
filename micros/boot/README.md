## 简单的boot配置
# 涉及spring注入, jetty(tomcat端口配置)
* mvn clean package -Dmaven.test.skip=true -U

# 命令启动应用程序
#  不指定加载，使用默认的配置文件路径
* java -jar target/micros_spring_boot-1.5.3.RELEASE.jar

#  指定加载的配置文件路径 -Dspring.config.location
* java -jar -Dspring.config.location=classpath:/properties/*.yml target/micros_spring_boot-1.5.3.RELEASE.jar
# 指定服务对外端口号
* java -jar -Dserver.port=8090 target/micros_spring_boot-1.5.3.RELEASE.jar
# 
* java -jar -Dserver.port=8090 -Dspring.config.location=classpath:/application.yml,classpath:/properties/default.yml target/micros_spring_boot-1.5.3.RELEASE.jar

## RxJava
# 四个基本概念：Observable (可观察者，即被观察者)、 Observer (观察者)、 subscribe (订阅)、事件。实质上就是观察者模式的基本概念。被观察者通过subscribe()方法与观察者实现订阅关系，之后一有事件发出就通知观察者。
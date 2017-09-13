## 简单的boot配置
# 涉及spring注入, jetty(tomcat端口配置)
# 不涉及数据库，缓存等配置
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
RxJava 2.0 最核心的是Publisher和Subscriber。Publisher可以发出一系列的事件，而Subscriber负责和处理这些事件

可以在 Publisher 中查询数据库或者从网络上获取数据，然后在 Subscriber 中显示。
Publisher 不只有一种，事实上 Flowable 和 Processor 所有的子类都属于 Publisher。
在数据发射途中，你可以利用操作符对数据进行变换。

Schedulers.io() 代表io操作的线程, 通常用于网络,读写文件等io密集型的操作
Schedulers.computation() 代表CPU计算密集型的操作, 例如需要大量计算的操作
Schedulers.newThread() 代表一个常规的新线程
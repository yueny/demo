## configserver 配置中心项目

启动服务
访问配置信息的URL与配置文件的映射关系如下：
    # /{application}/{profile}[/{label}]
    http://localhost:8889/configserver/soter/master 主配置
    http://localhost:8889/configserver/soter/qa/master 主配置+qa配置
    
    # /{application}-{profile}.yml
    http://localhost:8889/configserver/soter-qa.yml qa文件
    
    # /{label}/{application}-{profile}.yml
    http://localhost:8889/configserver/master/soter-qa.yml qa文件
    http://localhost:8889/configserver/master/soter-qa.yml master分支的 qa文件
    
    # /{application}-{profile}.properties
    http://localhost:8889/configserver/soter-qa.properties properties格式的配置文件
    
    # /{label}/{application}-{profile}.properties
    http://localhost:8889/configserver/master/soter-qa.properties master分支的properties格式的配置文件

上面的url会映射{application}-{profile}.properties对应的配置文件，其中{label}对应Git上不同的分支，默认为master。
我们可以尝试构造不同的url来访问不同的配置内容，比如，要访问master分支，config-client应用的dev环境，就可以访问这个url：http://localhost:1201/config-client/dev/master
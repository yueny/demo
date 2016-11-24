## metrics-spring
https://github.com/ryantenney/metrics-spring

这个库为Spring增加了Metric库， 提供基于XML或者注解方式。
    可以使用注解创建metric和代理类。 
    @Timed, 
    @Metered, 
    @ExceptionMetered, 
    @Counted
    为注解了 @Gauge 和 @CachedGauge的bean注册Gauge
    为@Metric注解的字段自动装配
    注册HealthCheck
    通过XML配置产生报表
    通过XML注册metric和metric组


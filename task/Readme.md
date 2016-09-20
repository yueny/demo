## task
【秒】   【分】  【时】   【日】  【月】   【周】  【年】  
"0 0 12 * * ?"    每天中午十二点触发 
"0 15 10 ? * *"    每天早上10：15触发 
"0 15 10 * * ?"    每天早上10：15触发 
"0 15 10 * * ? *"    每天早上10：15触发 
"0 15 10 * * ? 2005"    2005年的每天早上10：15触发 
"0 * 14 * * ?"    每天从下午2点开始到2点59分每分钟一次触发 
"0 0/5 14 * * ?"    每天从下午2点开始到2：55分结束每5分钟一次触发 
"0 0/5 14,18 * * ?"    每天的下午2点至2：55和6点至6点55分两个时间段内每5分钟一次触发 
"0 0-5 14 * * ?"    每天14:00至14:05每分钟一次触发 
"0 10,44 14 ? 3 WED"    三月的每周三的14：10和14：44触发 
"0 15 10 ? * MON-FRI"    每个周一、周二、周三、周四、周五的10：15触发 

====================== quartz.properties =================================
org.quartz.threadPool.threadCount
配置线程池的容量，即表示同时最多可运行的线程数量。在生产环境，此参数应根据实际情况配置。
org.quartz.jobStore.class=org.quartz.impl.jdbcjobstore.JobStoreTX
将任务持久化到数据中。因为集群中节点依赖于数据库来传播Scheduler实例的状态，你只能在使用JDBC JobStore时应用Quartz集群。
org.quartz.jobStore.isClustered=true
通知Scheduler实例要它参与到一个集群当中。
org.quartz.jobStore.clusterCheckinInterval=15000
属性定义了Scheduler实例检入到数据库中的频率(单位：毫秒)。Scheduler检查是否其他的实例到了它们应当检入的时候未检入；这能指出一个失败的Scheduler实例，且当前 Scheduler会以此来接管任何执行失败并可恢复的Job。通过检入操作，Scheduler 也会更新自身的状态记录。clusterChedkinInterval越小，Scheduler节点检查失败的Scheduler实例就越频繁。默认值是 15000 (即15 秒)。

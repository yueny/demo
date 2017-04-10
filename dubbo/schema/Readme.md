# Maven
<dependency>
	<groupId>com.yueny.demo.dubbo</groupId>
	<artifactId>dubbo-schema</artifactId>
	<version>1.0.1-SNAPSHOT</version>
</dependency>
#
. 设计配置属性和JavaBean
. 编写XSD文件
. 编写NamespaceHandler和BeanDefinitionParser完成解析工作
. 编写spring.handlers和spring.schemas串联起所有部件.
这两个文件的地址必须是META-INF/spring.handlers和META-INF/spring.schemas，spring会默认去载入它们
eg：
spring.handlers:
http\://blog.codealy.com/schema/lock=com.yueny.demo.dubbo.config.schema.LockNamespaceHandler
spring.schemas: http\://blog.codealy.com/schema/lock/lock.xsd=META-INF/lock.xsd
. 在Bean文件中应用

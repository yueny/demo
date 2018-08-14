package com.yueny.demo.dubbo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Spring Boot 应用启动类
 *
 * Created by yueny on 2018/7/18 0018.
 */
// Spring Boot 应用的标识
@SpringBootApplication
public class ServerApplication {
    /**
     * 日志记录器
     */
    private static final Logger logger = LoggerFactory.getLogger(ServerApplication.class);

    public static void main(final String[] args) {
        try {
            SpringApplication.run(ServerApplication.class, args);
        } catch (final Exception e) {
            logger.error("服务启动异常:", e);
            e.printStackTrace();
        }
    }
    
}

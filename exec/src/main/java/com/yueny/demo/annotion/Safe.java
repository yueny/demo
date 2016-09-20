package com.yueny.demo.annotion;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.stereotype.Component;

/**
 * 安全的
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2016年1月27日 下午10:35:26
 *
 */
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface Safe {

}
